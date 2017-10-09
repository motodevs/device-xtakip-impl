package com.openvehicletracking.device.xtakip;

import com.openvehicletracking.core.alarm.AlarmAction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by oksuz on 04/09/2017.
 *
 */
public class Alarms {

    private static Map<Integer, XtakipAlarm> all;

    static {
        List<AlarmAction> infoAction = Collections.singletonList(AlarmAction.INFO);
        List<AlarmAction> notificationAction = Collections.singletonList(AlarmAction.SEND_NOTIFICATION);
        List<AlarmAction> notificationAndSmsAction = Arrays.asList(AlarmAction.SEND_SMS, AlarmAction.SEND_NOTIFICATION);

        all = new ConcurrentHashMap<>();
        all.put(1, new XtakipAlarm(1, "Noktaya giriş/çıkış yapıldı", infoAction));
        all.put(2, new XtakipAlarm(2, "RFID kart okundu", infoAction));
        all.put(3, new XtakipAlarm(3, "Maksimum hız limit aşıldı", Arrays.asList(AlarmAction.INFO, AlarmAction.SEND_NOTIFICATION)));
        all.put(4, new XtakipAlarm(4, "Maksimum bekleme süresi aşıldı.", infoAction));
        all.put(5, new XtakipAlarm(5, "Hızlanma ivme limiti aşıldı.", notificationAction));
        all.put(6, new XtakipAlarm(6, "Yavaşlama ivme limiti aşıldı.", infoAction));
        all.put(7, new XtakipAlarm(7, "Akü bağlantısı kesildi.", notificationAndSmsAction));
        all.put(8, new XtakipAlarm(8, "Giriş2 alarm tipi 1 oluştu.", infoAction));
        all.put(9, new XtakipAlarm(9, "Giriş2 alarm tipi 2 oluştu.", infoAction));
        all.put(10, new XtakipAlarm(10, "Giriş1 alarm tipi 1 oluştu.", infoAction));
        all.put(11, new XtakipAlarm(11, "Giriş1 alarm tipi 2 oluştu.", infoAction));
        all.put(12, new XtakipAlarm(12, "Kontak açıldı.", notificationAndSmsAction));
        all.put(13, new XtakipAlarm(13, "Kontak kapatıldı.", notificationAction));
        all.put(14, new XtakipAlarm(14, "Yurtdışına çıkıldı.", Arrays.asList(AlarmAction.SEND_NOTIFICATION, AlarmAction.SEND_EMAIL)));
        all.put(15, new XtakipAlarm(15, "Yurtiçine girildi.", notificationAction));
        all.put(16, new XtakipAlarm(16, "Sensör 1 max. sıcaklık aşıldı.", notificationAction));
        all.put(17, new XtakipAlarm(17, "Sensör 1 min. Sıcaklık aşıldı.", notificationAction));
        all.put(18, new XtakipAlarm(18, "Giriş3 alarm tipi 1 oluştu.", infoAction));
        all.put(19, new XtakipAlarm(19, "Giriş3 alarm tipi 2 oluştu.", infoAction));
        all.put(20, new XtakipAlarm(20, "Yakıt seviyesi uyarısı.", notificationAction));
        all.put(21, new XtakipAlarm(21, "GPS alınamıyor.", notificationAction));
        all.put(22, new XtakipAlarm(22, "Rölanti süresi aşıldı.", infoAction));
        all.put(23, new XtakipAlarm(23, "RequestID uyarısı.", infoAction));
        all.put(24, new XtakipAlarm(24, "Taksimetre uyarısı.", infoAction));
        all.put(25, new XtakipAlarm(25, "Darbe girişi1 için limit aşıldı.", infoAction));
        all.put(26, new XtakipAlarm(26, "Darbe girişi2 için limit aşıldı.", infoAction));
        all.put(27, new XtakipAlarm(27, "Darbe girişi3 için limit aşıldı.", infoAction));
        all.put(28, new XtakipAlarm(28, "Düşük hız limit alarmı.", infoAction));
        all.put(29, new XtakipAlarm(29, "Sensör 2 max. sıcaklık aşıldı.", infoAction));
        all.put(30, new XtakipAlarm(30, "Sensör 2 min. Sıcaklık aşıldı.", infoAction));
        all.put(31, new XtakipAlarm(31, "Sensör 3 max. sıcaklık aşıldı.", infoAction));
        all.put(32, new XtakipAlarm(32, "Sensör 3 min. Sıcaklık aşıldı.", infoAction));
        all.put(33, new XtakipAlarm(33, "Açı alarmı.", notificationAction));
        all.put(34, new XtakipAlarm(34, "Transparan mod alarmı.", infoAction));
        all.put(35, new XtakipAlarm(35, "Düşük hız bitti.", infoAction));
        all.put(36, new XtakipAlarm(36, "Yüksek hız bitti.", infoAction));
        all.put(37, new XtakipAlarm(37, "Rolanti Bitti.", infoAction));
        all.put(38, new XtakipAlarm(38, "Acil Durum.", notificationAndSmsAction));
        all.put(39, new XtakipAlarm(39, "IO Expander Alarm", infoAction));
        all.put(40, new XtakipAlarm(40, "G sensör x yön alarmı", infoAction));
        all.put(41, new XtakipAlarm(41, "G sensör y yön alarmı", infoAction));
        all.put(42, new XtakipAlarm(42, "G sensör z yön alarmı", infoAction));
        all.put(46, new XtakipAlarm(46, "Hassas Durak Alarmı", infoAction));
        all.put(47, new XtakipAlarm(47, "Genel Darbe Giris Alarmı", notificationAction));
        all.put(48, new XtakipAlarm(48, "Genel Giris Alarmı", infoAction));
        all.put(49, new XtakipAlarm(49, "Mobileye Alarm", infoAction));
        all.put(50, new XtakipAlarm(50, "M50S Darbe/Kaza/İvme Alarmı", infoAction));
        all.put(55, new XtakipAlarm(55, "G sensor Kasis Alarmı", infoAction));
        all.put(56, new XtakipAlarm(56, "G sensor Savrulma Alarmı", infoAction));
        all.put(57, new XtakipAlarm(57, "G sensor Hizlanma Ivme Alarmı", infoAction));
        all.put(58, new XtakipAlarm(58, "G sensor Yavaş. Ivme Alarmı", infoAction));
        all.put(59, new XtakipAlarm(59, "Düşük batarya", notificationAndSmsAction));
        all.put(60, new XtakipAlarm(60, "Dolu batarya", notificationAction));
        all.put(61, new XtakipAlarm(61, "Araç çekilme uyarısı", notificationAndSmsAction));
        all.put(62, new XtakipAlarm(62, "Cihaz kapatıldı", notificationAndSmsAction));
        all.put(63, new XtakipAlarm(63, "Cihaz harekete başladı", notificationAndSmsAction));
        all.put(64, new XtakipAlarm(64, "Cihaz hareketi bitti.", notificationAction));
        all.put(65, new XtakipAlarm(65, "Geçerli RFID Kart", infoAction));
        all.put(66, new XtakipAlarm(66, "Geçersiz RFID Kart", infoAction));
        all.put(67, new XtakipAlarm(67, "SIM kapağı açıldı", notificationAndSmsAction));
        all.put(68, new XtakipAlarm(68, "Düşük nem oranı", infoAction));
        all.put(69, new XtakipAlarm(69, "Yüksek nem oranı", infoAction));
        all.put(70, new XtakipAlarm(70, "Düşük sıcaklık(Nem sensörü dahili verisi)", infoAction));
        all.put(71, new XtakipAlarm(71, "Yüksek sıcaklık(Nem sensörü dahili verisi)", infoAction));
        all.put(72, new XtakipAlarm(72, "Geçerli iButton ID", infoAction));
        all.put(73, new XtakipAlarm(73, "Geçersiz iButton ID", infoAction));
        all.put(74, new XtakipAlarm(74, "Jamming Alarm", notificationAndSmsAction));
        all.put(75, new XtakipAlarm(75, "Akü bağlantısı takıldı.", notificationAction));
        all.put(76, new XtakipAlarm(76, "Serbest düşüş uyarısı", infoAction));
        all.put(77, new XtakipAlarm(77, "Sistem yeniden başlatıldı.", notificationAction));
    }


    static Map<Integer, XtakipAlarm> getAll() {
        return all;
    }
}
