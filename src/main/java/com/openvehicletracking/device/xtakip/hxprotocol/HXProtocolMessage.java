package com.openvehicletracking.device.xtakip.hxprotocol;


import com.google.gson.Gson;
import com.openvehicletracking.core.GsonFactory;
import com.openvehicletracking.core.message.impl.AbstractCommandMessage;

/**
 * Created by oksuz on 20/05/2017.
 *
 */
public final class HXProtocolMessage extends AbstractCommandMessage {

    private final transient Gson gson = GsonFactory.newGson();

    @Override
    public boolean isReplyRequired() {
        return false;
    }


    @Override
    public HXProtocolMessage fromJsonString(String json) {
        return gson.fromJson(json, this.getClass());
    }

    @Override
    public String asJsonString() {
        return gson.toJson(this);
    }
}
