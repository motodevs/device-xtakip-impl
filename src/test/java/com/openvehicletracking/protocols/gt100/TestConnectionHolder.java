package com.openvehicletracking.protocols.gt100;

import com.openvehicletracking.core.ConnectionHolder;
import com.openvehicletracking.core.Reply;

public class TestConnectionHolder implements ConnectionHolder<Void> {

    @Override
    public void write(Reply reply) {

    }

    @Override
    public Void getConnection() {
        return null;
    }
}