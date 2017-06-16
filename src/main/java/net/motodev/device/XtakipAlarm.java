package net.motodev.device;

import net.motodev.core.alarm.AlarmAction;

import java.util.List;

/**
 * Created by yo on 08/06/2017.
 */
public class XtakipAlarm {

    private int id;
    private String description;
    private List<AlarmAction> actions;

    public XtakipAlarm(int id, String description, List<AlarmAction> actions) {
        this.id = id;
        this.description = description;
        this.actions = actions;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<AlarmAction> getActions() {
        return actions;
    }

    public void setActions(List<AlarmAction> actions) {
        this.actions = actions;
    }
}
