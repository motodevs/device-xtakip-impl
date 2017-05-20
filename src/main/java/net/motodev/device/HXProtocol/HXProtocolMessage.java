package net.motodev.device.HXProtocol;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import net.motodev.core.Callback;
import net.motodev.core.Message;
import net.motodev.core.utility.DateUtility;
import net.motodev.device.DeviceConstants;
import net.motodev.device.XTakip;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by oksuz on 20/05/2017.
 */
public class HXProtocolMessage implements Message {

    private String deviceId;
    private Date datetime;
    private String requestId;
    private String[] params;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getRequestId() {
        return requestId;
    }

    public String[] getParams() {
        return params;
    }

    @Override
    public String subject() {
        return DeviceConstants.XTAKIP_HX_MESSAGE;
    }


    @Override
    public void save(MongoClient mongoClient, String collection, Callback<Object> callback) {
        JsonObject query = new JsonObject();
        query.put("deviceId", deviceId);
        query.put("requestId", requestId);

        JsonObject update = new JsonObject();
        JsonObject $set = new JsonObject();
        JsonObject deviceResponse = new JsonObject();

        deviceResponse.put("params", new JsonArray(Arrays.asList(params)));
        deviceResponse.put("responseTime", new JsonObject().put("$date", DateUtility.toISODateFormat(datetime)));

        $set.put("response", deviceResponse);
        $set.put("read", true);

        update.put("$set", $set);

        if (null == callback) {
            mongoClient.updateCollection(collection, query, update, result -> mongoClient.close());
        } else {
            mongoClient.updateCollection(collection, query, update, result -> callback.call(result));
        }
    }

    @Override
    public String device() {
        return XTakip.NAME;
    }

    @Override
    public String deviceId() {
        return this.deviceId;
    }
}
