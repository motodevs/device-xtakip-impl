package net.motodev.device;


import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import net.motodev.core.DeviceStatus;
import net.motodev.core.alarm.Alarm;
import net.motodev.core.db.DeviceQueryHelper;
import net.motodev.device.lprotocol.LProtocolMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Created by yo on 22/06/2017.
 *
 */
public class MovingAlarmGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingAlarmGenerator.class);
    private static final long FIVE_MIN_IN_MILIS = MILLISECONDS.convert(5, MINUTES);

    private LProtocolMessage message;
    private DeviceQueryHelper deviceQueryHelper;
    private Handler<Alarm> alarmHandler;


    public MovingAlarmGenerator(LProtocolMessage message, DeviceQueryHelper deviceQueryHelper, Handler<Alarm> alarmHandler) {
        this.message = message;
        this.deviceQueryHelper = deviceQueryHelper;
        this.alarmHandler = alarmHandler;
    }

    public void create() {
        deviceQueryHelper.readMeta(metaHandler(metaSuccessHandler()));
    }

    private Handler<Object> metaHandler(Handler<JsonObject> metaSuccessHandler) {
        return listOfDeviceMeta -> {

            if (listOfDeviceMeta == null || ((List) listOfDeviceMeta).size() == 0) {
                LOGGER.info("device status unknown");
                return;
            }

            JsonObject meta = (JsonObject) ((List) listOfDeviceMeta).get(0);
            metaSuccessHandler.handle(meta);
        };
    }


    private Handler<JsonObject> metaSuccessHandler() {
        return meta -> {
            DeviceStatus status;
            try {
                status = DeviceStatus.valueOf(meta.getString("status"));
            } catch (Exception e) {
                LOGGER.error("device status not found in meta", e);
                return;
            }

            if ((status == DeviceStatus.CONNECTION_LOST || status == DeviceStatus.PARKED) && message.getStatus().getIgnitiKeyOff() != null && message.getStatus().getIgnitiKeyOff()) {
                deviceQueryHelper.readAlarms(readAlarmHandler(), 1);
            }
        };
    }

    private Handler<List<Alarm>> readAlarmHandler() {
        return alarms -> {
            if (alarms.size() == 0) {
                alarmHandler.handle(getMovingAlarm(message));
                return;
            }

            Alarm lastAlarm = alarms.get(0);
            HashMap<Object, Object> extra = lastAlarm.extraData();

            if (new Date().getTime() - lastAlarm.datetime() > FIVE_MIN_IN_MILIS && extra.containsKey("xTakipAlarmId") && (int)extra.get("xTakipAlarmId") != DeviceConstants.ALARM_MOVING_ID) {
                alarmHandler.handle(getMovingAlarm(message));
                return;
            }

            if (extra.containsKey("xTakipAlarmId") && (int)extra.get("xTakipAlarmId") != DeviceConstants.ALARM_MOVING_ID && message.getDistance() - (double)extra.get("distance") > 1) {
                alarmHandler.handle(getMovingAlarm(message));
            }

        };

    }

    public Alarm getMovingAlarm(LProtocolMessage message) {
        HashMap<Object, Object> extra = new HashMap<>();
        extra.put("xTakipAlarmId", DeviceConstants.ALARM_MOVING_ID);
        extra.put("distance", message.getDistance());
        return new Alarm(message.getDeviceId(), DeviceConstants.ALARM_MOVING_DESCRIPTION, DeviceConstants.CRITICAL_ALARM_ACTION, message.datetime(), extra);
    }
}
