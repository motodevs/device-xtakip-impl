package net.motodev.device.hxprotocol;

import net.motodev.core.Message;
import net.motodev.device.DeviceConstants;
import net.motodev.device.XTakip;

import java.util.Date;

/**
 * Created by oksuz on 20/05/2017.
 */
public class HXProtocolMessage implements Message {

    private String deviceId;
    private Date datetime;
    private String requestId;
    private String[] params;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getRequestId() {
        return requestId;
    }

    public String[] getParams() {
        return params;
    }

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
        return DeviceConstants.MESSAGE_TYPE_HX;
    }

    @Override
    public Date messageDate() {
        return new Date();
    }

    @Override
    public String requestId() {
        return getRequestId();
    }

    @Override
    public String[] extraParameters() {
        return getParams();
    }
}
