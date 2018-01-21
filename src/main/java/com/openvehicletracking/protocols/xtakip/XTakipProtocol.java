package com.openvehicletracking.protocols.xtakip;


import com.openvehicletracking.core.protocol.MessageHandler;
import com.openvehicletracking.core.protocol.MessagingProtocol;
import com.openvehicletracking.protocols.xtakip.hxprotocol.HXProtocolMessageHandler;
import com.openvehicletracking.protocols.xtakip.lprotocol.LProtocolMessageHandler;
import com.openvehicletracking.protocols.xtakip.oxprotocol.OXProtocolMessageHandler;

import java.util.ArrayList;

public class XTakipProtocol implements MessagingProtocol {

    private ArrayList<MessageHandler> handlers = new ArrayList<>();

    public XTakipProtocol() {
        handlers.add(new LProtocolMessageHandler());
        handlers.add(new HXProtocolMessageHandler());
        handlers.add(new OXProtocolMessageHandler());
    }

    @Override
    public String getName() {
        return XTakip.class.getName();
    }

    @Override
    public ArrayList<MessageHandler> getHandlers() {
        return handlers;
    }
}
