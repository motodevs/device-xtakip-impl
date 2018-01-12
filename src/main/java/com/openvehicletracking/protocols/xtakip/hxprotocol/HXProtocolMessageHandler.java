package com.openvehicletracking.protocols.xtakip.hxprotocol;


import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.MessageHandler;

import java.util.regex.Pattern;

/**
 * Created by oksuz on 20/05/2017.
 *
 */
public class HXProtocolMessageHandler implements MessageHandler {

    @Override
    public boolean isMatch(Object msg) {
        if (msg != null) {
            String message = (String) msg;
            return Pattern.compile("^@HX;\\d+;.*;.*;.*;.*;.*!$").matcher(message).matches();
        }

        return false;
    }

    @Override
    public Message handle(Object msg) {
        return new HXProtocolParser((String) msg).parse();
    }

}
