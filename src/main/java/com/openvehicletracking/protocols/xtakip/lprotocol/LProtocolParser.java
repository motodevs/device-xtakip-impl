package com.openvehicletracking.protocols.xtakip.lprotocol;


import com.openvehicletracking.core.Position;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.Parser;
import com.openvehicletracking.protocols.ConversionHelper;
import com.openvehicletracking.protocols.xtakip.XTakipConstants;
import com.openvehicletracking.core.GpsStatus;


import java.math.BigInteger;
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

    public LProtocolParser(Object message) {
        this.message = (String) message;
        format = new SimpleDateFormat("ddMMyyHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public String getMessage() {
        return message;
    }

    public LProtocolMessage parse() {
        LProtocolMessageBuilder messageBuilder = new LProtocolMessageBuilder();
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

        createAttributes(message.substring(0, 8), messageBuilder);
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

        try {
            messageBuilder.date(format.parse(dateTime));
        } catch (ParseException e) {
            messageBuilder.date(new Date());
        }

        Position position = new Position();
        position.setLatitude(latitude);
        position.setLongitude(longitude);
        position.setDirection(direction);
        position.setSpeed((int) speed);
        messageBuilder.deviceId(deviceId);
        messageBuilder.gpsStatus(gpsStatus);
        messageBuilder.raw(this.message);
        messageBuilder.alert(alarm);
        messageBuilder.position(position);
        messageBuilder.attribute(Message.ATTR_DISTANCE, distance);




        return messageBuilder.build();
    }

    public void createAttributes(String raw, LProtocolMessageBuilder builder) {
        String bits = new BigInteger(raw, 16).toString(2);
        bits = new StringBuilder(bits).reverse().toString();
        byte[] bytes = bits.getBytes();
        byte t = 49; // 1

        for (int i = 0; i < bytes.length; i++) {
            switch (i) {
                case 0:
                    builder.attribute(XTakipConstants.ATTR_INPUT_1_ACTIVE, bytes[i] == t);
                    break;
                case 1:
                    builder.attribute(XTakipConstants.ATTR_INPUT_2_ACTIVE, bytes[i] == t);
                    break;
                case 2:
                    builder.attribute(XTakipConstants.ATTR_INPUT_3_ACTIVE, bytes[i] == t);
                    break;
                case 3:
                    builder.attribute(Message.ATTR_IGN_KEY_ON, !(bytes[i] == t));
                    break;
                case 4:
                    builder.attribute(XTakipConstants.ATTR_IS_BATTERY_CUT, bytes[i] == t);
                    break;
                case 5:
                    builder.attribute(XTakipConstants.ATTR_OUTPUT_1_ACTIVE, bytes[i] == t);
                    break;
                case 6:
                    builder.attribute(XTakipConstants.ATTR_OUTPUT_2_ACTIVE, bytes[i] == t);
                    break;
                case 7:
                    builder.attribute(XTakipConstants.ATTR_OUTPUT_3_ACTIVE, bytes[i] == t);
                    break;
                case 8:
                    builder.attribute(XTakipConstants.ATTR_OUT_OF_TEMP_LIMIT, bytes[i] == t);
                    break;
                case 9:
                    builder.attribute(XTakipConstants.ATTR_OUT_OF_SPEED_LIMIT, bytes[i] == t);
                    break;
                case 10:
                    builder.attribute(XTakipConstants.ATTR_GPRS_OPENED_OVERSEA, bytes[i] == t);
                    break;
                case 11:
                    builder.attribute(XTakipConstants.ATTR_DELTA_DISTANCE_OPENED, bytes[i] == t);
                    break;
                case 12:
                    builder.attribute(XTakipConstants.ATTR_IS_OFFLINE_RECORD, bytes[i] == t);
                    break;
                case 13:
                    builder.attribute(XTakipConstants.ATTR_IS_INVALID_RTC, bytes[i] == t);
                    break;
            }
        }
    }

}
