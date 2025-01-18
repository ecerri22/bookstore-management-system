package com.example.bookstore.test;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Librarian;
import com.example.bookstore.model.Transaction;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTestingRosela {

    private BookController bookController;
    private File tempBookFile;
    private File tempBillFile;
    private Librarian librarian = new Librarian("John", "Doe", "123456789", "test@example.com", "password", "Librarian", 5000.0, new Date(), true, true);

    @BeforeAll
    void setup() throws IOException {
        // Set up temporary files for testing
        tempBookFile = File.createTempFile("testBooks", ".bin");
        tempBillFile = File.createTempFile("testBills", ".bin");

        // Initialize BookController with temporary files
        bookController = new BookController();
        bookController.getBooks().clear();
        bookController.getBills().clear();
        bookController.file2 = tempBookFile;
        bookController.file1 = tempBillFile;
    }

    @AfterAll
    void cleanup() {
        // Delete temporary files after testing
        if (tempBookFile.exists()) {
            tempBookFile.delete();
        }
        if (tempBillFile.exists()) {
            tempBillFile.delete();
        }
    }

    @BeforeEach
    void resetBooksAndBills() {
        bookController.getBooks().clear();
        bookController.getBills().clear();
    }

    @Test
    void testReadWriteBooks() throws IOException {
        // Create sample books
        Book book1 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Book book2 = new Book("978-1-61-729494-5", "Java Concurrency in Practice", "Brian Goetz", "Programming",
                "TechBooks Supplier", new Date(), 40.0, 55.0, 60.0, 50);

        // Add books to the controller
        bookController.getBooks().add(book1);
        bookController.getBooks().add(book2);

        // Write books to file
        bookController.writeBooks();

        // Clear the current list and read from the file
        bookController.getBooks().clear();
        ArrayList<Book> booksFromFile = bookController.readBooks();

        assertEquals(2, booksFromFile.size());
        assertEquals("Effective Java", booksFromFile.get(0).getTitle());
        assertEquals("Java Concurrency in Practice", booksFromFile.get(1).getTitle());
    }

    @Test
    void testReadWriteBills() throws IOException {
        // Create Book and Transaction objects
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction = new Transaction(book, 1);

        // Create a Bill and add it to the controller
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Bill bill = new Bill(transactions, librarian);
        bookController.getBills().add(bill);

        // Write bills to the file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempBillFile))) {
            oos.writeObject(bookController.getBills());
        }

        // Clear the current list and read from the file
        bookController.getBills().clear();
        ArrayList<Bill> billsFromFile = bookController.readBills();

        assertEquals(1, billsFromFile.size());
        assertEquals("John Doe Librarian", billsFromFile.get(0).seller);
        assertEquals(1, billsFromFile.get(0).getTransactions().size());
        assertEquals("978-3-16-148410-0", billsFromFile.get(0).getTransactions().get(0).getBooks().get(0).getISBN());
    }

    @Test
    void testAddBookAndVerify() {
        // Add a book using verifyBook
        boolean isAdded = bookController.verifyBook("978-3-16-148410-0", "Effective Java", "Joshua Bloch",
                "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);

        // Check if the book is added
        assertTrue(isAdded);
        assertEquals(1, bookController.getBooks().size());
        assertEquals("Effective Java", bookController.getBooks().get(0).getTitle());

        // Try to add the same book again
        boolean isAddedAgain = bookController.verifyBook("978-3-16-148410-0", "Effective Java", "Joshua Bloch",
                "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);

        // Check that the duplicate book is not added
        assertFalse(isAddedAgain);
        assertEquals(1, bookController.getBooks().size());
    }

    @Test
    void testNrOfBooksSold() {
        // Create books, transactions, and bills
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction = new Transaction(book, 5);
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Bill bill = new Bill(transactions, librarian);
        bookController.getBills().add(bill);

        // Read bills and calculate total books sold
        int totalBooksSold = bookController.nrOfBooksSold();

        assertEquals(5, totalBooksSold);
    }
}
