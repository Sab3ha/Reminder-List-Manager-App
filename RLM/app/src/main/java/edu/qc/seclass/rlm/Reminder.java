package edu.qc.seclass.rlm;

import java.io.Serializable;
import java.util.Objects;

public class Reminder implements Serializable {
    private int reminder_id;
    private int listID;
    private String reminderName;
    private String Date;
    private String Time;
    private String Location;
    private  ReminderType type;
    //private ReminderList list;
    protected boolean isCheckedOff;
    protected boolean isAlertOn;




    //constructors


    public Reminder(int reminder_id,int listID,ReminderType type,String reminderName, String date,String time, String location, boolean isCheckedOff, boolean isAlertOn) {
        this.reminder_id = reminder_id;
        this.reminderName = reminderName;
        this.listID = listID;
        //this.list = list;
        this.Date = date;
        this.Time = time;
        this.Location = location;
        this.isAlertOn = isAlertOn;
        this.isCheckedOff = isCheckedOff;
        this.type = type;
    }
    // constructor

    public Reminder(int reminder_id, String reminderName) {
        this.reminder_id = reminder_id;
        this.reminderName = reminderName;
        this.isCheckedOff = isCheckedOff;
    }

    public Reminder() {

    }

    public Reminder(int reminder_id, int listID, ReminderType type) {
        this.reminder_id = reminder_id;
        this.listID = listID;
        this.type = type;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public boolean isCheckedOff() {
        return isCheckedOff;
    }

    public void setCheckedOff(boolean checkedOff) {
        isCheckedOff = checkedOff;
    }

    public boolean isAlertOn() {
        return isAlertOn;
    }

    public void setAlertOn(boolean alertOn) {
        isAlertOn = alertOn;
    }

    //to string

    public ReminderType getType() {
        return type;
    }

    public void setType(ReminderType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "ReminderData{" +
                "reminder_id=" + reminder_id +
                ", reminderName='" + reminderName + '\'' +
                ", Date=" + Date +
                ", Location='" + Location + '\'' +
                ", isCheckedOff=" + isCheckedOff +
                ", isAlertOn=" + isAlertOn +
                ", type=" + type +
                ", time=" + Time +
                ", listId=" + listID +

                '}';
    }
}
