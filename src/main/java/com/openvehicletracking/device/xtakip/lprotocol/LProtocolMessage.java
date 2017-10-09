package com.openvehicletracking.device.xtakip.lprotocol;


import com.google.gson.JsonArray;
import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.core.message.LocationMessage;
import com.openvehicletracking.device.xtakip.DeviceConstants;
import com.openvehicletracking.device.xtakip.XTakip;
import com.openvehicletracking.device.xtakip.XTakipStatus;

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

    // json serialization
    private final String device = XTakip.NAME;
    private final String type = DeviceConstants.MESSAGE_TYPE_L;

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
        return device;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public Optional<String> getType() {
        return Optional.of(type);
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

    @Override
    public boolean isReplyRequired() {
        return true;
    }

    @Override
    public String toString() {
        return "LProtocolMessage{" +
                "rawMessage='" + rawMessage + '\'' +
                '}';
    }
}
