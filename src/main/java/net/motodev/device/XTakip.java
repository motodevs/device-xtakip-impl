package net.motodev.device;

import net.motodev.core.Device;
import net.motodev.core.MessageHandler;
import net.motodev.device.HXProtocol.HXProtocolMessageHandler;
import net.motodev.device.LProtocol.LProtocolMessageHandler;
import net.motodev.device.OXProtocol.OXProtocolMessageHandler;

import java.util.Vector;


/**
 * Created by oksuz on 19/05/2017.
 */
public class XTakip implements Device {

    public static final String NAME = "xtakip";

    private final Vector<MessageHandler> messageHandlers = new Vector<>();

    public String name() {
        return NAME;
    }

    public Vector<MessageHandler> handlers() {
        messageHandlers.add(new LProtocolMessageHandler());
        messageHandlers.add(new HXProtocolMessageHandler());
        messageHandlers.add(new OXProtocolMessageHandler());
        return messageHandlers;
    }


    @Override
    public String periodicMessageSubject() {
        return DeviceConstants.PERIODIC_MESSAGE;
    }
}
