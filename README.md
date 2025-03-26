[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/AY2425S2-CS2103-F08-1/tp/actions)

[![codecov](https://codecov.io/gh/AY2425S2-CS2103-F08-1/tp/graph/badge.svg?token=8D3EDHO8BA)](https://codecov.io/gh/AY2425S2-CS2103-F08-1/tp)

![Ui](docs/images/Ui.png)

# ReserveMate User Guide

## Table of Contents
1. [Introduction](#introduction)
2. [Features](#features)
3. [Command Summary](#command-summary)
4. [Acknowledgement](#acknowledgement)

## Introduction
Welcome to **ReserveMate**, your go-to application for managing reservations efficiently. ReserveMate helps you track **reservation details**. This guide provides instructions on how to use ReserveMate effectively.

## Features
ReserveMate supports a variety of commands to help you manage your reservations:

### Feature: Create a new reservation
**Purpose:** The create function will enable users to add new reservations to reserve by capturing relevant details such as name, number of diners, contact information, date and time of reservation.

**Command format:** `add n/<NAME> p/<PHONE> e/<EMAIL> x/<NUMBER_OF_DINERS> d/<DATETIME> <o/OCCASION>...…​`

**Example commands:**
```
add n/John Doe p/98765432 e/johnd@example.com x/5 d/2026-12-12 1800 o/Birthday
ADD n/John Doe e/johnd@example.com x/5 d/2026-12-12 1800 t/nutAllergy o/Anniversary
```

### Feature: Editing a reservation
**Purpose:** The edit function will enable users to edit existing reservations by specifying the detail(s) of the reservation to be changed.

**Command format:** `edit <INDEX> [n/NAME] [p/PHONE] [e/EMAIL] [x/NUMBER_OF_DINERS] [d/DATETIME] [o/OCCASION]…​`

**Example commands:**
```
edit 1 n/John Doe p/98765432 e/johnd@example.com x/5 d/2026-12-12 1800 o/Birthday
EDIT 1 n/John Doe e/johnd@example.com x/5 d/2026-12-12 1800 o/None p/98765432
edit 1 d/2026-12-12 1800
```
### Feature: Delete reservation by reservation number
**Purpose:** To delete a reservation.

**Command format:** `delete <INDEX cfm>`

**Example commands:**
```
delete 2 cfm
Delete 2 cfm
```
### Feature: Show Reservation details
**Purpose:** Show details of a specific reservation to user.

**Command format:** `show <INDEX>`

**Example commands:**
```
show 1
Show 1
```

### Feature: View Reservation list
**Purpose:** Allow the user to view the entire reservation schedule without specifying a date range.

**Command format:** `list`

**Example commands:**
```
list
List
```

### Feature: View list of commands 
**Purpose:** Display the available list of commands to the user.

**Command format:** `help`

**Example commands:**
```
help
Help
```

### Feature: Find reservation by name
**Purpose:** Get reservation information easily

**Command format:** `find <RESERVATION NAME>`

**Example commands:**
```
find Bobby
Find Bobby
```

### Feature: Clearing all reservations
**Purpose:** Deletes all reservations in ReserveMate.

**Command format:** `clear`

**Example commands:**
```
clear
Clear
```

### Feature: Exiting the application
**Purpose:** Terminates the application when no longer in use.

**Command format:** `exit`

**Example commands:**
```
exit
Exit
```

## Command Summary
| Feature                   | Command Format                                                                          | Purpose                                       |
|---------------------------|-----------------------------------------------------------------------------------------|-----------------------------------------------|
| Create a new reservation  | `add n/<NAME> p/<PHONE> e/<EMAIL> x/<NUMBER_OF_DINERS> d/<DATETIME> [t/TAG]…​`                    | Add new reservation to ReserveMate            |
| Editing a reservation     | `edit <INDEX> [n/NAME] [p/PHONE] [e/EMAIL] [x/NUMBER_OF_DINERS] [d/DATETIME] [t/TAG]…​` | Edit existing details of a reservation        |
| Delete reservation        | `delete <INDEX>`                                                                        | Remove a reservation from ReserveMate         |
| Show Reservation details  | `show <INDEX>`                                                                          | Show details of reservation to user           |
| View reservation list     | `list`                                                                                  | View the entire reservation schedule          |
| View help list            | `help`                                                                                  | Display the available list of commands to the user |
| Find reservation by name  | `find <RESERVATION NAME>`                                                               | Retrieve reservation information              |
| Clearing all reservations | `clear`                                                                                 | Deletes all reservations in ReserveMate       |
| Exiting the application | `exit`                                                                                  | Terminates the application                    |

### Acknowledgement
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

