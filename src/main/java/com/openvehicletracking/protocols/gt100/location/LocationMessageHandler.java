package com.openvehicletracking.protocols.gt100.location;


import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.GT100BaseMessageHandler;

import java.nio.ByteBuffer;

public class LocationMessageHandler extends GT100BaseMessageHandler {

    @Override
    protected Message handle(ByteBuffer msg, ConnectionHolder<?> connectionHolder) {
        LocationMessageParser parser = new LocationMessageParser(msg);
        GT100LocationMessage message = (GT100LocationMessage) parser.parse();
        if (message.getDevice() != null) {
            message.getDevice().addConnection(connectionHolder);
        }
        return message;
    }

    @Override
    public boolean isMatch(Object msg) {
        ByteBuffer buffer = convertToByteBuffer(msg);
        return buffer != null && isDeviceMessage(buffer, (byte) 0x22);
    }
}

