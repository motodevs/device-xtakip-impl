package com.openvehicletracking.protocols.gt100.login;

import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.DeviceState;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.exception.StateCreateNotSupportException;
import com.openvehicletracking.core.json.GsonFactory;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.Gt100Protocol;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class LoginMessage implements Message {

    private final Device device;
    private final String raw;
    public static final int TYPE = 0x01;

    public LoginMessage(Device device, String raw) {
        this.device = device;
        this.raw = raw;
    }

    @Override
    public Object getRaw() {
        return raw;
    }

    @Nullable
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
        return GsonFactory.getGson().toJson(this);
    }

    @Override
    public DeviceState createState() throws StateCreateNotSupportException {
        throw new StateCreateNotSupportException("HeartbeatMessage doesn't support to creating state");
    }
}
