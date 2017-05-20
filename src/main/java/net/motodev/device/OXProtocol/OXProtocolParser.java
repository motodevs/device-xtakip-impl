package net.motodev.device.OXProtocol;

import net.motodev.core.Parser;

import java.util.Date;

/**
 * Created by oksuz on 20/05/2017.
 */
public class OXProtocolParser implements Parser {

    String message;

    public OXProtocolParser(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public OXProtocolMessage message() {
        OXProtocolMessage m = new OXProtocolMessage();

        String message = getMessage();
        message = message.replace("!", "").replace("@", "");

        String[] splittedMsg = message.split(";");

        m.setDeviceId(splittedMsg[1]);
        m.setRequestId(splittedMsg[5]);
        m.setParams(new String[]{splittedMsg[2], splittedMsg[3], splittedMsg[4]});
        m.setDatetime(new Date());

        return m;
    }
}
