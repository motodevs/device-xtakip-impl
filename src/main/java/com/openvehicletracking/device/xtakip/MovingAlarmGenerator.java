package com.openvehicletracking.device.xtakip;


/**
 * Created by yo on 22/06/2017.
 *
 */
public class MovingAlarmGenerator {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MovingAlarmGenerator.class);
//    private static final long FIVE_MIN_IN_MILIS = MILLISECONDS.convert(5, MINUTES);
//
//    private LProtocolMessage message;
//    private DeviceDAO deviceDAO;
//    private Handler<Alarm> alarmHandler;
//
//
//    public MovingAlarmGenerator(LProtocolMessage message, DeviceDAO deviceDAO, Handler<Alarm> alarmHandler) {
//        this.message = message;
//        this.deviceDAO = deviceDAO;
//        this.alarmHandler = alarmHandler;
//    }
//
//    public void create() {
//        deviceDAO.readMeta(metaHandler(metaSuccessHandler()));
//    }
//
//    private Handler<JsonObject> metaHandler(Handler<JsonObject> metaSuccessHandler) {
//        return deviceMeta -> {
//
//            if (deviceMeta == null) {
//                LOGGER.info("device status unknown");
//                return;
//            }
//
//            metaSuccessHandler.handle(deviceMeta);
//        };
//    }
//
//    private Handler<JsonObject> metaSuccessHandler() {
//        return meta -> {
//            DeviceState state = DeviceState.fromJson(meta);
//
//            if ((state.getDeviceStatus() == DeviceStatus.CONNECTION_LOST || state.getDeviceStatus() == DeviceStatus.PARKED) && message.getDeviceState().getIgnitiKeyOff() != null && message.getDeviceState().getIgnitiKeyOff()) {
//                LOGGER.info("device connection lost or device parked and state ignition key off");
//                deviceDAO.readAlarms(readAlarmHandler(), 1);
//            }
//        };
//    }
//
//    private Handler<List<Alarm>> readAlarmHandler() {
//        return alarms -> {
//            if (alarms.size() == 0) {
//                LOGGER.info("empty alarm list generating moving alarm");
//                alarmHandler.handle(getMovingAlarm(message));
//                return;
//            }
//
//            Alarm lastAlarm = alarms.get(0);
//            JsonObject extra = lastAlarm.getExtraData();
//
//            double xtakipAlarmId = extra.getDouble("xTakipAlarmId");
//            double movingAlarmId = (double) DeviceConstants.ALARM_MOVING_ID;
//            double timeDiff = new Date().getTime() - lastAlarm.getDatetime();
//
//            if (timeDiff > FIVE_MIN_IN_MILIS && extra.containsKey("xTakipAlarmId") && xtakipAlarmId != movingAlarmId) {
//                LOGGER.info("time diff over five min ({}ms) and last alert is not movingAlert. generating moving alarm");
//                alarmHandler.handle(getMovingAlarm(message));
//                return;
//            }
//
//            if (extra.containsKey("xTakipAlarmId") && xtakipAlarmId == movingAlarmId && message.getDistance() - extra.getDouble("distance") > 1) {
//                LOGGER.info("last alarm is moving alarm and device moved over 1km. generating moving alarm");
//                alarmHandler.handle(getMovingAlarm(message));
//            }
//
//        };
//
//    }
//
//    public Alarm getMovingAlarm(LProtocolMessage message) {
//        JsonObject extra = new JsonObject();
//        extra.put("xTakipAlarmId", DeviceConstants.ALARM_MOVING_ID);
//        extra.put("distance", message.getDistance());
//        return new Alarm(message.getDeviceId(), DeviceConstants.ALARM_MOVING_DESCRIPTION, DeviceConstants.CRITICAL_ALARM_ACTION, message.getDatetime(), extra);
//    }
}
