package com.example.bookstore.test.firstPhase;

import com.example.bookstore.controller.AuthorController;
import com.example.bookstore.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RoselaTest {
    private Librarian librarian;
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        authorController = new AuthorController();
        authorController.author.clear();
        librarian = new Librarian("John", "Doe", "123456789", "test@example.com", "password", "Librarian", 5000.0, new Date(), true, true);
    }

    // Boundary Value Testing for nrOfBooks
    @Test
    void testRobustBVTForNrOfBooks() {
        Calendar calendar = Calendar.getInstance();
        // Extreme past date
        calendar.set(1900, Calendar.JANUARY, 1);
        Date pastDate = calendar.getTime();

        // Extreme future date
        calendar.set(3000, Calendar.JANUARY, 1);
        Date futureDate = calendar.getTime();

        assertThrows(IllegalArgumentException.class, () -> librarian.nrOfBooks(null), "Null date should throw exception");
        assertEquals(0, librarian.nrOfBooks(pastDate), "Extreme past date should have 0 books");
        assertEquals(0, librarian.nrOfBooks(futureDate), "Extreme future date should have 0 books");
    }

    @Test
    void testNormalBVTForNrOfBooks() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JANUARY, 1);
        Date earliestDate = calendar.getTime();

        calendar.set(2022, Calendar.DECEMBER, 31);
        Date latestDate = calendar.getTime();

        Book book1 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Book book2 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);

        Transaction transaction1 = new Transaction(book1, 1);
        Transaction transaction2 = new Transaction(book2, 1);
        Bill earliestDateBill = new Bill(new ArrayList<Transaction>() {{
            add(transaction1);
        }}, librarian);
        earliestDateBill.setDateCreated(earliestDate);
        Bill latestDateBill = new Bill(new ArrayList<Transaction>() {{
            add(transaction2);
        }}, librarian);
        latestDateBill.setDateCreated(latestDate);

        ArrayList<Bill> bills = new ArrayList<>();
        bills.add(earliestDateBill);
        bills.add(latestDateBill);
        librarian.bills = bills;

        assertEquals(1, librarian.nrOfBooks(earliestDate), "Should return 1 book for the earliest date");
        assertEquals(1, librarian.nrOfBooks(latestDate), "Should return 1 book for the latest date");
    }

    @Test
    void testWorstCaseBVTForNrOfBooks() {
        Calendar calendar = Calendar.getInstance();
        // Set earliest possible date (January 1st, 1900)
        calendar.set(1900, Calendar.JANUARY, 1);
        Date earliestDate = calendar.getTime();

        // Set latest possible date (December 31st, 3000)
        calendar.set(3000, Calendar.DECEMBER, 31);
        Date latestDate = calendar.getTime();

        Book book1 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Book book2 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);

        Transaction transaction1 = new Transaction(book1, 1);
        Transaction transaction2 = new Transaction(book2, 1);
        Bill earliestDateBill = new Bill(new ArrayList<Transaction>() {{
            add(transaction1);
        }}, librarian);

        Bill latestDateBill = new Bill(new ArrayList<Transaction>() {{
            add(transaction2);
        }}, librarian);

        earliestDateBill.setDateCreated(earliestDate);
        latestDateBill.setDateCreated(latestDate);

        ArrayList<Bill> bills = new ArrayList<>();
        bills.add(earliestDateBill);
        bills.add(latestDateBill);
        librarian.bills = bills;

        assertEquals(1, librarian.nrOfBooks(earliestDate), "Should return 1 book for the earliest date");
        assertEquals(1, librarian.nrOfBooks(latestDate), "Should return 1 book for the latest date");
    }

    @Test
    void testRobustWorstCaseBVTForInvalidDates() {
        Date invalidDate1 = null;  // Null date
        try {
            librarian.nrOfBooks(invalidDate1); // Should throw an exception or return 0
            fail("Date can't be null");
        } catch (IllegalArgumentException e) {
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void testNormalWeakEquivalenceClassForMoneyMade() {
        Calendar calendar = Calendar.getInstance();

        // Valid specific date with bills
        calendar.set(2023, Calendar.MARCH, 15);
        Date validDate = calendar.getTime();

        Book book1 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction1 = new Transaction(book1, 2);
        Bill validBill = new Bill(new ArrayList<Transaction>() {{
            add(transaction1);
        }}, librarian);
        validBill.setDateCreated(validDate);

        librarian.bills = new ArrayList<>() {{
            add(validBill);
        }};

        // Assert the method returns the correct total for the valid date
        assertEquals(100.00, librarian.moneyMade(validDate), 0.01, "Should return the correct total for a valid date");
    }

    @Test
    void testStrongNormalEquivalenceClassForMoneyMade() {
        Calendar calendar = Calendar.getInstance();

        // Valid specific dates
        calendar.set(2023, Calendar.MARCH, 15);
        Date validDateWithBill = calendar.getTime();
        calendar.set(2024, Calendar.JUNE, 20);
        Date validDateWithoutBill = calendar.getTime();

        Book book1 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction1 = new Transaction(book1, 3);
        Bill validBill = new Bill(new ArrayList<Transaction>() {{
            add(transaction1);
        }}, librarian);
        validBill.setDateCreated(validDateWithBill);

        librarian.bills = new ArrayList<>() {{
            add(validBill);
        }};

        // Assert the method returns the correct total for the date with a bill
        assertEquals(150.00, librarian.moneyMade(validDateWithBill), 0.01, "Should return the correct total for a date with a bill");

        // Assert the method returns 0 for the date without a bill
        assertEquals(0.0, librarian.moneyMade(validDateWithoutBill), "Should return 0 for a date without a bill");
    }

    @Test
    void testSingleWeakRobustEquivalenceClassForMoneyMade() {
        Date invalidDate1 = null;  // Null date
        try {
            librarian.moneyMade(invalidDate1); // Should throw an exception or return 0
            fail("Date can't be null");
        } catch (IllegalArgumentException e) {
        }
    }


    @Test
    void testStrongRobustEquivalenceClassForMoneyMade() {
        Calendar calendar = Calendar.getInstance();
        // Valid specific dates
        calendar.set(2023, Calendar.MARCH, 15);
        Date validDateWithBill = calendar.getTime();
        calendar.set(2024, Calendar.JUNE, 20);
        Date validDateWithoutBill = calendar.getTime();

        Book book1 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction1 = new Transaction(book1, 4);
        Bill validBill = new Bill(new ArrayList<Transaction>() {{
            add(transaction1);
        }}, librarian);
        validBill.setDateCreated(validDateWithBill);
        librarian.bills = new ArrayList<>() {{
            add(validBill);
        }};

        // **Valid Inputs:**
        // Assert the method returns the correct total for the date with a bill
        assertEquals(200.0, librarian.moneyMade(validDateWithBill), 0.01, "Should return the correct total for a date with a bill");

        // Assert the method returns 0 for the date without a bill
        assertEquals(0.0, librarian.moneyMade(validDateWithoutBill), "Should return 0 for a date without a bill");

        // **Invalid Inputs:**
        // Invalid input: Null date (this should throw an exception)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> librarian.moneyMade(null));
        assertEquals("Date can't be null", exception.getMessage(), "Should throw an exception for null date");

        // Invalid input: A date in the far future, not expecting any bill for this date
        calendar.set(9999, Calendar.DECEMBER, 31);
        Date farFutureDate = calendar.getTime();
        assertEquals(0.0, librarian.moneyMade(farFutureDate), "Should return 0 for a far future date with no bill");

        // Invalid input: An invalid date format (if your system handles it in a particular way)
        try {
            Date invalidDate = new SimpleDateFormat("yyyy-MM-dd").parse("INVALID DATE");
            assertThrows(ParseException.class, () -> librarian.moneyMade(invalidDate));
        } catch (ParseException e) {
            // Handle ParseException gracefully
            System.out.println("Invalid date format handled");
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void testBoundaryValueTestingForVerifyAuthor() {
        authorController.author.clear();
        // Simulate adding an author and verifying the logic
        authorController.author.add(new Author("ExistingAuthor"));

        // Test with a valid name (Boundary case, valid input)
        assertTrue(authorController.verify("Rosela Berberi"), "Valid name should return true");

        //Test with existing name
        assertFalse(authorController.verify("ExistingAuthor"), "Existing name should return false");


    }

    @Test
    void testEquivalenceClassTestingForVerifyAuthor() {
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
