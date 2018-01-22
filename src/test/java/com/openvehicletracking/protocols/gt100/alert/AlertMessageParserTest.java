package com.openvehicletracking.protocols.gt100.alert;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class AlertMessageParserTest {
    @Test
    public void parse() throws Exception {

        byte[] rawMsg = new byte[] {120,120,37,38,18,1,16,21,9,15,-59,4,100,-27,0,3,31,-102,48,1,21,86,9,1,30,3,91,117,0,-124,51,72,6,1,3,2,0,13,-62,-6,13,10};

        AlertMessageParser parser = new AlertMessageParser(ByteBuffer.wrap(rawMsg));

        parser.parse();


    }

}