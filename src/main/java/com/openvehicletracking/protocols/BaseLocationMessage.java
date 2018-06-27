package com.openvehicletracking.protocols;

import com.openvehicletracking.core.*;
import com.openvehicletracking.core.exception.StateCreateNotSupportException;
import com.openvehicletracking.core.protocol.LocationMessage;
import com.openvehicletracking.core.protocol.Message;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

abstract public class BaseLocationMessage implements LocationMessage {

    protected final Position position;
    protected final long datetime;
    protected final HashMap<String, Object> attributes;
    protected final GpsStatus gpsStatus;
    protected final Object raw;

    public BaseLocationMessage(AbstractLocationMessageBuilder<?> builder) {
        this.position = builder.getPosition();
        this.datetime = builder.getDate().getTime();
        this.attributes = builder.getAttributes();
        this.gpsStatus = builder.getGpsStatus();
        this.raw = builder.getRaw();
    }

    @Override
    public DeviceState createState() throws StateCreateNotSupportException {
        Optional<HashMap<String, Object>> attrs = this.getAttributes();
        if (!attrs.isPresent()) {
            return null;
        }

        DeviceState state = new DeviceState();
        HashMap<String, Object> attributes = attrs.get();

        Object ignKeyOn = attributes.get(Message.ATTR_IGN_KEY_ON);

        state.setCreatedAt(new Date().getTime());
        state.setUpdatedAt(new Date().getTime());
        state.setDeviceDate(getDate().getTime());
        state.setDeviceStatus(DeviceStatus.ONLINE);
        if (ignKeyOn != null) {
            state.setVehicleStatus((boolean)ignKeyOn ? VehicleStatus.MOVING : VehicleStatus.PARKED);
        }
        state.setGpsStatus(getStatus());
        state.setPosition(getPosition());
        attributes.forEach(state::addAttribute);

        return state;
    }
}
