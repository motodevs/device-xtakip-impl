package net.motodev.device;

import net.motodev.core.alarm.AlarmAction;

import java.util.Arrays;
import java.util.List;

/**
 * Created by oksuz on 19/05/2017.
 */
public class DeviceConstants {

    public static final String MESSAGE_TYPE_L = "L";
    public static final String MESSAGE_TYPE_HX = "HX";
    public static final String MESSAGE_TYPE_OX = "OX";


    public static final String ALARM_MOVING_DESCRIPTION = "Bir hareket algilandi.";
    public static final int ALARM_MOVING_ID = 99;
    public static final List<AlarmAction> CRITICAL_ALARM_ACTION = Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_SMS);

}
