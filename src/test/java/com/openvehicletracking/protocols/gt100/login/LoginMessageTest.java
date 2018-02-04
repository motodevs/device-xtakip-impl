package com.openvehicletracking.protocols.gt100.login;


import com.openvehicletracking.protocols.gt100.TestConnectionHolder;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class LoginMessageTest {

    private byte[] loginMsg = new byte[]{
            120,120, // header
            17, // package length
            1, // message type
            5,88,37,64,0,-120,99,38, // deviceId
            19,0, // type identity code
            50,1, // timezone
            0,30, // information serial
            120,-78, // error check
            13,10 // \r\n
    };


    @Test
    public void parseTest() throws Exception {

        LoginMessageHandler handler = new LoginMessageHandler();
        LoginMessage loginMessage = (LoginMessage) handler.handle(ByteBuffer.wrap(loginMsg), new TestConnectionHolder());

        Assert.assertEquals("558254000886326", loginMessage.getDevice().getId());

    }
}