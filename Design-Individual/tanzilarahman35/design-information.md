# design-information

Requirements: 

1. The application must contain a list of reminders with CRUD functionality. To realize this, I’ve added the Reminder list class, which will showcase the list with Reminders. Also methods that will let users create, read, update, and delete the reminders with the provided methods.
 
2. The application must contain a database (DB) of reminders and corresponding data.  To realize this, I’ve added the DataBase class with attribute reminders, and methods for saving and retrieving the reminders data. 

3. Users must be able to add reminders to a list by picking them from a hierarchical list. ,
where the first level is the reminder type (e.g., Appointment), and the second level is the
name of the actual reminder (e.g., Dentist Appointment). To realize this, I’ve added the attribute, type of reminder first, then added the actual reminder name inside the reminder list class. I’ve also added the method “groupByType()” inside the reminder list class, to display the reminders by their type. 

4. Users must also be able to specify a reminder by typing its name. In this case, the
the application must look in its DB for reminders with similar names and ask the user
whether that is the item they intended to add. If a match (or nearby match) cannot be
found, the application must ask the user to select a reminder type for the reminder, or
add a new one, and then save the new reminder and its type in the DB. 
To realize this, I’ve added the “findSimilarReminder(Name): Boolean”  method inside the database to retrieve any similar names that users have typed. 

5. The reminders must be saved automatically and immediately after they are modified.
To realize this, I’ve connected the DB class to the reminder class, and the “+saveReminder()” method in DB to save any reminders immediately that the users have created.
 
6. Users must be able to check off reminders in the list (without deleting them).
To realize this, I’ve added the checkOff() method in the reminder list class. 

7. Users must also be able to clear all the check-off marks in the reminder list at once.
To realize this I’ve added the clearCheckOffs() method inside the reminder list class, this way users can clear the reminders they have already checked off. 

8. Check-off marks for the reminder list are persistent and must also be saved immediately.
To realize this, I’ve added the autoSave() method inside the reminder list class, this way any update can be saved immediately, and users will not lose their data.
 
9. The application must present the reminders grouped by type. To realize this, I’ve added the groupByType() method in the reminder list class. 

10. The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”,
“Kid’s Reminders”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete reminder lists.
To realize this, I’ve added the ReminderListManager class with attribute reminderLists, as well as methods to create, rename, select, and delete the individual lists of reminders. 

11. The application should be able to set up reminders with day and time alerts. If this
the option is selected to allow the option to repeat the behavior.
To realize this, I’ve added the alert class with the following attributes:  reminder, alertTime, and repeat. Users can use methods to set the alert and repeat() the alert. 

12. Extra Credit: Option to set up reminders based on location.
To realize this, I’ve added the Location class connected to the Reminder class with attributes: reminder name and the location as well as the method to set the location.
 
13. The User Interface (UI) must be intuitive and responsive. Not considered because it does not affect the design directly.

Additional information: 
The design of the UML class diagram starts with the User accessing the Reminder List manager that manages all the different types of lists that the user can create. After that appears the individual Reminder list with the CRUD functionality for each reminder. When adding a new reminder, users can access the Reminder Type class that will specify the type of reminder they wish to add. There are also other methods that satisfy the requirements such as the checkOff(), clearCheckOff(), groupByType() methods under the reminder list class. The AutoSave() method will ensure that data is not lost and the app constantly saves any actions done by the user. The database is used for saving reminders, displaying reminders accordingly. There is also a findSimilarReminder() method that will help users find similar reminders with the name they provided, in the search bar. The alert and the location classes are also connected to the reminder class, and will work according to the preference of the user when setting up the individual reminders. 
