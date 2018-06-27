package com.openvehicletracking.protocols.xtakip;


import com.openvehicletracking.core.*;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.xtakip.lprotocol.LProtocolMessage;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class XTakip implements Device {

    private final String name = XTakip.class.getSimpleName();
    private String deviceId;
    private transient ConnectionHolder<?> connectionHolder;

    public XTakip(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String getName() {
        return name;
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

