package com.openvehicletracking.device.xtakip;


import com.openvehicletracking.core.JsonDeserializeable;
import com.openvehicletracking.core.JsonSerializeable;
import com.openvehicletracking.core.alert.AlertAction;

import java.util.List;

/**
 * Created by yo on 08/06/2017.
 */
public class XtakipAlert implements JsonDeserializeable<XtakipAlert>, JsonSerializeable {

    private int id;
    private String description;
    private List<AlertAction> actions;

    public XtakipAlert(int id, String description, List<AlertAction> actions) {
        this.id = id;
        this.description = description;
        this.actions = actions;
    }

    @Override
    public XtakipAlert fromJsonString(String json) {
        return null;
    }

    @Override
    public String asJsonString() {
        return null;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<AlertAction> getActions() {
        return actions;
    }

    public void setActions(List<AlertAction> actions) {
        this.actions = actions;
    }
}
