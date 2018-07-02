package com.openvehicletracking.protocols.xtakip.hxprotocol;


import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.xtakip.XTakipBaseMessageHandler;

import java.nio.ByteBuffer;

/**
 * Created by oksuz on 20/05/2017.
 *
 */
public class HXProtocolMessageHandler extends XTakipBaseMessageHandler {

    @Override
    protected Message handle(String msg, ConnectionHolder<?> connectionHolder) {
        Message message = new HXProtocolParser(msg).parse();
        if (message != null && message.getDevice() != null) {
            message.getDevice().addConnection(connectionHolder);
            return message;
        }

        return null;
    }

    @Override
    public boolean isMatch(Object msg) {
        return isMatch(convertToString(msg), "^@HX;\\d+;.*;.*;.*;.*;.*!$");
    }

}
