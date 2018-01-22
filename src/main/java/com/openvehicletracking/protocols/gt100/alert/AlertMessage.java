package com.openvehicletracking.protocols.gt100.alert;

import com.openvehicletracking.core.*;
import com.openvehicletracking.protocols.BaseLocationMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class AlertMessage extends BaseLocationMessage {

    public AlertMessage(AbstractLocationMessageBuilder<?> builder) {
        super(builder);
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public GpsStatus getStatus() {
        return null;
    }

    @Override
    public Object getRaw() {
        return null;
    }

    @Override
    public Device getDevice() {
        return null;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Optional<HashMap<String, Object>> getAttributes() {
        return null;
    }

    @Override
    public void reply(Reply reply) {

    }

    @Override
    public String asJson() {
        return null;
    }
}
