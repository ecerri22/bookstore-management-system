package com.example.bookstore.test.firstPhase;

import com.example.bookstore.controller.AdminController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
//equivalence class testing - decision table testing
public class ClassCovDaniela {

    private AdminController admin;

    @BeforeEach
    public void setup() {
        admin = new AdminController();
    }

    @Test
    public void testIsAdded_ValidEmail_ValidSalary_UniqueEmail_ValidRole() {
        assertTrue(admin.isAdded("John", "Doe", "1234567890", "john.doe@example.com", "password123", "Librarian", 50000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_ValidEmail_ValidSalary_UniqueEmail_InvalidRole() {
        assertFalse(admin.isAdded("Jane", "Doe", "1234567890", "jane.doe@example.com", "password123", "NonExistentRole", 50000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_ValidEmail_InvalidSalary_UniqueEmail_ValidRole() {
        assertFalse(admin.isAdded("Tom", "Smith", "0987654321", "tom.smith@example.com", "password123", "Admin", -5000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_ValidEmail_InvalidSalary_UniqueEmail_InvalidRole() {
        assertFalse(admin.isAdded("Alice", "White", "1234567890", "alice.white@example.com", "password123", "NonExistentRole", -5000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_InvalidEmail_ValidSalary_UniqueEmail_ValidRole() {
        assertFalse(admin.isAdded("Sara", "Connor", "1234567890", "sara.connorexample.com", "password123", "Librarian", 50000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_InvalidEmail_ValidSalary_ExistingEmail_ValidRole() {
        assertFalse(admin.isAdded("Bob", "Green", "1234567890", "bob.green@example.com", "password123", "Manager", 50000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_InvalidEmail_InvalidSalary_UniqueEmail_ValidRole() {
        assertFalse(admin.isAdded("Charlie", "Brown", "1234567890", "charlie.brownexample.com", "password123", "Admin", -10000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_InvalidEmail_InvalidSalary_ExistingEmail_ValidRole() {
        assertFalse(admin.isAdded("Eve", "Adams", "1234567890", "eve.adamsexample.com", "password123", "Manager", -5000.0, new Date(), true, true));
    }

    @Test
    public void testIsAdded_ValidEmail_ValidSalary_ExistingEmail_ValidRole() {
        assertFalse(admin.isAdded("George", "King", "1234567890", "george.king@example.com", "password123", "Admin", 50000.0, new Date(), true, true));
    }
}
