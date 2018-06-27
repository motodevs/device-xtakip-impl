package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.*;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.BaseLocationMessage;

import java.util.Date;
import java.util.HashMap;

public class GT100Device implements Device {

    public static final String NAME = "GT100Device";
    private String deviceId;
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
    public void addConnection(ConnectionHolder<?> connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public ConnectionHolder<?> getConnection() {
        return connectionHolder;
    }
}
