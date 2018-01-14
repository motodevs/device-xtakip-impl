package com.openvehicletracking.protocols.xtakip.lprotocol;


import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.xtakip.BaseMessageHandler;

/**
 * Created by oksuz on 19/05/2017.
 */
public class LProtocolMessageHandler extends BaseMessageHandler {

    @Override
    public boolean isMatch(Object msg) {
        return isMatch(convertToString(msg), "^@L.*!$");
    }

    @Override
    protected Message handle(String msg, ConnectionHolder<?> connectionHolder) {
        return new LProtocolParser(msg).parse();
    }
}
