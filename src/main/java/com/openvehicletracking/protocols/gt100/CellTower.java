package com.openvehicletracking.protocols.gt100;

/**
 * https://developers.google.com/maps/documentation/geolocation/intro#cell_tower_object
 */
public class CellTower {

    private short mcc;
    private short mnc;
    private short lac;
    private int cellTower;

    public CellTower(short mobileCountryCode, byte mobileNetworkCode, short locationAreaCode, byte[] cellTowerId) {
        mcc = mobileCountryCode;
        mnc = mobileNetworkCode;
        lac = locationAreaCode;
        cellTower = createCellTowerId(cellTowerId);
    }

    public short getMcc() {
        return mcc;
    }

    public short getMnc() {
        return mnc;
    }

    public short getLac() {
        return lac;
    }

    public int getCellTower() {
        return cellTower;
    }

    private int createCellTowerId(byte[] cellTowerId) {
        return ((cellTowerId[1] << 16) & 0xFF) | ((cellTowerId[1] << 8) & 0xFF) | (cellTowerId[2] & 0xFF);
    }
}
