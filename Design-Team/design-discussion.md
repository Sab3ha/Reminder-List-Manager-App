# Design-Discussion:
**Version:** 2
## Design 1:


![design1 -1](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/106637240/58d9cd4f-7a2f-4e7a-ae1d-eac911bdc86f)


__Pros__ :
- The design contains important class diagrams such as ReminderListManager, ReminderList, and Reminder.
- The Reminder class has alert and location classes.
- The attributes and functions related to ReminderList, Reminder, and ReminderManager are correct according to the instructions. 
- The diagram shows multiplicities which helps us to know the allowable number of instances of the class.

__Cons__ : 

- The location should be defined by longitude and latitude instead of being stored as a string. 
- There is a lot of redundancy in the relationship between different classes.
- User interface and User can be simplified into one single class.


## Design 2:


![Design 2-1](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/106637240/15ac9785-1595-4505-a909-277a5fd9441c)


__Pros__ :
- This UML design has enough attributes and methods to create a reminder database
- The diagram mentions the return type of the methods
- The design is neat and simple
  
__Cons__ : 
-  Some of the classes are not connected to one another which makes the diagram a little bit confusing. 
-  The reminder repository is not connected to any other entity. So, it cannot access the reminders it needs for the methods
- The diagram doesn’t satisfy the kind of relationship each entity has with each other




## Design 3:


![design 3-1](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/106637240/04347e1c-eea8-4bb8-807b-c4abe14cf344)


__Pros__ :
- The location class is separated and follows given instructions.
- The alert class is separate and follows the given instructions.  
- The UML diagram is nicely organized. 

__Cons__ :
- The database is empty, with no attributes or methods. 
- The ReminderType should be something Reminder takes, not the other way around.


## Design 4:


![design 4-1](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/106637240/4c17629e-11a3-4fd9-b480-6fd7de03f379)


__Pros__ :
- This design is precise, and it has all the necessary classes needed for the reminder UML diagram. 
- The methods inside each class are precise with the requirements of the UML diagram such as add, delete, check, uncheck, etc methods in the ReminderList class. 
- It’s easy to read and add on. 

__Cons__ :
- The class User is not needed for the application to function as there is no function like user login
-The database ReminderDB is not necessary to be represented as it is a Class Diagram
- The diagram doesn’t contain any labels of relationships between classes.



## Design 5:


![design 5-1](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/106637240/da24a73d-3d9b-43dd-8952-4ff064fd5974)


__Pros__ :
- The names of the attributes and methods are clear and are similar to the Java syntax. 
- The attributes and methods in the Location and Alert classes are written accurately such as the latitude and longitude similar to accessing Geolocation in Java. 

__Cons__ :  
- The relationships of the classes are not suitable for the UML diagram.    
- The database class has a lot of relations with almost every class, which makes it redundant. 
- This UML diagram can be simplified a lot more so that it’s much more concise, and easy to follow through. 



## Design 6:


![design 6-1](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/106637240/302ba237-e13a-45f1-9c9d-e1579c1553e0)


__Pros__ :
- The UML diagram looks neat and organized so it is easy to follow.
- The AutoSave function in ReminderList seems like a thoughtful design choice appropriate for the proper functionality of the app. 
- All the classes except database and user are needed for the diagram.

__Cons__ :
- The “triggers” relationship between the Reminder class and the Alert class seems inappropriate.
- While the attributes and methods have their accessibility specified, the “setRepeat” method and the “setLocation” method do not have their accessibility specified.
-  The aggregation relationship of ReminderType and Reminder is not appropriate since Reminder is a type itself.

---

## Team Design:
![Team design](https://github.com/qc-se-fall23/370Fall23Sec131Team1/assets/106637240/e84f7c48-ad59-4d1c-988f-b095a42cea80)



__Commonalities__ :

- The classes we used in the team design are pretty similar to the classes of our individual designs.
- Most of the methods of the classes are common between the team design and individual designs.
- The multiplicities of each class are also common. 

__Differences__ :
- This UML diagram contains neither a user class nor a Database class. 
- It’s much more concise and less redundant. 
- The labeling of the relationships is different compared to individual designs

---

## Summary: 

In our discussions about design, teamwork, and various other relevant aspects, we've learned some valuable lessons. 
In terms of design, the project helped to gain an in-depth analysis of the UML diagram. We learned to identify the 
relevant entities needed to draw the diagram. We gained a better understanding of identifying the relationships 
between the entities. Analyzing each other’s designs gave us an idea about the features that we would need to 
incorporate in the final design. We also developed an understanding of how to design UML diagrams so that they 
satisfy all the requirements in a simple non-redundant way. In terms of creating the design, we also thought 
about the implementation aspect. So, we learned how to make the diagram comprehensible.

