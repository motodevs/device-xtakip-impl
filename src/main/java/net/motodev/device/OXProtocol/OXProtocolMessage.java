package net.motodev.device.OXProtocol;

import net.motodev.core.Message;
import net.motodev.device.DeviceConstants;
import net.motodev.device.XTakip;

import java.util.Date;

/**
 * Created by oksuz on 20/05/2017.
 *
 */

public class OXProtocolMessage implements Message {

    private String deviceId;
    private String requestId;
    private String[] params;
    private Date datetime;

    @Override
    public String device() {
        return XTakip.NAME;
    }

    @Override
    public String deviceId() {
        return this.deviceId;
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

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getDatetime() {
        return datetime;
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
