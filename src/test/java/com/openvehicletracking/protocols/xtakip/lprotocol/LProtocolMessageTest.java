package com.openvehicletracking.protocols.xtakip.lprotocol;

import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.xtakip.XTakipAlerts;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by oksuz on 07/09/2017.
 *
 */
public class LProtocolMessageTest {

    private String msgString = "@L0368630710199160521203121714301644101319202838495400001009000000057D08200D!";

    @Test
    public void handlerTest() throws Exception {
        byte[] msg = msgString.getBytes();
        LProtocolMessageHandler handler = new LProtocolMessageHandler();
        LProtocolMessage message = (LProtocolMessage) handler.handle(msg, null);
        Assert.assertTrue(handler.isMatch(msg));
        Assert.assertEquals(msgString, message.getRaw());
    }

    @Test
    public void alertTest() throws Exception {
        byte[] msg = msgString.getBytes();
        LProtocolMessageHandler handler = new LProtocolMessageHandler();
        LProtocolMessage message = (LProtocolMessage) handler.handle(msg, null);

        HashMap<String, Object> attributes = message.getAttributes().get();

        Assert.assertTrue(attributes.containsKey(Message.ATTR_ALERT));

        String actualAlertDesc = ((Alert) attributes.get(Message.ATTR_ALERT)).getDescription();
        String expectedAlertDesc = XTakipAlerts.getAll().get(13).getDesciption();

        Assert.assertEquals(actualAlertDesc, expectedAlertDesc);

    }
}