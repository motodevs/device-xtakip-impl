package com.openvehicletracking.protocols.xtakip.hxprotocol;

import com.openvehicletracking.core.protocol.Parser;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by oksuz on 20/05/2017.
 */
public class HXProtocolParser implements Parser {

    private String message;

    public HXProtocolParser(String s) {
        message = s;
    }

    @Override
    public HXProtocolMessage parse() {
        String message = getMessage();
        message = message.replace("!", "").replace("@", "");
        String[] splittedMsg = message.split(";");

        HashMap<String, Object> extraParameters = new HashMap<>();
        extraParameters.put("param1", splittedMsg[2]);
        extraParameters.put("param2", splittedMsg[3]);
        extraParameters.put("param3", splittedMsg[4]);
        extraParameters.put("param4", splittedMsg[5]);

        HXProtocolMessage m = new HXProtocolMessage();
        m.setDeviceId(splittedMsg[1]);
        m.setRequestId(splittedMsg[6]);
        m.setAttributes(extraParameters);
        m.setDatetime(new Date());

        return m;
    }

    private String getMessage() {
        return message;
    }
}
