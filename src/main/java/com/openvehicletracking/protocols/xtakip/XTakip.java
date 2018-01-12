package com.openvehicletracking.protocols.xtakip;


import com.openvehicletracking.core.*;

/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class XTakip implements Device {

    public static final String NAME = "xtakip";

    private String deviceId;
    private DeviceState state;
    public XTakip(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getId() {
        return deviceId;
    }

    @Override
    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }
}

