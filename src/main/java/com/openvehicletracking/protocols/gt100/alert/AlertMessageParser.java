package com.openvehicletracking.protocols.gt100.alert;

import com.openvehicletracking.core.*;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.GT100BaseMessageParser;

import java.nio.ByteBuffer;

public class AlertMessageParser extends GT100BaseMessageParser {

    public AlertMessageParser(ByteBuffer msg) {
        super(msg);
    }

    @Override
    public Message parse() {
        byte[] datetime = new byte[6];
        byte[] cellTowerId = new byte[3];

        short header = message.getShort();
        byte length = message.get();
        byte protocol = message.get();
        message.get(datetime);
        byte gpsInfo = message.get();
        double latitude = message.getInt() / 60.0 / 30000.0;
        double longitude = message.getInt() / 60.0 / 30000.0;
        byte speed = message.get();
        short courseAndStatus = message.getShort();
        byte lbsLength = message.get();
        short mobileCountryCode = message.getShort();
        byte mobileNetworkCode = message.get();
        short locationAreaCode = message.getShort();
        message.get(cellTowerId);
        byte terminalInfo = message.get();
        byte voltageLevel = message.get();
        byte gsmSignalStrength = message.get();
        byte alert = message.get();
        byte alertLanguage = message.get();
        short infoSerialNumber = message.getShort();
        short errorCheck = message.getShort();
        short end = message.getShort();

        Position position = new Position();
        position.setCellTower(new CellTower(mobileCountryCode, mobileNetworkCode, locationAreaCode, createCellTowerId(cellTowerId)));
        position.setCourseAndStatus(CourseAndStatus.fromShort(courseAndStatus));
        position.setLongitude(longitude);
        position.setLatitude(latitude);
        position.setSpeed(speed);
        position.setDirection(CourseAndStatus.fromShort(courseAndStatus).getCourse());

        AlertMessageBuilder builder = new AlertMessageBuilder();
        builder.position(position)
                .date(parseDate(datetime))
                .raw(createRaw())
                .attribute(Message.ATTR_NUMBER_OF_SATELLITES, getNumberOfSatellites(gpsInfo))
                .attribute(Message.ATTR_VOLTAGE, VoltageLevel.create(voltageLevel))
                .attribute(Message.ATTR_GSM_SIGNAL_STRENGHT, GsmSignalStrength.create(gsmSignalStrength))
                .attribute(Message.ATTR_ALERT, createAlert(alert));

        createTerminalInfo(terminalInfo).forEach(builder::attribute);

        return builder.build();
    }

}
