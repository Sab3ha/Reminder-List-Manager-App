# Design Information
1. A list consisting of reminders the users want to be aware of. The application must allow
users to add reminders to a list, delete reminders from a list, and edit the reminders in
the list. To realize this requirement, I added Reminder class, ReminderType class and ReminderList class. The ReminderList class has the addReminder(), deleteReminder(), EditReminder methods to perform the above actions. 
2. The application must contain a database (DB) of reminders 
and corresponding data. To realize this, I added database entity which stores the reminders, reminder lists and reminder types.
3. Users must be able to add reminders to a list by picking them from a hierarchical list,
where the first level is the reminder type (e.g., Appointment), and the second level is the
name of the actual reminder (e.g., Dentist Appointment). To realize this reminder, there is a ReminderListManager class that manages the reminder lists. This has methods that lets the user add, edit and delete lists with AddList(), DeleteList(), EditList methods(). In addition to that ReminderList contains the method that can add, remove and update the reminders in a list. There is also ReminderType class that lets the user choose the type of reminder they have. So, the addReminder() method will ensure a user can choose the type and then write their reminders.
4. Users must also be able to specify a reminder by typing its name. In this case, the
application must look in its DB for reminders with similar names and ask the user
whether that is the item they intended to add. If a match (or nearby match) cannot be
found, the application must ask the user to select a reminder type for the reminder, or
add a new one, and then save the new reminder, together with its type, in the DB. To fulfill therequirement the database has fetchSimilarReminder() method and Reminder class has reminderMatched(string) method. That will generate similar types when he user is typing type names.
5. The reminders must be saved automatically and immediately after they are modified. To do this there is saveReminder() function in database and reminder class
6. Users must be able to check off reminders in the list (without deleting them). To realize the requirement clearCheckOffMarks() method exists in ReminderList
7. Users must also be able to clear all the check-off marks in the reminder list at once.To realize the requirement clearCheckOffMarks() method exists in ReminderList
8. Check-off marks for the reminder list are persistent and must also be saved immediately.Thats why save saveCheckOff() method exists in database
9. The application must present the reminders grouped by type. Thats why groupByType() method exists in reminderList
10. The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”,
“Kid’s Reminders”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete reminder lists. Thats why we have due date attribute in reminder class and date class to help with this. Also edit method and delete method and add method in ReminderListManager class can help with the second condition of creating, renaming, selecting and deleting lists.
11. The application should have the option to set up reminders with day and time alert. If this
option is selected allow option to repeat the behavior.To help with this we have alert class with day time attributes and repeat attribute
12. Extra Credit: Option to set up reminder based on location. Thats why i have location class associated with Reminder class.
13. The User Interface (UI) must be intuitive and responsive. It can't be defined in uml diagram