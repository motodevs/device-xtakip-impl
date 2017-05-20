package net.motodev.device.LProtocol;

import net.motodev.core.Message;
import net.motodev.core.MessageHandler;

import java.util.regex.Pattern;

/**
 * Created by oksuz on 19/05/2017.
 */
public class LProtocolMessageHandler implements MessageHandler<String> {

    public Pattern pattern() {
        return Pattern.compile("^@L.*!$");
    }

    public Message handle(String message) {
        return new LProtocolParser(message).message();
    }
}
