package net.motodev.device.HXProtocol;

import net.motodev.core.Parser;

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
    public HXProtocolMessage message() {
        String message = getMessage();
        message = message.replace("!", "").replace("@", "");
        String[] splittedMsg = message.split(";");

        HXProtocolMessage m = new HXProtocolMessage();
        m.setDeviceId(splittedMsg[1]);
        m.setRequestId(splittedMsg[6]);
        m.setParams(new String[]{splittedMsg[2], splittedMsg[3], splittedMsg[4], splittedMsg[5]});
        m.setDatetime(new Date());

        return m;
    }

    private String getMessage() {
        return message;
    }
}
