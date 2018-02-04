package com.openvehicletracking.protocols.xtakip.hxprotocol;


import com.openvehicletracking.protocols.BaseCommandMessage;
import com.openvehicletracking.protocols.xtakip.XTakipProtocol;

/**
 * Created by oksuz on 20/05/2017.
 *
 */
public final class HXProtocolMessage extends BaseCommandMessage {

    @Override
    public String getProtocolName() {
        return XTakipProtocol.NAME;
    }

    @Override
    public int getType() {
        return 'H' + 'X';
    }
}
