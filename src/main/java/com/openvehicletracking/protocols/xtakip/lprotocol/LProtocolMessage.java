package com.openvehicletracking.protocols.xtakip.lprotocol;

import com.openvehicletracking.core.*;
import com.openvehicletracking.core.json.GsonFactory;
import com.openvehicletracking.protocols.BaseLocationMessage;
import com.openvehicletracking.protocols.xtakip.XTakip;
import com.openvehicletracking.protocols.xtakip.XTakipProtocol;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;


/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class LProtocolMessage extends BaseLocationMessage {

    public LProtocolMessage(AbstractLocationMessageBuilder<?> builder) {
        super(builder);
        LProtocolMessageBuilder lProtocolMessageBuilder = (LProtocolMessageBuilder) builder;
        device = new XTakip(lProtocolMessageBuilder.getDeviceId());
        device.createStateFromMessage(this);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public GpsStatus getStatus() {
        return gpsStatus;
    }

    @Override
    public Object getRaw() {
        return raw;
    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public Date getDate() {
        return new Date(datetime);
    }

    @Override
    public Optional<HashMap<String, Object>> getAttributes() {
        return Optional.of(attributes);
    }

    @Override
    public void reply(Reply reply) {

    }

    @Override
    public String getProtocolName() {
        return XTakipProtocol.NAME;
    }

    @Override
    public int getType() {
        return 'L';
    }

    @Override
    public String asJson() {
        return GsonFactory.getGson().toJson(this);
    }
}
