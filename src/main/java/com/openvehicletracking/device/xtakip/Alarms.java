package com.openvehicletracking.device.xtakip;


import com.openvehicletracking.core.alert.AlertAction;

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

    private static Map<Integer, XtakipAlert> all;

    static {
        List<AlertAction> infoAction = Collections.singletonList(AlertAction.INFO);
        List<AlertAction> notificationAction = Collections.singletonList(AlertAction.SEND_NOTIFICATION);
        List<AlertAction> notificationAndSmsAction = Arrays.asList(AlertAction.SEND_SMS, AlertAction.SEND_NOTIFICATION);

        all = new ConcurrentHashMap<>();
        all.put(1, new XtakipAlert(1, "Noktaya giriş/çıkış yapıldı", infoAction));
        all.put(2, new XtakipAlert(2, "RFID kart okundu", infoAction));
        all.put(3, new XtakipAlert(3, "Maksimum hız limit aşıldı", Arrays.asList(AlertAction.INFO, AlertAction.SEND_NOTIFICATION)));
        all.put(4, new XtakipAlert(4, "Maksimum bekleme süresi aşıldı.", infoAction));
        all.put(5, new XtakipAlert(5, "Hızlanma ivme limiti aşıldı.", notificationAction));
        all.put(6, new XtakipAlert(6, "Yavaşlama ivme limiti aşıldı.", infoAction));
        all.put(7, new XtakipAlert(7, "Akü bağlantısı kesildi.", notificationAndSmsAction));
        all.put(8, new XtakipAlert(8, "Giriş2 alarm tipi 1 oluştu.", infoAction));
        all.put(9, new XtakipAlert(9, "Giriş2 alarm tipi 2 oluştu.", infoAction));
        all.put(10, new XtakipAlert(10, "Giriş1 alarm tipi 1 oluştu.", infoAction));
        all.put(11, new XtakipAlert(11, "Giriş1 alarm tipi 2 oluştu.", infoAction));
        all.put(12, new XtakipAlert(12, "Kontak açıldı.", notificationAndSmsAction));
        all.put(13, new XtakipAlert(13, "Kontak kapatıldı.", notificationAction));
        all.put(14, new XtakipAlert(14, "Yurtdışına çıkıldı.", Arrays.asList(AlertAction.SEND_NOTIFICATION, AlertAction.SEND_EMAIL)));
        all.put(15, new XtakipAlert(15, "Yurtiçine girildi.", notificationAction));
        all.put(16, new XtakipAlert(16, "Sensör 1 max. sıcaklık aşıldı.", notificationAction));
        all.put(17, new XtakipAlert(17, "Sensör 1 min. Sıcaklık aşıldı.", notificationAction));
        all.put(18, new XtakipAlert(18, "Giriş3 alarm tipi 1 oluştu.", infoAction));
        all.put(19, new XtakipAlert(19, "Giriş3 alarm tipi 2 oluştu.", infoAction));
        all.put(20, new XtakipAlert(20, "Yakıt seviyesi uyarısı.", notificationAction));
        all.put(21, new XtakipAlert(21, "GPS alınamıyor.", notificationAction));
        all.put(22, new XtakipAlert(22, "Rölanti süresi aşıldı.", infoAction));
        all.put(23, new XtakipAlert(23, "RequestID uyarısı.", infoAction));
        all.put(24, new XtakipAlert(24, "Taksimetre uyarısı.", infoAction));
        all.put(25, new XtakipAlert(25, "Darbe girişi1 için limit aşıldı.", infoAction));
        all.put(26, new XtakipAlert(26, "Darbe girişi2 için limit aşıldı.", infoAction));
        all.put(27, new XtakipAlert(27, "Darbe girişi3 için limit aşıldı.", infoAction));
        all.put(28, new XtakipAlert(28, "Düşük hız limit alarmı.", infoAction));
        all.put(29, new XtakipAlert(29, "Sensör 2 max. sıcaklık aşıldı.", infoAction));
        all.put(30, new XtakipAlert(30, "Sensör 2 min. Sıcaklık aşıldı.", infoAction));
        all.put(31, new XtakipAlert(31, "Sensör 3 max. sıcaklık aşıldı.", infoAction));
        all.put(32, new XtakipAlert(32, "Sensör 3 min. Sıcaklık aşıldı.", infoAction));
        all.put(33, new XtakipAlert(33, "Açı alarmı.", notificationAction));
        all.put(34, new XtakipAlert(34, "Transparan mod alarmı.", infoAction));
        all.put(35, new XtakipAlert(35, "Düşük hız bitti.", infoAction));
        all.put(36, new XtakipAlert(36, "Yüksek hız bitti.", infoAction));
        all.put(37, new XtakipAlert(37, "Rolanti Bitti.", infoAction));
        all.put(38, new XtakipAlert(38, "Acil Durum.", notificationAndSmsAction));
        all.put(39, new XtakipAlert(39, "IO Expander Alarm", infoAction));
        all.put(40, new XtakipAlert(40, "G sensör x yön alarmı", infoAction));
        all.put(41, new XtakipAlert(41, "G sensör y yön alarmı", infoAction));
        all.put(42, new XtakipAlert(42, "G sensör z yön alarmı", infoAction));
        all.put(46, new XtakipAlert(46, "Hassas Durak Alarmı", infoAction));
        all.put(47, new XtakipAlert(47, "Genel Darbe Giris Alarmı", notificationAction));
        all.put(48, new XtakipAlert(48, "Genel Giris Alarmı", infoAction));
        all.put(49, new XtakipAlert(49, "Mobileye Alarm", infoAction));
        all.put(50, new XtakipAlert(50, "M50S Darbe/Kaza/İvme Alarmı", infoAction));
        all.put(55, new XtakipAlert(55, "G sensor Kasis Alarmı", infoAction));
        all.put(56, new XtakipAlert(56, "G sensor Savrulma Alarmı", infoAction));
        all.put(57, new XtakipAlert(57, "G sensor Hizlanma Ivme Alarmı", infoAction));
        all.put(58, new XtakipAlert(58, "G sensor Yavaş. Ivme Alarmı", infoAction));
        all.put(59, new XtakipAlert(59, "Düşük batarya", notificationAndSmsAction));
        all.put(60, new XtakipAlert(60, "Dolu batarya", notificationAction));
        all.put(61, new XtakipAlert(61, "Araç çekilme uyarısı", notificationAndSmsAction));
        all.put(62, new XtakipAlert(62, "Cihaz kapatıldı", notificationAndSmsAction));
        all.put(63, new XtakipAlert(63, "Cihaz harekete başladı", notificationAndSmsAction));
        all.put(64, new XtakipAlert(64, "Cihaz hareketi bitti.", notificationAction));
        all.put(65, new XtakipAlert(65, "Geçerli RFID Kart", infoAction));
        all.put(66, new XtakipAlert(66, "Geçersiz RFID Kart", infoAction));
        all.put(67, new XtakipAlert(67, "SIM kapağı açıldı", notificationAndSmsAction));
        all.put(68, new XtakipAlert(68, "Düşük nem oranı", infoAction));
        all.put(69, new XtakipAlert(69, "Yüksek nem oranı", infoAction));
        all.put(70, new XtakipAlert(70, "Düşük sıcaklık(Nem sensörü dahili verisi)", infoAction));
        all.put(71, new XtakipAlert(71, "Yüksek sıcaklık(Nem sensörü dahili verisi)", infoAction));
        all.put(72, new XtakipAlert(72, "Geçerli iButton ID", infoAction));
        all.put(73, new XtakipAlert(73, "Geçersiz iButton ID", infoAction));
        all.put(74, new XtakipAlert(74, "Jamming Alarm", notificationAndSmsAction));
        all.put(75, new XtakipAlert(75, "Akü bağlantısı takıldı.", notificationAction));
        all.put(76, new XtakipAlert(76, "Serbest düşüş uyarısı", infoAction));
        all.put(77, new XtakipAlert(77, "Sistem yeniden başlatıldı.", notificationAction));
    }


    static Map<Integer, XtakipAlert> getAll() {
        return all;
    }
}
