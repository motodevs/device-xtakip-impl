package com.openvehicletracking.device.xtakip;


import com.google.gson.JsonObject;
import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.DeviceState;
import com.openvehicletracking.core.ResponseAdapter;
import com.openvehicletracking.core.alarm.Alarm;
import com.openvehicletracking.core.geojson.GeoJsonResponse;
import com.openvehicletracking.core.message.*;
import com.openvehicletracking.core.message.exception.UnsupportedReplyTypeException;
import com.openvehicletracking.core.message.impl.StringReplies;
import com.openvehicletracking.core.message.impl.StringReply;
import com.openvehicletracking.device.xtakip.hxprotocol.HXProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessage;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.oxprotocol.OXProtocolMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class XTakip implements Device {

    public static final String NAME = "xtakip";
    private static final Logger LOGGER = LoggerFactory.getLogger(XTakip.class);

    private final CopyOnWriteArrayList<MessageHandler> messageHandlers = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<Integer, XtakipAlarm> alarms = new ConcurrentHashMap<>();

    public XTakip() {
        messageHandlers.addAll(Arrays.asList(new LProtocolMessageHandler(), new HXProtocolMessageHandler(), new OXProtocolMessageHandler()));
        alarms.putAll(Alarms.getAll());
        LOGGER.info("device {} initialized", NAME);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public CopyOnWriteArrayList<MessageHandler> getHandlers() {
        return messageHandlers;
    }

    @Override
    public Alarm generateAlarmFromMessage(Message message) {
        if (!(message instanceof LProtocolMessage)) {
            return null;
        }

        LProtocolMessage lProtocolMessage = (LProtocolMessage) message;
        if (alarms.containsKey(lProtocolMessage.getAlarm())) {
            XtakipAlarm alarm = alarms.get(lProtocolMessage.getAlarm());

            JsonObject extra = new JsonObject();
            extra.addProperty("xTakipAlarmId", lProtocolMessage.getAlarm());
            extra.addProperty("distance", lProtocolMessage.getDistance());

            Alarm deviceAlarm = new Alarm(lProtocolMessage.getDeviceId(), alarm.getDescription(), alarm.getActions(), lProtocolMessage.getDatetime(), extra);
            LOGGER.info("device alarm created {}", deviceAlarm);
            return deviceAlarm;
        }

        return null;
    }

    @Override
    public <T extends Reply> T replyMessage(Message message, List<? extends CommandMessage> unreadMessages, Class<T> tClass) throws UnsupportedReplyTypeException {

        if (unreadMessages == null || unreadMessages.size() == 0) {
            return null;
        }

        if (tClass == StringReply.class) {
            return tClass.cast(new StringReply(unreadMessages.get(0).getCommand()));
        } else if (tClass == StringReplies.class) {
            List<String> stringReplies = unreadMessages.stream().map(CommandMessage::getCommand).collect(Collectors.toList());
            return tClass.cast(new StringReplies(stringReplies));
        }

        throw new UnsupportedReplyTypeException(String.format("ReplyType %s is not supported", tClass.getName()));
    }

    @Override
    public Class<? extends LocationMessage> getLocationType() {
        return LProtocolMessage.class;
    }

    @Override
    public DeviceState createStateFromMessage(Message message) {
        return null;
    }

    @Override
    public GeoJsonResponse responseAsGeoJson(List<? extends LocationMessage> messages) {
        return null;
    }

    @Override
    public ResponseAdapter getResponseAdapter() {
        return null;
    }

}

