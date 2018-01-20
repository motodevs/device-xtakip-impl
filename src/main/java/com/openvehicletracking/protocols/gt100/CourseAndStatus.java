package com.openvehicletracking.protocols.gt100;

public class CourseAndStatus {

    private int course;
    private boolean realtimeGps;
    private boolean gpsHasBeenPositioned;
    private boolean eastLongitude;
    private boolean northLatitude;


    private CourseAndStatus() {
    }

    public int getCourse() {
        return course;
    }

    public boolean isRealtimeGps() {
        return realtimeGps;
    }

    public boolean isGpsHasBeenPositioned() {
        return gpsHasBeenPositioned;
    }

    public boolean isEastLongitude() {
        return eastLongitude;
    }

    public boolean isNorthLatitude() {
        return northLatitude;
    }

    public static CourseAndStatus fromShort(short data) {
        CourseAndStatus courseAndStatus = new CourseAndStatus();
        courseAndStatus.course = createCourse(data);
        courseAndStatus.realtimeGps = (data >> 13 & 1) == 1;
        courseAndStatus.gpsHasBeenPositioned = (data >> 12 & 1) == 1;
        courseAndStatus.eastLongitude = (data >> 11 & 1) == 1;
        courseAndStatus.northLatitude = (data >> 10 & 1) == 1;
        return courseAndStatus;
    }

    private static int createCourse(short data) {
        String bits = String.format("%16s", Integer.toBinaryString(data)).replace(' ', '0');
        return Integer.parseInt(bits.substring(5), 2);
    }


}
