package net.motodev.device;

/**
 * Created by oksuz on 04/02/2017.
 *
 */
public class ConversionHelper {

    public static double convertLatitude(String data) {
        String begin = data.substring(0, 2);
        String decimal = "0." + data.substring(2);

        return Integer.parseInt(begin) + Double.parseDouble(decimal) / 0.6f;
    }

    public static double convertLongitude(String data) {
        String begin = data.substring(0, 3);
        String decimal = "0." + data.substring(3);
        return Integer.parseInt(begin) + Double.parseDouble(decimal) / 0.6f;
    }

    public static double knotToKm(String knot) {
        int converted = Integer.parseInt(knot, 16);
        return (converted / 100) * 1.852;
    }

    public static double getDistance(String dist) {
        return Integer.parseInt(dist, 16) / 16 * 1.852;
    }

}
