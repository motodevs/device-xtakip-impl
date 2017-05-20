package net.motodev.device.OXProtocol;

import net.motodev.core.Message;
import net.motodev.core.MessageHandler;

import java.util.regex.Pattern;

/**
 * Created by oksuz on 20/05/2017.
 */
public class OXProtocolMessageHandler implements MessageHandler<String> {

    @Override
    public Pattern pattern() {
        return Pattern.compile("^@OX;\\d+;.*;.*;.*;.*!$");
    }

    @Override
    public Message handle(String s) {
        return new OXProtocolParser(s).message();
    }
}
