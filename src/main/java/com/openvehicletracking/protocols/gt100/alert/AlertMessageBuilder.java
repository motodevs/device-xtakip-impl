package com.openvehicletracking.protocols.gt100.alert;

import com.openvehicletracking.core.AbstractLocationMessageBuilder;

public class AlertMessageBuilder extends AbstractLocationMessageBuilder<AlertMessage> {
    @Override
    public AlertMessage build(Object... args) {
        return new AlertMessage(this);
    }
}
