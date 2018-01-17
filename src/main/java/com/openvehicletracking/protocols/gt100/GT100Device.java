package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.DeviceState;

public class GT100Device implements Device {

    public static final String NAME = "GT100Device";
    private String deviceId;
    private DeviceState state = new DeviceState();
    private transient ConnectionHolder<?> connectionHolder;

    public GT100Device(String deviceId) {
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

    @Override
    public void addConnection(ConnectionHolder<?> connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public ConnectionHolder<?> getConnection() {
        return connectionHolder;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }
}
