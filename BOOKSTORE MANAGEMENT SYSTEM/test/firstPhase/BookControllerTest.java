package com.example.bookstore.test.firstPhase;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class BookControllerTest {

    private BookController bookController;
    private User realUser;

    @BeforeEach
    public void setUp() {
        bookController = new BookController();
        realUser = new User("John", "Doe", "1234567890", "john.doe@example.com", "password123", "Librarian", 50000, new Date(), true, true);
    }

    // ------------------- BVT (Boundary Value Testing) for addBook() -------------------- //

    @Test
    public void testAddBookWithZeroStock() {
        int stock = 0;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookController.addBook("123", "Test Book", "Author", "Category", "Supplier",
                    new Date(), 10, 15, 20, stock);
        });
        assertEquals("Stock must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testAddBookWithExcessivelyLargeStock() {
        int stock = Integer.MAX_VALUE;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookController.addBook("124", "Large Stock Book", "Author", "Category", "Supplier",
                    new Date(), 10, 15, 20, stock);
        });
        assertEquals("Stock must not exceed 10,000.", exception.getMessage());
    }


    // ------------------- ECT (Equivalence Class Testing) for isISBNExists() -------------------- //

    @Test
    public void testIsISBNExistsValidISBN() {
        // Equivalence Class: ISBN exists
        Book book = new Book("125", "Valid Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 5);
        bookController.addBook("125", "Valid Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 5);

        boolean result = bookController.isISBNExists("125"); // Checking if the ISBN exists
        assertTrue(result, "The ISBN should exist in the book list.");
    }

    @Test
    public void testIsISBNExistsInvalidISBN() {
        // Equivalence Class: ISBN does not exist
        boolean result = bookController.isISBNExists("126");
        assertFalse(result, "The ISBN should not exist in the book list.");
    }

    @Test
    public void testIsISBNExistsEmptyISBN() {
        // Equivalence Class: Empty ISBN
        boolean result = bookController.isISBNExists("");
        assertFalse(result, "An empty ISBN should not be found.");
    }

    // ------------------- Code Coverage Testing for nrOfBooksSoldMonthly() -------------------- //

    // ------------------------ Statement Coverage (SC) --------------------------- //

    @Test
    public void testNrOfBooksSoldMonthly_AllBillsWithinRange() {
        // Define date range
        Date start = java.sql.Date.valueOf("2023-10-01");
        Date end = java.sql.Date.valueOf("2023-10-31");

        // Create books
        Book book1 = new Book("123", "Test Book 1", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 5);
        Book book2 = new Book("124", "Test Book 2", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 10);

        // Create transactions
        Transaction transaction1 = new Transaction(book1, 2); // 2 copies of book1
        Transaction transaction2 = new Transaction(book2, 3); // 3 copies of book2

        // Create bills within the date range
        ArrayList<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(transaction1);
        Bill bill1 = new Bill(transactions1, realUser);

        ArrayList<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(transaction2);
        Bill bill2 = new Bill(transactions2, realUser);

        // Add bills to the controller
        bookController.getBills().add(bill1);
        bookController.getBills().add(bill2);

        // Call the method and assert the result
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(5, result, "The total number of books sold in the date range should be calculated correctly.");
    }

    // ------------------------ Branch Coverage (BC) --------------------------- //

    @Test
    public void testNrOfBooksSoldMonthly_BillBeforeStartDate() {
        // Define the date range
        Date start = java.sql.Date.valueOf("2023-10-01");
        Date end = java.sql.Date.valueOf("2023-10-31");

        // Create a book and transaction
        Book book = new Book("123", "Test Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 5);
        Transaction transaction = new Transaction(book, 2); // 2 copies of the book

        // Create a bill with a date before the start date
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Bill billBeforeStart = new Bill(transactions, realUser);
        billBeforeStart.getDateCreated().setYear(2023 - 1900); // Set year to 2023
        billBeforeStart.getDateCreated().setMonth(8); // Set to September (0-based index, 8 = September)

        // Add the bill to the controller
        bookController.getBills().add(billBeforeStart);

        // Call the method and assert the result
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be 0 if the bill date is before the start date.");
    }

    @Test
    public void testNrOfBooksSoldMonthly_BillAfterEndDate() {
        // Define the date range
        Date start = java.sql.Date.valueOf("2023-10-01");
        Date end = java.sql.Date.valueOf("2023-10-31");

        // Create a book and transaction
        Book book = new Book("123", "Test Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 5);
        Transaction transaction = new Transaction(book, 3); // 3 copies of the book

        // Create a bill with a date after the end date
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Bill billAfterEnd = new Bill(transactions, realUser);
        billAfterEnd.getDateCreated().setYear(2023 - 1900); // Set year to 2023
        billAfterEnd.getDateCreated().setMonth(10); // Set to November (0-based index, 10 = November)

        // Add the bill to the controller
        bookController.getBills().add(billAfterEnd);

        // Call the method and assert the result
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be 0 if the bill date is after the end date.");
    }


    // ------------------------ Condition Coverage (CC) --------------------------- //

    @Test
    public void testNrOfBooksSoldMonthly_OnlyStartDateConditionTrue() {
        // Only the start date condition is true
        Date start = new Date(2023, 10, 1);
        Date end = new Date(2023, 10, 31);

        Bill bill3 = new Bill(new ArrayList<>(), realUser);
        bill3.getDateCreated().setYear(2023);
        bill3.getDateCreated().setMonth(10); // Same month as start date, but check condition >= 0

        bookController.getBills().add(bill3);
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be 0 if only the start condition is true.");
    }

    @Test
    public void testNrOfBooksSoldMonthly_OnlyEndDateConditionTrue() {
        // Only the end date condition is true
        Date start = new Date(2023, 10, 1);
        Date end = new Date(2023, 10, 31);

        Bill bill4 = new Bill(new ArrayList<>(), realUser);
        bill4.getDateCreated().setYear(2023);
        bill4.getDateCreated().setMonth(10); // Same month as end date, but check condition <= 0

        bookController.getBills().add(bill4);
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be 0 if only the end condition is true.");
    }

    // ------------------------ Modified Condition/Decision Coverage (MC/DC) --------------------------- //

    @Test
    public void testNrOfBooksSoldMonthly_BothConditionsTrue() {
        // Both conditions are true
        Date start = new Date(2023, 10, 1);
        Date end = new Date(2023, 10, 31);

        Bill bill5 = new Bill(new ArrayList<>(), realUser);
        bill5.getDateCreated().setYear(2023);
        bill5.getDateCreated().setMonth(10); // Same month, so both conditions are true

        bookController.getBills().add(bill5);
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be calculated correctly when both conditions are true.");
    }

    @Test
    public void testNrOfBooksSoldMonthly_BothConditionsFalse() {
        // Both conditions are false (bill not in range)
        Date start = new Date(2023, 10, 1);
        Date end = new Date(2023, 10, 31);

        Bill bill6 = new Bill(new ArrayList<>(), realUser);
        bill6.getDateCreated().setYear(2023);
        bill6.getDateCreated().setMonth(8); // Outside range

        bookController.getBills().add(bill6);
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be 0 if the bill is outside the range.");
    }
}
