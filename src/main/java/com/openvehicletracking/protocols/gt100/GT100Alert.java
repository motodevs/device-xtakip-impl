package com.openvehicletracking.protocols.gt100;

public class GT100Alert {

    public static final String SOS_ALERT = "Sos Alert";
    public static final String POWER_CUT_ALERT = "Power Cut Alert";
    public static final String VIBRATION_ALERT = "Vibration Alert";
    public static final String FENCE_IN_ALERT = "Fence In Alert";
    public static final String FENCE_OUT_ALERT = "Fence Out Alert";
    public static final String OVER_SPEED_ALERT = "Over Speed Alert";
    public static final String MOVING_ALERT = "Moving Alert";
    public static final String SHOCK_ALERT = "Shock Alert";
    public static final String LOW_POWER = "Low Power Alert";


    private byte id;
    private String description;

    public GT100Alert(byte id, String description) {
        this.id = id;
        this.description = description;
    }

    public byte getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

