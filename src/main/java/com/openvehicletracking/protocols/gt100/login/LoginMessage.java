package com.openvehicletracking.protocols.gt100.login;

import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.Gt100Protocol;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class LoginMessage implements Message {

    private final Device device;
    private final byte[] raw;
    public static final int TYPE = 0x01;

    public LoginMessage(Device device, byte[] raw) {
        this.device = device;
        this.raw = raw;
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
        return new Date();
    }

    @Override
    public Optional<HashMap<String, Object>> getAttributes() {
        return Optional.empty();
    }

    @Override
    public void reply(Reply reply) {

    }

    @Override
    public String getProtocolName() {
        return Gt100Protocol.NAME;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public String asJson() {
        return toString();
    }
}
