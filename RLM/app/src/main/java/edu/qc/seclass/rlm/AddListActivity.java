package edu.qc.seclass.rlm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class AddListActivity extends AppCompatActivity {

    //list of color for the spinner
    private final List<Integer> colorsIntegers = Arrays.asList(
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.YELLOW,
            Color.WHITE
    );

    // array of strings to display the color names
    private final String[] colorsStrings = new String[]{
            "Black",
            "Blue",
            "Cyan",
            "Dark gray",
            "Gray",
            "Green",
            "Light gray",
            "Magenta",
            "Red",
            "Yellow",
            "White"
    };

    private ReminderListManager rlm;
    private Spinner colorSpinner;
    private int action;
    String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        rlm = ReminderListManager.getInstance(this);
        colorSpinner = findViewById(R.id.colorSpinner);

        //populates the spinner with color
        colorSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colorsStrings));

        //receives data from MainActivity
        action = getIntent().getIntExtra("action", R.string.add_list);
        TextView button = findViewById(R.id.newAddList);

        //Edit operations
        if (action == R.string.edit_list) {

            button.setText(R.string.edit_list);

            String name = getIntent().getStringExtra("list");
            //fetching list from database with the given name
            ReminderList list = rlm.getReminderLists().get(name);
            //storing the name of fetched list
            oldName = list.getList_name();

            ((EditText) findViewById(R.id.newListName)).setText(oldName);
            colorSpinner.setSelection(colorsIntegers.indexOf(list.getColor()));

        } else
            button.setText(R.string.add_list);

    }

    public void navigateToMainActivity(View v) {
        //fetching list name and storing it in a string
        String listName = ((EditText) findViewById(R.id.newListName)).getText().toString();
        //fetching list color index
        int selectedColorIndex = colorSpinner.getSelectedItemPosition();
        int listColor = -1;

        if (selectedColorIndex != AdapterView.INVALID_POSITION) {
            listColor = colorsIntegers.get(selectedColorIndex);
        }

        if (action == R.string.add_list) {

            //if the list  already exists, new list can not be created with same name
            if (rlm.getReminderLists().get(listName) != null) {
                Toast.makeText(this, "This reminder list name already exists!", Toast.LENGTH_LONG).show();
                return;
            }

            //adding list to db
            ReminderList reminderList = new ReminderList(listName, listColor);

            try {
                rlm.addReminderList(reminderList);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else if (action == R.string.edit_list) {

            //fetching the old reminder list
            ReminderList reminderList = rlm.getReminderLists().get(oldName);

            //set new name and color
            reminderList.setList_name(listName);
            reminderList.setColor(listColor);
            rlm.editReminderList(oldName, reminderList);

        }

        // Create an Intent to start the MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
