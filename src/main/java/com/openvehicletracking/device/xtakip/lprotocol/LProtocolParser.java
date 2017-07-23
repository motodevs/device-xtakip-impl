package com.openvehicletracking.device.xtakip.lprotocol;


import com.openvehicletracking.device.xtakip.ConversionHelper;
import com.openvehicletracking.device.xtakip.XTakipStatus;
import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.core.message.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by oksuz on 19/05/2017.
 */
public class LProtocolParser implements Parser {

    private SimpleDateFormat format;
    private String message;

    public LProtocolParser(String message) {
        this.message = message;
        format = new SimpleDateFormat("ddMMyyHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public String getMessage() {
        return message;
    }

    public LProtocolMessage parse() {
        LProtocolMessage p = new LProtocolMessage();
        String message = getMessage();

        message = message.substring(2, message.length() - 1);

        String header = message.substring(0, 3);
        message = message.substring(3);

        String deviceId = message.substring(0, 17);
        message = message.substring(17);

        String dateTime = message.substring(0, 12);
        message = message.substring(12);

        int deviceGpsStatus = Integer.parseInt(message.substring(0, 1));
        GpsStatus gpsStatus;
        if (deviceGpsStatus <= 3) {
            gpsStatus = GpsStatus.VALID;
        } else if (deviceGpsStatus == 8) {
            gpsStatus = GpsStatus.NO_DATA;
        } else {
            gpsStatus = GpsStatus.INVALID;
        }

        message = message.substring(1);

        double latitude = ConversionHelper.convertLatitude(message.substring(0, 8));
        message = message.substring(8);

        double longitude = ConversionHelper.convertLongitude(message.substring(0, 9));
        message = message.substring(9);

        XTakipStatus status = parseState(message.substring(0, 8));
        message = message.substring(8);

        double speed = ConversionHelper.knotToKm(message.substring(0, 4));
        message = message.substring(4);

        double distance = ConversionHelper.getDistance(message.substring(0, 6));
        message = message.substring(6);

        int direction = Integer.parseInt(message.substring(0, 3), 16);
        message = message.substring(3);

        int alarm = Integer.parseInt(message.substring(0, 3), 16);
        message = message.substring(3);

        String additional = message;

        p.setRawMessage(getMessage());
        p.setDeviceId(deviceId);
        p.setStatus(gpsStatus);
        p.setLatitude(latitude);
        p.setLongitude(longitude);
        p.setDeviceState(status);
        p.setSpeed(speed);
        p.setDistance(distance);
        p.setAlarm(alarm);
        p.setDirection(direction);
        p.setAdditional(additional);
        try {
            p.setDatetime(format.parse(dateTime).getTime());
        } catch (ParseException e) {
            p.setDatetime(new Date().getTime());
        }

        return p;
    }

    private XTakipStatus parseState(String raw) {
        XTakipStatus status = new XTakipStatus();
        status.setRaw(raw);
        int convertedStatus = Integer.parseInt(raw);
        String bits = Integer.toString(convertedStatus, 2);
        bits = new StringBuilder(bits).reverse().toString();
        byte[] bytes = bits.getBytes();
        byte t = 49; // 1

        for (int i = 0; i < bytes.length; i++) {
            switch (i) {
                case 0:
                    status.setInput1Active(bytes[i] == t);
                    break;
                case 1:
                    status.setInput2Active(bytes[i] == t);
                    break;
                case 2:
                    status.setInput3Active(bytes[i] == t);
                    break;
                case 3:
                    status.setIgnitiKeyOff(bytes[i] == t);
                    break;
                case 4:
                    status.setBatteryCutted(bytes[i] == t);
                    break;
                case 5:
                    status.setOutput1Active(bytes[i] == t);
                    break;
                case 6:
                    status.setOutput2Active(bytes[i] == t);
                    break;
                case 7:
                    status.setOutput3Active(bytes[i] == t);
                    break;
                case 8:
                    status.setOutOfTempLimit(bytes[i] == t);
                    break;
                case 9:
                    status.setOutOfSpeedLimit(bytes[i] == t);
                    break;
                case 10:
                    status.setGprsOpendOnOversea(bytes[i] == t);
                    break;
                case 11:
                    status.setDeltaDistinaceOpened(bytes[i] == t);
                    break;
                case 12:
                    status.setOfflineRecord(bytes[i] == t);
                    break;
                case 13:
                    status.setInvalidRTC(bytes[i] == t);
                    break;
                case 14:
                    status.setEngineStopActive(bytes[i] == t);
                    break;
                case 15:
                    status.setMaxStopResuming(bytes[i] == t);
                    break;
                case 16:
                    status.setIdleStatusResuming(bytes[i] == t);
                    break;
                case 17:
                    status.setgSensorAlarmResuming(bytes[i] == t);
                    break;
                case 18:
                    status.setInput4Active(bytes[i] == t);
                    break;
                case 19:
                    status.setInput5Active(bytes[i] == t);
                    break;
                case 20:
                    status.setExternalPowerCut(bytes[i] == t);
                    break;

            }
        }


        return status;
    }

}
