package com.openvehicletracking.protocols.xtakip.lprotocol;


import com.openvehicletracking.core.*;
import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.alert.AlertImp;
import com.openvehicletracking.core.json.GsonFactory;
import com.openvehicletracking.core.protocol.LocationMessage;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.xtakip.XTakipAlert;
import com.openvehicletracking.protocols.xtakip.XTakipAlerts;
import com.openvehicletracking.protocols.xtakip.XTakipConstants;
import com.openvehicletracking.protocols.xtakip.XTakip;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class LProtocolMessage implements LocationMessage {

    private final double latitude;
    private final double longitude;
    private final double speed;
    private final double direction;
    private final double accuracy;
    private final long datetime;
    private final GpsStatus gpsStatus;
    private final XTakip device;
    private final HashMap<String, Object> attributes;

    private final String rawMessage;
    private final double distance;
    private final Alert alert;
    private final String messageType = XTakipConstants.MESSAGE_TYPE_L;

    private LProtocolMessage(LProtocolMessageBuilder builder) {
        latitude = builder.latitude;
        longitude = builder.longitude;
        speed = builder.speed;
        direction = builder.direction;
        gpsStatus = builder.status;
        device = new XTakip(builder.deviceId);
        datetime = builder.datetime.getTime();
        attributes = builder.attributes;
        rawMessage = builder.rawMessage;
        distance = builder.distance;
        alert = builder.alert;
        accuracy = builder.accuracy;
        createState();
    }

    private void createState() {

        if (attributes.containsKey(XTakipConstants.ATTR_IS_OFFLINE_RECORD)) {
            boolean isOfflineRecord = (boolean) attributes.get(XTakipConstants.ATTR_IS_OFFLINE_RECORD);
            if (isOfflineRecord) {
                device.setState(null);
                return;
            }
        }

        boolean ignKeyOff = false;
        if (attributes.containsKey(XTakipConstants.ATTR_IS_IGNITION_KEY_OFF)) {
            ignKeyOff = (boolean) attributes.get(XTakipConstants.ATTR_IS_IGNITION_KEY_OFF);
        }


        DeviceState state = new DeviceState();
        state.setCreatedAt(new Date().getTime());
        state.setUpdatedAt(new Date().getTime());
        state.setDeviceDate(datetime);
        state.setDeviceStatus(DeviceStatus.ONLINE);
        state.setVehicleStatus(ignKeyOff ? VehicleStatus.MOVING : VehicleStatus.PARKED);
        state.setGpsStatus(gpsStatus);
        state.setLatitude(latitude);
        state.setLongitude(longitude);
        state.setSpeed(speed);
        state.setDirection(direction);
        state.addAttribute("distance", distance);
        state.addAttribute("ignKeyOff", ignKeyOff);
        state.addAttribute("accuracy", accuracy);
        device.setState(state);
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public double getAccuracy() {
        return accuracy;
    }

    @Override
    public GpsStatus getStatus() {
        return gpsStatus;
    }

    @Override
    public Object getRaw() {
        return rawMessage;
    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public Date getDate() {
        return new Date(datetime);
    }

    @Override
    public Optional<HashMap<String, Object>> getAttributes() {
        return Optional.of(attributes);
    }

    @Override
    public void reply(Reply reply) {

    }

    @Override
    public String asJson() {
        return GsonFactory.getGson().toJson(this);
    }

    public  static class LProtocolMessageBuilder {
        private double latitude;
        private double longitude;
        private double speed;
        private double direction;
        private double accuracy;
        private Date datetime;
        private GpsStatus status;
        private String deviceId;
        private HashMap<String, Object> attributes = new HashMap<>();

        private String rawMessage;
        private double distance;
        private Alert alert;


        public LProtocolMessageBuilder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public LProtocolMessageBuilder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public LProtocolMessageBuilder setSpeed(double speed) {
            this.speed = speed;
            return this;
        }

        public LProtocolMessageBuilder setDirection(double direction) {
            this.direction = direction;
            return this;
        }

        public LProtocolMessageBuilder setDatetime(Date datetime) {
            this.datetime = datetime;
            return this;
        }

        public LProtocolMessageBuilder setStatus(GpsStatus status) {
            this.status = status;
            return this;
        }

        public LProtocolMessageBuilder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public LProtocolMessageBuilder addAttribute(String key, Object val) {
            this.attributes.putIfAbsent(key, val);
            return this;
        }

        public LProtocolMessageBuilder setRawMessage(String rawMessage) {
            this.rawMessage = rawMessage;
            return this;
        }

        public LProtocolMessageBuilder setDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public LProtocolMessageBuilder setAlert(int alertId) {
            Alert alert = createAlertFromId(alertId, datetime);
            if (alert != null) {
                attributes.put(Message.ATTR_ALERT, alert);
            }

            return this;
        }

        public LProtocolMessageBuilder setAccuracy(double accuracy) {
            this.accuracy = accuracy;
            return this;
        }

        public LProtocolMessage build() {
            return new LProtocolMessage(this);
        }

        Alert createAlertFromId(int alert, Date date) {
            if (XTakipAlerts.getAll().containsKey(alert)) {
                XTakipAlert xTakipAlert = XTakipAlerts.getAll().get(alert);

                AlertImp.AlertBuilder alertBuilder = new AlertImp.AlertBuilder()
                        .id(alert)
                        .description(xTakipAlert.getDesciption())
                        .date(date);
                xTakipAlert.getActions().forEach(alertBuilder::action);

                return alertBuilder.build();
            }
            return null;
        }
    }
}
