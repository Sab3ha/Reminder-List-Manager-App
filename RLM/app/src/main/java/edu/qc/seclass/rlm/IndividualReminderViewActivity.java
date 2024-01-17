package edu.qc.seclass.rlm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


public class IndividualReminderViewActivity extends AppCompatActivity {

    Reminder SelectedReminder;
    Button deleteReminder;
    TextView reminderType;
    TextView reminderTitle;
    TextView reminderListName;
    Button updateReminder;
    String updatedTitle;
    String updatedDate;
    String updatedTime;
    boolean updatedAlert;
    TextView date;
    TextView time;
    SwitchCompat alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_reminder_view);

        deleteReminder = findViewById(R.id.deleteReminder);
        reminderType = findViewById(R.id.reminderType);
        reminderTitle = findViewById(R.id.reminderTitle);
        reminderListName = findViewById(R.id.reminderListName);
        updateReminder = findViewById(R.id.addNewReminderButton);
        alert = findViewById(R.id.alertSwitch);
        date = findViewById(R.id.reminderDate);
        time = findViewById(R.id.reminderTime);

        reminderType.setKeyListener(null);
        reminderType.setFocusable(false);
        reminderListName.setKeyListener(null);
        reminderListName.setFocusable(false);

        Intent intent = getIntent();
        Log.d("IndividualReminderView", "Received Intent: " + intent);

        //if the intent is not null and Selected reminder data is received then create selected reminder object
        if (intent != null && intent.hasExtra("SELECTED_REMINDER")) {
            SelectedReminder = (Reminder) intent.getSerializableExtra("SELECTED_REMINDER");
        }
        Log.d("IndividualReminderView", "received new reminder: " + SelectedReminder.getReminderName());

        //sets the text of textfields with the selected reminder name and type
        ReminderDB db = new ReminderDB(IndividualReminderViewActivity.this);
        if (db.doesReminderExist(SelectedReminder)) {
            reminderType.setText(SelectedReminder.getType().getTypeName());
            reminderTitle.setText(SelectedReminder.getReminderName());
            reminderListName.setText(db.getListNameById(SelectedReminder.getListID()));
            date.setText(SelectedReminder.getDate());
            time.setText(SelectedReminder.getTime());
            alert.setChecked(SelectedReminder.isAlertOn());
            db.close();
        } else {
            db.close();
            reminderType.setText("");
            reminderTitle.setText("");
            reminderListName.setText("");
        }

        //Method to delete individual reminder.
        // Method to delete individual reminder.
        deleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reminderName = SelectedReminder.getReminderName();
                ReminderDB db = new ReminderDB(IndividualReminderViewActivity.this);

                // Removes the selected reminder from the database
                boolean success = db.removeReminder(SelectedReminder);
                if (success) {
                    Toast.makeText(IndividualReminderViewActivity.this, "Deleted " + reminderName, Toast.LENGTH_SHORT).show();

                    Intent backToReminderListIntent = new Intent();
                    backToReminderListIntent.putExtra("DELETED_REMINDER", SelectedReminder);
                    //backToReminderListIntent.putExtra("SELECTED_REMINDER_TYPE", intent.getSerializableExtra("SELECTED_REMINDER_TYPE"));

                    //startActivity(backToReminderListIntent);
                    setResult(RESULT_OK, backToReminderListIntent);
                    finish(); // Finish the current instance of the activity
                } else {
                    Toast.makeText(IndividualReminderViewActivity.this, "Could not delete " + reminderName, Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });


        updateReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatedTitle = reminderTitle.getText().toString().trim();
                updatedDate = date.getText().toString().trim();
                updatedTime = time.getText().toString().trim();
                updatedAlert = alert.isChecked();

                //checks if the update name field is empty or not
                if (updatedTitle.isEmpty()) {
                    Toast.makeText(IndividualReminderViewActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
                    return;
                }

                //navigates to list reminder and sends the reminder that was selected to update and the new name
                Intent backToListReminderIntent = new Intent();
                backToListReminderIntent.putExtra("SELECTED_REMINDER", SelectedReminder);
                backToListReminderIntent.putExtra("UPDATED_REMINDER_NAME", updatedTitle);
                backToListReminderIntent.putExtra("UPDATED_REMINDER_DATE", updatedDate);
                backToListReminderIntent.putExtra("UPDATED_REMINDER_TIME", updatedTime);
                backToListReminderIntent.putExtra("UPDATED_REMINDER_ALERT", updatedAlert);
                //startActivity(backToListReminderIntent);
                setResult(RESULT_OK, backToListReminderIntent);
                finish();
            }
        });


    }


}