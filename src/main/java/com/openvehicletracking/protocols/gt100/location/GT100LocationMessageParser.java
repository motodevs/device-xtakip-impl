package com.openvehicletracking.protocols.gt100.location;

import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.core.protocol.Parser;
import com.openvehicletracking.protocols.gt100.CellTower;
import com.openvehicletracking.protocols.gt100.CourseAndStatus;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GT100LocationMessageParser implements Parser {

    private final ByteBuffer message;

    public GT100LocationMessageParser(ByteBuffer msg) {
        message = msg;
    }

    @Override
    public Message parse() {
        Gt100LocationMessage locationMessage = new Gt100LocationMessage();
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
        short mobileCountryCode = message.getShort();
        byte mobileNetworkCode = message.get();
        short locationAreaCode = message.getShort();
        message.get(cellTowerId);
        boolean ignKeyOn = message.get() == 0x1;
        byte gpsDataUploadMode = message.get();
        byte gpsRealTimeUpload = message.get();
        short infoSerialNumber = message.getShort();
        short errorCheck = message.getShort();
        short end = message.getShort();

        Date messageDate = parseDate(datetime);
        int numberOfSatellites = getNumberOfSatellites(gpsInfo);
        CourseAndStatus course = CourseAndStatus.fromShort(courseAndStatus);
        CellTower cellTower = new CellTower(mobileCountryCode, mobileNetworkCode, locationAreaCode, cellTowerId);
        return locationMessage;
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

    protected double getCourseAndStatus(short data) {

        return 0;
    }
}
