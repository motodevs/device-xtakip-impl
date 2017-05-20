package net.motodev.device.LProtocol;

import net.motodev.core.Parser;
import net.motodev.device.ConversionHelper;

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

    public LProtocolMessage message() {
        LProtocolMessage p = new LProtocolMessage();
        String message = getMessage();

        message = message.substring(2, message.length() - 1);

        String header = message.substring(0, 3);
        message = message.substring(3);

        String deviceId = message.substring(0, 17);
        message = message.substring(17);

        String dateTime = message.substring(0, 12);
        message = message.substring(12);

        int gpsStatus = Integer.parseInt(message.substring(0, 1));
        message = message.substring(1);

        double latitude = ConversionHelper.convertLatitude(message.substring(0, 8));
        message = message.substring(8);

        double longitude = ConversionHelper.convertLongitude(message.substring(0, 9));
        message = message.substring(9);

        String status = message.substring(0, 8);
        message = message.substring(8);

        double speed = ConversionHelper.knotToKm(message.substring(0, 4));
        message = message.substring(4);

        double distance = ConversionHelper.getDistance(message.substring(0, 6));
        message = message.substring(6);

        String direction = message.substring(0, 3);
        message = message.substring(3);

        int alarm = Integer.parseInt(message.substring(0, 3), 16);
        message = message.substring(3);

        String additional = message;

        p.setRawMessage(getMessage());
        p.setHeader(header);
        p.setDeviceId(deviceId);
        p.setGpsStatus(gpsStatus);
        p.setLatitude(latitude);
        p.setLongitude(longitude);
        p.setStatus(status);
        p.setSpeed(speed);
        p.setDistance(distance);
        p.setAlarm(alarm);
        p.setDirection(direction);
        p.setAdditional(additional);
        try {
            p.setDatetime(format.parse(dateTime));
        } catch (ParseException e) {
            p.setDatetime(new Date());
        }

        return p;
    }
}
