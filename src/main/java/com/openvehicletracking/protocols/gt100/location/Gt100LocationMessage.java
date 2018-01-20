package com.openvehicletracking.protocols.gt100.location;

import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.protocol.LocationMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class Gt100LocationMessage implements LocationMessage {

    @Override
    public double getLatitude() {
        return 0;
    }

    @Override
    public double getLongitude() {
        return 0;
    }

    @Override
    public double getSpeed() {
        return 0;
    }

    @Override
    public double getDirection() {
        return 0;
    }

    @Override
    public double getAccuracy() {
        return 0;
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
