package edu.qc.seclass.rlm;

import java.io.Serializable;

public class ReminderType implements Serializable {

    protected String type;
    protected ReminderList List;

    public ReminderType() {
    }

    public ReminderType(String type, ReminderList List) {
        this.type = type;
        this.List = List;
    }
    public String getTypeName(){
        return  type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public ReminderList getList_id() {
        return List;
    }

    public void setList_id(int id) {
        this.List.setList_id(id);
    }


    public String toString () {

        return "ReminderListType{" + "ReminderTypeName= " + type + "ReminderList_ID= " + List + '}';
    }


}
