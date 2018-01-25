package com.openvehicletracking.protocols.gt100.heartbeat;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.GT100BaseMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class HeartbeatMessageHandler extends GT100BaseMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatMessageHandler.class);

    @Override
    protected Message handle(ByteBuffer msg, ConnectionHolder<?> connectionHolder) {

        ByteBuffer reply = ByteBuffer.allocate(10);
        reply.put((byte) 0x78)
            .put((byte) 0x78)
            .put((byte) 0x05)
            .put((byte) 0x13)
            .put((byte) 0x00)
            .put((byte) 0x01)
            .put((byte) 0xD9)
            .put((byte) 0xDC)
            .put((byte) 0x0D)
            .put((byte) 0x0A);

        LOGGER.info("writing reply to heartbeat");
        connectionHolder.write(new Reply(reply.array()));
        return null;
    }

    @Override
    public boolean isMatch(Object msg) {
        ByteBuffer buffer = convertToByteBuffer(msg);
        return buffer != null && isDeviceMessage(buffer, (byte) 0x13);
    }
}
