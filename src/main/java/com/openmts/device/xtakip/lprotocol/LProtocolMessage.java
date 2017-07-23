package com.openmts.device.xtakip.lprotocol;


import com.openmts.device.xtakip.DeviceConstants;
import com.openmts.device.xtakip.XTakip;
import com.openmts.device.xtakip.XTakipStatus;
import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.core.message.LocationMessage;
import io.vertx.core.json.JsonArray;

import java.util.Optional;

/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class LProtocolMessage implements LocationMessage {

    /*
    interface fields
     */
    private double latitude;
    private double longitude;
    private double speed;
    private double direction;
    private long datetime;
    private GpsStatus status;
    private String deviceId;

    // device
    private int alarm;
    private XTakipStatus deviceState;
    private double distance;
    private String additional;
    private String rawMessage;

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
    public GpsStatus getStatus() {
        return status;
    }

    @Override
    public Optional<String> getRequestId() {
        return Optional.empty();
    }

    @Override
    public String getDevice() {
        return deviceId;
    }

    @Override
    public String getDeviceId() {
        return XTakip.NAME;
    }

    @Override
    public Optional<String> getType() {
        return Optional.of(DeviceConstants.MESSAGE_TYPE_L);
    }

    @Override
    public boolean isCommand() {
        return false;
    }

    @Override
    public long getDatetime() {
        return datetime;
    }

    @Override
    public Optional<JsonArray> getExtraParameters() {
        return Optional.empty();
    }

    public int getAlarm() {
        return alarm;
    }

    public XTakipStatus getDeviceState() {
        return deviceState;
    }

    public double getDistance() {
        return distance;
    }

    public String getAdditional() {
        return additional;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    // setters


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public void setStatus(GpsStatus status) {
        this.status = status;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public void setDeviceState(XTakipStatus deviceState) {
        this.deviceState = deviceState;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }
}
