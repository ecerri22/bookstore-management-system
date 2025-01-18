package com.example.bookstore.test;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.controller.LibrarianController;
import com.example.bookstore.controller.TransactionController;
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
    private LibrarianController librarianController = new LibrarianController();
    private TransactionController transactionController = new TransactionController();
    private File tempBookFile;
    private File tempBillFile;
    private File tempTransactionFile;
    private Librarian librarian = new Librarian("John", "Doe", "123456789", "test@example.com", "password", "Librarian", 5000.0, new Date(), true, true);

    @BeforeAll
    void setup() throws IOException {
        // Set up temporary files for testing
        tempBookFile = File.createTempFile("testBooks", ".bin");
        tempBillFile = File.createTempFile("testBills", ".bin");
        tempTransactionFile = File.createTempFile("testTransactions", ".bin");
        // Initialize BookController with temporary files
        bookController = new BookController();
        bookController.getBooks().clear();
        bookController.getBills().clear();
        bookController.file2 = tempBookFile;
        bookController.file1 = tempBillFile;

        transactionController.file = tempTransactionFile;
        transactionController.getAllTransactions().clear();
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
        if (tempTransactionFile.exists()) {
            tempTransactionFile.delete();
        }
    }

    @BeforeEach
    void reset() {
        bookController.getBooks().clear();
        bookController.getBills().clear();
        librarianController.getBooks().clear();
        transactionController.getAllTransactions().clear();

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

    /// //////////////////////////LIBRARIAN CONTROLLER/////////////////////////////

    @Test
    void testWriteBooksToFileAndReadBooksFromFile() throws FileNotFoundException {
        // Create sample books
        Book book1 = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Book book2 = new Book("978-1-61-729494-5", "Java Concurrency in Practice", "Brian Goetz", "Programming",
                "TechBooks Supplier", new Date(), 40.0, 55.0, 60.0, 50);

        // Add books to the controller
        librarianController.getBooks().add(book1);
        librarianController.getBooks().add(book2);

        // Write books to the file
        try (FileOutputStream fos = new FileOutputStream(tempBookFile)) {
            librarianController.writeBooks(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read books from the file
        try (FileInputStream fis = new FileInputStream(tempBookFile)) {
            librarianController.readBooks(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Verify the books are correctly read
        ArrayList<Book> booksFromFile = librarianController.getBooks();
        assertEquals(2, booksFromFile.size());
        assertEquals("Effective Java", booksFromFile.get(0).getTitle());
        assertEquals("Java Concurrency in Practice", booksFromFile.get(1).getTitle());
    }
    /// //////////////////////////TRANSACTION CONTROLLER///////////////////////////////////////////

    @Test
    void testReadWriteTransactions() throws IOException {
        // Create sample books and transaction
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction = new Transaction(book, 1);

        // Add transaction to the controller
        transactionController.addInAllTransactions(transaction);

        // Write transactions to the file
        transactionController.writeAllTransactions();

        // Clear the current list and read from the file
        transactionController.getAllTransactions().clear();
        transactionController.readAllTransactions();

        assertEquals(1, transactionController.getAllTransactions().size());
        assertEquals("978-3-16-148410-0", transactionController.getAllTransactions().get(0).getBooks().get(0).getISBN());
    }

    @Test
    void testAddTransactionAndVerify() {
        // Create a sample book and transaction
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction = new Transaction(book, 1);

        // Add transaction to the controller
        transactionController.addInAllTransactions(transaction);

        // Verify the transaction was added
        assertEquals(1, transactionController.getAllTransactions().size());
        assertEquals("978-3-16-148410-0", transactionController.getAllTransactions().get(0).getBooks().get(0).getISBN());

        // Add another transaction
        Transaction transaction2 = new Transaction(book, 2);
        transactionController.addInAllTransactions(transaction2);

        // Verify both transactions exist
        assertEquals(2, transactionController.getAllTransactions().size());
        assertEquals("978-3-16-148410-0", transactionController.getAllTransactions().get(1).getBooks().get(0).getISBN());
    }

    @Test
    void testClearTransactions() {
        // Create sample books and transactions
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        Transaction transaction = new Transaction(book, 1);

        // Add transaction and clear transactions
        transactionController.addInAllTransactions(transaction);
        assertEquals(1, transactionController.getAllTransactions().size());

        // Clear all transactions
        transactionController.clearTransactions();

        // Verify the transaction list is empty
        assertTrue(transactionController.getAllTransactions().isEmpty(), "Transaction list should be empty after clearing.");
    }
}
