package edu.qc.seclass.rlm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ReminderTypeViewActivity extends AppCompatActivity {
    ListView listView;
    private static final int DELETE_REMINDER_TYPE_REQUEST = 1;

    ArrayAdapter<String> adapter;
    List<String> reminderItems;
    ReminderList reminderList;
    ReminderType newReminderType;
//    Button DeleteListButton;
//    Button UpdateListButton;
    ReminderType selectedReminderType;
    ReminderDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new ReminderDB(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_type_view);

        listView = findViewById(R.id.reminderListOne);
        reminderItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reminderItems);
        //gets all the reminders from db and and adds to list
        List<ReminderType> allReminderTypes = db.getEveryReminderType();
        listView.setAdapter(adapter);


        // Receive new reminder types AND list
        Intent intent = getIntent();
        Log.d("ReminderTypeViewActivity", "Received Intent: " + intent);

        // checks if selected list was received
        if (intent != null && intent.hasExtra("CHOSEN_LIST")) {
            //checks if new reminder type was created
            if (intent.hasExtra("NEW_REMINDER_TYPE")) {
                newReminderType = (ReminderType) intent.getSerializableExtra("NEW_REMINDER_TYPE");
            }
            reminderList = (ReminderList) intent.getSerializableExtra("CHOSEN_LIST");
            Log.d("ReminderTypeViewActivity", "Received NEW_REMINDER_TYPE: " + newReminderType);
            Log.d("ReminderTypeViewActivity", "Received CHOSEN_LIST: " + reminderList);

            //adds the reminder type name to reminder Items
            if (newReminderType != null && !newReminderType.getTypeName().isEmpty()) {

                reminderItems.add(newReminderType.getTypeName());
                Log.d("ReminderTypeViewActivity", "newReminderType" + newReminderType.getTypeName());
                adapter.notifyDataSetChanged(); // Notify adapter about the data change

            } else {
                // newReminderType is null
                Log.d("ReminderTypeViewActivity", "newReminderType is null");
            }
        }


        //adds to list view if list id in type matches with list id in selected list
        // and if there exists a reminder that is has matching reminderList list id and reminder type
        for (ReminderType reminderType : allReminderTypes) {
            ReminderDB db = new ReminderDB(ReminderTypeViewActivity.this);

            if (reminderType.getList_id() != null && reminderList != null && reminderType.getList_id().getList_id() == reminderList.getList_id() && db.doesTypeExistInReminderList(reminderList)) {
                db.close();
                reminderItems.add(reminderType.getTypeName());
            }

        }

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // gets the selected reminder type name from the list
                String typeOfSelectedReminder = reminderItems.get(position);
                //filters the reminder type whose name is equal to selected reminder type name from the database
                selectedReminderType = db.getEveryReminderType().stream()
                        .filter(type -> type.getTypeName().equals(typeOfSelectedReminder))
                        .findFirst()
                        .orElse(null);
                //sends data to listReminder
                if (selectedReminderType != null) {
                    Intent intent = new Intent(ReminderTypeViewActivity.this, ListReminderView.class);
                    intent.putExtra("SELECTED_REMINDER_TYPE", selectedReminderType);
                    startActivityForResult(intent, DELETE_REMINDER_TYPE_REQUEST);
                    Log.d("ReminderTypeViewActivity", "Selected ReminderType" + selectedReminderType.getTypeName());

                } else {
                    // Handle the case when selectedReminderType is null
                    Log.e("ReminderTypeViewActivity", "Selected ReminderType is null");
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DELETE_REMINDER_TYPE_REQUEST && resultCode == RESULT_OK) {
            // Check if the DELETED_REMINDER_TYPE extra is present
            if (data != null && data.hasExtra("DELETED_REMINDER_TYPE")) {
                ReminderType deletedReminderType = (ReminderType) data.getSerializableExtra("DELETED_REMINDER_TYPE");
                Log.d("ReminderTypeViewActivity", "Received intent from ListReminderView");
                Log.d("ReminderTypeViewActivity", "Deleted ReminderType: " + deletedReminderType.getTypeName());

                // Update reminderItems based on the data from the database
                reminderItems.clear();
                List<ReminderType> allReminderTypes = db.getEveryReminderType();
                for (ReminderType reminderType : allReminderTypes) {
                    if (reminderType.getList_id() != null && reminderList != null && reminderType.getList_id().getList_id() == reminderList.getList_id()) {
                        reminderItems.add(reminderType.getTypeName());
                    }
                }

                // Notify adapter about the data change
                adapter.notifyDataSetChanged();

            }
        }
    }


    //on pressing back button takes u to main activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainActivityReturn = new Intent(ReminderTypeViewActivity.this, MainActivity.class);
        startActivity(mainActivityReturn);
        finish();
    }
}