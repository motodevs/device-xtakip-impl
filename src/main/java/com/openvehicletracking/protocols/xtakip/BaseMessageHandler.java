package com.openvehicletracking.protocols.xtakip;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.MessageHandler;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

public abstract class BaseMessageHandler implements MessageHandler {

    protected abstract Message handle(String msg, ConnectionHolder<?> connectionHolder);

    protected boolean isMatch(@Nullable String message, String regex) {
        return message != null && Pattern.compile(regex).matcher(message).matches();
    }

    @Override
    public Message handle(Object msg, ConnectionHolder<?> connectionHolder) {
        String message = convertToString(msg);
        if (message != null) {
            return handle(message, connectionHolder);
        }

        return null;
    }

    @Nullable
    protected String convertToString(Object msg) {
        if (msg instanceof byte[]) {
            return new String((byte[]) msg);
        }

        return null;
    }
}
