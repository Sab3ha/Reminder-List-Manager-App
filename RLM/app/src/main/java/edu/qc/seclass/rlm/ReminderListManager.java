package edu.qc.seclass.rlm;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReminderListManager {

    private static ReminderListManager instance;

    private ReminderDB reminderDB;
    private Map<String, ReminderList> reminderLists;

    private List<ReminderType> allreminderTypes;

    private  List<Reminder> allReminder;
    private ReminderListManager(Context context) {
        this.reminderDB = new ReminderDB(context);
        this.reminderLists = reminderDB.getEveryReminderList().stream().collect(Collectors.toMap(ReminderList::getList_name, rl -> rl));
        this.allreminderTypes = new ArrayList<>();
        this.allReminder = new ArrayList<>();
        loadAllReminderTypes();
        loadAllReminder();

    }

    public List<ReminderType> getAllreminderTypes() {
        return allreminderTypes;
    };



    // Adds the type to the database and updates the local list
    public boolean addReminderType(ReminderType reminderType) {
        boolean success = reminderDB.addReminderType(reminderType);
        if (success) {
            allreminderTypes.add(reminderType);
        }
        return success;
    }

    // loads the reminder types when the page is refreshed
    private void loadAllReminderTypes() {
        List<ReminderType> loadedReminderTypes = reminderDB.getEveryReminderType();
        allreminderTypes.addAll(loadedReminderTypes);
    }



    // adds reminders to db
    public boolean addReminder(Reminder reminder) {
        allReminder.add(reminder);
        return reminderDB.addReminder(reminder);

    }

    //loads reminders when page is refreshed
    private void loadAllReminder() {
        List<Reminder> loadedReminder = reminderDB. getEveryReminder();
        allReminder.addAll(loadedReminder);
    }




    public Map<String, ReminderList> getReminderLists() {
        return reminderLists;
    }

    public void addReminderList(ReminderList reminderList) throws Exception {

        if(!reminderDB.addReminderList(reminderList))
            throw new Exception("Failed to add reminder list");

        reminderLists.put(reminderList.list_name, reminderList);

    }

    public void deleteReminderList(ReminderList reminderList) {
        reminderDB.removeReminderInsideList(reminderList);
        reminderLists.remove(reminderList.getList_name());

    }

    public void editReminderList(String oldName, ReminderList reminderList) {

        reminderDB.updateReminderList(reminderList);
        reminderLists.remove(oldName);
        reminderLists.put(reminderList.getList_name(), reminderList);

    }


    public static ReminderListManager getInstance(Context context) {

        if (instance == null)
            instance = new ReminderListManager(context.getApplicationContext());

        return instance;

    }
    public List<ReminderList> getAllreminderLists() {
        return new ArrayList<>(reminderLists.values());
    }


}