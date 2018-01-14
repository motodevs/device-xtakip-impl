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
    private transient ConnectionHolder<?> connectionHolder;

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

