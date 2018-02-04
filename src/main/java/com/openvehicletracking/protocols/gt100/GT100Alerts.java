package com.openvehicletracking.protocols.gt100;

import java.util.HashMap;

public class GT100Alerts {

    public static final byte TERMINAL_INFO_ALARM_SHOCK = 0x08;
    public static final byte TERMINAL_INFO_ALARM_POWER_CUT = 0x0A;
    public static final byte TERMINAL_INFO_ALARM_LOW_BATTERY = 0x18;
    public static final byte TERMINAL_INFO_ALARM_SOS = 0x20;

    private static final HashMap<Byte, GT100Alert> alerts = new HashMap<>();

    static {
        alerts.put((byte) 0x01, new GT100Alert((byte) 0x01, GT100Alert.SOS_ALERT));
        alerts.put((byte) 0x02, new GT100Alert((byte) 0x02, GT100Alert.POWER_CUT_ALERT));
        alerts.put((byte) 0x03, new GT100Alert((byte) 0x03, GT100Alert.VIBRATION_ALERT));
        alerts.put((byte) 0x04, new GT100Alert((byte) 0x04, GT100Alert.FENCE_IN_ALERT));
        alerts.put((byte) 0x05, new GT100Alert((byte) 0x05, GT100Alert.FENCE_OUT_ALERT));
        alerts.put((byte) 0x06, new GT100Alert((byte) 0x06, GT100Alert.OVER_SPEED_ALERT));
        alerts.put((byte) 0x09, new GT100Alert((byte) 0x09, GT100Alert.MOVING_ALERT));
        // heartbeat alerts
        alerts.put(TERMINAL_INFO_ALARM_SHOCK, new GT100Alert((byte) 0x03, GT100Alert.VIBRATION_ALERT));
        alerts.put(TERMINAL_INFO_ALARM_POWER_CUT, new GT100Alert((byte) 0x02, GT100Alert.POWER_CUT_ALERT));
        alerts.put(TERMINAL_INFO_ALARM_LOW_BATTERY, new GT100Alert(TERMINAL_INFO_ALARM_LOW_BATTERY, GT100Alert.LOW_POWER));
        alerts.put(TERMINAL_INFO_ALARM_SOS, new GT100Alert((byte) 0x01, GT100Alert.SOS_ALERT));
    }

    public static HashMap<Byte, GT100Alert> getAll() {
        return alerts;
    }

    public static boolean isExists(byte alertId) {
        return getAll().containsKey(alertId);
    }

    public static GT100Alert get(byte alertId) {
        return getAll().get(alertId);
    }
}
