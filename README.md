
# Bookstore Management System

## Overview

The Bookstore Management System is a Java-based application designed to manage key operations in a bookstore. The application provides a role-based user system with three levels of access: Librarian, Manager, and Administrator. Each role has distinct permissions and functionalities tailored to their responsibilities.

The application stores data in **binary and text files** and implements **Object-Oriented Programming (OOP)** concepts such as encapsulation, inheritance, polymorphism, abstract classes, and interfaces. The graphical user interface is built using **JavaFX**, and the project follows the **Model-View-Controller (MVC)** design pattern.

---

## Features

### General Features
- **Login System**: Role-based access for Librarian, Manager, and Administrator.
- **Data Storage**: Uses text and binary files for storing data, ensuring persistence.
- **Exception Handling**: Includes validation for user input and proper error handling with custom exceptions.

### Roles and Functionalities
1. **Librarian**:
   - Check out books and create bills (`Bill[BillNo].txt`).
   - Manage stock: Alert if a book is out of stock or does not exist.
   - Automatically updates files when transactions occur.

2. **Manager**:
   - Add books to stock or create new book categories/authors.
   - Track low stock: Alerts for books with fewer than 5 items.
   - Generate performance statistics for librarians (e.g., total bills, books sold, revenue).
   - Access daily, monthly, or total statistics for books sold and purchased.

3. **Administrator**:
   - Manage employees (Librarians and Managers): Register, update, and delete users.
   - View bookstore financials: Total income, costs, and salaries for a given period.
   - Revoke or grant permissions (e.g., allow librarians to add books to stock).

---

## Requirements Fulfilled

### Programming Features
1. **Encapsulation, Inheritance, Polymorphism, Abstract Classes, Interfaces**:
   - Classes and methods are organized to leverage OOP principles.
   - Role-based functionality is achieved using inheritance and polymorphism.
2. **File Handling**:
   - Text and binary files are used for storing book, user, and transaction data.
3. **Validation**:
   - String functions and regular expressions validate input fields (e.g., ISBN, prices, and user credentials).

### JavaFX (GUI and Events)
- The UI is interactive and user-friendly, with menus for navigation.
- JavaFX events handle user actions like logging in, adding books, and generating statistics.

### Model-View-Controller (MVC)
- **Model**: Handles data storage and logic for books, users, and transactions.
- **View**: JavaFX-based GUI for displaying menus and data.
- **Controller**: Manages user interactions and links the view to the model.

---

## How to Run the Project

1. Clone the repository from [GitHub](https://github.com/ecerri22/bookstore-management-system/tree/master).
2. Open the project in an IDE (e.g., IntelliJ IDEA).
3. Ensure you have **JDK 21** installed.
4. Run the `Main.java` file to launch the application.
5. Use the credentials below to log in as different users.

---

## Project Structure

The project is organized as follows:

- **Model**: Contains classes for `Book`, `User`, `Transaction`, etc.
- **View**: JavaFX components for GUI elements like menus and tables.
- **Controller**: Handles events and links the user interface with backend logic.

---

## GitHub Usage

All team members contributed to a single repository for version control. While the commits were minimal and focused on a single file, the repository ensured centralized management and code accessibility.

---

## Login Credentials

| **Role**         | **Email**                | **Password** |
|------------------|--------------------------|------|
| Librarian        | rberberi22@gmail.com     | 123  |
| Manager          | dshahini22@gmail.com     | 123  |
| Administrator    | ecerri22@gmail.com       | 123  |

