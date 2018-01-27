package com.openvehicletracking.protocols.gt100.heartbeat;

import com.openvehicletracking.core.GsmSignalStrength;
import com.openvehicletracking.core.VoltageLevel;
import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.GT100BaseMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Objects;

public class HeartbeatMessageParser extends GT100BaseMessageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatMessageParser.class);


    public HeartbeatMessageParser(ByteBuffer msg) {
        super(msg);
    }

    @Override
    public Message parse() {
        LOGGER.debug("new message {}", createRaw());
        message.position(4); // skip header, length and protocol no
        HashMap<String, Object> terminalInfo = createTerminalInfo(message.get());
        terminalInfo.put(Message.ATTR_VOLTAGE, VoltageLevel.create(message.get()));
        terminalInfo.put(Message.ATTR_GSM_SIGNAL_STRENGHT, GsmSignalStrength.create(message.get()));
        message.get(); // skip alert language
        Alert alert = createAlert(message.get());
        if (terminalInfo.containsKey(Message.ATTR_ALERT)) {
            Alert terminalInfoAlert = (Alert) terminalInfo.get(Message.ATTR_ALERT);
            if (!Objects.equals(terminalInfoAlert.getDescription(), alert.getDescription())) {
                terminalInfo.put(Message.ATTR_ALERT, alert);
                terminalInfo.put(Message.ATTR_ADDITIONAL_ALERT, terminalInfoAlert);
            }
        } else {
            terminalInfo.put(Message.ATTR_ALERT, alert);
        }

        HeartbeatMessage heartbeatMessage = new HeartbeatMessage(createRaw(), terminalInfo);
        LOGGER.debug("Message parsed : {}", heartbeatMessage.asJson());
        return heartbeatMessage;
    }
}
