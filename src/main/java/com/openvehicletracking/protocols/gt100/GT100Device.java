package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.*;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.BaseLocationMessage;

import java.util.Date;
import java.util.HashMap;

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
    public Device createStateFromMessage(Message message) {
        if (message instanceof BaseLocationMessage) {
            BaseLocationMessage baseLocationMessage = (BaseLocationMessage) message;
            HashMap<String, Object> attributes = baseLocationMessage.getAttributes().get();
            boolean ignKeyOn = (boolean) attributes.get(Message.ATTR_IGN_KEY_ON);

            state.setCreatedAt(new Date().getTime());
            state.setUpdatedAt(new Date().getTime());
            state.setDeviceDate(baseLocationMessage.getDate().getTime());
            state.setDeviceStatus(DeviceStatus.ONLINE);
            state.setVehicleStatus(ignKeyOn ? VehicleStatus.MOVING : VehicleStatus.PARKED);
            state.setGpsStatus(baseLocationMessage.getStatus());
            state.setPosition(baseLocationMessage.getPosition());
            if (baseLocationMessage.getAttributes().isPresent()) {
                baseLocationMessage.getAttributes().get().forEach(state::addAttribute);
            }
            baseLocationMessage.setDevice(this);
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
