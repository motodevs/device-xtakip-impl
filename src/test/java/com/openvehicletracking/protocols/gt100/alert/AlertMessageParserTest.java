package com.openvehicletracking.protocols.gt100.alert;

import com.openvehicletracking.core.protocol.Message;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class AlertMessageParserTest {
    @Test
    public void parse() throws Exception {

        byte[] rawMsg = new byte[] {120,120,37,38,18,1,22,21,11,18,-59,4,100,-30,-88,3,31,-101,12,0,21,101,9,1,30,3,91,117,0,-122,49,80,4,3,2,2,0,16,62,-18,13,10};

        AlertMessageHandler handler = new AlertMessageHandler();

        Message message = handler.handle(rawMsg, null);

        System.out.println();

    }

}