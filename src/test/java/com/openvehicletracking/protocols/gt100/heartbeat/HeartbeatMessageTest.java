package com.openvehicletracking.protocols.gt100.heartbeat;


import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.GsmSignalStrength;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.VoltageLevel;
import com.openvehicletracking.core.alert.Alert;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.GT100Alert;
import com.openvehicletracking.protocols.gt100.GT100Contants;
import com.openvehicletracking.protocols.gt100.TestConnectionHolder;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.HashMap;


public class HeartbeatMessageTest {

    private byte[] heartbeatMsg = new byte[]{
            120,120, // header
            10, // length
            19, // type
            1, // terminal info
            4, // voltage level
            1, // gsm signal strength
            0, // alert
            2, // alert language
            0, 12, // serial number 1
            127,-106, // error check
            13,10 // \r\n
            };
    private byte[] wrongHeartbeatMsg = new byte[]{120,120,10,0x14,1,5,2,0,2,1,110,63,-67,13,10};

    @Test
    public void handleTest() throws Exception {
        HeartbeatMessageHandler handler = new HeartbeatMessageHandler();
        Assert.assertTrue(handler.isMatch(heartbeatMsg));
        Assert.assertFalse(handler.isMatch(wrongHeartbeatMsg));
    }


    @Test
    public void parseTest() throws Exception {
        HashMap<String, Object> attributes = getAttributesForMessage(heartbeatMsg);
        Alert alert = (Alert) attributes.getOrDefault(Message.ATTR_ALERT, null);
        VoltageLevel voltageLevel = (VoltageLevel) attributes.getOrDefault(Message.ATTR_VOLTAGE, null);
        GsmSignalStrength gsmSignalStrength = (GsmSignalStrength) attributes.getOrDefault(Message.ATTR_GSM_SIGNAL_STRENGHT, null);

        Assert.assertTrue(alert == null);
        Assert.assertEquals(VoltageLevel.MEDIUM, voltageLevel);
        Assert.assertEquals(GsmSignalStrength.EXTREMELY_WEAK_SIGNAL, gsmSignalStrength);
    }


    @Test
    public void alertTest() {
        byte[] cloneHeartbeatMessage = heartbeatMsg.clone();
        cloneHeartbeatMessage[4] = 9; // change terminal info in bits 00001001 = 3,5 bits = 001 = Shock alert
        HashMap<String, Object> attributes = getAttributesForMessage(cloneHeartbeatMessage);

        Alert alert = (Alert) attributes.getOrDefault(Message.ATTR_ALERT, null);

        Assert.assertEquals(GT100Alert.SHOCK_ALERT, alert.getDescription());
    }

    @Test
    public void terminalInfoTest() {
        byte[] cloneHeartbeatMessage = heartbeatMsg.clone();
        cloneHeartbeatMessage[4] = (byte) 0xDE; // 222 = 11011110
        /*
        1 //  oil and electricity disconnected
        1 // GPS tracking is on
        011 // Low Battery Alarm
        1 //Charge On
        1 // ACC high
        0 // Defense Deactivated
        */
        HashMap<String, Object> attributes = getAttributesForMessage(cloneHeartbeatMessage);

        Alert alert = (Alert) attributes.getOrDefault(Message.ATTR_ALERT, null);
        Boolean gpsTrackingOn = (Boolean) attributes.getOrDefault(GT100Contants.GPS_TRACKING_ON, null);
        Boolean electricityDisconnected = (Boolean) attributes.getOrDefault(GT100Contants.OIL_AND_ELECTRICITY_DISCONNECTED, null);
        Boolean defenseActivated = (Boolean) attributes.getOrDefault(GT100Contants.DEFENSE_ACTIVATED, null);
        Boolean ignKeyOn = (Boolean) attributes.getOrDefault(Message.ATTR_IGN_KEY_ON, null);
        Boolean chargeOn = (Boolean) attributes.getOrDefault(GT100Contants.CHARGE_ON, null);

        Assert.assertEquals(GT100Alert.LOW_POWER, alert.getDescription());
        Assert.assertTrue(gpsTrackingOn);
        Assert.assertTrue(electricityDisconnected);
        Assert.assertFalse(defenseActivated);
        Assert.assertTrue(ignKeyOn);
        Assert.assertTrue(chargeOn);
    }

    @Test
    public void terminalInfoAndAlertTest() {
        byte[] cloneHeartbeatMessage = heartbeatMsg.clone();
        cloneHeartbeatMessage[4] = 9;
        cloneHeartbeatMessage[7] = 0x03;


        HashMap<String, Object> attributes = getAttributesForMessage(cloneHeartbeatMessage);

        Alert alert = (Alert) attributes.get(Message.ATTR_ALERT);
        Alert alert2 = (Alert) attributes.get(Message.ATTR_ADDITIONAL_ALERT);


        Assert.assertEquals(GT100Alert.VIBRATION_ALERT, alert.getDescription());
        Assert.assertEquals(GT100Alert.SHOCK_ALERT, alert2.getDescription());

    }

    private HashMap<String, Object> getAttributesForMessage(byte[] message) {
        return createHeartbeatMessageFromBytes(message).getAttributes().get();
    }

    private HeartbeatMessage createHeartbeatMessageFromBytes(byte[] message) {
        HeartbeatMessageHandler handler = new HeartbeatMessageHandler();
        return (HeartbeatMessage) handler.handle(ByteBuffer.wrap(message), new TestConnectionHolder());
    }


}