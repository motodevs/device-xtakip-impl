package com.openvehicletracking.protocols.xtakip.lprotocol;

import com.openvehicletracking.core.message.LocationMessage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by oksuz on 07/09/2017.
 *
 */
public class LProtocolMessageTest {

    @Test
    public void fromJson() throws Exception {
        String json = "{\"deviceId\": \"123\"}";
        LProtocolMessage message = LocationMessage.fromJson(json, LProtocolMessage.class);
        Assert.assertEquals("123", message.getDeviceId());
    }
}