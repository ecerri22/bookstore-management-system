package com.example.bookstore.test;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerIntegrationTest {
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        adminController = new AdminController();
    }

    @Test
    void testAddEmployee() {
        Date birthday = new Date();
        boolean isAdded = adminController.isAdded(
                "John",
                "Doe",
                "123456789",
                "johndoe@example.com",
                "password123",
                "Admin",
                5000.0,
                birthday,
                true,
                true
        );
        assertTrue(isAdded, "Employee should be added successfully.");

        User addedEmployee = adminController.getAllEmployees().stream()
                .filter(user -> user.getEmail().equals("johndoe@example.com"))
                .findFirst()
                .orElse(null);

        assertNotNull(addedEmployee, "Added employee should be in the list.");
        assertEquals("John", addedEmployee.getFName(), "First name should match.");
        assertEquals("Admin", addedEmployee.getRole(), "Role should match.");
    }

    @Test
    void testModifyEmployeeDetails() {
        // Add a user first
        Date birthday = new Date();
        adminController.isAdded(
                "Jane",
                "Smith",
                "987654321",
                "janesmith@example.com",
                "password123",
                "Librarian",
                4000.0,
                birthday,
                true,
                false
        );

        // Find the user using the email (unique identifier)
        User jane = adminController.getAllEmployees().stream()
                .filter(user -> user.getEmail().equals("janesmith@example.com"))
                .findFirst()
                .orElse(null);

        assertNotNull(jane, "Employee should exist.");

        // Modify details
        boolean isModified = adminController.modifyFName(jane, "Janet");
        assertTrue(isModified, "Modification should be successful.");

        // Verify modification
        User updatedJane = adminController.getAllEmployees().stream()
                .filter(user -> user.getEmail().equals("janesmith@example.com"))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedJane, "Updated employee should still exist.");
        assertEquals("Janet", updatedJane.getFName(), "First name should be updated.");
    }



    @Test
    void testDeleteEmployee() {
        Date birthday = new Date();

        // 1. Create a new user
        adminController.isAdded(
                "Jake",
                "Miller",
                "543216789",
                "jakemiller@example.com",
                "password123",
                "Manager",
                6000.0,
                birthday,
                true,
                true
        );

        // 2. Verify the user was added
        User jake = adminController.getAllEmployees().stream()
                .filter(user -> user.getEmail().equals("jakemiller@example.com"))
                .findFirst()
                .orElse(null);

        assertNotNull(jake, "Employee should exist.");

        // 3. Delete the user
        boolean isDeleted = adminController.isDeleted(jake);
        assertTrue(isDeleted, "Employee should be deleted successfully.");

        // 4. Verify the user is deleted
        User deletedJake = adminController.findEmployee(jake);
        assertNull(deletedJake, "Deleted employee should not exist in the list.");
    }

    @Test
    void testIsEmailExists() {
        // Add a user first
        Date birthday = new Date();
        adminController.isAdded(
                "Ella",
                "Brown",
                "123123123",
                "ellabrown@example.com",
                "password123",
                "Manager",
                5500.0,
                birthday,
                false,
                false
        );

        // Test email existence
        assertTrue(adminController.isEmailExists("ellabrown@example.com"), "Email should exist in the system.");
        assertFalse(adminController.isEmailExists("nonexistent@example.com"), "Non-existent email should not be found.");
    }

    @Test
    void testFileAvailability() {
        assertTrue(adminController.isFileAvailable("allEmployees.bin"), "File 'allEmployees.bin' should be available in the resources folder.");
    }
}
