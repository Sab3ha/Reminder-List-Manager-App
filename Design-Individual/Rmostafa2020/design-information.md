1. A list consisting of reminders the users want to be aware of. The application must allow
users to add reminders to a list, delete reminders from a list, and edit the reminders in
the list. To realize of this requirement, I have created a class in the UML diagram as ReminderList. It has methods to add, delete and edit reminders.
2. The application must contain a database (DB) of reminders and corresponding data. - In the UML diagram, I have created a "ReminderDB" class where all data will be stored and user can retreive the date from that database. 
3. Users must be able to add reminders to a list by picking them from a hierarchical list,
where the first level is the reminder type (e.g., Appointment), and the second level is the
name of the actual reminder (e.g., Dentist Appointment). I have added a class that is called "ReminderType". It represents the hierarchical list of reminder types. Reminder class has an association with ReminderType to associate each reminder with a type.
4. Users must also be able to specify a reminder by typing its name. In this case, the
application must look in its DB for reminders with similar names and ask the user
whether that is the item they intended to add. If a match (or nearby match) cannot be
found, the application must ask the user to select a reminder type for the reminder, or
add a new one, and then save the new reminder, together with its type, in the DB. To realize this requirement, I have added ReminderDB. When the user starts typing, if such string or reminder already exist, then it can suggest the reminders and save it thereafter.
5. The reminders must be saved automatically and immediately after they are modified. -ReminderList has a method saveReminderList which is part of all the methods in the list and it saves the list automatically as it is called after every method .
6. Users must be able to check off reminders in the list (without deleting them). - To realize the requirement I have added a method called check which would represent has been recognized. This does not delete the reminder.
7. Users must also be able to clear all the check-off marks in the reminder list at once. - The method uncheckAll in the ReminderList allows the user to uncheck in the lists.
8. Check-off marks for the reminder list are persistent and must also be saved immediately. - SaveReminderList gets called after the invocation of each method in the ReminderList. Therefore, when it is checked or checked the status gets saved in the list.

9. The application must present the reminders grouped by type. -To realize the requirement I have added ReminderListType as an attribute to ReminderList. It helps in grouping reminders by type.
10. The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”,
“Kid’s Reminders”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete reminder lists. To realize this requirement, I have added ListManager which is a class of list of Reminders and has methods to create lists, rename list, select list and delete list.
11. The application should have the option to set up reminders with day and time alert. If this
option is selected allow option to repeat the behavior. - Alert class is added including attributes for day,time and repetition setting for this purpose.
12. Extra Credit: Option to set up reminder based on location. To realize this requirement, a feature is added to the design, potentially with attributes in the "Alert" class for loaction-based reminder.
13. The User Interface (UI) must be intuitive and responsive. -Not considered because it does not affect the design directly.