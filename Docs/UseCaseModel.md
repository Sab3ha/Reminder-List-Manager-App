# Use Case Model


**Author**: Rahnuma Mostafa, Michael Zheng

## 1 Use Case Diagram

![Use Case diagram.png](https://hackmd.io/_uploads/r1HT7VQmT.png)


## 2 Use Case Descriptions


- **Requirements:**

 The "Reminder App" use case must allow the user manage their important appointments or events efficiently.They can create, edit, and delete reminderlist or reminder. Also, they can set specific dates,times and location. Moreover, this app sends notification to the user to ensure they do not forget their appointment or valuable events and it helps user stay organized.

- **Pre-conditions:**

Some conditions that must be true before the use case is ran, such as the data and resources required from the user input to start. The use case can't run if there is no user input. Some other conditions can be user authentication to prevent others messing with already made reminders on the app. If the use case interacts with any external systems or services, those external sources need to be actively ready at all times. Some prerequisite need to happen too in order for the next use case to happen, example; in order to delete a reminder, one must already have a reminder made first.

- **Post-conditions:**

The app needs to be able to handle errors. If any case fails, the app needs to be able to log the errors and make sure that nothing else affects the remaining data in the system. User inputs that has been entered, the system needs to be able to reflect any changes made after the use case is executed successfully. The system should return to default screen after successfully after the use case is executed. There should be an output notification after user input has been made.

- **Scenarios:**

1. user opens the application
2. user navigates the screen to the create reminder section
3. user inputs a name for the reminder
4. user inputs the date and time for the reminder
5. consults database if neccessary, any external system or services
6. user can enter some notes/additional details for reminder
7. user now confirms and save the reminder
8. system now checks for error. If error it will return user to previous state, if no error it moves on
9. the system now stores the reminder 
10. the application will send out notifications for the reminder