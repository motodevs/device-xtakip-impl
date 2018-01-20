package com.openvehicletracking.protocols.gt100.location;


import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.Parser;
import com.openvehicletracking.protocols.gt100.GT100BaseMessageHandler;

import java.nio.ByteBuffer;

public class Gt100LocationMessageHandler extends GT100BaseMessageHandler {

    @Override
    protected Message handle(ByteBuffer msg, ConnectionHolder<?> connectionHolder) {
        Parser parser = new GT100LocationMessageParser(msg);
        Gt100LocationMessage message = (Gt100LocationMessage) parser.parse();


        return message;
    }

    @Override
    public boolean isMatch(Object msg) {
        ByteBuffer buffer = convertToByteBuffer(msg);
        return buffer != null && isDeviceMessage(convertToByteBuffer(msg), (byte) 0x22);
    }
}

