---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Developer Guide

**Team**: F08-1

**Name**: ReserveMate

**Target User Profile**: Our application is designed for small restaurant owners who have to manage numerous customer reservations and contact details. These users often operate in fast-paced environments and need a simple yet effective system to organize all reservations.


**Value Proposition**: ReserveMate provides small restaurant owners with a fast and intuitive way to manage reservation and customer contact details through a Command Line Interface (CLI). This enhances operational efficiency by streamlining organisation and ensuring easy access to customer contact details.

# Table of Contents
1. [User stories](#user-stories)
2. [User cases](#use-cases)
3. [Non-functional Requirements](#non-functional-requirements)
4. [Glossary](#glossary)


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

**Use case: UC01 - View Schedule Details**

**MSS**

1.  User requests to view the schedule list
2.  ReserveMate displays the schedule list
3.  User requests to view a specific schedule details in the list
4.  ReserveMate displays the schedule details in a popup box

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.

  Use Case ends.

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. ReserveMate shows an error message.

      Use case resumes at step 2.

**Use case: UC02 - Create A New Reservation**

**MSS**

1.  User requests to add a new reservation with customer name, number of diners, contact number, and date-time.
2.  ReserveMate validates the input.
3.  ReserveMate adds the reservation and confirms success.

    Use case ends.

**Extensions**

* 2a. Invalid customer name.
    * 2a1. ReserveMate shows an error message:
        * `Invalid customer name: Name cannot contain numbers.` (if it contains numerical values)
        * `Invalid customer name: Name must not exceed 50 characters.` (if it exceeds 50 characters)
    * Use case resumes at step 1.

* 2b. Invalid number of diners.
    * 2b1. ReserveMate shows an error message:
        * `Invalid reservation: Maximum number of diners is 10.` (if >10)
        * `Invalid reservation: Number of diners must be at least 1.` (if <1)
    * Use case resumes at step 1.

* 2c. Invalid contact number.
    * 2c1. ReserveMate shows an error message:
        * `Invalid contact number: Must start with country code '65'.` (if it does not start with "65")
        * `Invalid contact number: Must contain exactly 10 digits.` (if it is not exactly 10 digits long)
        * `Invalid contact number: Must start with '65' followed by 8 or 9.` (if the third digit is not 8 or 9)
    * Use case resumes at step 1.

* 2d. Invalid date and time.
    * 2d1. ReserveMate shows an error message:
        * `Invalid date format: Must be in YYYY-MM-DD HHmm format.` (if the format is incorrect)
        * `Invalid date: Cannot check or reserve dates beyond allowed range.` (if it is in the past beyond the allowed range)
        * `Invalid date: Nonexistent date entered (e.g., 29, 30, 31 February).` (if an invalid date is provided)
        * `Invalid reservation time: Restaurant operates between 10:00 and 21:00.` (if outside restaurant hours, considering last reservation at 21:00)
    * Use case resumes at step 1.

**Use case: UC03 - Listing Commands**

**MSS**

1. User requests to view all available commands by entering "list" (not case-sensitive).
2. ReserveMate displays the list of available commands to user.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command format.

    * 1a1. ReserveMate shows the error message: "Invalid command entered!"

      Use case ends.

**Use case: UC04 - Delete Reservation**

**MSS**

1. User requests to delete a reservation by providing an index.
2. ReserveMate deletes the particular reservation.

    Use case ends.

**Extensions**
* 1a. The index is invalid.
  * 1a1. ReserveMate prompts the user to enter a valid index.

    Use case resumes at step 1.


**Use case: UC05 - View Schedule**

**MSS**

1.  User requests to view schedule.
2.  ReserveMate retrieves and displays all existing reservations.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.

    Use Case ends.

* 2a. The list is empty.

  Use case ends.

**Use case: UC06 - Find Reservation by Name**

**MSS**

1.  User enters name to find reservation.

2.  ReserveMate shows a list of reservation where customer name contains the input name.


Use case ends.

**Extensions**

* 1a. ReserveMate detects an error in the entered data.

  * 1a1. ReserveMate requests for the correct data.

  * 1a2. User enters new data.

  * Steps 1a1-1a2 are repeated until the data entered are correct.

  Use case resumes from step 2.

* 1b. ReserveMate found no matches.

  * 1b1. ReserveMate shows No reservation found for NAME.

  Use case ends.


*{More to be added}*


### Non-Functional Requirements
1) The system should be primarily command-line based.
2) A user who can type fast should be able to accomplish tasks faster through this system compared to using
one which relies on the mouse.
3) The system can be used by a user who can understand and write english easily with a minimal learning curve.
4) The system should use Gradle as a build automation tool, and it must run on any OS which has Java 17.
5) All reservation details will be stored in a file saved locally which should allow read and write access to the
system.
6) The maximum number of reservation the system can hold is 100.
7) All code pushed into the repository must adhere to checkstyle to ensure readability and maintainability.
8) The system is designed for a single-user.

### Glossary

1. **Index**: A whole number representing the position of a reservation in the system, ranging from 1 to the total number of reservations.


2. **Contact Number**: A Singapore phone number that must start with "65" and be exactly 10 digits long, with the third digit being 8 or 9.


3. **Reservation**: A booking made by a customer to accommodate 1 - 10 diners at a specific date and time.


4. **Customer Name**: The full name of the individual making the reservation. It must not contain numbers, special symbols and should be between 2 - 50 characters.


5. **Find**: A feature that allows users to search for reservations based on a customer’s name.


6. **User**: Restaurant manager using ReserveMates
