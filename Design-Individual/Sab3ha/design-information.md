#Design Information

1. A list consisting of reminders the users want to be aware of. The application must allow users to add reminders to a list, delete reminders from a list, and edit the reminders in the list. 
To realize this requirement, I added to the design a class ReminderManager with attribute reminder and methods addRemindertoList, deleteReminderfromList and editReminder. I also added a class Reminder with attribute name and method setName. Furthermore, I added a class ReminderList with attribute reminderList. Additionally, created the class User that interacts with class ReminderManager.

2. The application must contain a database (DB) of reminders and corresponding data. 
To realize this requirement, I added to the design a class Database with attribute reminderDatabase. 

3. Users must be able to add reminders to a list by picking them from a hierarchical list, where the first level is the reminder type (e.g., Appointment), and the second level is the name of the actual reminder (e.g., Dentist Appointment). 
To realize this requirement, I added to the design a class ReminderType with attribute typeName and method setTypeName. Additionally, I added a method groupByType to class ReminderList.

4. Users must also be able to specify a reminder by typing its name. In this case, the application must look in its DB for reminders with similar names and ask the user whether that is the item they intended to add. If a match (or nearby match) cannot be found, the application must ask the user to select a reminder type for the reminder, or add a new one, and then save the new reminder, together with its type, in the DB.
To realize this requirement, I added to the design a method ReminderMatchFound to class Database. Additionally, I added a method createReminder to class ReminderManager. 

5. The reminders must be saved automatically and immediately after they are modified.
To realize this requirement, I added the many to one relation “stored in”  between class Reminder and class Database.

6. Users must be able to check off reminders in the list (without deleting them).
To realize this requirement, I added to the design the attribute checkoff and method setCheckOff to class Reminder.

7. Users must also be able to clear all the check-off marks in the reminder list at once.
To realize this requirement, I added to the design the method clearCheckOffMarks to class ReminderManager.

8. Check-off marks for the reminder list are persistent and must also be saved immediately.
To realize this requirement, I added to the design the attribute checkoff and method setCheckOff to class Reminder. Additionally, I added the many to one relation “stored in”  between class Reminder and class Database.

9. The application must present the reminders grouped by type.
To realize this requirement, I added to the design the method groupByType to class ReminderManager.
	
10. The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”, “Kid’s Reminders”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete reminder lists.
To realize this requirement, I added to the design a class ReminderListManager with attributes reminderLists and selectedReminder. I also included the methods createReminderList, renameReminderList, selectReminderList and deleteReminderList to class ReminderListManager. Additionally, I added the one to one relation “Interacts with” between class User and class ReminderListManager.

11. The application should have the option to set up reminders with day and time alert. If this option is selected allow option to repeat the behavior.
To realize this requirement, I added to the design a class Alert with attributes dayTime and repeat and methods setDayTime and setRepeat. Additionally, I added the attribute alert and method setAlert to class Reminder.

12. Extra Credit: Option to set up reminder based on location.
To realize this requirement, I added to the design a class Location with attributes latitude, longitude and place and methods setLatitude, setLongitude and setPlace. Additionally, I added the attribute location and the method setLocation to class Reminder. 

13. The User Interface (UI) must be intuitive and responsive. 
Not considered because it does not affect the design directly.


