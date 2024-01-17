package edu.qc.seclass.rlm;

import java.io.Serializable;

public class ReminderList implements Serializable {
    protected int list_id;
    protected String list_name;
    protected int Color;

    //toString

    @Override
    public String toString() {
        return "ReminderList{" +
                "List_ID=" + list_id +
                ", List_Name='" + list_name + '\'' +
                '}';
    }

    //constructors

    public ReminderList(String list_Name, int color) {
        this.list_name = list_Name;
        this.Color = color;
    }

    public ReminderList(int list_id, String list_name) {
        this.list_id = list_id;
        this.list_name = list_name;
    }

    public ReminderList(int List_ID, String List_Name, int color) {
        this.list_id = List_ID;
        this.list_name = List_Name;
        this.Color = color;
    }

    public ReminderList() {
    }
    //getters and setters


    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }


}
