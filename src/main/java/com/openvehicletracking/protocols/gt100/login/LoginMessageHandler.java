package com.openvehicletracking.protocols.gt100.login;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.Device;
import com.openvehicletracking.core.Reply;
import com.openvehicletracking.core.protocol.Message;
import com.openvehicletracking.protocols.gt100.GT100BaseMessageHandler;
import com.openvehicletracking.protocols.gt100.GT100Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;
public class LoginMessageHandler extends GT100BaseMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginMessageHandler.class);

    @Override
    public boolean isMatch(Object msg) {
        if (!(msg instanceof byte[])) {
            return false;
        }

        ByteBuffer buffer = ByteBuffer.wrap((byte[]) msg);
        short header = buffer.getShort();
        if (header != 0x7878) {
            return false;
        }

        byte length = buffer.get();
        byte type = buffer.get();
        return type == LoginMessage.TYPE;
    }

    @Override
    protected Message handle(ByteBuffer msg, ConnectionHolder<?> connectionHolder) {
        byte[] byteArrMsg = msg.array();

        byte[] header = Arrays.copyOfRange(byteArrMsg, 0, 2);
        byte[] lenght = Arrays.copyOfRange(byteArrMsg, 2, 3);
        byte[] type = Arrays.copyOfRange(byteArrMsg, 3, 4);
        byte[] serial = Arrays.copyOfRange(byteArrMsg, 4, 12);

        Device device = new GT100Device(toHex(serial));
        LoginMessage loginMessage = new LoginMessage(device, byteArrMsg);

        LOGGER.info("message parsed: {}", loginMessage.asJson());


        ByteBuffer response = ByteBuffer.allocate(10);
        response.put((byte) 0x78)
                .put((byte) 0x78)
                .put((byte) 0x05)
                .put((byte) 0x01)
                .put((byte) 0x00)
                .put((byte) 0x01)
                .put((byte) 0xD9)
                .put((byte) 0xDC)
                .put((byte) 0x0D)
                .put((byte) 0x0A);

        connectionHolder.write(new Reply(response.array()));
        return loginMessage;
    }


}
