package edu.qc.seclass.rlm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReminderDB extends SQLiteOpenHelper {
    public static final String REMINDER_TABLE = "REMINDER_TABLE";
    public static final String COLUMN_ID = "REMINDER_ID";
    public static final String COLUMN_REMINDER_NAME = "REMINDER_NAME";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_CHECK_OFF = "CHECK_OFF";
    public static final String COLUMN_IS_ALERT = "IS_ALERT";
    public static final String REMINDER_LIST_TABLE = "ReminderList_TABLE";
    public static final String LIST_ID = "List_ID";
    public static final String LIST_NAME = "List_Name";
    public static final String LIST_ID1 = "LIST_ID";
    public static final String COLUMN_LIST_ID1 = "COLUMN_" + LIST_ID1;
    public static final String COLUMN_LIST_ID = COLUMN_LIST_ID1;

    public static final String REMINDER_TYPE_TABLE = "REMINDER_TYPE_TABLE";
    public static final String TYPE_NAME = "TYPE_NAME";
    public static final String COLUMN_TYPE_NAME = "COLUMN_TYPE_NAME";
    public static final String REMINDER_LIST_TABLE1 = "REMINDER_LIST_TABLE";
    public static final String COLOR = "COLOR";
    public static final String COLUMN_REMINDER_LIST_ID = "COLUMN_REMINDER_LIST_ID";
    private static final String COLUMN_TIME = "TIME";

    public ReminderDB(@Nullable Context context) {
        super(context, "reminder.db", null, 26);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createListStatement = "CREATE TABLE " + REMINDER_LIST_TABLE + " (" +
                LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LIST_NAME + " Text UNIQUE, " +
                COLOR + " INTEGER  )";

        String createTypeStatement = "CREATE TABLE " + REMINDER_TYPE_TABLE + "(" +
                TYPE_NAME + " TEXT PRIMARY KEY," +
                COLUMN_LIST_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_LIST_ID + ") REFERENCES " + REMINDER_LIST_TABLE + "(" + LIST_ID + ") )";


        String createTableStatement = "CREATE TABLE " + REMINDER_TABLE +
                " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE_NAME + " TEXT, " +
                COLUMN_REMINDER_NAME + " TEXT, " +
                COLUMN_DATE + " DATE, " +
                COLUMN_TIME + " TIME, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_CHECK_OFF + " BOOL, " +
                COLUMN_IS_ALERT + " BOOL, " +
                COLUMN_REMINDER_LIST_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_TYPE_NAME + ") REFERENCES " + REMINDER_TYPE_TABLE + "(" + TYPE_NAME + ")," +
                "FOREIGN KEY (" + COLUMN_REMINDER_LIST_ID + ") REFERENCES " + REMINDER_LIST_TABLE+ "("+ LIST_ID +"))";


        db.execSQL(createListStatement);
        db.execSQL(createTypeStatement);
        db.execSQL(createTableStatement);

        ContentValues listValues = new ContentValues();

        listValues.put(LIST_NAME, "Default List");
        listValues.put(COLOR, Color.WHITE);

        db.insert(REMINDER_LIST_TABLE, null, listValues);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + REMINDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REMINDER_LIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REMINDER_TYPE_TABLE);

        // Recreate tables
        onCreate(db);
    }

    public void updateReminderList(ReminderList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LIST_NAME, list.getList_name());
        values.put(COLOR, list.getColor());
        db.update(REMINDER_LIST_TABLE, values, LIST_ID + " = " + list.getList_id(), null);
        db.close();
    }

    public boolean addReminderList(ReminderList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            // Check for null or empty values
            if (list == null || list.getList_name().isEmpty()) {
                Log.e("Add ReminderList", "Invalid ReminderList");
                return false;
            }

            values.put(LIST_NAME, list.getList_name());
            values.put(COLOR, list.getColor());

            long id = db.insert(REMINDER_LIST_TABLE, null, values);

            if (id == -1) {
                Log.e("Add ReminderList", "Failed to add ReminderList");
                return false;
            } else {
                Log.d("Add ReminderList", "Successfully added ReminderList");
                list.setList_id((int) id);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception stack trace
            return false;
        } finally {
            db.close(); // Close the database connection
        }
    }


    public List<ReminderList> getEveryReminderList() {
        List<ReminderList> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + REMINDER_LIST_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int listID = cursor.getInt(0);
                String listName = cursor.getString(1);
                int color = cursor.getInt(2);
                ReminderList newReminderList = new ReminderList(listID, listName, color);
                returnList.add(newReminderList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;
    }

    //removes reminder list (not reminders inside the db that are in that list)
    public boolean removeReminderList(ReminderList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REMINDER_LIST_TABLE + " WHERE " + LIST_ID + " = " + list.getList_id();
        Cursor cursor = db.rawQuery(queryString, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            updateReminderList(list);
            db.close();
            return true;
        } else {
            return false;
        }
    }

    //removes reminders inside the db that are in that list
    public boolean removeReminderInsideList(ReminderList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REMINDER_TABLE + " WHERE " + COLUMN_REMINDER_LIST_ID + " = " + list.getList_id();
        Cursor cursor = db.rawQuery(queryString, null);
        if (!cursor.moveToFirst()) {
            removeReminderTypeInsideList(list);
            removeReminderList(list);
            cursor.close();
            db.close();
            updateReminderList(list);
            return true;
        } else {
            return false;
        }

    }

    //Removes tuples inside type table associated with the list id of deleted list
    public boolean removeReminderTypeInsideList(ReminderList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REMINDER_TYPE_TABLE + " WHERE " + COLUMN_LIST_ID + " = " + list.getList_id();
        Cursor cursor = db.rawQuery(queryString, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            updateReminderList(list);
            db.close();
            return true;
        } else {
            return false;
        }
    }

    //this method is essential for deleting a list that has type with no reminders associated to it
    public boolean removeReminderTypeInsideList2(ReminderList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REMINDER_TYPE_TABLE + " WHERE " + COLUMN_LIST_ID + " = " + list.getList_id();
        Cursor cursor = db.rawQuery(queryString, null);
        if (!cursor.moveToFirst()) {
            removeReminderList(list);
            cursor.close();
            updateReminderList(list);
            db.close();
            return true;
        } else {
            return false;
        }
    }



    //remindertype method

    public boolean addReminderType(ReminderType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            if (type == null || type.getTypeName().isEmpty() || type.getList_id() == null) {

                return false;
            }
            values.put(TYPE_NAME, type.getTypeName());
            values.put(COLUMN_LIST_ID, type.getList_id().getList_id());
            long insert = db.insert(REMINDER_TYPE_TABLE, null, values);
            if (insert == -1) {
                Log.e("Add ReminderType", "Failed to Add ReminderType");

            } else {
                Log.e("Add ReminderType", "ReminderType is added successfully");
            }

            return insert != -1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public List<ReminderType> getEveryReminderType() {
        List<ReminderType> returnType = new ArrayList<>();
        String queryString = "SELECT t.*, l." + LIST_NAME + ", l." + LIST_ID +
                " FROM " + REMINDER_TYPE_TABLE + " t" +
                " LEFT JOIN " + REMINDER_LIST_TABLE + " l ON t." + COLUMN_LIST_ID + " = l." + LIST_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null) {
            int typeNameIndex = cursor.getColumnIndex(TYPE_NAME);
            int listNameIndex = cursor.getColumnIndex(LIST_NAME);
            int listIdIndex = cursor.getColumnIndex(LIST_ID);

            while (cursor.moveToNext()) {
                String typeName = cursor.getString(typeNameIndex);
                String listName = cursor.getString(listNameIndex);
                int listId = cursor.getInt(listIdIndex);

                // Create ReminderList
                ReminderList reminderList = new ReminderList(listId, listName);

                // Create ReminderType with associated ReminderList
                ReminderType newReminderType = new ReminderType(typeName, reminderList);
                returnType.add(newReminderType);
            }

            cursor.close();
        }

        db.close();
        return returnType;
    }

    //this method gets the type names as List of Strings
    public List<String> getEveryReminderTypeName() {
        List<String> returnType = new ArrayList<>();
        String queryString = "SELECT t.*, l." + LIST_NAME + ", l." + LIST_ID +
                " FROM " + REMINDER_TYPE_TABLE + " t" +
                " LEFT JOIN " + REMINDER_LIST_TABLE + " l ON t." + COLUMN_LIST_ID + " = l." + LIST_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null) {
            int typeNameIndex = cursor.getColumnIndex(TYPE_NAME);
            int listNameIndex = cursor.getColumnIndex(LIST_NAME);
            int listIdIndex = cursor.getColumnIndex(LIST_ID);

            while (cursor.moveToNext()) {
                String typeName = cursor.getString(typeNameIndex);

                returnType.add(typeName);
            }

            cursor.close();
        }

        db.close();
        return returnType;
    }



    //removes reminders associated with a type from the reminder table
    public boolean removeReminderRelatedToType(ReminderType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();

            // Step 1: Delete reminders associated with the type
            String deleteRemindersQuery = "DELETE FROM " + REMINDER_TABLE +
                    " WHERE " + COLUMN_TYPE_NAME + " = ?";
            db.execSQL(deleteRemindersQuery, new String[]{type.getTypeName()});

            // Step 2: Delete the ReminderType
            String deleteReminderTypeQuery = "DELETE FROM " + REMINDER_TYPE_TABLE +
                    " WHERE " + TYPE_NAME + " = ?";
            db.execSQL(deleteReminderTypeQuery, new String[]{type.getTypeName()});

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }



    public boolean addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        try {
            // Check for null or empty values
            if (reminder == null || reminder.getType() == null ||
                    reminder.getReminderName().isEmpty() || reminder.getType().getTypeName().isEmpty()) {
                return false;
            }

            cv.put(COLUMN_REMINDER_NAME, reminder.getReminderName());
            cv.put(COLUMN_TYPE_NAME, reminder.getType().getTypeName());
            cv.put(COLUMN_REMINDER_LIST_ID, reminder.getListID());
            cv.put(COLUMN_DATE, reminder.getDate());
            cv.put(COLUMN_TIME, reminder.getTime());
            cv.put(COLUMN_CHECK_OFF, reminder.isCheckedOff() ? 1 : 0);
            cv.put(COLUMN_IS_ALERT, reminder.isAlertOn() ? 1 : 0);
            cv.put(COLUMN_LOCATION, reminder.getLocation());

            long insert = db.insert(REMINDER_TABLE, null, cv);

            reminder.setReminder_id((int) insert);

            if (insert != -1) {
                // Log success
                Log.d("AddReminder", "Reminder added successfully");
            } else {
                // Log failure
                Log.e("AddReminder", "Failed to add reminder");
            }

            return insert != -1;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception stack trace
            return false;
        } finally {
            db.close(); // Close the database connection
        }
    }



    public List<Reminder> getEveryReminder() {
        List<Reminder> returnList = new ArrayList<>();
        String queryString =
                "SELECT r.*, t." + COLUMN_LIST_ID + " AS TypeListID, l." + LIST_NAME +
                        " FROM " + REMINDER_TABLE + " r" +
                        " JOIN " + REMINDER_TYPE_TABLE + " t ON r." + COLUMN_TYPE_NAME + " = t." + TYPE_NAME +
                        " JOIN " + REMINDER_LIST_TABLE + " l ON t." + COLUMN_LIST_ID + " = l." + LIST_ID +
                        " WHERE r." + COLUMN_REMINDER_LIST_ID + " = t." + COLUMN_LIST_ID + " AND t." + COLUMN_LIST_ID + " = l." + LIST_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int typeColumnIndex = cursor.getColumnIndex(COLUMN_TYPE_NAME);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_REMINDER_NAME);
            int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);
            int timeColumnIndex = cursor.getColumnIndex(COLUMN_TIME);
            int locationColumnIndex = cursor.getColumnIndex(COLUMN_LOCATION);
            int checkOffColumnIndex = cursor.getColumnIndex(COLUMN_CHECK_OFF);
            int isAlertColumnIndex = cursor.getColumnIndex(COLUMN_IS_ALERT);

            int typeListIdColumnIndex = cursor.getColumnIndex("TypeListID");
            int listNameColumnIndex = cursor.getColumnIndex(LIST_NAME);

            while (cursor.moveToNext()) {
                int reminderID = cursor.getInt(idColumnIndex);
                String reminderType = cursor.getString(typeColumnIndex);
                String reminderName = cursor.getString(nameColumnIndex);
                String reminderDate = cursor.getString(dateColumnIndex);
                String reminderTime = cursor.getString(timeColumnIndex);
                String reminderLocation = cursor.getString(locationColumnIndex);
                boolean isCheckedOff = cursor.getInt(checkOffColumnIndex) == 1;
                boolean isAlert = cursor.getInt(isAlertColumnIndex) == 1;

                // Additional columns from joined tables
                int listId = cursor.getInt(typeListIdColumnIndex);
                String listName = cursor.getString(listNameColumnIndex);

                // Create ReminderList
                ReminderList reminderList = new ReminderList(listId, listName);

                // Create ReminderType with associated ReminderList
                ReminderType newReminderType = new ReminderType(reminderType, reminderList);

                // Create Reminder
                Reminder newReminder = new Reminder(
                        reminderID, listId, newReminderType, reminderName, reminderDate, reminderTime, reminderLocation, isCheckedOff, isAlert
                );

                returnList.add(newReminder);
            }

            cursor.close();
        }

        db.close();
        return returnList;
    }

    public List<String> getEveryReminderName() {
        List<String> returnList = new ArrayList<>();
        String queryString =
                "SELECT r.*, t." + COLUMN_LIST_ID + " AS TypeListID, l." + LIST_NAME +
                        " FROM " + REMINDER_TABLE + " r" +
                        " JOIN " + REMINDER_TYPE_TABLE + " t ON r." + COLUMN_TYPE_NAME + " = t." + TYPE_NAME +
                        " JOIN " + REMINDER_LIST_TABLE + " l ON t." + COLUMN_LIST_ID + " = l." + LIST_ID +
                        " WHERE r." + COLUMN_REMINDER_LIST_ID + " = t." + COLUMN_LIST_ID + " AND t." + COLUMN_LIST_ID + " = l." + LIST_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int typeColumnIndex = cursor.getColumnIndex(COLUMN_TYPE_NAME);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_REMINDER_NAME);
            int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);
            int timeColumnIndex = cursor.getColumnIndex(COLUMN_TIME);
            int locationColumnIndex = cursor.getColumnIndex(COLUMN_LOCATION);
            int checkOffColumnIndex = cursor.getColumnIndex(COLUMN_CHECK_OFF);
            int isAlertColumnIndex = cursor.getColumnIndex(COLUMN_IS_ALERT);
            int typeListIdColumnIndex = cursor.getColumnIndex("TypeListID");
            int listNameColumnIndex = cursor.getColumnIndex(LIST_NAME);

            while (cursor.moveToNext()) {
                int reminderID = cursor.getInt(idColumnIndex);
                String reminderType = cursor.getString(typeColumnIndex);
                String reminderName = cursor.getString(nameColumnIndex);
                String reminderDate = cursor.getString(dateColumnIndex);
                String reminderTime = cursor.getString(timeColumnIndex);
                String reminderLocation = cursor.getString(locationColumnIndex);
                boolean isCheckedOff = cursor.getInt(checkOffColumnIndex) == 1;
                boolean isAlert = cursor.getInt(isAlertColumnIndex) == 1;

                // Additional columns from joined tables
                int listId = cursor.getInt(typeListIdColumnIndex);
                String listName = cursor.getString(listNameColumnIndex);
                returnList.add(reminderName);
            }

            cursor.close();
        }

        db.close();
        return returnList;
    }


    //deletes individual reminder
    public boolean removeReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REMINDER_TABLE + " WHERE " + COLUMN_ID + " = " + reminder.getReminder_id();
        Cursor cursor = db.rawQuery(queryString, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            updateReminder(reminder);
            db.close();
            return true;
        } else {
            return false;
        }
    }


    public void updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_NAME, reminder.getType().getTypeName());
        values.put(COLUMN_REMINDER_LIST_ID, reminder.getListID());
        values.put(COLUMN_REMINDER_NAME, reminder.getReminderName());
        values.put(COLUMN_DATE, reminder.getDate());
        values.put(COLUMN_TIME,reminder.getTime());
        values.put(COLUMN_LOCATION, reminder.getLocation());
        values.put(COLUMN_CHECK_OFF, reminder.isCheckedOff());
        values.put(COLUMN_IS_ALERT, reminder.isAlertOn());
        db.update(REMINDER_TABLE, values, COLUMN_ID + " = " + reminder.getReminder_id(), null);
        db.close();

    }


    // checks if  a specific Reminder List is connected to types and reminders
    // (returns false if the list is empty without reminders/types)
    //NEEDS BOTH TYPE AND REMINDER TO BE RELATED TO LIST
    public boolean hasConnectedRecordsTypeAndReminder( ReminderList reminderList) {
        SQLiteDatabase db = this.getReadableDatabase();
        int listId = reminderList.getList_id();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*)" +
                        " FROM " + REMINDER_LIST_TABLE + " rl" +
                        " LEFT JOIN " + REMINDER_TYPE_TABLE + " rt ON rl." + LIST_ID + " = rt." + COLUMN_LIST_ID +
                        " LEFT JOIN " + REMINDER_TABLE + " r ON rl." + LIST_ID + " = r." + COLUMN_REMINDER_LIST_ID +
                        " WHERE rl." + LIST_ID + " = ?" +
                        " AND rt." + COLUMN_LIST_ID + " IS NOT NULL AND r." + COLUMN_REMINDER_LIST_ID + " IS NOT NULL;",
                new String[]{String.valueOf(listId)}
        );

        boolean hasConnectedRecordsTypeAndReminder = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            // If count is greater than zero, there are connected records
            hasConnectedRecordsTypeAndReminder = count > 0;
        }

        cursor.close();
        db.close();
        return hasConnectedRecordsTypeAndReminder;
    }

    public boolean hasConnectedRecordsTypeNotReminder( ReminderList reminderList) {
        SQLiteDatabase db = this.getReadableDatabase();
        int listId = reminderList.getList_id();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*)" +
                        " FROM " + REMINDER_LIST_TABLE + " rl" +
                        " LEFT JOIN " + REMINDER_TYPE_TABLE + " rt ON rl." + LIST_ID + " = rt." + COLUMN_LIST_ID +
                        " LEFT JOIN " + REMINDER_TABLE + " r ON rl." + LIST_ID + " = r." + COLUMN_REMINDER_LIST_ID +
                        " WHERE rl." + LIST_ID + " = ?" +
                        " AND rt." + COLUMN_LIST_ID + " IS NOT NULL AND r." + COLUMN_REMINDER_LIST_ID + " IS NOT NULL;",
                new String[]{String.valueOf(listId)}
        );

        boolean hasConnectedRecordsTypeNotReminder = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            // If count is greater than zero, there are connected records
            hasConnectedRecordsTypeNotReminder = count > 0;
        }

        cursor.close();
        db.close();
        return hasConnectedRecordsTypeNotReminder;
    }

    //Method to check if a Reminder object exists in the database
    public boolean doesReminderExist(Reminder reminder) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*)" +
                        " FROM " + REMINDER_TABLE +
                        " WHERE " + COLUMN_ID + " = ?",
                new String[]{String.valueOf(reminder.getReminder_id())}
        );

        boolean exists = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            // If count is greater than zero, the reminder exists
            exists = count > 0;
        }

        cursor.close();
        db.close();
        return exists;
    }

    //Method to check if a Reminder name exists in the database
    public boolean doesReminderNameExist(String reminder) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*)" +
                        " FROM " + REMINDER_TABLE +
                        " WHERE " + COLUMN_REMINDER_NAME + " = ?",
                new String[]{String.valueOf(reminder)}
        );

        boolean exists = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            // If count is greater than zero, the reminder exists
            exists = count > 0;
        }

        cursor.close();
        db.close();
        return exists;
    }


    public boolean doesReminderExistInType(ReminderType type) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*)" +
                        " FROM " + REMINDER_TABLE +
                        " WHERE " + COLUMN_TYPE_NAME + " = ?",
                new String[]{String.valueOf(type.getTypeName())}
        );

        boolean exists = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            // If count is greater than zero, the reminder exists
            exists = count > 0;
        }

        cursor.close();
        db.close();
        return exists;
    }

    public boolean doesTypeExistInReminderList(ReminderList list) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*)" +
                        " FROM " + REMINDER_TYPE_TABLE +
                        " WHERE " + COLUMN_LIST_ID + " = ?",
                new String[]{String.valueOf(list.getList_id())}
        );

        boolean exists = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            // If count is greater than zero, the reminder type exists
            exists = count > 0;
        }

        cursor.close();
        db.close();
        return exists;
    }

    // Method to find records based on list_id and return the list name
    @SuppressLint("Range")
    public String getListNameById(int listId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + LIST_NAME +
                        " FROM " + REMINDER_LIST_TABLE +
                        " WHERE " + LIST_ID + " = ?",
                new String[]{String.valueOf(listId)}
        );

        String listName = null;
        if (cursor.moveToFirst()) {
            listName = cursor.getString(cursor.getColumnIndex(LIST_NAME));
        }

        cursor.close();
        db.close();
        return listName;
    }

    // Add this method to check if a reminder with a specific name and type exists
    public boolean doesSpecificReminderExistInSpecificType(String reminderName, String typeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + REMINDER_TABLE +
                    " WHERE " + COLUMN_REMINDER_NAME + " = ? AND " +
                    COLUMN_TYPE_NAME + " = ?";

            cursor = db.rawQuery(query, new String[]{reminderName, typeName});

            // If the cursor has rows, then the reminder exists
            return cursor.getCount() > 0;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    // Method to get a Reminder by its name
    public Reminder getReminderByName(String reminderName) {
        Reminder oldReminder = null;
        String queryString =
                "SELECT r.*, t." + COLUMN_LIST_ID + " AS TypeListID, l." + LIST_NAME +
                        " FROM " + REMINDER_TABLE + " r" +
                        " JOIN " + REMINDER_TYPE_TABLE + " t ON r." + COLUMN_TYPE_NAME + " = t." + TYPE_NAME +
                        " JOIN " + REMINDER_LIST_TABLE + " l ON t." + COLUMN_LIST_ID + " = l." + LIST_ID +
                        " WHERE r." + COLUMN_REMINDER_NAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{reminderName});

        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int typeColumnIndex = cursor.getColumnIndex(COLUMN_TYPE_NAME);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_REMINDER_NAME);
            int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);
            int timeColumnIndex = cursor.getColumnIndex(COLUMN_TIME);
            int locationColumnIndex = cursor.getColumnIndex(COLUMN_LOCATION);
            int checkOffColumnIndex = cursor.getColumnIndex(COLUMN_CHECK_OFF);
            int isAlertColumnIndex = cursor.getColumnIndex(COLUMN_IS_ALERT);

            int typeListIdColumnIndex = cursor.getColumnIndex("TypeListID");
            int listNameColumnIndex = cursor.getColumnIndex(LIST_NAME);

            while (cursor.moveToNext()) {
                int reminderID = cursor.getInt(idColumnIndex);
                String reminderType = cursor.getString(typeColumnIndex);
                reminderName = cursor.getString(nameColumnIndex);
                String reminderDate = cursor.getString(dateColumnIndex);
                String reminderTime = cursor.getString(timeColumnIndex);
                String reminderLocation = cursor.getString(locationColumnIndex);
                boolean isCheckedOff = cursor.getInt(checkOffColumnIndex) == 1;
                boolean isAlert = cursor.getInt(isAlertColumnIndex) == 1;

                // Additional columns from joined tables
                int listId = cursor.getInt(typeListIdColumnIndex);
                String listName = cursor.getString(listNameColumnIndex);

                // Create ReminderList
                ReminderList reminderList = new ReminderList(listId, listName);

                // Create ReminderType with associated ReminderList
                ReminderType newReminderType = new ReminderType(reminderType, reminderList);

                // Create Reminder
                oldReminder = new Reminder(
                        reminderID, listId, newReminderType, reminderName, reminderDate, reminderTime, reminderLocation, isCheckedOff, isAlert
                );

            }

            cursor.close();
        }

        db.close();
        return oldReminder;
    }

    // Method to check if a record exists in the REMINDER_TABLE
    public boolean doesReminderHaveSpecificListAndType(int listId, String typeName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_REMINDER_LIST_ID + " = ? AND " + COLUMN_TYPE_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(listId), typeName};

        Cursor cursor = db.query(
                REMINDER_TABLE,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean recordExists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return recordExists;
    }

    public boolean doesThisReminderExistForSpecificListAndType(String reminderName, int listId, String typeName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + REMINDER_TABLE +
                " WHERE " + COLUMN_REMINDER_NAME + " = ?" +
                " AND " + COLUMN_REMINDER_LIST_ID + " = ?" +
                " AND " + COLUMN_TYPE_NAME + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{reminderName, String.valueOf(listId), typeName});

        boolean recordExists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return recordExists;
    }
    //see if a type exists in the database
    public boolean isTypeExists(String typeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " + TYPE_NAME + " FROM " + REMINDER_TYPE_TABLE + " WHERE " + TYPE_NAME + " = ?";
            cursor = db.rawQuery(query, new String[]{typeName});

            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

    }

    // Define the method to get ReminderType by type name
    public ReminderType getReminderTypeByTypeName(String typeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ReminderType reminderType = null;

        // Define the columns you want to retrieve
        String[] projection = {
                TYPE_NAME,
                COLUMN_LIST_ID
        };

        // Define the WHERE clause
        String selection = TYPE_NAME + " = ?";
        String[] selectionArgs = {typeName};

        // Query the database
        Cursor cursor = db.query(
                REMINDER_TYPE_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Check if the cursor has a valid row
        if (cursor.moveToFirst()) {
            // Retrieve data from the cursor
            String type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_NAME));
            int listId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LIST_ID));

            // Create a ReminderList object (you need to implement ReminderList constructor)
            ReminderList list = new ReminderList();
            list.setList_id(listId);
            list.setList_name(getListNameById(listId));

            // Create a ReminderType object
            reminderType = new ReminderType(type, list);
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return reminderType;
    }
}
