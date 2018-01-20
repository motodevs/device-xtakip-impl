package com.openvehicletracking.protocols.xtakip;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.BaseMessageHandler;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

abstract public class XTakipBaseMessageHandler extends BaseMessageHandler {

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

}
