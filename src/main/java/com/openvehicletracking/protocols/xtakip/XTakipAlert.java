
package com.openvehicletracking.protocols.xtakip;


import com.openvehicletracking.core.alert.AlertAction;

import java.util.ArrayList;
import java.util.List;

public class XTakipAlert {

    private int id;
    private String desciption;
    private List<AlertAction> actions = new ArrayList<>();

    public XTakipAlert(int i, String s, List<AlertAction> infoAction) {
        this.id = i;
        this.desciption = s;
        this.actions = infoAction;
    }

    public int getId() {
        return id;
    }

    public String getDesciption() {
        return desciption;
    }

    public List<AlertAction> getActions() {
        return actions;
    }
}
