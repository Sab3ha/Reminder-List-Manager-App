# Design Document

**Author**: Farhikhta Farzan, Sabeha Khan, Tanzila Rahman 

Version 3: Update Dropdowns and buttons were added. Color-choosing theme for lists was also added to Reminder Lists. 

Version 2: The User Interface design was updated and much more concise according to the app requirements such as a list consisting of types, and Reminder types consisting of reminders. Delete buttons are also implemented for reminders, types, and lists. 

## 1 Design Considerations


### 1.1 Assumptions

* The app is compatible on android phone.
* The user prefers simple interface.
* The user prefers to have reminders sorted based on type.
* The app can work without stable internet connection.
* The app will not be needing user login.
* The app assumes the user might need alerts for the reminders.
* The app assumes it might need access to user location for reminder customization purposes.
* The reminders will be automatically added to a default list if the user doesn't have a customized list.
* The user is expected to have a smart phone for the app to work
### 1.2 Constraints

* A user can not add a reminder without specifying a type. 
* A user can not add a reminder without providing name/description of the reminder.
* A user can not have two reminder types with same name.
* The reminders must be shown grouping by type
* The app can support multiple lists
* The user needs to set alart date/time if alert preferance is turned on.

### 1.3 System Environment

* Should be comapatible on smart phones with android api version 25 or higher.
* Should utilize local storage of the android phone.
* Should be able to interact with notification settings and date and time to render alert in timely manner.
* Should interact with location services for time zones.
* Should interact with touch sensors of smart phones to detect user's selections.
* Can fetch data from database to give users reminders or remider types with similar names.
* Can save necessary data to database.

## 2 Architectural Design



### 2.1 Component Diagram


![Copy of Component Diagram.png](https://hackmd.io/_uploads/BJjBod4Xa.png)




* The component diagram consists of main components of the architectural design. Reminder List Manager is a component that provides management to the Reminder List component. Persistence Infrastructure makes the data persist from Reminder List component as well as the Reminder and ReminderType component. 




### 2.2 Deployment Diagram

![Deployment diagram.png](https://hackmd.io/_uploads/H1kdC4mmp.png)



## 3 Low-Level Design



### 3.1 Class Diagram


![design-team.png](https://hackmd.io/_uploads/BybGh8E7p.png)


### 3.2 Other Diagrams
Sequence Diagram:

![Sequence diagram.png](https://hackmd.io/_uploads/ryZbzFVQ6.png)


## 4 User Interface Design

#### Home screen 

![Screenshot 2023-12-03 at 6 49 02 PM](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/7bca4e5c-bc5f-4b98-b0af-59e7255d552f)



#### Create New Reminder

![AddNewReminder](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/3a01bbe0-ec99-49b3-8473-1f18f0e7a40e)



#### Create New Reminder List

![AddNewList](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/2f26c780-8b8a-43f0-bd6b-3cd1793d64d9)


#### Update or Delete List (Long Press)

![Screenshot 2023-12-03 at 6 49 10 PM](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/3bbf9155-5059-4d2d-84ea-6e5817bc1e40)



#### Reminder Types (List) View

![Screenshot 2023-12-03 at 6 49 17 PM](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/254816f2-623b-44c9-be1b-7a205ef80b2c)


#### Reminders (List) View
![Screenshot 2023-12-03 at 6 43 10 PM](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/e8daa8b5-e9f2-40d0-b856-a97466f59083)



#### Individual Reminder details view

![IndividualReminderView](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/38cf4fe4-7719-48df-82cd-117b54ce9e7c)


#### All Views 

![Screenshot 2023-12-03 at 6 47 11 PM](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/143470312/d7055a51-10fa-4820-95ca-558a815615dc)


