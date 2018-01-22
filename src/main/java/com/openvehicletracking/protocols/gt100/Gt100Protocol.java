package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.protocol.MessageHandler;
import com.openvehicletracking.core.protocol.MessagingProtocol;
import com.openvehicletracking.protocols.gt100.location.LocationMessageHandler;
import com.openvehicletracking.protocols.gt100.login.LoginMessageHandler;

import java.util.ArrayList;

public class Gt100Protocol implements MessagingProtocol {

    private final ArrayList<MessageHandler> handlers = new ArrayList<>();

    public Gt100Protocol() {
        handlers.add(new LoginMessageHandler());
        handlers.add(new LocationMessageHandler());
    }

    @Override
    public String getName() {
        return "gt100";
    }

    @Override
    public ArrayList<MessageHandler> getHandlers() {
        return handlers;
    }
}
