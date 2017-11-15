package com.openvehicletracking.device.xtakip;


import com.google.gson.JsonObject;
import com.openvehicletracking.core.*;
import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.exception.UnsupportedMessageTypeException;
import com.openvehicletracking.core.geojson.GeoJsonResponse;
import com.openvehicletracking.core.message.*;
import com.openvehicletracking.core.message.exception.UnsupportedReplyTypeException;
import com.openvehicletracking.core.message.impl.ReplyImpl;
import com.openvehicletracking.device.xtakip.hxprotocol.HXProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessage;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.oxprotocol.OXProtocolMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

        Boolean ignKeyOff = lProtocolMessage.getDeviceState().getIgnitiKeyOff();

        DeviceState state = new DeviceState();
        state.setDeviceId(lProtocolMessage.getDeviceId());
        state.setDistance(lProtocolMessage.getDistance());
        state.setCreatedAt(new Date().getTime());
        state.setUpdatedAt(new Date().getTime());
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
        return null;
    }

    @Override
    public ResponseAdapter getResponseAdapter() {
        return null;
    }

}

