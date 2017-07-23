package com.openvehicletracking.device.xtakip.hxprotocol;


import com.openvehicletracking.core.message.Message;
import com.openvehicletracking.core.message.MessageHandler;

import java.util.regex.Pattern;

/**
 * Created by oksuz on 20/05/2017.
 *
 */
public class HXProtocolMessageHandler implements MessageHandler<String> {

    @Override
    public Pattern pattern() {
        return Pattern.compile("^@HX;\\d+;.*;.*;.*;.*;.*!$");
    }

    @Override
    public Message handle(String s) {
        return new HXProtocolParser(s).parse();
    }
}
