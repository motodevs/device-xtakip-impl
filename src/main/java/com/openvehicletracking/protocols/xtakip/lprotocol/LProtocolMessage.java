package com.openvehicletracking.protocols.xtakip.lprotocol;

import com.openvehicletracking.core.*;
import com.openvehicletracking.core.exception.StateCreateNotSupportException;
import com.openvehicletracking.core.json.GsonFactory;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.BaseLocationMessage;
import com.openvehicletracking.protocols.xtakip.XTakip;
import com.openvehicletracking.protocols.xtakip.XTakipConstants;
import com.openvehicletracking.protocols.xtakip.XTakipProtocol;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;


/**
 * Created by oksuz on 19/05/2017.
 *
 */
public class LProtocolMessage extends BaseLocationMessage {

    private final XTakip device;

    public LProtocolMessage(AbstractLocationMessageBuilder<?> builder) {
        super(builder);
        LProtocolMessageBuilder lProtocolMessageBuilder = (LProtocolMessageBuilder) builder;
        device = new XTakip(lProtocolMessageBuilder.getDeviceId());
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

    @Nullable
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

    @Override
    public DeviceState createState() throws StateCreateNotSupportException {
        Optional<HashMap<String, Object>> attributes = this.getAttributes();
        boolean ignKeyOff = false;
        DeviceState state = new DeviceState();
        if (attributes.isPresent()) {
            if (attributes.get().containsKey(XTakipConstants.ATTR_IS_OFFLINE_RECORD)) {
                boolean isOfflineRecord = (boolean) attributes.get().get(XTakipConstants.ATTR_IS_OFFLINE_RECORD);
                if (isOfflineRecord) {
                    return null;
                }
            }

            if (attributes.get().containsKey(Message.ATTR_IGN_KEY_ON)) {
                ignKeyOff = (boolean) attributes.get().get(Message.ATTR_IGN_KEY_ON);
            }

            this.getAttributes().get().forEach(state::addAttribute);
        }

        state.setCreatedAt(new Date().getTime());
        state.setUpdatedAt(new Date().getTime());
        state.setDeviceDate(this.getDate().getTime());
        state.setDeviceStatus(DeviceStatus.ONLINE);
        state.setVehicleStatus(!ignKeyOff ? VehicleStatus.MOVING : VehicleStatus.PARKED);
        state.setGpsStatus(this.getStatus());
        state.setPosition(this.getPosition());

        return state;
    }
}
