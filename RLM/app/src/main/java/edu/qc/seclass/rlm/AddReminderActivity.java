package edu.qc.seclass.rlm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddReminderActivity extends AppCompatActivity {

    Button addReminderBtn;
    AutoCompleteTextView reminderType;
    AutoCompleteTextView reminderName;
    EditText date;
    EditText time;
    EditText location;
    SwitchCompat alert;
    Spinner reminderList;
    private Map<String, ReminderList> listMap = new HashMap<>();
    ReminderType type;
    ReminderList selectedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        // find view by id
        addReminderBtn = findViewById(R.id.addNewReminderButton);
        reminderType = findViewById(R.id.reminderType);
        reminderName = findViewById(R.id.reminderTitle);
        reminderList = findViewById(R.id.listSpinner);
        date = findViewById(R.id.reminderDate);
        time = findViewById(R.id.reminderTime);
        location = findViewById(R.id.locationText);
        alert = findViewById(R.id.alertSwitch);

        reminderType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Create an array of example suggestions (replace with your data source)
                ReminderDB db = new ReminderDB(AddReminderActivity.this);
                String[] suggestions = db.getEveryReminderTypeName().toArray(new String[0]);
                db.close();

                // Set up the adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddReminderActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        suggestions);

                // Set the adapter to the AutoCompleteTextView
                reminderType.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                ReminderDB db = new ReminderDB(AddReminderActivity.this);
                //creates a reminder type that belongs to the selected list
                //makes sure list cannot share the same type
                if (db.isTypeExists(s.toString())){
                    //if the type exists automatically select the list associated with the type
                    type = db.getReminderTypeByTypeName(s.toString());
                    populateSpinner(type.getList_id());
                }

            }
        });

        reminderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //drop down that suggests existing reminder names that exist in db
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Create an array of example suggestions (replace with your data source)
                ReminderDB db = new ReminderDB(AddReminderActivity.this);
                String[] suggestions = db.getEveryReminderName().toArray(new String[0]);
                db.close();

                // Set up the adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddReminderActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        suggestions);

                // Set the adapter to the AutoCompleteTextView
                reminderName.setAdapter(adapter);
            }

            //If reminder with same name exists and user selects that reminder,
            // then the type name,date,time,alert is autofilled
            @Override
            public void afterTextChanged(Editable s) {
                ReminderDB db = new ReminderDB(AddReminderActivity.this);
                if (db.doesReminderNameExist(s.toString())) {
                    //if it exists retrieve the reminder by creating it from reminder table
                    // using db
                    Reminder oldReminder = db.getReminderByName(s.toString());
                    //and populate the addreminder activity with the data
                    String typeName = oldReminder.getType().getTypeName();
                    reminderType.setText(typeName);
                    date.setText(oldReminder.getDate());
                    time.setText(oldReminder.getTime());
                    alert.setChecked(oldReminder.isAlertOn());
                    ReminderList oldReminderList = oldReminder.getType().getList_id();
                    populateSpinner(oldReminderList);


                }
                db.close();
            }
        });

        //populates the spinner to show the reminder lists
        populateSpinner();

        addReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gets reminder name and type name from the text field and converts them to string.
                String name = reminderName.getText().toString().trim();

                ReminderDB db = new ReminderDB(AddReminderActivity.this);

                if (!db.doesReminderNameExist(name)) {

                    String typeName = reminderType.getText().toString().trim();
                    ReminderListManager rlm = ReminderListManager.getInstance(AddReminderActivity.this);


                    //the user can not leave type name and reminder name blank
                    if (name.isEmpty() || typeName.isEmpty()) {
                        Toast.makeText(AddReminderActivity.this, "Please enter a name and type for the reminder", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //the date has to be in valid format
                    if (!isValidDateTimeFormat(date.getText().toString().trim(), time.getText().toString().trim())) {
                        Toast.makeText(AddReminderActivity.this, "Please enter a valid date/time format (MM/DD/YYYY) (HH:MM am/pm)", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //selected list is the list user selects from the spinner
                    if (reminderList.getSelectedItem() != null){
                        if (db.isTypeExists(typeName) && listMap.get(reminderList.getSelectedItem())!= type.getList_id() ) {
                            selectedList = type.getList_id();
                            populateSpinner(selectedList);
                            Toast.makeText(AddReminderActivity.this, "The appropriate list "+type.getList_id().getList_name() +" has been selected for your new reminder.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            selectedList = listMap.get(reminderList.getSelectedItem());
                        }
                    }



                    //creates a reminder type that belongs to the selected list
                    type = new ReminderType(typeName, selectedList);
                    //checks if type already exists in db
                    boolean successType;
                    if (db.isTypeExists(typeName)) {
                        successType = true;
                    }
                    // if it does not exit we make add a new reminder type
                    else {
                        //  the reminder type is being added to db
                        successType = rlm.addReminderType(type);
                    }
                    db.close();


                    //if successfully added, then the following tasks takes place
                    if (successType) {
                        // Continue with creating reminder with given type
                        Reminder reminder = new Reminder(-1, selectedList.getList_id(), type, name, date.getText().toString(), time.getText().toString(), location.getText().toString(), false, alert.isChecked());


                        //saves reminder to db
                        boolean successReminder = ReminderListManager.getInstance(AddReminderActivity.this).addReminder(reminder);


                        //if the reminder is successfully saved
                        if (successReminder) {
                            // creates alarm
                            try {
                                if (alert.isChecked()) {
                                    Alarm.create(AddReminderActivity.this, reminder);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace(); // Print the stack trace to help diagnose the issue
                                Toast.makeText(AddReminderActivity.this, "Error creating alarm", Toast.LENGTH_SHORT).show();
                            }

                            //Creates an intent to main activity.
                            Intent intent = new Intent(AddReminderActivity.this, MainActivity.class);

                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(AddReminderActivity.this, "Reminder name already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void populateSpinner() {
        List<ReminderList> existingLists = ReminderListManager.getInstance(AddReminderActivity.this).getAllreminderLists();

        // Clear the map before populating it
        listMap.clear();

        // Populate the map with list names as keys and lists as values
        for (ReminderList list : existingLists) {
            listMap.put(list.getList_name(), list);
        }

        // Create an ArrayAdapter with the list names to display in the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(listMap.keySet()));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        reminderList.setAdapter(adapter);
    }

    private void populateSpinner(ReminderList oldReminderList) {
        List<ReminderList> existingLists = ReminderListManager.getInstance(AddReminderActivity.this).getAllreminderLists();

        listMap.clear();

        for (ReminderList list : existingLists) {
            listMap.put(list.getList_name(), list);
        }

        // Create an ArrayAdapter with the list names to display in the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(listMap.keySet()));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        reminderList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Now you can get the position using oldReminderList
        int defaultListIndex = adapter.getPosition(oldReminderList.getList_name());
        reminderList.setSelection(defaultListIndex);
    }


    //method to check if the date entered is valid or not

    private boolean isValidDateTimeFormat(String date, String time) {

        if (date.isEmpty() && time.isEmpty()) {
            return true;
        }

        if(date.isEmpty() || time.isEmpty()) {
            return false;
        }

        return date.matches("\\d{1,2}/\\d{1,2}/\\d{4}") && time.matches("^(0?[1-9]|1[0-2]):[0-5][0-9][APap][mM]$");

    }

}