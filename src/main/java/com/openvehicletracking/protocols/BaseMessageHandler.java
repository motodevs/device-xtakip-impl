package com.openvehicletracking.protocols;


import com.openvehicletracking.core.protocol.MessageHandler;
import javax.annotation.Nullable;
import java.nio.ByteBuffer;

public abstract class BaseMessageHandler implements MessageHandler {


    @Nullable
    protected String convertToString(Object msg) {
        return isByteArray(msg) ? createStringFromBytes((byte[]) msg) : null;
    }

    @Nullable
    protected ByteBuffer convertToByteBuffer(Object msg) {
        return isByteArray(msg) ? createByteBufferFromBytes((byte[]) msg) : null;
    }

    protected boolean isByteArray(Object msg) {
        return msg instanceof byte[];
    }

    protected String createStringFromBytes(byte[] msg) {
        return new String(msg);
    }

    protected ByteBuffer createByteBufferFromBytes(byte[] msg) {
        return ByteBuffer.wrap(msg);
    }
}
