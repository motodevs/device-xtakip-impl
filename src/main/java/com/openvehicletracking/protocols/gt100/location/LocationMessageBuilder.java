package com.openvehicletracking.protocols.gt100.location;

import com.openvehicletracking.core.AbstractLocationMessageBuilder;

public class LocationMessageBuilder extends AbstractLocationMessageBuilder<GT100LocationMessage> {

    @Override
    public GT100LocationMessage build(Object... args) {
        return new GT100LocationMessage(this);
    }
}
