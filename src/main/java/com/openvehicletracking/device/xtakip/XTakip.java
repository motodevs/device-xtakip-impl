package com.openvehicletracking.device.xtakip;


import com.openvehicletracking.core.DeviceState;
import com.openvehicletracking.core.db.CommandDAO;
import com.openvehicletracking.device.xtakip.hxprotocol.HXProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessage;
import com.openvehicletracking.device.xtakip.lprotocol.LProtocolMessageHandler;
import com.openvehicletracking.device.xtakip.oxprotocol.OXProtocolMessageHandler;
import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.DeviceStatus;
import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.core.alarm.Alarm;
import com.openvehicletracking.core.alarm.AlarmAction;
import com.openvehicletracking.core.db.DeviceDAO;
import com.openvehicletracking.core.message.Message;
import com.openvehicletracking.core.message.MessageHandler;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
        messageHandlers.add(new LProtocolMessageHandler());
        messageHandlers.add(new HXProtocolMessageHandler());
        messageHandlers.add(new OXProtocolMessageHandler());
        initializeAlarmMap();
    }

    public String getName() {
        return NAME;
    }

    public CopyOnWriteArrayList<MessageHandler> getHandlers() {
        return messageHandlers;
    }


    @Override
    public void generateAlarmFromMessage(Message message, DeviceDAO deviceQueryHelper, Handler<Alarm> alarmHandler) {
        if (!(message instanceof LProtocolMessage)) {
            return;
        }

        LProtocolMessage lProtocolMessage = (LProtocolMessage) message;
        if (alarms.containsKey(lProtocolMessage.getAlarm())) {
            XtakipAlarm alarm = alarms.get(lProtocolMessage.getAlarm());

            JsonObject extra = new JsonObject();
            extra.put("xTakipAlarmId", lProtocolMessage.getAlarm());
            extra.put("distance", lProtocolMessage.getDistance());

            Alarm deviceAlarm = new Alarm(lProtocolMessage.getDeviceId(), alarm.getDescription(), alarm.getActions(), lProtocolMessage.getDatetime(), extra);

            LOGGER.info("device alarm created {}", deviceAlarm);
            alarmHandler.handle(deviceAlarm);
            return;
        }


        MovingAlarmGenerator movingAlarmGenerator = new MovingAlarmGenerator(lProtocolMessage, deviceQueryHelper, alarmHandler);
        movingAlarmGenerator.create();
    }

    @Override
    public void updateMeta(Message message, DeviceDAO deviceQueryHelper) {
        if (!(message instanceof LProtocolMessage)) {
            return;
        }

        LProtocolMessage lProtocolMessage = (LProtocolMessage) message;

        DeviceState state = new DeviceState();

        state.setDeviceId(lProtocolMessage.getDeviceId());
        state.setDistance(lProtocolMessage.getDistance());
        state.setDirection(lProtocolMessage.getDirection());
        state.setSpeed(lProtocolMessage.getSpeed());
        state.setGpsStatus(lProtocolMessage.getStatus());
        state.setCreatedAt(new Date().getTime());
        state.setUpdatedAt(new Date().getTime());
        state.setDeviceDate(lProtocolMessage.getDatetime());


        if (lProtocolMessage.getDeviceState().getInvalidRTC() != null) {
            state.setInvalidDeviceDate(lProtocolMessage.getDeviceState().getInvalidRTC());
        }

        if (lProtocolMessage.getDeviceState().getIgnitiKeyOff() != null) {
            state.setIgnitionKeyOff(lProtocolMessage.getDeviceState().getIgnitiKeyOff());
        }

        if (lProtocolMessage.getAlarm() == 13 ||
                (lProtocolMessage.getDeviceState().getIgnitiKeyOff() != null && lProtocolMessage.getDeviceState().getIgnitiKeyOff())) {
            state.setDeviceStatus(DeviceStatus.PARKED);
        }

        if (lProtocolMessage.getAlarm() == 12 ||
                (lProtocolMessage.getDeviceState().getIgnitiKeyOff() != null && !lProtocolMessage.getDeviceState().getIgnitiKeyOff())) {
            state.setDeviceStatus(DeviceStatus.MOVING);
        }

        if (lProtocolMessage.getStatus() != GpsStatus.NO_DATA) {
            state.setLatitude(lProtocolMessage.getLatitude());
            state.setLongitude(lProtocolMessage.getLongitude());
        }

        JsonObject stateJson = state.toJson();
        LOGGER.debug("device meta created {}", stateJson.toString());
        if (state.getDeviceStatus() != null) {
            deviceQueryHelper.upsertMeta(state, new JsonObject());
            LOGGER.info("device meta updated {}", stateJson.toString());
        }
    }


    private void initializeAlarmMap() {
        alarms.put(1, new XtakipAlarm(1, "Noktaya giriş/çıkış yapıldı", Collections.singletonList(AlarmAction.INFO)));
        alarms.put(2, new XtakipAlarm(2, "RFID kart okundu", Arrays.asList(AlarmAction.INFO)));
        alarms.put(3, new XtakipAlarm(3, "Maksimum hız limit aşıldı", Arrays.asList(AlarmAction.INFO, AlarmAction.SEND_NOTIFICATION)));
        alarms.put(4, new XtakipAlarm(4, "Maksimum bekleme süresi aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(5, new XtakipAlarm(5, "Hızlanma ivme limiti aşıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(6, new XtakipAlarm(6, "Yavaşlama ivme limiti aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(7, new XtakipAlarm(7, "Akü bağlantısı kesildi.", Arrays.asList(AlarmAction.SEND_SMS, AlarmAction.SEND_NOTIFICATION)));
        alarms.put(8, new XtakipAlarm(8, "Giriş2 alarm tipi 1 oluştu.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(9, new XtakipAlarm(9, "Giriş2 alarm tipi 2 oluştu.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(10, new XtakipAlarm(10, "Giriş1 alarm tipi 1 oluştu.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(11, new XtakipAlarm(11, "Giriş1 alarm tipi 2 oluştu.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(12, new XtakipAlarm(12, "Kontak açıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS)));
        alarms.put(13, new XtakipAlarm(13, "Kontak kapatıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(14, new XtakipAlarm(14, "Yurtdışına çıkıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_EMAIL)));
        alarms.put(15, new XtakipAlarm(15, "Yurtiçine girildi.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(16, new XtakipAlarm(16, "Sensör 1 max. sıcaklık aşıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(17, new XtakipAlarm(17, "Sensör 1 min. Sıcaklık aşıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(18, new XtakipAlarm(18, "Giriş3 alarm tipi 1 oluştu.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(19, new XtakipAlarm(19, "Giriş3 alarm tipi 2 oluştu.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(20, new XtakipAlarm(20, "Yakıt seviyesi uyarısı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(21, new XtakipAlarm(21, "GPS alınamıyor.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(22, new XtakipAlarm(22, "Rölanti süresi aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(23, new XtakipAlarm(23, "RequestID uyarısı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(24, new XtakipAlarm(24, "Taksimetre uyarısı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(25, new XtakipAlarm(25, "Darbe girişi1 için limit aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(26, new XtakipAlarm(26, "Darbe girişi2 için limit aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(27, new XtakipAlarm(27, "Darbe girişi3 için limit aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(28, new XtakipAlarm(28, "Düşük hız limit alarmı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(29, new XtakipAlarm(29, "Sensör 2 max. sıcaklık aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(30, new XtakipAlarm(30, "Sensör 2 min. Sıcaklık aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(31, new XtakipAlarm(31, "Sensör 3 max. sıcaklık aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(32, new XtakipAlarm(32, "Sensör 3 min. Sıcaklık aşıldı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(33, new XtakipAlarm(33, "Açı alarmı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(34, new XtakipAlarm(34, "Transparan mod alarmı.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(35, new XtakipAlarm(35, "Düşük hız bitti.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(36, new XtakipAlarm(36, "Yüksek hız bitti.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(37, new XtakipAlarm(37, "Rolanti Bitti.", Arrays.asList(AlarmAction.INFO)));
        alarms.put(38, new XtakipAlarm(38, "Acil Durum.", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS)));
        alarms.put(39, new XtakipAlarm(39, "IO Expander Alarm", Arrays.asList(AlarmAction.INFO)));
        alarms.put(40, new XtakipAlarm(40, "G sensör x yön alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(41, new XtakipAlarm(41, "G sensör y yön alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(42, new XtakipAlarm(42, "G sensör z yön alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(46, new XtakipAlarm(46, "Hassas Durak Alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(47, new XtakipAlarm(47, "Genel Darbe Giris Alarmı", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(48, new XtakipAlarm(48, "Genel Giris Alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(49, new XtakipAlarm(49, "Mobileye Alarm", Arrays.asList(AlarmAction.INFO)));
        alarms.put(50, new XtakipAlarm(50, "M50S Darbe/Kaza/İvme Alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(55, new XtakipAlarm(55, "G sensor Kasis Alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(56, new XtakipAlarm(56, "G sensor Savrulma Alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(57, new XtakipAlarm(57, "G sensor Hizlanma Ivme Alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(58, new XtakipAlarm(58, "G sensor Yavaş. Ivme Alarmı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(59, new XtakipAlarm(59, "Düşük batarya", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS)));
        alarms.put(60, new XtakipAlarm(60, "Dolu batarya", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(61, new XtakipAlarm(61, "Araç çekilme uyarısı", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS)));
        alarms.put(62, new XtakipAlarm(62, "Cihaz kapatıldı", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS)));
        alarms.put(63, new XtakipAlarm(63, "Cihaz harekete başladı", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS)));
        alarms.put(64, new XtakipAlarm(64, "Cihaz hareketi bitti.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(65, new XtakipAlarm(65, "Geçerli RFID Kart", Arrays.asList(AlarmAction.INFO)));
        alarms.put(66, new XtakipAlarm(66, "Geçersiz RFID Kart", Arrays.asList(AlarmAction.INFO)));
        alarms.put(67, new XtakipAlarm(67, "SIM kapağı açıldı", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS)));
        alarms.put(68, new XtakipAlarm(68, "Düşük nem oranı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(69, new XtakipAlarm(69, "Yüksek nem oranı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(70, new XtakipAlarm(70, "Düşük sıcaklık(Nem sensörü dahili verisi)", Arrays.asList(AlarmAction.INFO)));
        alarms.put(71, new XtakipAlarm(71, "Yüksek sıcaklık(Nem sensörü dahili verisi)", Arrays.asList(AlarmAction.INFO)));
        alarms.put(72, new XtakipAlarm(72, "Geçerli iButton ID", Arrays.asList(AlarmAction.INFO)));
        alarms.put(73, new XtakipAlarm(73, "Geçersiz iButton ID", Arrays.asList(AlarmAction.INFO)));
        alarms.put(74, new XtakipAlarm(74, "Jamming Alarm", Arrays.asList(AlarmAction.SEND_SMS, AlarmAction.SEND_NOTIFICATION)));
        alarms.put(75, new XtakipAlarm(75, "Akü bağlantısı takıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));
        alarms.put(76, new XtakipAlarm(76, "Serbest düşüş uyarısı", Arrays.asList(AlarmAction.INFO)));
        alarms.put(77, new XtakipAlarm(77, "Sistem yeniden başlatıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION)));

    }

    @Override
    public void replyMessage(Message message, CommandDAO commandDAO, Handler<JsonArray> handler) {
        commandDAO.getUnread(commands -> {
            if (null != commands && commands.size() > 0) {
                LOGGER.info("replying message: {}, replies: {}", message, commands);
                handler.handle(commands);
            }
        });
    }
}
