package com.openvehicletracking.device.xtakip.oxprotocol;


import com.google.gson.JsonArray;
import com.openvehicletracking.core.message.Parser;

import java.util.Date;

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
        JsonArray extraParameters = new JsonArray();
        extraParameters.add(splittedMsg[2]);
        extraParameters.add(splittedMsg[3]);
        extraParameters.add(splittedMsg[4]);


        m.setDeviceId(splittedMsg[1]);
        m.setRequestId(splittedMsg[5]);
        m.setExtraParameters(extraParameters);
        m.setDatetime(new Date().getTime());

        return m;
    }
}
