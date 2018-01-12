package com.openvehicletracking.protocols.xtakip.lprotocol;


import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.MessageHandler;

import java.util.regex.Pattern;


/**
 * Created by oksuz on 19/05/2017.
 */
public class LProtocolMessageHandler implements MessageHandler {

    @Override
    public boolean isMatch(Object msg) {
        if (msg != null) {
            String message = (String) msg;
            return Pattern.compile("^@L.*!$").matcher(message).matches();
        }

        return false;
    }

    @Override
    public Message handle(Object msg) {
        return new LProtocolParser(msg).parse();
    }
}
