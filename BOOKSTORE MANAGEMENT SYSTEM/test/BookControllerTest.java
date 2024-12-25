import controller.BookController;
import model.Bill;
import model.Book;
import model.User;
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
        // Boundary Value: Stock = 0
        Book book = new Book("123", "Test Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 0);
        bookController.addBook("123", "Test Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, 0);
        assertEquals(1, bookController.getBooks().size(), "Book should be added, even with zero stock.");
    }

    @Test
    public void testAddBookWithLargeStock() {
        // Boundary Value: Very large stock (edge case)
        Book book = new Book("124", "Large Stock Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, Integer.MAX_VALUE);
        bookController.addBook("124", "Large Stock Book", "Author", "Category", "Supplier", new Date(), 10, 15, 20, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, bookController.getBooks().get(0).getStock(), "Book should be added with maximum stock value.");
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
        // All bills fall within the specified date range
        Date start = new Date(2023, 10, 1); // Start date: 1st Oct 2023
        Date end = new Date(2023, 10, 31);  // End date: 31st Oct 2023

        Bill bill1 = new Bill(new ArrayList<>(), realUser);
        Bill bill2 = new Bill(new ArrayList<>(), realUser);
        bookController.getBills().add(bill1);
        bookController.getBills().add(bill2);

        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold in the given range should be calculated correctly.");
    }

    // ------------------------ Branch Coverage (BC) --------------------------- //

    @Test
    public void testNrOfBooksSoldMonthly_BillBeforeStartDate() {
        // Bill's date is before the start date (test branch where one condition is false)
        Date start = new Date(2023, 10, 1);
        Date end = new Date(2023, 10, 31);

        Bill bill1 = new Bill(new ArrayList<>(), realUser);
        bill1.getDateCreated().setYear(2023);
        bill1.getDateCreated().setMonth(9); // Set to September (before start date)

        bookController.getBills().add(bill1);
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be 0 if bill is before start date.");
    }

    @Test
    public void testNrOfBooksSoldMonthly_BillAfterEndDate() {
        // Bill's date is after the end date (test branch where one condition is false)
        Date start = new Date(2023, 10, 1);
        Date end = new Date(2023, 10, 31);

        Bill bill2 = new Bill(new ArrayList<>(), realUser);
        bill2.getDateCreated().setYear(2023);
        bill2.getDateCreated().setMonth(11); // Set to November (after end date)

        bookController.getBills().add(bill2);
        int result = bookController.nrOfBooksSoldMonthly(start, end);
        assertEquals(0, result, "Books sold should be 0 if bill is after end date.");
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
