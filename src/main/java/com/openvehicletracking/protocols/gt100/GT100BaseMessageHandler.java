package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.BaseMessageHandler;

import java.nio.ByteBuffer;

abstract public class GT100BaseMessageHandler extends BaseMessageHandler {

    protected abstract Message handle(ByteBuffer msg, ConnectionHolder<?> connectionHolder);

    protected boolean isDeviceMessage(ByteBuffer msg, byte type) {
        return isMatch(msg, (short) 0x7878, type);
    }

    protected boolean isCommandReply(ByteBuffer msg, byte type) {
        return isMatch(msg, (short) 0x7979, type);
    }

    private boolean isMatch(ByteBuffer msg, short header, byte type) {
        short head = msg.getShort();
        byte length = msg.get();
        byte protocol = msg.get();

        return header == head && type == protocol;
    }

    @Override
    public Message handle(Object msg, ConnectionHolder<?> connectionHolder) {
        ByteBuffer buffer = convertToByteBuffer(msg);
        return buffer != null ? handle(buffer, connectionHolder) : null;
    }

    protected String toHex(byte[] in) {
        StringBuilder builder = new StringBuilder();
        for (byte anIn : in) {
            builder.append(String.format("%02x", anIn));
        }

        return builder.toString();
    }
}
