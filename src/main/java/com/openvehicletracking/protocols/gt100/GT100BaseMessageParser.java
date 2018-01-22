package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.alert.AlertAction;
import com.openvehicletracking.core.alert.AlertImp;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.Parser;
import com.openvehicletracking.protocols.gt100.location.LocationMessageBuilder;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

abstract public class GT100BaseMessageParser implements Parser {

    protected final ByteBuffer message;

    public GT100BaseMessageParser(ByteBuffer msg) {
        message = msg;
    }

    protected int createCellTowerId(byte[] cellTowerId) {
        return ((cellTowerId[0] & 0x0F) << 16) | ((cellTowerId[1] & 0xFF) << 8) | (cellTowerId[2] & 0xFF);
    }

    protected GpsStatus createGpsStatus(LocationMessageBuilder locationMessageBuilder) {

        if (locationMessageBuilder.getPosition().getLatitude() == 0 && locationMessageBuilder.getPosition().getLongitude() == 0) {
            return GpsStatus.NO_DATA;
        }

        if (locationMessageBuilder.getAttributes().containsKey(Message.ATTR_NUMBER_OF_SATELLITES)) {
            int numberOfSatellites = (int) locationMessageBuilder.getAttributes().get(Message.ATTR_NUMBER_OF_SATELLITES);
            if (numberOfSatellites == 0) {
                return GpsStatus.INVALID;
            }
        } else {
            return GpsStatus.INVALID;
        }

        return GpsStatus.VALID;
    }

    protected String createRaw() {
        StringBuilder builder = new StringBuilder("[");
        message.clear();
        while (message.hasRemaining()) {
            builder.append(message.get());
            if (message.hasRemaining()) {
                builder.append(",");
            }
        }
        return builder.append("]").toString();
    }

    protected Date parseDate(byte[] date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateInString = String.format("%d-%02d-%02d %02d:%02d:%02d", date[0], date[1], date[2], date[3], date[4], date[5]);
        try {
            return sdf.parse(dateInString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    protected int getNumberOfSatellites(byte data) {
        return Integer.parseInt(String.format("%02x", data).split("")[1], 16);
    }

    protected Alert createAlert(byte alert) {
        if (!GT100Alerts.isExists(alert)) {
            return null;
        }

        GT100Alert gt100Alert = GT100Alerts.get(alert);
        return new AlertImp.AlertBuilder()
                .action(AlertAction.SEND_NOTIFICATION)
                .action(AlertAction.SEND_SMS)
                .description(gt100Alert.getDescription())
                .id(alert)
                .build();
    }

    protected HashMap<String, Boolean> createTerminalInfo(byte info) {
        HashMap<String, Boolean> terminalInfo = new HashMap<>();
        terminalInfo.put(GT100Contants.DEFENSE_ACTIVATED, (info & 1) == 1);
        terminalInfo.put(Message.ATTR_IGN_KEY_ON, (info >> 1 & 1) == 1);
        terminalInfo.put(GT100Contants.CHARGE_ON, (info >> 2 & 1) == 1);
        terminalInfo.put(GT100Contants.GPS_TRACKING_ON, (info >> 6 & 1) == 1);
        terminalInfo.put(GT100Contants.OIL_AND_ELECTRICITY_CONNECTED, (info >> 7 & 1) == 1);
        return terminalInfo;
    }
}
