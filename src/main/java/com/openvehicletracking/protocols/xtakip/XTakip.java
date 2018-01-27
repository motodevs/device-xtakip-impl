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
    private DeviceState state;
    private transient ConnectionHolder<?> connectionHolder;

    public XTakip(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String getName() {
        return XTakip.class.getName();
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
    public Device createStateFromMessage(Message message) {
        if (message instanceof LProtocolMessage) {
            LProtocolMessage lProtocolMessage = (LProtocolMessage) message;
            HashMap<String, Object> attributes =lProtocolMessage.getAttributes().get();
            if (attributes.containsKey(XTakipConstants.ATTR_IS_OFFLINE_RECORD)) {
                boolean isOfflineRecord = (boolean) attributes.get(XTakipConstants.ATTR_IS_OFFLINE_RECORD);
                if (isOfflineRecord) {
                    this.state = null;
                    return this;
                }
            }

            boolean ignKeyOff = false;
            if (attributes.containsKey(Message.ATTR_IGN_KEY_ON)) {
                ignKeyOff = (boolean) attributes.get(Message.ATTR_IGN_KEY_ON);
            }

            DeviceState state = new DeviceState();
            state.setCreatedAt(new Date().getTime());
            state.setUpdatedAt(new Date().getTime());
            state.setDeviceDate(lProtocolMessage.getDate().getTime());
            state.setDeviceStatus(DeviceStatus.ONLINE);
            state.setVehicleStatus(!ignKeyOff ? VehicleStatus.MOVING : VehicleStatus.PARKED);
            state.setGpsStatus(lProtocolMessage.getStatus());
            state.setPosition(lProtocolMessage.getPosition());

            if (lProtocolMessage.getAttributes().isPresent()) {
                lProtocolMessage.getAttributes().get().forEach(state::addAttribute);
            }

            this.state = state;
        }
        return this;
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

