package com.openvehicletracking.device.xtakip.oxprotocol;


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

}
