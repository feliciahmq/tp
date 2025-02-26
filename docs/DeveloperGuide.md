---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

### User stories

Priorities: High (Must have) - `* * *`, Medium (Good to have) - `* *`, Low (Extension) - `*`

| Priority | As a …​             | I want to …​                                                                           | So that I can…​                                                     |
|----------|---------------------|----------------------------------------------------------------------------------------|---------------------------------------------------------------------|
| `* * *`  | Frequent user       | Delete reservations by customer name                                                   | Quickly remove a cancelled booking                                  |
| `* * *`  | Productive user     | Know how many diners for each reservation                                              | Plan the seating arrangement beforehand                             |
| `* * *`  | Beginner            | Enter reservations using a simple command format                                       | Quickly add customers without getting overwhelmed                   |
| `* * *`  | Beginner            | View a list of commands within the app                                                 | Don’t have to remember them all                                     |
| `* * *`  | Hardworking user    | View the schedule                                                                      | Know when are the reservations                                      |
| `* *`    | frequent user       | filter reservations by date and time,                                                  | I can check upcoming bookings easily                                |
| `* *`    | frequent user       | blacklist customers who repeatedly cancel or don’t show up                             | I can manage reservations more effectively and avoid late customers |
| `* *`    | Advanced user       | Sort reservations by table number                                                      | Optimise seating arrangements                                       |
| `* *`    | Beginner            | Navigate around the app using minimal keystrokes                                       | Learn how to use the app quickly                                    |
| `* *`    | Beginner            | Receive confirmation prompts before deleting a reservation                             | Don’t accidentally remove a customer                                |
| `* *`    | Forgetful user      | Know shortcuts to different actions                                                    | Look up the input format every time I use the app                   |
| `* *`    | Impatient user      | Find free time slots                                                                   | Quickly schedule reservations                                       |
| `* *`    | Thoughtful user     | Reschedule reservations                                                                | Accommodate customer needs                                          |
| `* *`    | Long-time user      | Sort reservations in ascending date order                                              | Know when customers are arriving                                    |
| `* *`    | Long-time user      | Search for reservations                                                                | Get customer information easily                                     |
| `* *`    | Long-time user      | Update reservations                                                                    | Change reservations based on customer requests                      |
| `* *`    | Beginner user       | Receive a notification when double-booking a table                                     | Avoid booking conflicts                                             |
| `* *`    | Long-time user      | Store and manage customer contact details securely                                     | Protect customer privacy and ensure communication                   |
| `* *`    | Frequent user       | Sort reservations by table type (e.g. window seat, private room)                       | View reservations by seating preference                             |
| `* *`    | Long-time user      | Tag reservations by special request (e.g. Birthday, Allergy)                           | Handle them accordingly                                             |
| `* *`    | Forgetful user      | Automatically record customer preferences (e.g. dietary restrictions, seating choices) | Provide better service without relying on memory                    |
| `* *`    | Cost-conscious user | Set up reservation deposit requirements                                                | Reduce cancellations and ensure customer commitment                 |
| `* *`    | Overwhelmed user    | Automatically decline overbooking requests                                             | Don’t have to manually reject customers                             |
| `*`      | Advanced user       | Export reservation data to a CSV file                                                  | Review past bookings or analyse trends                              |
| `*`      | Overwhelmed user    | Archive reservations                                                                   | Gather insights on customers                                        |
| `*`      | Productive user     | See statistics on customers                                                            | Identify regular customers                                          |
| `*`      | Long-time user      | Send automated reminders and confirmations                                             | Reduce last-minute cancellations                                    |
| `*`      | Frequent user       | Add custom shortcuts for frequently used commands                                      | Increase efficiency                                                 |
| `*`      | Frequent user       | See visuals of booked tables and available seats                                       | Assign tables to customers easily                                   |
| `*`      | Beginner user       | Add table size for each type of table                                                  | Organise seating more effectively                                   |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `ReserveMate` and the **Actor** is the `user`, unless specified otherwise)

**Use case: View Schedule Details**
Use case: UC01 - View Schedule Details

**MSS**

1.  User requests to view the schedule list
2.  ReserveMate displays the schedule list
3.  User requests to view a specific schedule details in the list
4.  ReserveMate displays the schedule details in a popup box

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.
  
    Use Case ends.

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ReserveMate shows an error message.

      Use case resumes at step 2.

**Use case: View Schedule**
Use case: UC05 - View Schedule

**MSS**

1.  User requests to view schedule 
2.  ReserveMate retrieves and displays all existing reservations

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.
  
    Use Case ends.

* 2a. The list is empty.

  Use case ends.
    
*{More to be added}*
