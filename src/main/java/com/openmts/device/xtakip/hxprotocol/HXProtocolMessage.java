package com.openmts.device.xtakip.hxprotocol;


import com.openmts.core.message.Message;
import com.openmts.device.xtakip.DeviceConstants;
import com.openmts.device.xtakip.XTakip;

/**
 * Created by oksuz on 20/05/2017.
 */
public class HXProtocolMessage implements Message {

    private String deviceId;
    private long datetime;
    private String requestId;
    private String[] params;

    @Override
    public String device() {
        return XTakip.NAME;
    }

    @Override
    public String deviceId() {
        return getDeviceId();
    }

    @Override
    public boolean isCommand() {
        return true;
    }

    @Override
    public String type() {
        return DeviceConstants.MESSAGE_TYPE_HX;
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

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getRequestId() {
        return requestId;
    }

    public String[] getParams() {
        return params;
    }

}
