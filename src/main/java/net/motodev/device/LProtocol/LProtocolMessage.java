package net.motodev.device.LProtocol;

import net.motodev.core.Message;
import net.motodev.device.DeviceConstants;
import net.motodev.device.XTakip;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oksuz on 19/05/2017.
 */
public class LProtocolMessage implements Message {

    private transient String header;
    private String deviceId;
    private Date datetime;
    private int gpsStatus;
    private double latitude;
    private double longitude;
    private Map<String, Boolean> status = new HashMap<>();
    private double speed;
    private double distance;
    private String direction;
    private int alarm;
    private String additional;
    private String rawMessage;
    private String rawStatus;

    @Override
    public String device() {
        return XTakip.NAME;
    }

    @Override
    public String deviceId() {
        return this.deviceId;
    }

    private final transient StatusDefinition[] statusDefinitions = new StatusDefinition[]{
            StatusDefinition.INPUT_1_ACTIVE,
            StatusDefinition.INPUT_2_ACTIVE,
            StatusDefinition.INPUT_3_ACTIVE,
            StatusDefinition.IGNITION_KEY_OFF,
            StatusDefinition.BATTERY_CUTTED,
            StatusDefinition.OUTPUT_1_ACTIVE,
            StatusDefinition.OUTPUT_2_ACTIVE,
            StatusDefinition.OUTPUT_3_ACTIVE,
            StatusDefinition.OUT_OF_TEMPRATURE_LIMIT,
            StatusDefinition.OUT_OF_SPEED_LIMIT,
            StatusDefinition.GPRS_OPENED_ON_OVERSEAS,
            StatusDefinition.DELTA_DISTANCE_OPENED,
            StatusDefinition.OFFLINE_RECORD,
            StatusDefinition.INVALID_RTC,
            StatusDefinition.ENGINE_STOP_ACTIVE,
            StatusDefinition.MAX_STOP_RESUMING,
            StatusDefinition.IDLE_STATUS_RESUMING,
            StatusDefinition.G_SENSOR_ALARM_RESUMING,
            StatusDefinition.INPUT_4_ACTIVE,
            StatusDefinition.INPUT_5_ACTIVE,
            StatusDefinition.EXTERNAL_POWER_CUT
    };

    public enum StatusDefinition {
        INPUT_1_ACTIVE,
        INPUT_2_ACTIVE,
        INPUT_3_ACTIVE,
        IGNITION_KEY_OFF,
        BATTERY_CUTTED,
        OUTPUT_1_ACTIVE,
        OUTPUT_2_ACTIVE,
        OUTPUT_3_ACTIVE,
        OUT_OF_TEMPRATURE_LIMIT,
        OUT_OF_SPEED_LIMIT,
        GPRS_OPENED_ON_OVERSEAS,
        DELTA_DISTANCE_OPENED,
        OFFLINE_RECORD,
        INVALID_RTC,
        ENGINE_STOP_ACTIVE,
        MAX_STOP_RESUMING,
        IDLE_STATUS_RESUMING,
        G_SENSOR_ALARM_RESUMING,
        INPUT_4_ACTIVE,
        INPUT_5_ACTIVE,
        EXTERNAL_POWER_CUT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
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

    public void setGpsStatus(int gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStatus(String status) {
        this.rawStatus = status;
        int convertedStatus = Integer.parseInt(status);
        String bits = Integer.toString(convertedStatus, 2);
        bits = new StringBuilder(bits).reverse().toString();
        byte[] bytes = bits.getBytes();
        byte t = 49; // 1
        for (int i = 0; i < bytes.length; i++) {
            if (statusDefinitions.length > i) { // check array size
                this.status.put(statusDefinitions[i].toString(), bytes[i] == t);
            }
        }
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

    public int getGpsStatus() {
        return gpsStatus;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Map<String, Boolean> getStatus() {
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

    public String getRawStatus() {
        return rawStatus;
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
}
