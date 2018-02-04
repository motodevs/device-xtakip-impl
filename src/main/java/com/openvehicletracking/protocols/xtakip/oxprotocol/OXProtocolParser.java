package com.openvehicletracking.protocols.xtakip.oxprotocol;


import com.openvehicletracking.core.protocol.Parser;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by oksuz on 20/05/2017.
 */
public class OXProtocolParser implements Parser {

    private String message;

    public OXProtocolParser(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public OXProtocolMessage parse() {
        OXProtocolMessage m = new OXProtocolMessage();

        String message = getMessage();
        message = message.replace("!", "").replace("@", "");

        String[] splittedMsg = message.split(";");
        HashMap<String, Object> extraParameters = new HashMap<>();
        extraParameters.put("param1", splittedMsg[2]);
        extraParameters.put("param2", splittedMsg[3]);
        extraParameters.put("param3", splittedMsg[4]);

        m.setDeviceId(splittedMsg[1]);
        m.setRequestId(splittedMsg[5]);
        m.setAttributes(extraParameters);
        m.setDatetime(new Date());

        return m;
    }
}
