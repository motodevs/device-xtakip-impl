package net.motodev.device.oxprotocol;


import net.motodev.core.message.Message;
import net.motodev.device.DeviceConstants;
import net.motodev.device.XTakip;

/**
 * Created by oksuz on 20/05/2017.
 *
 */

public class OXProtocolMessage implements Message {

    private String deviceId;
    private String requestId;
    private String[] params;
    private long datetime;

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
        return true;
    }

    @Override
    public String type() {
        return DeviceConstants.MESSAGE_TYPE_OX;
    }

    @Override
    public long datetime() {
        return getDatetime();
    }

    @Override
    public String requestId() {
        return getRequestId();
    }

    @Override
    public String[] extraParameters() {
        return getParams();
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String[] getParams() {
        return params;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getDatetime() {
        return datetime;
    }
}
