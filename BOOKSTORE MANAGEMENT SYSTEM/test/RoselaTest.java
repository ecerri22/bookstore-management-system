package com.example.bookstore.test;

import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Librarian;
import com.example.bookstore.model.Transaction;
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
    private File testFile;

    @BeforeEach
    void setUp() {
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


}
