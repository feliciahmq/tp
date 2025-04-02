---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---
# ReserveMate Developer Guide

## Table of Contents
- [Acknowledgements](#acknowledgements)
- [Setting up, getting started](#setting-up-and-getting-started)
- [Design]()
  - [Architecture]()
  - [UI component]()
  - [Logic component]()
  - [Model component]()
  - [Storage component]()
  - [Common classes]()
- [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
- [Appendix: Requirements](#appendix-requirements)
  - [Product scope]()
  - [User stories](#user-stories)
  - [User cases](#use-cases)
  - [Non-functional Requirements](#non-functional-requirements)
  - [Glossary](#glossary)
- [Appendix: Instructions for manual testing]()
  - [Launch and shutdown]()
  - [Deleting a reservation]()
  - [Saving data]()

## Acknowledgements
This project is based on the **AddressBook-Level3** 
project created by the [SE-EDU initiative](https://se-education.org).

Generative AI tools (ChatGPT, GitHub Copilot and DeepSeek) were used for:
- Ideation and creation of JUnit test cases
- Generating method name suggestions
- Writing detailed Javadoc comments

## Setting up and getting started
Refer to the guide [Setting up and getting started](https://se-education.org/addressbook-level3/SettingUp.html).

## Documentation, logging, testing, configuration, dev-ops
- [Documentation guide]()
- [Testing guide]()
- [Logging guide]()
- [Configuration guide]()
- [DevOps guide]()


## Appendix: Requirements

**Team**: F08-1

**Name**: ReserveMate

**Target User Profile**: Our application is designed for small restaurant owners who have to manage numerous customer reservations and contact details. These users often operate in fast-paced environments and need a simple yet effective system to organize all reservations.


**Value Proposition**: ReserveMate provides small restaurant owners with a fast and intuitive way to manage reservation and customer contact details through a Command Line Interface (CLI). This enhances operational efficiency by streamlining organisation and ensuring easy access to customer contact details.


### User stories

Priorities: High (Must have) - `* * *`, Medium (Good to have) - `* *`, Low (Extension) - `*`

| Priority | As a …             | I want to …                                                                         | So that I can…                                               |
|----------|--------------------|-------------------------------------------------------------------------------------|--------------------------------------------------------------|
| `* * *`  | Frequent user      | Delete reservations by customer name                                                | Quickly remove a cancelled booking                           |
| `* * *`  | Productive user    | Know how many diners for each reservation                                           | Plan the seating arrangement beforehand                      |
| `* * *`  | Beginner           | Enter reservations using a simple command format                                    | Quickly add customers without getting overwhelmed            |
| `* * *`  | Beginner           | View a list of commands within the app                                              | Don’t have to remember them all                              |
| `* * *`  | Hardworking user   | View the schedule                                                                   | Know when are the reservations                               |
| `* *`    | frequent user      | filter reservations by date and time,                                               | I can check upcoming bookings easily                         |
| `* *`    | frequent user      | blacklist customers who repeatedly cancel or don’t show up                          | I can manage reservations more effectively and avoid late customers |
| `* *`    | Advanced user      | Sort reservations by table number                                                   | Optimise seating arrangements                                |
| `* *`    | Beginner           | Navigate around the app using minimal keystrokes                                    | Learn how to use the app quickly                             |
| `* *`    | Beginner           | Receive confirmation prompts before deleting a reservation                          | Don’t accidentally remove a customer                         |
| `* *`    | Forgetful user     | Know shortcuts to different actions                                                 | Look up the input format every time I use the app            |
| `* *`    | Impatient user     | Find free time slots                                                                | Quickly schedule reservations                                |
| `* *`    | Thoughtful user    | Reschedule reservations                                                             | Accommodate customer needs                                   |
| `* *`    | Long-time user     | Sort reservations in ascending date order                                           | Know when customers are arriving                             |
| `* *`    | Long-time user     | Search for reservations                                                             | Get customer information easily                              |
| `* *`    | Long-time user     | Update reservations                                                                 | Change reservations based on customer requests               |
| `* *`    | Beginner user      | Receive a notification when double-booking a table                                  | Avoid booking conflicts                                      |
| `* *`    | Long-time user     | Store and manage customer contact details securely                                  | Protect customer privacy and ensure communication            |
| `* *`    | Frequent user      | Sort reservations by table type (e.g. window seat, private room)                    | View reservations by seating preference                      |
| `* *`    | Long-time user     | Occasion reservations by special request (e.g. Birthday, Allergy)                   | Handle them accordingly                                      |
| `* *`    | Forgetful user     | Automatically record customer preferences (e.g. dietary restrictions, seating choices) | Provide better service without relying on memory             |
| `* *`    | Cost-conscious user | Set up reservation deposit requirements                                             | Reduce cancellations and ensure customer commitment          |
| `* *`    | Overwhelmed user   | Automatically decline overbooking requests                                          | Don’t have to manually reject customers                      |
| `*`      | Advanced user      | Export reservation data to a CSV file                                               | Review past bookings or analyse trends                       |
| `*`      | Overwhelmed user   | Archive reservations                                                                | Gather insights on customers                                 |
| `*`      | Productive user    | See statistics on customers                                                         | Identify regular customers                                   |
| `*`      | Long-time user     | Send automated reminders and confirmations                                          | Reduce last-minute cancellations                             |
| `*`      | Frequent user      | Add custom shortcuts for frequently used commands                                   | Increase efficiency                                          |
| `*`      | Frequent user      | See visuals of booked tables and available seats                                    | Assign tables to customers easily                            |
| `*`      | Beginner user      | Add table size for each type of table                                               | Organise seating more effectively                            |


### Use cases

(For all use cases below, the **System** is the `ReserveMate` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - View Reservation's Details**

**MSS**

1.  User performs <u>View Reservations (UC05)</u> to view all reservations.
2.  User requests to view a specific reservation's details.
3.  ReserveMate displays the reservation's details as a message.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.

    Use Case ends.

* 2a. The given index is invalid.

    * 2a1. ReserveMate shows an error message.

      Use case resumes at step 2.

**Use case: UC02 - Create A New Reservation**

Actor: Restaurant Manager, ReserveMate

**MSS**

1. Restaurant Manager requests customer's reservation details.
2. Restaurant Manager enter reservation details.
3. ReserveMate validates the input.
4. ReserveMate adds the reservation and confirms success.

    Use case ends.

**Extensions**

* 3a. ReserveMate detects an error in the user input.
    * 3a1. ReserveMate shows an error message.
    * 3a2. User enters new input.
    * Steps 3a1 - 3a2 are repeated until user input is correct.
    * Use case resumes at step 4.
* 3b. ReserveMate detects a duplicate reservation.
    * 3b1. ReserveMate display the duplicate reservation error message.
    * 3b2. User modify the reservation details.
    * Steps 3b1 - 3b2 are repeated until the reservation details are unique.
    * Use case resumes at step 4.

**Use case: UC03 - Listing Commands**

**MSS**

1. User requests to view all available commands by entering "help".
2. ReserveMate displays the list of available commands to user.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command format.

    * 1a1. ReserveMate shows an error message

      Use case ends.

**Use case: UC04 - Delete Reservation**

**MSS**

1. User requests to delete a reservation by providing an index and confirmation.
2. ReserveMate deletes the particular reservation.

    Use case ends.

**Extensions**
* 1a. The index is invalid.

  * 1a1. ReserveMate prompts the user to enter a valid index.

    Use case resumes at step 1.

* 1b. Confirmation not given

  * 1b1. ReserveMate prompts the user to confirm the deletion

    use case resumes at step 1.

* 1c. User enters an invalid command format.

  * 1c1. ReserveMate shows an error message providing the correct format.

    use case resumes at step 1.


**Use case: UC05 - View Reservation list**

**MSS**

1.  User requests to view all reservations.
2.  ReserveMate retrieves and displays all existing reservations.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.

    Use Case ends.

* 2a. The list is empty.

  * 2a1. ReserveMate shows an error message.

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

**Use case: UC07 - Edit a Reservation**

**MSS**

1. User requests to edit a reservation by providing an index and new details.

2. ReserveMate check if the reservation has not passed the current date and time.

3. ReserveMate updates the reservation with new details.

4. ReserveMate displays success message with updated reservation details in the reservation list.

Use case ends.

**Extensions**

* 1a. User enters an invalid command format.

  * 1a1. ReserveMate shows an error message providing the correct format.

  Use case resumes at step 1.

* 2a. The user given index is not a valid reservation.

  * 2a1. ReserveMate shows invalid reservation index error.

  Use case resumes at step 1.

* 2b. The reservation date has already passed.

  * 2b1. ReserveMate shows cannot edit past reservation error.

  Use case ends.

* 3a. The new user details would cause duplicate reservation.

  * 3a1. ReserveMate shows duplicate reservation message.

  Use case resumes at step 1.


**Use case: UC08 - View all free time slots for a specific day**

**MSS**

1. User requests to view free time slots for a specific day.

2. ReserveMate retrieve all free time slots for that specific day and display it to User.

Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. ReserveMate shows an error message.

      Use Case ends.

* 1a. User enters an invalid date.

    * 2a1. ReserveMate shows an error message.

      Use case ends.

**Use case: UC09 - Filter Reservations**

**MSS**

1. User requests to filter reservations between 2 given dates.

2. ReserveMate displays reservations which fits criteria.

Use case ends

**Extensions**
* 1a. The user enters an invalid command.

  * 1a1 ReserveMate prompts user to input a valid command.

  Use case resumes at step 1.

* 2a. ReserveMate found no reservations between the 2 dates.

  * 2a1. ReserveMate displays No reservations found for the date range.


**Use case: UC10 - Manage Reservation Preferences**

**MSS**

1. User requests to save a preference for a specific reservation by providing an index and preference text.
2. ReserveMate saves the preference for the specified reservation.
3. ReserveMate displays a success message.

   Use case ends.

**Alternative Flow 1: View Preference**

1. User requests to view a preference for a specific reservation by providing an index.
2. ReserveMate retrieves the preference for the specified reservation.
3. ReserveMate displays the preference.

   Use case ends.

**Extensions**

* 1a. The user enters an invalid command format.
  * 1a1. ReserveMate shows an error message providing the correct format.

    Use case resumes at step 1.

* 1b. The specified index is invalid.
  * 1b1. ReserveMate shows an invalid reservation index error.

    Use case resumes at step 1.

* 2a. No preference has been set for the reservation.
  * 2a1. ReserveMate displays a message indicating no preference set.

    Use case ends


**Use case: UC11 - Viewing statistics**

**MSS**

1. User enters the "stats" command to display reservation statistics.
2. ReserveMate displays a new window containing the reservation statistics, a pie chart grouped by number of diners.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command format.

    * 1a1. ReserveMate shows an error message.

      Use case ends.
* 2a. There is no reservation data available.

    * 2a1. ReserveMate shows an empty chart indicating that there are no reservations to summarize.

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

* **User**: Restaurant manager using ReserveMates
* **Mainstream OS**: Windows, MacOS, Linux, Unix
* **Reservation**: Reservation details of the customer
