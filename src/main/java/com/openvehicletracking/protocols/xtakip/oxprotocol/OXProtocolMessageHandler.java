package com.openvehicletracking.protocols.xtakip.oxprotocol;


import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.MessageHandler;

import java.util.regex.Pattern;

/**
 * Created by oksuz on 20/05/2017.
 */
public class OXProtocolMessageHandler implements MessageHandler {

    @Override
    public boolean isMatch(Object msg) {
        if (msg != null) {
            String message = (String) msg;
            return Pattern.compile("^@OX;\\d+;.*;.*;.*;.*!$").matcher(message).matches();
        }

        return false;
    }

    @Override
    public Message handle(Object msg) {
        return new OXProtocolParser((String) msg).parse();
    }



}
