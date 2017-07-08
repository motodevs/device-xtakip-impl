package com.openmts.device.xtakip.adapter;

import com.openmts.core.adapter.ResponseAdapter;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by yo on 01/06/2017.
 */
public class MessageResponseAdapter implements ResponseAdapter<JsonObject, List<JsonObject>> {

    @Override
    public JsonObject result(List<JsonObject> jsonObjects) {
        return null;
    }
}
