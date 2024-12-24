package com.example.bookstore.test;

import com.example.bookstore.controller.AuthorController;
import com.example.bookstore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RoselaTest {
    private Librarian librarian;
    private Book book;
    private ArrayList<Transaction> transaction;

    private AuthorController authorController;
    private File testFile;

    @BeforeEach
    void setUp() {
        authorController = new AuthorController();
        authorController.author.clear();
        librarian = new Librarian("John", "Doe", "123456789", "test@example.com", "password", "Librarian", 5000.0, new Date(), true, true);
        book = new Book(
                "978-3-16-148410-0", // ISBN
                "Effective Java", // Title
                "Joshua Bloch", // Author
                "Programming", // Category
                "TechBooks Supplier", // Supplier
                new Date(), // Purchased Date
                30.0, // Purchased Price
                45.0, // Original Price
                50.0, // Selling Price
                100 // Stock
        );
        transaction = new ArrayList<>();
        transaction.add(new Transaction(book, 2));

        testFile = new File("bills.bin");
        if (testFile.exists()) {
            testFile.delete();
        }

        // Create a mock bills.bin file
        try {
            if (testFile.createNewFile()) {
                System.out.println("Mock bills.bin created for testing.");
            }
        } catch (IOException e) {
            fail("Failed to create mock bills.bin: " + e.getMessage());
        }
    }


    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    // Boundary Value Testing for nrOfBooks
    @Test
    void testNrOfBooksBoundaryValues() {
        // Test no matching date
        Date today = new Date();
        ArrayList<Bill> bills = new ArrayList<>();
        bills.add(new Bill(transaction, librarian));
        librarian.bills = bills;
        int result = 0;
        // Test exact date match (ignoring time)
        LocalDate localToday = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate matchingDate = localToday; // Today, no time component

        result = librarian.nrOfBooks(Date.from(matchingDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertEquals(2, result, "Books count should match the exact date.");  // 2 books for this transaction
    }

    // Equivalence Class Testing for nrOfBooks
    @Test
    void testNrOfBooksEquivalenceClasses() {
        // Valid Case: Matching date
        Date today = new Date();
        ArrayList<Bill> validBills = new ArrayList<>();
        validBills.add(new Bill(transaction, librarian));
        librarian.bills = validBills;

        int result = librarian.nrOfBooks(today);
        assertEquals(2, result, "Books sold today should match.");

        // Invalid Case: Invalid date format (non-matching date)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, -1); // Yesterday
        Date invalidDate = calendar.getTime();

        result = librarian.nrOfBooks(invalidDate);
        assertEquals(0, result, "Books count should be 0 for a non-matching date.");

        assertThrows(IllegalArgumentException.class, () -> librarian.nrOfBooks(null), "Should throw exception if date is null.");

        int resultTomorrow = librarian.nrOfBooks(new Date(today.getTime() + 24 * 60 * 60 * 1000));
        assertEquals(0, resultTomorrow, "Books sold tomorrow should be 0.");
    }

    @Test
    void testBoundaryValueTestingForVerifyAuthor() {authorController.author.clear();
        // Simulate adding an author and verifying the logic
        authorController.author.add(new Author("ExistingAuthor"));

        // Test with a valid name (Boundary case, valid input)
        assertTrue(authorController.verify("Roselaa Berberiii"), "Valid name should return true");

        //Test with existing name
        assertFalse(authorController.verify("ExistingAuthor"), "Existing name should return false");


    }

    @Test
    void testEquivalenceClassTestingForVerifyAuthor(){
        // Simulate adding an author and verifying the logic
        authorController.author.add(new Author("ExistingAuthor"));
        //Test with null input
        assertFalse(authorController.verify(null), "Null input should return false");

        // Test with a valid name (Boundary case, valid input)
        assertTrue(authorController.verify("R B"), "Valid name should return true");

        //Test with existing name
        assertFalse(authorController.verify("ExistingAuthor"), "Existing name should return false");

        // Test with empty string input
        assertFalse(authorController.verify(""), "Empty string should return false");

    }

    @Test
    void testStatementCoverage() {
        // Simulate adding an author and verifying the logic
        authorController.author.add(new Author("ExistingAuthor"));

        // Check if the new author can be added and existing author cannot
        assertTrue(authorController.verify("NewAuthor"), "New author should be added successfully");
        assertFalse(authorController.verify("ExistingAuthor"), "Existing author should not be added again");
    }

    @Test
    void testBranchCoverage() {
        // Simulate adding an author and verifying the logic
        authorController.author.add(new Author("ExistingAuthor"));

        // Test with a valid unique name
        assertTrue(authorController.verify("AnotherAuthor"), "New author should be added");

        // Test with an existing author
        assertFalse(authorController.verify("ExistingAuthor"), "Existing author should not be added");
    }

    @Test
    void testConditionCoverage() {
        // Simulate adding an author and verifying the logic
        authorController.author.add(new Author("ExistingAuthor"));

        // Test null case (condition for author != null and author.getFullName().equals() will be tested)
        assertFalse(authorController.verify(null), "Null input should return false");

        // Test with a valid name (should trigger both conditions)
        assertTrue(authorController.verify("John"), "John should be added successfully");

        // Test for an existing author(should trigger both conditions)
        assertFalse(authorController.verify("ExistingAuthor"), "Duplicate author should not be added");
    }

    @Test
    void testMCDC() {
        // Simulate adding an author and verifying the logic
        authorController.author.add(new Author("ExistingAuthor"));

        // Test with null value (testing both conditions of 'author1 != null' and 'author1.getFullName().equals(lastName1)')
        assertFalse(authorController.verify(null), "Null input should return false");

        // Test with a new valid name
        assertTrue(authorController.verify("NewAuthor"), "New author should be added");

        // Test with an existing author
        authorController.verify("ExistingAuthor");
        assertFalse(authorController.verify("ExistingAuthor"), "Existing author should not be added");

        // Test with empty string
        assertFalse(authorController.verify(""), "Empty string should return false");
    }




}
