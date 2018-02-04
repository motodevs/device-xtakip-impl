package com.openvehicletracking.protocols.xtakip;


import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.alert.AlertAction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by oksuz on 04/09/2017.
 *
 */
public class XTakipAlerts {

    private static Map<Integer, XTakipAlert> all = new HashMap<>();

    static {
        List<AlertAction> infoAction = Collections.singletonList(AlertAction.INFO);
        List<AlertAction> notificationAction = Collections.singletonList(AlertAction.SEND_NOTIFICATION);
        List<AlertAction> notificationAndSmsAction = Arrays.asList(AlertAction.SEND_SMS, AlertAction.SEND_NOTIFICATION);

        all = new ConcurrentHashMap<>();
        all.put(1, new XTakipAlert(1, "Noktaya giriş/çıkış yapıldı", infoAction));
        all.put(2, new XTakipAlert(2, "RFID kart okundu", infoAction));
        all.put(3, new XTakipAlert(3, "Maksimum hız limit aşıldı", Arrays.asList(AlertAction.INFO, AlertAction.SEND_NOTIFICATION)));
        all.put(4, new XTakipAlert(4, "Maksimum bekleme süresi aşıldı.", infoAction));
        all.put(5, new XTakipAlert(5, "Hızlanma ivme limiti aşıldı.", notificationAction));
        all.put(6, new XTakipAlert(6, "Yavaşlama ivme limiti aşıldı.", infoAction));
        all.put(7, new XTakipAlert(7, "Akü bağlantısı kesildi.", notificationAndSmsAction));
        all.put(8, new XTakipAlert(8, "Giriş2 alarm tipi 1 oluştu.", infoAction));
        all.put(9, new XTakipAlert(9, "Giriş2 alarm tipi 2 oluştu.", infoAction));
        all.put(10, new XTakipAlert(10, "Giriş1 alarm tipi 1 oluştu.", infoAction));
        all.put(11, new XTakipAlert(11, "Giriş1 alarm tipi 2 oluştu.", infoAction));
        all.put(12, new XTakipAlert(12, "Kontak açıldı.", notificationAndSmsAction));
        all.put(13, new XTakipAlert(13, "Kontak kapatıldı.", notificationAction));
        all.put(14, new XTakipAlert(14, "Yurtdışına çıkıldı.", Arrays.asList(AlertAction.SEND_NOTIFICATION, AlertAction.SEND_EMAIL)));
        all.put(15, new XTakipAlert(15, "Yurtiçine girildi.", notificationAction));
        all.put(16, new XTakipAlert(16, "Sensör 1 max. sıcaklık aşıldı.", notificationAction));
        all.put(17, new XTakipAlert(17, "Sensör 1 min. Sıcaklık aşıldı.", notificationAction));
        all.put(18, new XTakipAlert(18, "Giriş3 alarm tipi 1 oluştu.", infoAction));
        all.put(19, new XTakipAlert(19, "Giriş3 alarm tipi 2 oluştu.", infoAction));
        all.put(20, new XTakipAlert(20, "Yakıt seviyesi uyarısı.", notificationAction));
        all.put(21, new XTakipAlert(21, "GPS alınamıyor.", notificationAction));
        all.put(22, new XTakipAlert(22, "Rölanti süresi aşıldı.", infoAction));
        all.put(23, new XTakipAlert(23, "RequestID uyarısı.", infoAction));
        all.put(24, new XTakipAlert(24, "Taksimetre uyarısı.", infoAction));
        all.put(25, new XTakipAlert(25, "Darbe girişi1 için limit aşıldı.", infoAction));
        all.put(26, new XTakipAlert(26, "Darbe girişi2 için limit aşıldı.", infoAction));
        all.put(27, new XTakipAlert(27, "Darbe girişi3 için limit aşıldı.", infoAction));
        all.put(28, new XTakipAlert(28, "Düşük hız limit alarmı.", infoAction));
        all.put(29, new XTakipAlert(29, "Sensör 2 max. sıcaklık aşıldı.", infoAction));
        all.put(30, new XTakipAlert(30, "Sensör 2 min. Sıcaklık aşıldı.", infoAction));
        all.put(31, new XTakipAlert(31, "Sensör 3 max. sıcaklık aşıldı.", infoAction));
        all.put(32, new XTakipAlert(32, "Sensör 3 min. Sıcaklık aşıldı.", infoAction));
        all.put(33, new XTakipAlert(33, "Açı alarmı.", notificationAction));
        all.put(34, new XTakipAlert(34, "Transparan mod alarmı.", infoAction));
        all.put(35, new XTakipAlert(35, "Düşük hız bitti.", infoAction));
        all.put(36, new XTakipAlert(36, "Yüksek hız bitti.", infoAction));
        all.put(37, new XTakipAlert(37, "Rolanti Bitti.", infoAction));
        all.put(38, new XTakipAlert(38, "Acil Durum.", notificationAndSmsAction));
        all.put(39, new XTakipAlert(39, "IO Expander Alarm", infoAction));
        all.put(40, new XTakipAlert(40, "G sensör x yön alarmı", infoAction));
        all.put(41, new XTakipAlert(41, "G sensör y yön alarmı", infoAction));
        all.put(42, new XTakipAlert(42, "G sensör z yön alarmı", infoAction));
        all.put(46, new XTakipAlert(46, "Hassas Durak Alarmı", infoAction));
        all.put(47, new XTakipAlert(47, "Genel Darbe Giris Alarmı", notificationAction));
        all.put(48, new XTakipAlert(48, "Genel Giris Alarmı", infoAction));
        all.put(49, new XTakipAlert(49, "Mobileye Alarm", infoAction));
        all.put(50, new XTakipAlert(50, "M50S Darbe/Kaza/İvme Alarmı", infoAction));
        all.put(55, new XTakipAlert(55, "G sensor Kasis Alarmı", infoAction));
        all.put(56, new XTakipAlert(56, "G sensor Savrulma Alarmı", infoAction));
        all.put(57, new XTakipAlert(57, "G sensor Hizlanma Ivme Alarmı", infoAction));
        all.put(58, new XTakipAlert(58, "G sensor Yavaş. Ivme Alarmı", infoAction));
        all.put(59, new XTakipAlert(59, "Düşük batarya", notificationAndSmsAction));
        all.put(60, new XTakipAlert(60, "Dolu batarya", notificationAction));
        all.put(61, new XTakipAlert(61, "Araç çekilme uyarısı", notificationAndSmsAction));
        all.put(62, new XTakipAlert(62, "Cihaz kapatıldı", notificationAndSmsAction));
        all.put(63, new XTakipAlert(63, "Cihaz harekete başladı", notificationAndSmsAction));
        all.put(64, new XTakipAlert(64, "Cihaz hareketi bitti.", notificationAction));
        all.put(65, new XTakipAlert(65, "Geçerli RFID Kart", infoAction));
        all.put(66, new XTakipAlert(66, "Geçersiz RFID Kart", infoAction));
        all.put(67, new XTakipAlert(67, "SIM kapağı açıldı", notificationAndSmsAction));
        all.put(68, new XTakipAlert(68, "Düşük nem oranı", infoAction));
        all.put(69, new XTakipAlert(69, "Yüksek nem oranı", infoAction));
        all.put(70, new XTakipAlert(70, "Düşük sıcaklık(Nem sensörü dahili verisi)", infoAction));
        all.put(71, new XTakipAlert(71, "Yüksek sıcaklık(Nem sensörü dahili verisi)", infoAction));
        all.put(72, new XTakipAlert(72, "Geçerli iButton ID", infoAction));
        all.put(73, new XTakipAlert(73, "Geçersiz iButton ID", infoAction));
        all.put(74, new XTakipAlert(74, "Jamming Alarm", notificationAndSmsAction));
        all.put(75, new XTakipAlert(75, "Akü bağlantısı takıldı.", notificationAction));
        all.put(76, new XTakipAlert(76, "Serbest düşüş uyarısı", infoAction));
        all.put(77, new XTakipAlert(77, "Sistem yeniden başlatıldı.", notificationAction));
    }


    public static Map<Integer, XTakipAlert> getAll() {
        return all;
    }
}
