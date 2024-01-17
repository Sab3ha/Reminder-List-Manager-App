1. The ReminderList is what is used to manage lists of reminders
2. ReminderRepository will be the class to manage insertion, deletion, and updates for Reminders and ReminderLists.
3. Reminders have a type property for filtering by type.
4. ReminderList will handle searching by name, but it is also doable in ReminderViewModel due to Reminders being in a single table.
5. ReminderViewModel will have listeners that call upon ReminderRepository when any Reminder is modified. A timer could also be added for automatic saves.
6. Reminders have a checked property.
7. A checkbox can be on the top left of the screen aligned with the rest of the checkboxes to check all.
8. Reminders have a checked property that gets saved to the database.
9. Due to Reminders having a type property, this can be done.
10. The ReminderViewModel will have a list of reminder lists.
11. Reminders have an alert time property for this.