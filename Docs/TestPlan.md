# Test Plan

**Author**: Mohin Ahmed, Sabeha Khan, Tanzila Rahman

**Version**: 2

## 1 Testing Strategy

### 1.1 Overall strategy
#### Unit Testing:
Testing individual units' functionality to ensure each unit(method or function or section of code) is running the way it is intended to run. Some unit testing examples are testing the functions that add reminders to a list, testing certain functions in the database that saves/updates reminders in a list. This will help confirm that the functionality of a unit is appropriate and efficient. 

#### Integration Testing:
Verifying the interactions between the components of the app which allows for the desired functionality. Testing the integration of the UI and the creating reminder functionality to ensure that reminders are correctly added and displayed.

#### System Testing:
Testing that the app functions as a whole to validate all the components work together while providing a user friendly experience. Performing tests of creating, removing, editing, and checking off reminders.

#### Regression Testing:
Verify that the addition of newly implented code with specific functionality does not interfere with pre written code with its functionality. After implenting some new feature or modifying existing feature, perform regression testing to ensure that existing functions still work as expected.


### 1.2 Test Selection

#### Black-Box Testing Techniques:

##### Functional Testing:
* Add Reminder: Test the addition of a reminder by typing its name and via hierarchical list. Verify they are created appropriately.

* Remove Reminder: Test the removal of a reminder.

* Edit Reminder: Test the editing functionality of an existing reminder to verify that edits to a reminder are saved within the database.

* Check off Reminder: Test the functionality of checking off reminders and checking off all reminders.
 
* Grouped by Type: Test that reminders are presented grouped by type.

##### User Interface Testing: 
* Test the usability of the user interface for adding, deleting, and editing reminders.

* Test list hierarchy for selecting reminder by type.
    
##### Regression Testing
* After modifying app by enhancing features or making any changes, perform regression testing to verify functionality of app is kept.
    
#### White-Box Testing Technique:

##### Unit Testing:
* Test each function dealing with adding, deleting, editing, and saving reminders.

* Test database functions for updating and storing reminders and reminder types.

##### Integration Testing:
* Test interactions of the different componenets within the app.
    
##### Database Testing:
* Test the functionality of the database when storing and retrieving reminders and their type.
    
##### Location-Based Testing:
* Test the functionality of location-based reminders.

### 1.3 Adequacy Criterion

We will use feedback from so called users for improvements within the application. Alongside with that we will also update our test cases as we are making improvements on the app to consider for the changes being made and if there are any flaws with the changes.


### 1.4 Bug Tracking

We will be using JIRA for bug tracking. It's a versatile and robust issue tracking system that offers a range of features for managing software development projects, including bug tracking and enchancement requests. 

### 1.5 Technology

Some testing technologies to be used will be selenium and JUnit testing.

## 2 Test Cases

<p><b>Test Case 1:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify a user can add a reminder.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app.<br>
        2.	Click the “Add Reminder” button.<br>
        3.	Select a reminder name , reminder type, reminder list and other optional details.<br>
        4.	Confirm the addition.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The reminder should be added to the reminder list with the correct type and name.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The reminder is added to the reminder list with the correct type and name.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensure the app correctly associates the reminder with the chosen type and name.</td>
  </tr>
</table>
<p><b>Test Case 2:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify a user can successfully edit a reminder, and the changes are updated properly.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app and navigate to the reminder list.<br>
        2.	Verify an existing reminder to edit and click on it.<br>
        3.	Make the desired modifications.<br>
        4.	Click update to confirm the changes.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The reminder should be updated with the appropriate modifications.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The reminder is updated with the appropriate modifications.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensures the app allows the user to make any changes to an existing reminder, and the new data should be properly updated.</td>
  </tr>
</table>
<p><b>Test Case 3:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify a user can successfully delete a reminder from a reminder list.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app and navigate to the reminder list.<br>
        2.  Navigate to the reminder type of the reminder you want to delete. <br>
        2.	Verify an existing reminder to delete and click on it.<br>
        3.	Confirm the deletion by clicking delete.<br>
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The reminder should be deleted from the reminder list.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The reminder is deleted from the reminder list.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensures the app allows the user to delete a reminder from a reminder list.</td>
  </tr>
