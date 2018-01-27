package com.openvehicletracking.protocols.gt100.heartbeat;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.GT100BaseMessageHandler;
import java.nio.ByteBuffer;

public class HeartbeatMessageHandler extends GT100BaseMessageHandler {

    @Override
    protected Message handle(ByteBuffer msg, ConnectionHolder<?> connectionHolder) {

        ByteBuffer reply = ByteBuffer.allocate(10);
        reply.put((byte) 0x78)
            .put((byte) 0x78)
            .put((byte) 0x05)
            .put((byte) 0x13)
            .put((byte) 0x00)
            .put((byte) 0x01)
            .putShort((short) 0xE9F1)
            .put((byte) 0x0D)
            .put((byte) 0x0A);

        connectionHolder.write(new Reply(reply.array()));
        HeartbeatMessageParser heartbeatMessageParser = new HeartbeatMessageParser(msg);
        return heartbeatMessageParser.parse();
    }

    @Override
    public boolean isMatch(Object msg) {
        ByteBuffer buffer = convertToByteBuffer(msg);
        return buffer != null && isDeviceMessage(buffer, (byte) 0x13);
    }
}
