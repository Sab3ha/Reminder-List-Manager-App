package edu.qc.seclass.rlm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateList extends AppCompatActivity {

    Button updateList;
    TextView newListName;
    String updatedListName;
    ReminderList oldReminderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list);
        updateList = findViewById(R.id.updateButtonList);
        newListName = findViewById(R.id.updateListField);

        updateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatedListName = newListName.getText().toString().trim();

                //receives list name from typeViewActivity
                Intent intent = getIntent();
                if(intent != null && intent.hasExtra("LIST_NAME")) {
                   oldReminderList = (ReminderList) intent.getSerializableExtra("LIST_NAME");
                }
                //checks if the update name field is empty or not
                if(updatedListName.isEmpty()) {
                    Toast.makeText(UpdateList.this, "Please enter new list name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //navigates to main activity and sends the new list name and old list name
                Intent intent1 = new Intent(UpdateList.this, MainActivity.class);
                intent1.putExtra("NEW_LIST_NAME", updatedListName);
                intent1.putExtra("OLD_LIST", oldReminderList);
                startActivity(intent1);
                finish();
            }
        });
    }
}