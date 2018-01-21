package com.openvehicletracking.protocols.xtakip.lprotocol;

import com.openvehicletracking.core.AbstractLocationMessageBuilder;
import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.alert.AlertImp;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.xtakip.XTakipAlert;
import com.openvehicletracking.protocols.xtakip.XTakipAlerts;

import java.util.Date;

public class LProtocolMessageBuilder extends AbstractLocationMessageBuilder<LProtocolMessage> {

    private String deviceId;

    public void deviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public LProtocolMessageBuilder alert(int alertId) {
        Alert alert = createAlertFromId(alertId, getDate());
        if (alert != null) {
            attribute(Message.ATTR_ALERT, alert);
        }
        return this;
    }

    private Alert createAlertFromId(int alert, Date date) {
        if (XTakipAlerts.getAll().containsKey(alert)) {
            XTakipAlert xTakipAlert = XTakipAlerts.getAll().get(alert);

            AlertImp.AlertBuilder alertBuilder = new AlertImp.AlertBuilder()
                    .id(alert)
                    .description(xTakipAlert.getDesciption())
                    .date(date);
            xTakipAlert.getActions().forEach(alertBuilder::action);

            return alertBuilder.build();
        }
        return null;
    }
    @Override
    public LProtocolMessage build(Object... args) {
        return new LProtocolMessage(this);
    }

}
