package edu.qc.seclass.rlm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private ReminderListManager rlm;
    private ArrayAdapter<String> rlmAdapter;
    ListView listView;
    List<String> rlNames;
    Map<String, ReminderList> reminderLists;
    ReminderDB db;
    private static final int DELETE_REMINDER_LIST_REQUEST = 3;
    private static final int EDIT_REMINDER_LIST_REQUEST = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // In MainActivity onCreate method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.search);

        //find views by id
        rlm = ReminderListManager.getInstance(this);
        db = new ReminderDB(this);
        listView = findViewById(R.id.reminderListOne);

        //maps reminder list names to reminder lists
        reminderLists = rlm.getReminderLists();
        rlNames = new ArrayList<>(rlm.getReminderLists().keySet());


        rlmAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rlNames) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // sets the colors
                TextView view = (TextView) super.getView(position, convertView, parent);
                String reminderListName = view.getText().toString();

                if (reminderLists.containsKey(reminderListName)) {
                    int bgColor = reminderLists.get(reminderListName).getColor();

                    if (bgColor != 1) {
                        view.setBackgroundColor(bgColor);
                        Color color = Color.valueOf(bgColor);

                        if ((color.red() * 255 * 0.299 + color.green() * 255 * 0.587 + color.blue() * 255 * 0.114) <= 127.5)
                            view.setTextColor(Color.WHITE);
                        else
                            view.setTextColor(Color.BLACK);
                    }
                }

                return view;
            }
        };

        listView.setAdapter(rlmAdapter);
        registerForContextMenu(listView);

        //sends selected reminder list to typeview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedReminder = (String) adapterView.getItemAtPosition(position);

                //ReminderList selectedReminderList = rlm.getReminderLists().get(selectedReminder);
                ReminderList selectedReminderList = rlm.getReminderLists().get(selectedReminder);
                Log.d("MainActivity", "REMINDER LIST NAME"+ selectedReminderList.getList_name());

                Intent intent = new Intent(MainActivity.this, ReminderTypeViewActivity.class);
                intent.putExtra("CHOSEN_LIST", selectedReminderList);
                startActivity(intent);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                rlmAdapter.getFilter().filter(s);
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult: " + requestCode + ", " + resultCode);
        if (requestCode == DELETE_REMINDER_LIST_REQUEST && resultCode == RESULT_OK) {
            // Check if the DELETED_REMINDER_LIST extra is present
            if (data != null && data.hasExtra("DELETED_REMINDER_LIST")) {
                ReminderList deletedReminderList = (ReminderList) data.getSerializableExtra("DELETED_REMINDER_LIST");

                reminderLists.remove(deletedReminderList.getList_name());
                rlNames.remove(deletedReminderList.getList_name());
                rlmAdapter.notifyDataSetChanged();
            }
        }



    }



    //navigations on clicking buttons
    public void navigateToAddReminderActivity(View v) {
        // Create an Intent to start the AddReminderActivity
        Intent intent = new Intent(this, AddReminderActivity.class);
        startActivity(intent);
    }

    public void navigateToAddListActivity(View v) {
        // Create an Intent to start the AddListActivity
        Intent intent = new Intent(this, AddListActivity.class);

        intent.putExtra("action", R.string.add_list);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.reminderListOne)
            getMenuInflater().inflate(R.menu.menu_reminder_list_manager, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView textView = (TextView) info.targetView;
        ReminderList list = rlm.getReminderLists().get(textView.getText());

        if (item.getItemId() == R.id.edit_list) {
            Intent intent = new Intent(this, AddListActivity.class);

            intent.putExtra("action", R.string.edit_list);
            intent.putExtra("list", list.getList_name());

            startActivity(intent);
            finish();
            return true;

        } else if (item.getItemId() == R.id.delete_list) {
            rlm.deleteReminderList(list);
            rlmAdapter.clear();
            rlmAdapter.addAll(rlm.getReminderLists().keySet());
            return true;
        }

        return false;
    }

}
