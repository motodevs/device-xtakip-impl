package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.protocol.MessageHandler;
import com.openvehicletracking.core.protocol.MessagingProtocol;
import com.openvehicletracking.protocols.gt100.alert.AlertMessageHandler;
import com.openvehicletracking.protocols.gt100.heartbeat.HeartbeatMessageHandler;
import com.openvehicletracking.protocols.gt100.location.LocationMessageHandler;
import com.openvehicletracking.protocols.gt100.login.LoginMessageHandler;

import java.util.ArrayList;

public class Gt100Protocol implements MessagingProtocol {

    private final ArrayList<MessageHandler> handlers = new ArrayList<>();
    public static final String NAME = "GT100";
    public static int responseIndex;
    public Gt100Protocol() {
        handlers.add(new LoginMessageHandler());
        handlers.add(new LocationMessageHandler());
        handlers.add(new AlertMessageHandler());
        handlers.add(new HeartbeatMessageHandler());
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ArrayList<MessageHandler> getHandlers() {
        return handlers;
    }

    public static int incrementAndGetMessageIndex() {
        return ++responseIndex;
    }
}
