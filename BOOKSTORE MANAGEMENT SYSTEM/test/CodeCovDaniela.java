package com.example.bookstore.test;

import com.example.bookstore.controller.AdminController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//code coverage testing - condition coverage testing
public class CodeCovDaniela {

    private AdminController admin;

    @BeforeEach
    public void setUp() {
        admin = new AdminController();
        admin.allEmployees.clear();
    }

    @Test
    public void testIsAddedValidEmailValidSalaryUniqueEmailLibrarian() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "validemail@example.com";
        String password = "password123";
        String role = "Librarian";
        double salary = 5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = false;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertTrue(result);
    }

    @Test
    public void testIsAddedInvalidEmailValidSalaryUniqueEmailLibrarian() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "invalidemail";
        String password = "password123";
        String role = "Librarian";
        double salary = 5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = false;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertFalse(result);
    }

    @Test
    public void testIsAddedValidEmailNegativeSalaryUniqueEmailLibrarian() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "validemail@example.com";
        String password = "password123";
        String role = "Librarian";
        double salary = -5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = false;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertFalse(result);
    }

    @Test
    public void testIsAddedValidEmailValidSalaryExistingEmailLibrarian() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "validemail@example.com";
        String password = "password123";
        String role = "Librarian";
        double salary = 5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = false;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertTrue(result);
    }

    @Test
    public void testIsAddedValidEmailValidSalaryUniqueEmailManager() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "validemail@example.com";
        String password = "password123";
        String role = "Manager";
        double salary = 5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = true;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertTrue(result);
    }

    @Test
    public void testIsAddedValidEmailValidSalaryUniqueEmailAdmin() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "validemail@example.com";
        String password = "password123";
        String role = "Admin";
        double salary = 5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = true;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertTrue(result);
    }

    @Test
    public void testIsAddedValidEmailValidSalaryUniqueEmailDefaultRole() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "validemail@example.com";
        String password = "password123";
        String role = "OtherRole";
        double salary = 5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = false;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertTrue(result);
    }

    @Test
    public void testIsAddedInvalidEmailInvalidSalaryExistingEmail() {
        String firstName = "John";
        String lastName = "Doe";
        String phone = "123-456-7890";
        String email = "valdemailexample.com";
        String password = "password123";
        String role = "OtherRole";
        double salary = -5000;
        Date birthday = new Date();
        boolean canAddBook = true;
        boolean canAddBill = false;

        boolean result = admin.isAdded(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
        assertFalse(result);
    }
}
