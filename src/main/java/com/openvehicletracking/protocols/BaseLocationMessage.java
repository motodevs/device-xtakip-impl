package com.openvehicletracking.protocols;

import com.openvehicletracking.core.AbstractLocationMessageBuilder;
import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.core.Position;
import com.openvehicletracking.core.protocol.LocationMessage;

import java.util.HashMap;

abstract public class BaseLocationMessage implements LocationMessage {

    protected final Position position;
    protected final long datetime;
    protected final HashMap<String, Object> attributes;
    protected final GpsStatus gpsStatus;
    protected final Object raw;
    protected Device device;

    public BaseLocationMessage(AbstractLocationMessageBuilder<?> builder) {
        this.position = builder.getPosition();
        this.datetime = builder.getDate().getTime();
        this.attributes = builder.getAttributes();
        this.gpsStatus = builder.getGpsStatus();
        this.raw = builder.getRaw();
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
