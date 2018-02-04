package com.openvehicletracking.protocols.gt100;

public enum GpsDataUploadMode {

    UPLOAD_BY_INTERVALS(0x00),
    UPLOAD_BY_DISTANCE(0x01),
    UPLOAD_FOR_CORNER_CORRECTION(0x02),
    UPLOAD_BY_ACC_STATURE_CHANGE(0x03),
    UPLOAD_LAST_POSITION_BEFORE_STATIC(0x04),
    UNKNOWN(0xFF);

    private final int val;

    GpsDataUploadMode(int i) {
        this.val = i;
    }

    public static GpsDataUploadMode createFrom(byte value) {
        switch (value) {
            case 0x00:
                return GpsDataUploadMode.UPLOAD_BY_INTERVALS;

            case 0x01:
                return GpsDataUploadMode.UPLOAD_BY_DISTANCE;

            case 0x02:
                return GpsDataUploadMode.UPLOAD_FOR_CORNER_CORRECTION;

            case 0x03:
                return GpsDataUploadMode.UPLOAD_BY_ACC_STATURE_CHANGE;

            case 0x04:
                return GpsDataUploadMode.UPLOAD_LAST_POSITION_BEFORE_STATIC;
            default:
                return GpsDataUploadMode.UNKNOWN;
        }
    }
}