</table>
<p><b>Test Case 4:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify a user can successfully delete a reminder list.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app.<br>
        2.	Verify an existing reminder list to delete and long press on it.<br>
        3.	Select delete from the menu that appears to confirm deletion.<br>
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The reminder list should be deleted from the app.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The reminder list is deleted from the app</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensures the app allows the user to delete an existing reminder list.</td>
  </tr>
</table>
</table>
<p><b>Test Case 5:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify a user can successfully edit a reminder list, and the changes are updated properly.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app.<br>
        2.	Verify an existing reminder list to edit and long press on it.<br>
        3. Select edit from the menu that appears to edit the selected reminder list.<br>
        4.	Make the desired modifications and save it.<br>
        5.	Confirm the saved changes.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The reminder list should be updated with the appropriate modifications.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The reminder list is updated with the appropriate modifications.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensures the app allows the user to make any changes to an existing reminder list, and the new data should be properly updated.</td>
  </tr>
</table>
<p><b>Test Case 6:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify  that user is able to specify a reminder by typing its name.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app.<br>
        2.	Click the “Add Reminder” button.<br>
        3.	Start typing the name of an existing reminder <br>
        4.	Select a specific reminder name from the drop-down menu that appears.<br>
        5.	Verify the details of the chosen existing reminder that is autofilled after selection.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The user should be able to find the details of an existing reminder when typing its name.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The user is be able to find the details of an existing reminder when typing its name and by selecting the suggested existing reminder provided by drop down menu.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensure the app correctly suggests the names of existing reminders when the user is typing the name and displays accurate information about the existing reminder once when it is selected from the menu.</td>
  </tr>
</table>
<p><b>Test Case 7:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify  that user to see the reminders grouped by type.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app and navigate to a reminder list.<br>
        2.	Navigate to a reminder type<br>
        3.	Verify the appropriate reminders are being displayed for the reminder type selected.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The user should be able to find existing reminders under the chosen reminder type.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The user is able to find existing reminders under the chosen reminder type.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensure the app correctly displays the existing reminders when the user selects a type.</td>
  </tr>
</table>
<p><b>Test Case 8:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify  that user to can add a reminder list.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app and click the add list button.<br>
        2.	Input the name of the reminder list to be created.<br>
        3.	Confirm the addition by clicking add.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The user should be able to create new reminder lists.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The user is able to create new reminder lists.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensure the app supports the creation of multiple reminder lists.</td>
  </tr>
</table>
</table>
<p><b>Test Case 9:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify  that user to can to set up reminders with day and time alert.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app and click the add reminder button.<br>
        2.	Input the the reminder name , reminder type , date and time.<br>
        3.  Switch on alert.<br>
        3.	Confirm the addition by clicking add.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The user should be able to set up reminders with day and time alert.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The user is able to set up reminders with day and time alert.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensure the application has the option to set up reminders with day and time alert.</td>
  </tr>
</table>
</table>
<p><b>Test Case 10:</b></p>
<table>
  <tr>
    <th>Purpose</th>
    <td>To verify  that user to can delete a reminder type.</td>
  </tr>
  <tr>
    <th>Steps</th>
    <td>1.	Open the app and navigate to a reminder list.<br>
        2.	Click on the reminder type to be deleted.<br>
        3.	Confirm the deletion by clicking delete type.
</td>
  </tr>
  <tr>
    <th>Expected result</th>
    <td>The user should be able to delete reminder types.</td>
  </tr>
  <tr>
    <th>Actual result</th>
    <td>The user is able to delete reminder types.</td>
  </tr>
  <tr>
    <th>Pass/Fail</th>
    <td>Pass</td>
  </tr>
  <tr>
    <th>Additional Information</th>
    <td>Ensure the app supports deletion of reminder types.</td>
  </tr>
</table>






