package com.openvehicletracking.device.xtakip.hxprotocol;


import com.google.gson.JsonArray;
import com.openvehicletracking.core.message.Parser;

import java.util.Date;

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

        JsonArray extraParameters = new JsonArray();
        extraParameters.add(splittedMsg[2]);
        extraParameters.add(splittedMsg[3]);
        extraParameters.add(splittedMsg[4]);
        extraParameters.add(splittedMsg[5]);

        HXProtocolMessage m = new HXProtocolMessage();
        m.setDeviceId(splittedMsg[1]);
        m.setRequestId(splittedMsg[6]);
        m.setExtraParameters(extraParameters);
        m.setDatetime(new Date().getTime());

        return m;
    }

    private String getMessage() {
        return message;
    }
}
