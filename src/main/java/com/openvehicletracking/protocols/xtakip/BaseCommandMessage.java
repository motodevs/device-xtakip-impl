package com.openvehicletracking.protocols.xtakip;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.json.GsonFactory;
import com.openvehicletracking.core.protocol.Command;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

abstract public class BaseCommandMessage implements Command {

    private String raw;
    private String requestId;
    private Device device;
    private long datetime;

    private HashMap<String, Object> attributes = new HashMap<>();

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setDeviceId(String deviceId) {
        this.device = new XTakip(deviceId);
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime.getTime();
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Optional<String> getRequestId() {
        return Optional.of(requestId);
    }

    @Override
    public <T> void send(ConnectionHolder<T> connectionHolder) {

    }

    @Override
    public Object getRaw() {
        return raw;
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
}
