package com.openvehicletracking.protocols.gt100;

import java.util.HashMap;

public class GT100Alerts {

    private static final HashMap<Byte, GT100Alert> alerts = new HashMap<>();

    static {
        alerts.put((byte) 0x01, new GT100Alert((byte) 0x01, GT100Alert.SOS_ALERT));
        alerts.put((byte) 0x02, new GT100Alert((byte) 0x02, GT100Alert.POWER_CUT_ALERT));
        alerts.put((byte) 0x03, new GT100Alert((byte) 0x03, GT100Alert.VIBRATION_ALERT));
        alerts.put((byte) 0x04, new GT100Alert((byte) 0x04, GT100Alert.FENCE_IN_ALERT));
        alerts.put((byte) 0x05, new GT100Alert((byte) 0x05, GT100Alert.FENCE_OUT_ALERT));
        alerts.put((byte) 0x06, new GT100Alert((byte) 0x06, GT100Alert.OVER_SPEED_ALERT));
        alerts.put((byte) 0x09, new GT100Alert((byte) 0x09, GT100Alert.MOVING_ALERT));
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
