package com.openvehicletracking.device.xtakip.oxprotocol;


import com.openvehicletracking.device.xtakip.DeviceConstants;
import com.openvehicletracking.device.xtakip.XTakip;
import com.openvehicletracking.core.message.Message;
import io.vertx.core.json.JsonArray;

import java.util.Arrays;
import java.util.Optional;

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
    public Optional<String> getRequestId() {
        return Optional.of(requestId);
    }

    @Override
    public String getDevice() {
        return XTakip.NAME;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public Optional<String> getType() {
        return Optional.of(DeviceConstants.MESSAGE_TYPE_OX);
    }

    @Override
    public boolean isCommand() {
        return true;
    }

    @Override
    public long getDatetime() {
        return datetime;
    }

    @Override
    public Optional<JsonArray> getExtraParameters() {
        return Optional.of(new JsonArray(Arrays.asList(this.params)));
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

    @Override
    public String toString() {
        return "OXProtocolMessage{" +
                "deviceId='" + deviceId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", params=" + Arrays.toString(params) +
                ", datetime=" + datetime +
                '}';
    }
}
