package net.motodev.device.lprotocol;


import net.motodev.core.GpsStatus;
import net.motodev.core.message.Message;
import net.motodev.device.DeviceConstants;
import net.motodev.device.XTakip;
import net.motodev.device.XTakipStatus;

import java.util.Date;

/**
 * Created by oksuz on 19/05/2017.
 */
public class LProtocolMessage implements Message {

    private transient String header;
    private String deviceId;
    private Date datetime;
    private GpsStatus gpsStatus;
    private double latitude;
    private double longitude;
    private XTakipStatus status;
    private double speed;
    private double distance;
    private String direction;
    private int alarm;
    private String additional;
    private String rawMessage;

    @Override
    public String device() {
        return XTakip.NAME;
    }

    @Override
    public String deviceId() {
        return this.deviceId;
    }

    @Override
    public boolean isCommand() {
        return false;
    }

    @Override
    public String type() {
        return DeviceConstants.MESSAGE_TYPE_L;
    }

    @Override
    public Date messageDate() {
        return getDatetime();
    }

    @Override
    public String requestId() {
        return null;
    }

    @Override
    public String[] extraParameters() {
        return null;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setGpsStatus(GpsStatus gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStatus(XTakipStatus status) {
        this.status = status;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getHeader() {
        return header;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public GpsStatus getGpsStatus() {
        return gpsStatus;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public XTakipStatus getStatus() {
        return status;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public String getDirection() {
        return direction;
    }

    public int getAlarm() {
        return alarm;
    }

    public String getAdditional() {
        return additional;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }
}
