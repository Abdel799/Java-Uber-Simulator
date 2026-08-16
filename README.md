# Java Uber Simulator

A command-line ride-sharing and food delivery simulation built in **Java**. The application models users, drivers, ride requests, deliveries, payments, and driver movement through a city grid.

This project demonstrates fundamental Java and computer science concepts including **object-oriented programming, inheritance, polymorphism, collections, sorting, file I/O, exception handling, and data management**.

## Features

* Register and manage users and drivers
* Request rides between locations
* Request food deliveries
* Assign available drivers to requests
* Simulate driver movement through a city
* Pick up and drop off rides and deliveries
* Calculate fares and system revenue
* Manage user wallet balances
* Validate addresses and calculate distances
* Sort users by name or wallet balance
* Load user and driver data from files
* Track active ride and delivery requests

## Technologies & Concepts

* **Java**
* Object-Oriented Programming (OOP)
* Classes and Objects
* Inheritance and Polymorphism
* Encapsulation
* Java Collections
* Data Structures
* Sorting and Comparators
* File I/O
* Exception Handling
* Input Validation
* Command-Line Interfaces

## Commands

The simulator is controlled through the following terminal commands:

| Command        | Description                                |
| -------------- | ------------------------------------------ |
| `DRIVERS`      | List all registered drivers                |
| `USERS`        | List all registered users                  |
| `REQUESTS`     | List all active ride and delivery requests |
| `REGDRIVER`    | Register a new driver                      |
| `REGUSER`      | Register a new user                        |
| `REQRIDE`      | Request a ride                             |
| `REQDLVY`      | Request a food delivery                    |
| `SORTBYNAME`   | Sort users by name                         |
| `SORTBYWALLET` | Sort users by wallet balance               |
| `CANCELREQ`    | Cancel a ride or delivery request          |
| `DROPOFF`      | Drop off a user or delivery                |
| `REVENUES`     | Display total system revenue               |
| `ADDR`         | Validate an address                        |
| `DIST`         | Get the distance between two addresses     |
| `PICKUP`       | Assign a request to a driver               |
| `LOADUSERS`    | Load users from a file                     |
| `LOADDRIVERS`  | Load drivers from a file                   |
| `DRIVETO`      | Simulate a driver traveling to an address  |
| `Q` or `QUIT`  | Exit the application                       |

## Project Structure

```text
CityMap.java
Driver.java
TMUberDelivery.java
TMUberRegistered.java
TMUberRide.java
TMUberService.java
TMUberSystemManager.java
TMUberUI.java
User.java
```

### Core Components

**`TMUberUI`**
Provides the command-line interface and processes commands entered by the user.

**`TMUberSystemManager`**
Contains the core application logic for managing users, drivers, ride requests, deliveries, and system operations.

**`TMUberService`**
Represents the common functionality of services within the system.

**`TMUberRide` and `TMUberDelivery`**
Represent ride-sharing and food-delivery services.

**`User` and `Driver`**
Model the users and drivers interacting with the system.

**`CityMap`**
Handles addresses, locations, and distance calculations within the simulated city.

**`TMUberRegistered`**
Handles loading registered users and drivers from files.

## Running the Project

### Requirements

* Java Development Kit (JDK)

Check that Java is installed:

```bash
java -version
javac -version
```

### Compile

Clone the repository and navigate into the project directory:

```bash
git clone <repository-url>
cd Java-Uber-Simulator
```

Compile the Java source files:

```bash
javac *.java
```

### Run

Start the simulator with:

```bash
java TMUberUI
```

The application will display a command prompt:

```text
>
```

Enter any of the supported commands to interact with the simulation.

For example:

```text
> USERS
> DRIVERS
> REQUESTS
```

Exit the application with:

```text
> Q
```

## Purpose

This project was developed to apply core **Java programming and object-oriented design principles** by modelling the behaviour and interactions of a ride-sharing system.

The application focuses on designing relationships between objects, managing application state, processing user commands, working with Java collections, and implementing the business logic required to coordinate users, drivers, rides, and deliveries.

# Author
**Abdelrahman Abdelaal**
Computer Science Co-op Student - Toronto Metropolitan University
