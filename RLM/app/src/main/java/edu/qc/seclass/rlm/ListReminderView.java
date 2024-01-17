
package edu.qc.seclass.rlm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListReminderView extends AppCompatActivity {
    ListView listView;
    private static final int DELETE_REMINDER_TYPE_REQUEST = 2;
    private  static  final  int EDIT_REMINDER_REQUEST = 3;
    ArrayAdapter<String> adapter;
    List<String> reminderItems;
    List<Reminder> allReminders;
    Reminder newReminder;
    ReminderType selectedReminderType;
    ReminderList selectedList;

    Button DeleteType;
    String updatedReminderName;
    String updatedReminderDate;
    String updatedReminderTime;
    boolean updatedAlert;
    Reminder updatedReminder;

    ReminderDB db;
    Reminder oldReminder;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reminder_view);
        db = new ReminderDB(this);
        // Find the views
        listView = findViewById(R.id.listOfReminders);
        DeleteType = findViewById(R.id.DeleteType);


        reminderItems = new ArrayList<>();
        // Create an ArrayAdapter to populate the ListView with reminders
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reminderItems);
        // Set the adapter to the ListView
        listView.setAdapter(adapter);
//        if (getIntent().hasExtra("DELETED_REMINDER_NAME")) {
//            String deletedReminderName = getIntent().getStringExtra("DELETED_REMINDER_NAME");
//
//            // Remove the deleted reminder from the reminderItems list
//            reminderItems.remove(deletedReminderName);
//
//            // Notify adapter about the data change
//            adapter.notifyDataSetChanged();
//        }

        //allReminders = ReminderListManager.getInstance(this).getAllReminder();
        allReminders = db.getEveryReminder();

        adapter.notifyDataSetChanged();

        //receive selected reminder types from type view and new reminder from add reminder activity
        Intent intent = getIntent();


        //if selected reminder type is received then goes to next actions
        if (intent != null && intent.hasExtra("SELECTED_REMINDER_TYPE")) {

            //checks if new reminder was added and received
            if (intent.hasExtra("NEW_REMINDER")) {
                newReminder = (Reminder) intent.getSerializableExtra("NEW_REMINDER");
            }


            selectedReminderType = (ReminderType) intent.getSerializableExtra("SELECTED_REMINDER_TYPE");
            selectedList = (ReminderList) intent.getSerializableExtra("SELECTED_LIST");

            ReminderDB db = new ReminderDB(ListReminderView.this);
            //populates the reminderItems List as long as the reminder is not null or empty
            if (newReminder != null && !newReminder.getReminderName().isEmpty() &&
            db.doesThisReminderExistForSpecificListAndType(newReminder.getReminderName(), selectedList.getList_id(), selectedReminderType.getTypeName())) {
                reminderItems.add(newReminder.getReminderName());
                adapter.notifyDataSetChanged(); // Notify adapter about the data change
            }
            db.close();

        }



        //adds to list view if type name of reminder matches with type name of selected type
        for (Reminder reminder : allReminders) {
            ReminderDB db = new ReminderDB(ListReminderView.this);
            if (reminder.getType() != null && selectedReminderType != null &&
                    reminder.getType().getTypeName().equals(selectedReminderType.getTypeName()) && db.doesReminderExistInType(selectedReminderType) ) {
                reminderItems.add(reminder.getReminderName());
            }
        }


        //sends reminder to individualReminderViews
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get the reminder name of the selected reminder
                String selectedReminderName = reminderItems.get(position);

                //filters the selected reminder whole name is equal to selected reminder name from the database
                Reminder selectedReminder = db.getEveryReminder().stream()
                        .filter(reminder -> reminder.getReminderName().equals(selectedReminderName))
                        .findFirst()
                        .orElse(null);

                //if selected reminder is not null then send data to individual reminder view activity
                if (selectedReminder != null) {
                    Intent intent = new Intent(ListReminderView.this, IndividualReminderViewActivity.class);
                    intent.putExtra("SELECTED_REMINDER", selectedReminder);
                    startActivityForResult(intent,EDIT_REMINDER_REQUEST);
                    //finish();
                } else {
                    // Handle the case when selectedReminder is null
                    Log.e("ListReminderView", "Selected Reminder is null");
                }
            }
        });

        //Delete type function to delete types with reminders belonging to the type
        DeleteType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type_name = selectedReminderType.getTypeName();
                Log.d("ListReminderView", "Type Name to Delete: " + type_name);
                ReminderDB db = new ReminderDB(ListReminderView.this);
                //removes the reminder from the db
                boolean success = db.removeReminderRelatedToType(selectedReminderType);
                if (success) {
                    Toast.makeText(ListReminderView.this, "Deleted " + type_name, Toast.LENGTH_SHORT).show();

                    // Start ReminderTypeViewActivity with startActivityForResult
                    Intent intent = new Intent();
                    intent.putExtra("DELETED_REMINDER_TYPE",selectedReminderType);
                    setResult(RESULT_OK, intent);
                    finish();
//                    //receives the result when the activity ends
//                    startActivityForResult(intent, DELETE_REMINDER_TYPE_REQUEST);
                } else {
                    Toast.makeText(ListReminderView.this, "Could not delete " + type_name, Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DELETE_REMINDER_TYPE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Check if the DELETED_REMINDER_TYPE extra is present
            if (data.hasExtra("DELETED_REMINDER_TYPE")) {
                ReminderType deletedReminderType = (ReminderType) data.getSerializableExtra("DELETED_REMINDER_TYPE");

                // Update reminderItems based on the data from the database
                List<Reminder> allReminders = db.getEveryReminder();

                // Filter out reminders that belong to the deleted type
                reminderItems.clear();
                for (Reminder reminder : allReminders) {
                    if (reminder.getType() == null || !reminder.getType().equals(deletedReminderType)) {
                        reminderItems.add(reminder.getReminderName());
                    }
                }

                // Notify adapter about the data change
                adapter.notifyDataSetChanged();
            }
        }
        if(requestCode == EDIT_REMINDER_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.hasExtra("SELECTED_REMINDER")) {
                //gets selected reminder from individual reminder class
                oldReminder = (Reminder) data.getSerializableExtra("SELECTED_REMINDER");
                Log.d("ListReminderView", "old Reminder" + oldReminder);
                //creates updated reminder which has same list id, type and reminder id
                updatedReminder = new Reminder(oldReminder.getReminder_id(),oldReminder.getListID(), oldReminder.getType());
                Log.d("ListReminderView", "updated reminder id" + updatedReminder.getReminder_id());
                Log.d("ListReminderView", "updated reminder type" + updatedReminder.getType().getTypeName());

                // gets the updated reminder name from IndividualReminder and sets the new name
                if (data.hasExtra("UPDATED_REMINDER_NAME")) {
                    updatedReminderName = data.getStringExtra("UPDATED_REMINDER_NAME");
                    updatedReminder.setReminderName(updatedReminderName);
                }
                // gets the updated reminder date from IndividualReminder and sets the new date
                if (data.hasExtra("UPDATED_REMINDER_DATE")) {
                    updatedReminderDate = data.getStringExtra("UPDATED_REMINDER_DATE");
                    updatedReminder.setDate(updatedReminderDate);
                }
                // gets the updated reminder time from IndividualReminder and sets the new time
                if (data.hasExtra("UPDATED_REMINDER_TIME")) {
                    updatedReminderTime = data.getStringExtra("UPDATED_REMINDER_TIME");
                    updatedReminder.setTime(updatedReminderTime);
                }
                // gets the updated reminder alert from IndividualReminder and sets the new alert
                if (data.hasExtra("UPDATED_REMINDER_ALERT")) {
                    updatedAlert = data.getBooleanExtra("UPDATED_REMINDER_ALERT", false);
                    updatedReminder.setAlertOn(updatedAlert);
                }



                //updates reminder in db
                db.updateReminder(updatedReminder);
                allReminders = db.getEveryReminder();
                reminderItems.remove(oldReminder.getReminderName());
                reminderItems.add(updatedReminderName);
                adapter.notifyDataSetChanged();

            }
            if(data.hasExtra("DELETED_REMINDER")) {
                Reminder deletedReminder = (Reminder) data.getSerializableExtra("DELETED_REMINDER");
                allReminders = db.getEveryReminder();
                reminderItems.remove(deletedReminder.getReminderName());
                adapter.notifyDataSetChanged();
            }

    }


}
}