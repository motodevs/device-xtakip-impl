package com.openvehicletracking.device.xtakip.hxprotocol;


import com.openvehicletracking.core.GsonFactory;
import com.openvehicletracking.core.message.impl.AbstractCommandMessage;

/**
 * Created by oksuz on 20/05/2017.
 *
 */
public final class HXProtocolMessage extends AbstractCommandMessage {

    @Override
    public boolean isReplyRequired() {
        return false;
    }

    @Override
    public String asJsonString() {
        return GsonFactory.getGson().toJson(this);
    }
}
