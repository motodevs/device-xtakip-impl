package com.openvehicletracking.device.xtakip;


import com.google.gson.JsonObject;
import com.openvehicletracking.core.*;
import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.exception.UnsupportedMessageTypeException;
import com.openvehicletracking.core.geojson.*;
import com.openvehicletracking.core.message.*;
import com.openvehicletracking.core.message.exception.UnsupportedReplyTypeException;
import com.openvehicletracking.core.message.impl.ReplyImpl;
import com.openvehicletracking.device.xtakip.hxprotocol.HXProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessage;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.oxprotocol.OXProtocolMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class XTakip implements Device {

    public static final String NAME = "xtakip";
    private static final Logger LOGGER = LoggerFactory.getLogger(XTakip.class);

    private final CopyOnWriteArrayList<MessageHandler> messageHandlers = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<Integer, XtakipAlert> alerts = new ConcurrentHashMap<>();

    public XTakip() {
        messageHandlers.addAll(Arrays.asList(new LProtocolMessageHandler(), new HXProtocolMessageHandler(), new OXProtocolMessageHandler()));
        alerts.putAll(Alarms.getAll());
        LOGGER.info("device {} initialized", NAME);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public CopyOnWriteArrayList<MessageHandler> getHandlers() {
        return messageHandlers;
    }

    @Override
    public Alert generateAlertFromMessage(Message message) {
        if (message.getClass() != getLocationType()) {
            return null;
        }

        LProtocolMessage lProtocolMessage = (LProtocolMessage) message;
        if (alerts.containsKey(lProtocolMessage.getAlert())) {
            XtakipAlert alert = alerts.get(lProtocolMessage.getAlert());

            JsonObject extra = new JsonObject();
            extra.addProperty("xTakipAlertId", lProtocolMessage.getAlert());
            extra.addProperty("distance", lProtocolMessage.getDistance());

            Alert deviceAlarm = new Alert(lProtocolMessage.getDeviceId(), alert.getDescription(), alert.getActions(), lProtocolMessage.getDatetime(), extra);
            LOGGER.info("device alarm created {}", deviceAlarm);
            return deviceAlarm;
        }

        return null;
    }

    @Override
    public <T> Reply<T> replyMessage(Message message, List<? extends CommandMessage> unreadMessages) throws UnsupportedReplyTypeException {
        if (unreadMessages == null || unreadMessages.size() == 0) {
            return null;
        }

        List<T> replies = unreadMessages.stream().map(CommandMessage<T>::getCommand).collect(Collectors.toList());
        return new ReplyImpl<>(replies);
    }


    @Override
    public Class<? extends LocationMessage> getLocationType() {
        return LProtocolMessage.class;
    }

    @Override
    public DeviceState createStateFromMessage(Message message) throws UnsupportedMessageTypeException {
        if (message.getClass() != getLocationType()) {
            throw new UnsupportedMessageTypeException("Message type should be " + getLocationType().getCanonicalName());
        }


        LProtocolMessage lProtocolMessage = (LProtocolMessage) message;
        if (lProtocolMessage.getStatus() == GpsStatus.NO_DATA) {
            return null;
        }

        Boolean isOfflineRecord = lProtocolMessage.getDeviceState().getOfflineRecord();
        if (isOfflineRecord == Boolean.TRUE) {
            return null;
        }

        Boolean ignKeyOff = lProtocolMessage.getDeviceState().getIgnitiKeyOff();

        Date now = new Date();
        DeviceState state = new DeviceState();
        state.setDeviceId(lProtocolMessage.getDeviceId());
        state.setDistance(lProtocolMessage.getDistance());
        state.setCreatedAt(now.getTime());
        state.setUpdatedAt(now.getTime());
        state.setDeviceDate(lProtocolMessage.getDatetime());
        state.setLatitude(lProtocolMessage.getLatitude());
        state.setLongitude(lProtocolMessage.getLongitude());
        state.setDirection(lProtocolMessage.getDirection());
        state.setIgnitionKeyOff(ignKeyOff == Boolean.TRUE);
        state.setInvalidDeviceDate(lProtocolMessage.getDeviceState().getInvalidRTC() == Boolean.TRUE);
        state.setGpsStatus(lProtocolMessage.getStatus());
        state.setSpeed(lProtocolMessage.getSpeed());

        switch (lProtocolMessage.getAlert()) {
            case DeviceConstants.IGN_KEY_OFF_ALARM_ID:
                state.setDeviceStatus(DeviceStatus.PARKED);
                break;
            case DeviceConstants.IGN_KEY_ON_ALARM_ID:
                state.setDeviceStatus(DeviceStatus.MOVING);
                break;
            default:
                if (lProtocolMessage.getSpeed() != 0 || ignKeyOff != null) {
                    state.setDeviceStatus((ignKeyOff == Boolean.TRUE ? DeviceStatus.PARKED : DeviceStatus.MOVING));
                } else {
                    state.setDeviceStatus(DeviceStatus.CONNECTION_LOST);
                }
        }

        return state;
    }

    @Override
    public GeoJsonResponse responseAsGeoJson(List<? extends LocationMessage> messages) {
        Objects.requireNonNull(messages, "messages cannot be null");
        final GeoJsonResponse geoJsonResponse = new GeoJsonResponse();

        LineStringGeometry lineStringGeometry = new LineStringGeometry();
        PointGeometry marker;
        Boolean ignKeyOff = null;
        String startColor = getRgbColor();
        String endColor = startColor;
        int startId = 1;
        int endId = startId;
        for (LocationMessage message : messages) {
            LProtocolMessage m = (LProtocolMessage) message;

            if (m.getDeviceState().getOfflineRecord() == Boolean.TRUE) {
                continue;
            }

            Boolean currIgnKeyOff = m.getDeviceState().getIgnitiKeyOff();
            // ignition key status changed
            if (!Objects.equals(ignKeyOff, currIgnKeyOff)) {
                // create marker for ignition status change location
                marker = new PointGeometry();
                marker.addPoint(createPoint(m));

                // if ignition key on, that means vehicle starting to move
                if (currIgnKeyOff == Boolean.FALSE) {
                    // add marker
                    geoJsonResponse.addFeature(createFeatureForPoint(startId, startColor, marker, false));

                    // configure end color, and end id
                    endColor = startColor;
                    endId = startId;

                    // create new color and id for new start
                    startColor = getRgbColor();
                    startId++;
                } else if (currIgnKeyOff == Boolean.TRUE) {
                    // vehicle stopped use configured endId and end color here
                    geoJsonResponse.addFeature(createFeatureForPoint(endId, endColor, marker, true));
                }

                if (lineStringGeometry.getCoordinates().size() > 1) {
                    // put collected lines as feature with end color and endId
                    geoJsonResponse.addFeature(createFeature(endId, endColor, lineStringGeometry));
                    // create new lineString for new start
                    lineStringGeometry = new LineStringGeometry();
                }

            }

            lineStringGeometry.addPoint(createPoint(m));
            ignKeyOff = currIgnKeyOff;
        }



        return geoJsonResponse;
    }

    private Feature createFeatureForPoint(int id, String color, PointGeometry geo, boolean ignKeyOff) {
        List<GeoJsonProperty> properties = new ArrayList<>();
        properties.add(new GeoJsonProperty("marker-color", color));
        properties.add(new GeoJsonProperty("marker-size", "small"));
        properties.add(new GeoJsonProperty("marker-symbol", ignKeyOff ? "parking" : "rocket"));
        properties.add(new GeoJsonProperty("id", String.valueOf(id)));
        return new Feature(id, properties, geo);
    }

    private Feature createFeature(int id, String color, Geometry geometry) {
        List<GeoJsonProperty> properties = new ArrayList<>();
        properties.add(new GeoJsonProperty("stroke", color));
        properties.add(new GeoJsonProperty("id", String.valueOf(id)));
        return new Feature(id, properties, geometry);
    }

    @Override
    public ResponseAdapter getResponseAdapter() {
        return null;
    }


    private Point createPoint(LProtocolMessage message) {
        return new Point(message.getLatitude(), message.getLongitude());
    }


    private String getRgbColor() {
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(256);
        int green = randomGenerator.nextInt(256);
        int blue = randomGenerator.nextInt(256);
        return String.format("#%02x%02x%02x", red, green, blue);
    }

}

