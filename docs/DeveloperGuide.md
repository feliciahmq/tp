---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---
# ReserveMate Developer Guide

## Table of Contents
- [Acknowledgements](#acknowledgements)
- [Setting up, getting started](#setting-up-and-getting-started)
- [Design](#design)
  - [Architecture]()
  - [UI component](#ui-component)
  - [Logic component](#logic-component)
  - [Model component](#model-component)
  - [Storage component](#storage-component)
  - [Common classes](#common-classes)
- [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
- [Appendix: Requirements](#appendix-requirements)
  - [Product scope](#architecture)
  - [User stories](#user-stories)
  - [User cases](#use-cases)
  - [Non-functional Requirements](#non-functional-requirements)
  - [Glossary](#glossary)
- [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)
  - [Launch and shutdown](#launch-and-shutdown)
  - [Deleting a reservation](#clearing-data)
  - [Saving data](#saving-data)

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**
This project is based on the **AddressBook-Level3**
project created by the [SE-EDU initiative](https://se-education.org).

Generative AI tools (ChatGPT, GitHub Copilot and DeepSeek) were used for:
- Ideation and creation of JUnit test cases
- Generating method name suggestions
- Writing detailed Javadoc comments

* The format of the Developer Guide was inspired by the Developer Guide of the past project
  [ArchDuke](https://ay2122s2-cs2103-w16-3.github.io/tp/DeveloperGuide.html) and
  [InTrack](https://ay2223s1-cs2103t-t11-2.github.io/tp/DeveloperGuide.html).

--------------------------------------------------------------------------------------------------------------------

## **Setting up and getting started**

Refer to the guide [Setting up and getting started](https://se-education.org/addressbook-level3/SettingUp.html).

--------------------------------------------------------------------------------------------------------------------
## **Design**
💡 Tip: The .puml files for generating diagrams in this document are located in the [diagrams](https://github.com/AY2425S2-CS2103-F08-1/tp/tree/master/docs/diagrams) folder.
For guidance on creating and modifying these diagrams, check out the [PlantUML Tutorial](https://se-education.org/guides/tutorials/plantUml.html) for more info.

### Architecture

The ***Architecture Diagram*** below illustrates the high-level structure of the application.

![img.png](images/ArchitectureDiagram.png)

**Overview of Main Components of the architecture**

**`Main`** contains two key classes:
[Main]() and
[MainApp](), which are responsible for,
* Application Startup: Ensuring all components are initialized in the correct order and properly linked.
* At shut down: Managing the termination of components and executing necessary cleanup operations.

[**`Commons`**]() serves as a collections of shared classes that are utilized by multiple components
within the application.

The Application is further structured into four core components:

* [**`UI`**](): The UI of the Application.
* [**`Logic`**](): Processes User commands.
* [**`Model`**](): Manages in-memory application data.
* [**`Storage`**](): Reads data from, and writes data to, the hard disk.

**Interaction between Architecture Components**

The *Sequence Diagram* below illustrates how the main components interact when processing a typical command,
such as `delete 1 cfm`.

<img src="images/ArchitectureSequenceDiagram.png" alt="Sequence Diagram" width="656" />

Each of the four components (illustrated in the diagram above) follows a structured design pattern:

* Each component defines its `interface`, ensuring a well-defined *API* for interaction.
* The implementation of each component follows this interface using a corresponding **manager class**.
* For instance, the `Logic` component defines its interface in `logic.java` and implements its functionality in
`logicManage.java`.
* Other components interact with a given component through its interface rather than its concrete
implementation. This approach minimizes coupling, making the system more modular and maintainable, as illustrated
in the (partial) class diagram below.

<img src="images/ComponentManagers.png" alt = "Component Manager" width="324" />

**Detailed overview of Components**

The following sections provide an in-depth explanation of each major component in the
system, including their responsibilities and how they interact with other parts of the application

### UI Component

The **UI Component** is responsible for handling user interactions. Its API is defined in
[`UI.java`]()

<img src="images/UIClassDiagram.png" alt = "UI Class Diagram" width="1156" />

The UI consists of a `MainWindow` that is composed of multiple subcomponents, including:
* `CommandBox`
* `ResultDisplay`
* `ReservationListPanel`
* `StatusBarFooter`
* `HelpWindow`

All these components, including `MainWindow`, inherit from the abstract `UiPart` class, which captures the
commonalities between different UI elements.

**Framework and layout**

The UI is built using the **JavaFx UI framework**. The layout of UI elements defined in .fxml is located in the
`src/main/resources/view` folder. For example, the layout for `MainWindow` is specified in `MainWinow.fxml`.

**Reponsibilities**

The UI Component performs the following key functions:
* Executing User Commands: It forwards user input to the `Logic` component for processing.
* Listening for Data Changes: It observes updates in the `Model` component to refresh the displayed data dynamically.
* Maintaining a Reference to Logic: The UI keeps a direct reference to `Logic` component since it relies on
it to execute commands.
* Displaying Model Data: It depends on some classes from the `Model` component to render `Reservation` objects
stored in memory.


### Logic Component

The **Logic Component** is responsible for interpreting user input, executing commands, and managing interactions
between `UI`, `Model`, and `Storage` components. Its API is defined in
[`Logic.java`]().

<img src="images/LogicClassDiagram.png" alt = "Logic Class Diagram" width="500"/>


**Command Execution Flow**
1. The UI component calls the `Logic` interface when a user enters a command.
2. `LogicManager` delegates the command parsing to an `ReserveaMateParser` object which in turn creates a parser that
matches the command (e.g, `DeleteCommandPaser`) and uses it to parse the command.
3. Once parsed, `LogicManger` exeutes the `Command` object, which may interact with the `Model` component to
update reservation data.
4. The `Command` object returns a `CommandResult`, encapsulating feedback on the execution outcome.
5. `LogicManager` forwards the `CommandResult` to the UI component, which updates the display accordingly.

The sequence Diagram below illustrates the interactions within the `logic` component for the `execute("delete 1 cfm")
API call.

![img.png](images/DeleteSequenceDiagram.png)

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

![img.png](images/ParserClasses.png)

💡 **Note:** The lifeline for `DeleteCommandParser`
should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

How the parsing works:
* When called upon to parse a user command, the `ReserveMateParser` class creates an `XYZCommandPaser` (`XYZ` is a
placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown aboev to parse
the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `ReservateMateParser` returns back as
a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteComandParser`,...) inherits from the `Parser`
interface so that they can be treated similarly where possible e.g, during testing.

### Model Component

Its API is defined in: [`Model.java`]()

![img.png](images/ModelClassDiagram.png)

The `Model` component,
* Stores the reserve mate data i.e, all `Reservation` objects (which are contained in a
`UniqueReservationList` object).
* Stores the currently 'selected' `Reservation` object (e.g., results of a search query) as a separate *filtered* list
which is exposed to outsiders as an unmodifiable `ObservableList<Reservation>` that can be 'observed' e.g the UI can be
bound to this list so that the UI automatically updates when the data in the list change.
* Stores a `UserPref` object that represents the user's preferences. This is exposed to the outside as a
`ReadOnlyUserPref` object.
* Does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
should make sense on their own without depending on other components)

💡**Note:** An alternative (arguably, a more OOP)
model is given below. It has a `Occasion` list in the `ReserveMate`, which `Reservation` references. This allows
`ReserveMate` to only require one `Occasion` object per unique occasion, instead of each `Reservation` needing their
own `Occasion` objects.<br>

![img.png](images/BetterModelClassDiagram.png)


### Storage component

Its API is defined in: [`Storage.java`]()

![img.png](images/StorageClassDiagram.png)

The `Storage` component,
* Can save both reserve mate data and user preference data in JSON format, and read them back into corresponding
objects.
* Inherits from both `ReserveMateStorage` and `userPrefStorage`, which means it can be treated as either one (if
only the functionality of only one is needed).
* Depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
that belong to the `model`)


### Common classes

Classes used by multiple components are in the `seedu.reserve.commons` package.

--------------------------------------------------------------------------------------------------------------------

## Implementation

This section describes some noteworthy details on how certain features are implemented.

--------------------------------------------------------------------------------------------------------------------

# **Inserts implementations here (TODO)**


--------------------------------------------------------------------------------------------------------------------

## Documentation, logging, testing, configuration, dev-ops
- [Documentation guide](https://github.com/AY2425S2-CS2103-F08-1/tp/blob/master/docs/Documentation.md)
- [Testing guide](https://github.com/AY2425S2-CS2103-F08-1/tp/blob/master/docs/Testing.md)
- [Logging guide](https://github.com/AY2425S2-CS2103-F08-1/tp/blob/master/docs/Logging.md)
- [Configuration guide](https://github.com/AY2425S2-CS2103-F08-1/tp/blob/master/docs/Configuration.md)
- [DevOps guide](https://github.com/AY2425S2-CS2103-F08-1/tp/blob/master/docs/DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## Appendix: Requirements

**Team**: F08-1

**Name**: ReserveMate

**Target User Profile**: Our application is designed for small restaurant owners who have to manage numerous customer reservations and contact details. These users often operate in fast-paced environments and need a simple yet effective system to organize all reservations.


**Value Proposition**: ReserveMate provides small restaurant owners with a fast and intuitive way to manage reservation and customer contact details through a Command Line Interface (CLI). This enhances operational efficiency by streamlining organisation and ensuring easy access to customer contact details.


### User stories

Priorities: High (Must have) - `* * *`, Medium (Good to have) - `* *`, Low (Extension) - `*`

| Priority | As a …             | I want to …                                                                            | So that I can…                                                      |
|----------|--------------------|----------------------------------------------------------------------------------------|---------------------------------------------------------------------|
| `* * *`  | Frequent user      | Delete reservations by customer name                                                   | Quickly remove a cancelled booking                                  |
| `* * *`  | Productive user    | Know how many diners for each reservation                                              | Plan the seating arrangement beforehand                             |
| `* * *`  | Beginner           | Enter reservations using a simple command format                                       | Quickly add customers without getting overwhelmed                   |
| `* * *`  | Beginner           | View a list of commands within the app                                                 | Don’t have to remember them all                                     |
| `* * *`  | Hardworking user   | View the schedule                                                                      | Know when are the reservations                                      |
| `* *`    | frequent user      | filter reservations by date and time,                                                  | I can check upcoming bookings easily                                |
| `* *`    | frequent user      | blacklist customers who repeatedly cancel or don’t show up                             | I can manage reservations more effectively and avoid late customers |
| `* *`    | Advanced user      | Sort reservations by table number                                                      | Optimise seating arrangements                                       |
| `* *`    | Beginner           | Navigate around the app using minimal keystrokes                                       | Learn how to use the app quickly                                    |
| `* *`    | Beginner           | Receive confirmation prompts before deleting a reservation                             | Don’t accidentally remove a customer                                |
| `* *`    | Forgetful user     | Know shortcuts to different actions                                                    | Look up the input format every time I use the app                   |
| `* *`    | Impatient user     | Find free time slots                                                                   | Quickly schedule reservations                                       |
| `* *`    | Thoughtful user    | Reschedule reservations                                                                | Accommodate customer needs                                          |
| `* *`    | Long-time user     | Sort reservations in ascending date order                                              | Know when customers are arriving                                    |
| `* *`    | Long-time user     | Search for reservations                                                                | Get customer information easily                                     |
| `* *`    | Long-time user     | Update reservations                                                                    | Change reservations based on customer requests                      |
| `* *`    | Beginner user      | Receive a notification when double-booking a table                                     | Avoid booking conflicts                                             |
| `* *`    | Long-time user     | Store and manage customer contact details securely                                     | Protect customer privacy and ensure communication                   |
| `* *`    | Frequent user      | Sort reservations by table type (e.g. window seat, private room)                       | View reservations by seating preference                             |
| `* *`    | Long-time user     | Occasion reservations by special request (e.g. Birthday, Allergy)                      | Handle them accordingly                                             |
| `* *`    | Forgetful user     | Automatically record customer preferences (e.g. dietary restrictions, seating choices) | Provide better service without relying on memory                    |
| `* *`    | Cost-conscious user | Set up reservation deposit requirements                                                | Reduce cancellations and ensure customer commitment                 |
| `* *`    | Overwhelmed user   | Automatically decline overbooking requests                                             | Don’t have to manually reject customers                             |
| `*`      | Advanced user      | Export reservation data to a CSV file                                                  | Review past bookings or analyse trends                              |
| `*`      | Overwhelmed user   | Archive reservations                                                                   | Gather insights on customers                                        |
| `*`      | Productive user    | See statistics on customers                                                            | Identify regular customers                                          |
| `*`      | Long-time user     | Send automated reminders and confirmations                                             | Reduce last-minute cancellations                                    |
| `*`      | Frequent user      | Add custom shortcuts for frequently used commands                                      | Increase efficiency                                                 |
| `*`      | Frequent user      | See visuals of booked tables and available seats                                       | Assign tables to customers easily                                   |
| `*`      | Beginner user      | Add table size for each type of table                                                  | Organise seating more effectively                                   |


### Use cases

(For all use cases below, the **System** is the `ReserveMate` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - View Reservation's Details**

**MSS**

1. User performs <u>View Reservations (UC05)</u> to view all reservations.

2. User requests to view a specific reservation's details.

3. ReserveMate displays the reservation's details as a message.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

    * 1a1. ReserveMate shows an error message.

      Use Case ends.

* 2a. User enters an invalid index.

    * 2a1. ReserveMate shows an error message.

      Use case resumes at step 2.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC02 - Create A New Reservation**

**MSS**

1. User requests to create a new reservation.

2. User enter reservation details.

3. ReserveMate validates the input.

4. ReserveMate adds the reservation and confirms success.

    Use case ends.

**Extensions**

* 3a. User enters invalid input.

    * 3a1. ReserveMate shows an error message.

    * 3a2. User re-enters the reservation details.

    * Steps 3a1 - 3a2 are repeated until user input is valid.

      Use case resumes at step 4.

* 3b. User enters a duplicate reservation.

    * 3b1. ReserveMate display the duplicate reservation error message.

    * 3b2. User modifies the reservation details.

    * Steps 3b1 - 3b2 are repeated until the reservation details are unique.

      Use case resumes at step 4.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC03 - Listing Commands**

**MSS**

1. User requests to view all available commands.

2. ReserveMate displays the list of available commands to user.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command format.

    * 1a1. ReserveMate shows an error message

      Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC04 - Delete Reservation**

**MSS**

1. User requests to delete a reservation by providing an index and a confirmation.

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

--------------------------------------------------------------------------------------------------------------------

**Use case: UC05 - View Reservation list**

**MSS**

1. User requests to view all reservations.

2. ReserveMate retrieves and displays all existing reservations.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.

    Use Case ends.

* 2a. The list of reservations is empty.

  * 2a1. ReserveMate shows an error message.

    Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC06 - Find Reservation by Name**

**MSS**

1.  User enters name to find reservation.

2.  ReserveMate shows a list of reservation where customer name contains the entered name.

    Use case ends.

**Extensions**

* 1a. ReserveMate detects an error in the entered data.

  * 1a1. ReserveMate requests for the correct data format.

  * 1a2. User enters new data.

  * Steps 1a1-1a2 are repeated until the data entered are correct.

    Use case resumes from step 2.

* 1b. ReserveMate found no matches.

  * 1b1. ReserveMate shows No reservation found for NAME.

    Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC07 - Edit a Reservation**

**MSS**

1. User requests to edit a reservation by providing an index and new details.

2. ReserveMate checks if the reservation has not passed the current date and time.

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

--------------------------------------------------------------------------------------------------------------------

**Use case: UC08 - View all free time slots**

**MSS**

1. User requests to view all free time slots for a specific day.

2. ReserveMate retrieve all free time slots for the specific day and displays them to User.

    Use case ends.

**Extensions**

* 1a. User enters an invalid command.

  * 1a1. ReserveMate shows an error message.

    Use Case ends.

* 2a. The reservation list is empty.

  * 2a1. ReserveMate shows an error message.

    Use case ends.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC09 - Filter Reservations**

**MSS**

1. User requests to filter reservations between two given dates.

2. ReserveMate displays reservations that falls within given dates

    Use case ends

**Extensions**

* 1a. The user enters an invalid command.

  * 1a1. ReserveMate shows an error message.

    Use case resumes at step 1.

* 1b. The user enter invalid date(s)

  * 1b1. ReserveMate shows an error message.

  * 1b2. User enters new dates.

  * Steps b1-1b2 are repeated until valid dates are provided.

    Use case resumes at step 2.

* 2a. ReserveMate found no reservations between the 2 dates.

  * 2a1. ReserveMate displays No reservations found for the date range.

--------------------------------------------------------------------------------------------------------------------

**Use case: UC10 - Manage Reservation Preferences**

**MSS**

1. User requests to save a preference for a specific reservation by providing an index and preference text.

2. ReserveMate saves the preference for the specified reservation.

3. ReserveMate displays a success message.

   Use case ends.

**Extensions**

* 1a. The user enters an invalid command format.

  * 1a1. ReserveMate shows an error message providing the correct format.

    Use case resumes at step 1.

* 1b. The specified index is invalid.

  * 1b1. ReserveMate shows an invalid reservation index error message.

    Use case resumes at step 1.

* 2a. No preference has been set for the reservation.

  * 2a1. ReserveMate displays a message indicating no preference set.

    Use case ends

--------------------------------------------------------------------------------------------------------------------

**Use case: UC11 - Viewing statistics**

**MSS**

1. User enters command to display reservation statistics.

2. ReserveMate displays a new window containing the reservation statistics, a bar chart grouped by number of diners.

   Use case ends.

**Extensions**

* 1a. User enters an invalid command format.

    * 1a1. ReserveMate shows an error message.

      Use case ends.

* 2a. There is no reservation data available.

    * 2a1. ReserveMate shows an empty chart indicating that there are no reservations to summarize.

      Use case ends.


--------------------------------------------------------------------------------------------------------------------

### Non-Functional Requirements
1) The system should be primarily command-line based.
2) A user who can type fast should be able to accomplish tasks faster through this system compared to using
one which relies on the mouse.
3) The system can be used by a user who can understand and write english easily with a minimal learning curve.
4) The system should use Gradle as a build automation tool, and it must run on any OS which has Java 17.
5) All reservation details will be stored in a file saved locally which should allow read and write access to the
system.
6) All code pushed into the repository must adhere to checkstyle to ensure readability and maintainability. 
7) The system is designed for a single-user. 
8) The response to any commands carried out by the user should become visible within 5 seconds. 
9) The user is not required to have an internet connection in order for the application to function. 
10) Data should be stored consistently even after closing and reopening the app. 
11) Should work on any mainstream OS as long as it has java 17 installed

### Glossary

* **User**: Restaurant manager using ReserveMates
* **Mainstream OS**: Windows, MacOS, Linux, Unix
* **Reservation**: Reservation details of the customer

--------------------------------------------------------------------------------------------------------------------

## Appendix: Instructions for manual testing

Given below are instructions to test the app manually.

💡 **Note:**
These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.


### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   2. Open a command terminal, cd into the folder you put the jar file in,
      and use the java -jar reservemate.jar command to run the application.<br>
      Expected: Shows the GUI with a set of sample reservations.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Display help

Command: `help` <br>
More information on usage:
[help command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#displaying-commands-help)

1. Test case: `help` <br>
   Expected: Displays a list of all commands in ReserveMate.

### Listing all reservations

Command: `list` <br>
More information on usage:
[list command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#listing-all-reservations-list)

1. Test case: `list` <br>
   Expected: Shows a list of all reservations in ReserveMate.

### Viewing statistics

Command: `stats` <br>
More information on usage:
[stats command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#display-reservation-statistics-stats)

1. Test case: `stats` <br>
   Expected: Displays statistics of reservation in ReserveMate.


### Exiting application

Command: `exit` <br>
More information on usage:
[exit command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#exiting-the-program-exit)

1. Test case: `exit` <br>
   Expected: Exits ReserveMate and all data is saved.

### Adding a reservation

Command: `add` <br>
More information on usage:
[add command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#adding-a-reservation-add)

1. Adding a reservation to ReserveMate.

   1. Test case: `add n/John Doe p/98765432 e/johnd@example.com x/5 d/2025-04-12 1800 o/BIRTHDAY`<br>
      Expected: New reservation added: John Doe; Phone: 98765432;
      Email: johnd@example.com; Number of Diners: 5; Occasion: [BIRTHDAY]

   2. Test case: `add n/Jane Doe e/betsycrowe@example.com x/3 p/81234567 o/GRADUATION d/2025-04-20 1800`<br>
      Expected: New reservation added: Jane Doe; Phone: 81234567;
      Email: betsycrowe@example.com; Number of Diners: 3; Occasion: [GRADUATION]

   3. Test case: `add n/Jett`<br>
      Expected: No reservation added. Error details shown in error message.

   4. Other incorrect add commands to try: `add` , `add n/jo p/999`<br>
      Expected: Similar to previous.

### Deleting a reservation

Command: `delete` <br>
More information on usage:
[delete command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#deleting-a-reservation-delete)

1. Deleting a reservation while all reservations are being shown.

   1. Prerequisites: List all reservations using the list command. Multiple reservations in the list.

   2. Test case: `delete 1 cfm`<br>
      Expected: First reservation is deleted from the list. Index of the deleted reservation is shown in the success
   message

   3. Test case: `delete 1`<br>
      Expected: No reservation deleted. An Error confirmation prompt shown in the error message.

   4. Test case: `delete 0`<br>
      Expected: No reservation is deleted. Error details shown in the error message.

   5. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a reservation while reservations are being filtered.

   1. Prerequisites: Filter the reservation list using either `find` command. Multiple reservations in the list.

   2. Test case: Similar to previous<br>
      Expected: Similar to previous


### Editing a reservation

Command: `edit` <br>
More information on usage:
[edit command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#editing-a-reservation-edit)

1. Editing an existing reservation.

   1. Prerequisites: There exists a reservation in ReserveMate.

   2. Testcase: `edit 1 p/91234567 e/johndoe@example.com`<br>
      Expected: Edited Reservation: John Doe; Phone: 91234567; Email: johndoe@example.com; Number of Diners: 5;
   Occasion: [BIRTHDAY]

   3. Testcase: `edit 2 n/Brittany o/`<br>
      Expected: Edited Reservation: Brittany; Phone: 91236474;
      Email: johnny@example.com; Number of Diners: 1; Occasion:

   4. Test case: `edit 1` <br>
      Expected: No reservation is edited. Error details shown in error message

   5. Other incorrect edit commands to try: `edit`, `edit 0 n/john`<br>
      Expected: Similar to previous


## Managing preference

Command: `pref save` <br>
More information on usage:
[pref command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#managing-reservation-preferences-pref)


1. Saving a preference for an existing reservation.

   1. Prerequisites: There exists a reservation in ReserveMate.

   2. Testcase: `pref save 1 Window seat preferred, allergic to nuts`<br>
      Expected: Saved preference for reservation: 1

   3. Testcase: `pref save 3 No seafood`<br>
      Expected: Saved preference for reservation: 3

   4. Testcase: `pref save 2`<br>
      Expected: No preference is saved. Error details shown in the error message.

2. Other incorrect commands to try

   1. Testcase: `pref update 1 Vegan menu`<br>
      Expected: Invalid command format. Error message displayed.

   2. Testcase: `pref`<br>
      Expected: Invalid command format. Error message displayed.


## Show reservation details

Command: `show`<br>
More information on usage:
[show command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#showing-reservation-details-show)

1. Viewing reservation details.

   1. Prerequisites: There exists a reservation in ReserveMate.

   2. Testcase: `show 1`<br>
      Expected: Details of Reservation: John Doe; Phone: 98765432;
      Email: johnd@example.com; Number of Diners: 5; Occasion: [BIRTHDAY]

   3. Testcase (reservation without an occasion): `show 2`<br>
      Expected: Details of Reservation: Jane Doe; Phone: 81234567;
      Email: betsycrowe@example.com; Number of Diners: 3; Occasion:

   4. Testcase: `show 0`<br>
      Expected: No reservation shown. Error details shown in error message.

   5. Other incorrect show commands to try: `show`, `show first`
      Expected: Similar to previous

## Find reservations

Command: `find`<br>
More information on usage:
[find command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#locating-reservations-by-name-find)

1. Finding reservation by name.

   1. Prerequisites: There exists a reservation in ReserveMate.

   2. Testcase: `find john`<br>
      Expected: 1 reservations listed! 1. John Doe (5 diners) - 2025-04-12 1800

   3. Testcase `find john jane`<br>
      Expected: 2 reservations listed! 1. John Doe (5 diners) - 2025-04-12 1800 2. Jane Doe
      (3 diners) - 2025-04-20 1800

   4. Testcase: `find Michael`<br>
      Expected: No reservation shown.

   5. Testcase: `find`<br>
      Expected: Invalid command format. Error message displayed.

   6. Other incorrect find commands to try: `find 1`<br>
      Expected: Similar to previous

## Filter reservations

Command: `filter`<br>
More information on usage:
[filter command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#filtering-the-reservations-filter)

1. Filtering reservations within a date range.

   1. Prerequisites: There exists a reservation in ReserveMate.

   2. Testcase: `filter sd/ 2025-04-12 1400 ed/ 2025-05-15 1400`<br>
      Expected: Here are the available reservations for the date range: 1. John Doe
      (5 diners) - 2025-04-12 1800 2. Jane Doe (3 diners) - 2025-04-20 1800

   3. Testcase `filter sd/ 2026-12-20 1200 ed/ 2026-12-22 1200`<br>
      Expected: No reservations found for the date range.

   4. Testcase: `filter sd/ 2025-05-20 1400 ed/ 2025-04-18 1400`<br>
      Expected: No reservations shown as user input an invalid date, an error message is displayed.

   5. Testcase: `filter sd/ 2026/12/12 1400 ed/ 2026-12-15 1400`<br>
      Expected:  Invalid date format, an error message is shown.

   6. Testcase: `filter sd/ 2026-12-12 1400`<br>
      Expected:  Invalid command format. Error message displayed.

   7. Other incorrect filter commands to try: `filter`<br>
      Expected: Similar to previous

## Find Free time slots

Command: `free`<br>
More information on usage:
[free command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#free-reservations-free)

1. Viewing available reservation slots base given date.

   1. Prerequisites: There are available reservation time slots in given date.

   2. Testcase: `free d/2025-04-28`<br>
      Expected: Available free time slots: - 2025-04-28 1600 to 2025-04-28 1700 - 2025-04-28 2000 to 2025-04-28 2100

   3. Testcase: `free today`<br>
      Expected: Invalid command format. Error message displayed.

   4. Other incorrect free commands to try: `free`<br>
      Expected: Similar to previous

## Clearing data

command: `clear`<br>
More information on usage:
[clear command](https://ay2425s2-cs2103-f08-1.github.io/tp/UserGuide.html#clearing-all-entries-clear)

1. Test case: `clear cfm`
   Expected: Reservation book has been cleared!

2. Test case: `clear`
   Expected: Reservation book not cleared. An Error confirmation prompt shown in the error message.

3. Other incorrect clear command: `clear confirm`, `clear cFM`
   Expected: Similar to previous


## Saving data

1. Dealing with missing/corrupted data files.

   1. Open reserveMate.jar and make any changes to the reservation list with the commands provided,
   being sure to leave at least one reservation in the list

   2. Edit the data/reservemate.json file by making any one of the following changes before saving the file and
   reopening reserveMate.jar

      1. Test case: Edit the phone field of the first reservation to `invaild`.<br>
      Expected: ReserveMate starts with an empty reservation list.

      2. Test case: Edit the dateTime field of the first reservation to `invalid`.<br>
      Expected: Similar to previous

      3. Test case: Edit the diners field of the first reservation to `invalid`.<br>
         Expected: Similar to previous

2. Dealing with missing files.

    1. Test case: Exit ReserveMate, then delete the `data/reservemate.json` file. Reopen ReserveMate.
       Expected: All reservations are deleted. ReserveMate will start as expected with sample data provided.

    2. Test case: Exit ReserveMate, then delete `preferences.json`. Reopen ReserveMate.
       Expected: The previous user preferences such as the size of the window will be deleted. ReserveMate starts
       with default settings.

    3. Test case: Exit ReserveMate, then delete the config.json file. Reopen ReserveMate.
       Expected: ReserveMate starts as expected, with either the sample data provided or any previously saved data,
       if present. The size of the window should be the same as the previously saved user preference.
