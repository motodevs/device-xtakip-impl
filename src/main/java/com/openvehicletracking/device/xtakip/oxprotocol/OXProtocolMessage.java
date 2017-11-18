package com.openvehicletracking.device.xtakip.oxprotocol;


import com.openvehicletracking.core.GsonFactory;
import com.openvehicletracking.core.message.impl.AbstractCommandMessage;

/**
 * Created by oksuz on 20/05/2017.
 *
 */

public final class OXProtocolMessage extends AbstractCommandMessage {

    @Override
    public boolean isReplyRequired() {
        return false;
    }


    @Override
    public OXProtocolMessage fromJsonString(String json) {
        return GsonFactory.getGson().fromJson(json, this.getClass());
    }

    @Override
    public String asJsonString() {
        return GsonFactory.getGson().toJson(this);
    }
}
