package com.example.bookstore.test;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Librarian;
import com.example.bookstore.model.Transaction;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnitTestingBookController {

    private BookController bookController;
    private Librarian librarian;

    @BeforeAll
    public static void initJavaFX() {
        // Initialize JavaFX toolkit
        new JFXPanel();
    }

    @BeforeEach
    public void setUp() {
        bookController = Mockito.spy(new BookController());
        librarian = new Librarian(
                "John", "Doe", "123456789", "test@example.com", "password",
                "Librarian", 5000.0, new Date(), true, true
        );
        bookController.getBooks().clear();
    }

    @Test
    public void testAddBook() {
        // Arrange
        String isbn = "123456";
        String title = "Test Book";
        String author = "Test Author";
        String category = "Fiction";
        String supplier = "Test Supplier";
        Date purchasedDate = new Date();
        double purchasedPrice = 10.0;
        double originalPrice = 15.0;
        double sellingPrice = 12.0;
        int stock = 10;

        // Act
        bookController.addBook(isbn, title, author, category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);

        // Assert
        assertEquals(1, bookController.getBooks().size());
        Book book = bookController.getBooks().get(0);
        assertEquals(isbn, book.getISBN());
        assertEquals(title, book.getTitle());
    }

    @Test
    public void testVerifyBook() {
        // Arrange
        String isbn = "123456";
        String title = "Test Book";
        String author = "Test Author";
        String category = "Fiction";
        String supplier = "Test Supplier";
        Date purchasedDate = new Date();
        double purchasedPrice = 10.0;
        double originalPrice = 15.0;
        double sellingPrice = 12.0;
        int stock = 10;

        bookController.addBook(isbn, title, author, category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);

        // Act
        boolean result = bookController.verifyBook(isbn, title, author, category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);

        // Assert
        assertFalse(result);  // The book already exists
    }

    @Test
    public void testIsISBNExists() {
        // Arrange
        String isbn = "123456";
        bookController.addBook(isbn, "Test Book", "Test Author", "Fiction", "Test Supplier", new Date(), 10.0, 15.0, 12.0, 10);

        // Act
        boolean exists = bookController.isISBNExists(isbn);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void testNrOfBooksSold() {
        // Arrange
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);

        Transaction transaction1 = new Transaction(book1, 3);
        Transaction transaction2 = new Transaction(book2, 2);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        Bill bill = new Bill(transactions, librarian);
        bookController.getBills().add(bill);

        // Act
        int totalBooksSold = bookController.nrOfBooksSold();

        // Assert
        assertEquals(5, totalBooksSold);
    }

    @Test
    public void testNrOfBooksSoldDaily() {
        // Arrange
        Date specificDate = new Date();
        Book book1 = new Book("123456", "Book 1", "Author 1", "Category 1", "Supplier 1", specificDate, 10.0, 15.0, 12.0, 10);
        Book book2 = new Book("654321", "Book 2", "Author 2", "Category 2", "Supplier 2", specificDate, 20.0, 25.0, 22.0, 5);

        Transaction transaction1 = new Transaction(book1, 3);
        Transaction transaction2 = new Transaction(book2, 2);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        Bill bill = new Bill(transactions, librarian);
        bookController.getBills().add(bill);

        // Act
        int booksSold = bookController.nrOfBooksSoldDaily(specificDate);

        // Assert
        assertEquals(5, booksSold);
    }

    @Test
    void testWriteAndReadBooks() throws Exception {
        // Arrange
        BookController bookController = new BookController();
        Book mockBook = new Book("123456789", "Test Title", "Test Author", "Test Category",
                "Test Supplier", new Date(), 50.0, 60.0, 70.0, 10);

        // Use a temporary file to simulate file storage for testing
        File tempFile = File.createTempFile("testBooks", ".bin");
        tempFile.deleteOnExit(); // Ensure the file is cleaned up after the test

        // Mock file2 to point to the temp file
        bookController.file2 = tempFile;

        // Clear any existing books for a clean test environment
        bookController.getBooks().clear();

        // Add the book and write it to the file
        bookController.addBook(mockBook.getISBN(), mockBook.getTitle(), mockBook.getAuthor(),
                mockBook.getCategory(), mockBook.getSupplier(), mockBook.getPurchasedDate(),
                mockBook.getPurchasedPrice(), mockBook.getOriginalPrice(),
                mockBook.getSellingPrice(), mockBook.getStock());
        bookController.writeBooks();

        // Act
        ArrayList<Book> booksFromFile = bookController.readBooks();

        // Assert
        assertNotNull(booksFromFile, "Books should not be null after reading from file");
        assertFalse(booksFromFile.isEmpty(), "Books should not be empty after reading from file");
        assertEquals(1, booksFromFile.size(), "Books size should be 1 after adding a single book");

        Book retrievedBook = booksFromFile.get(0);
        assertAll(
                () -> assertEquals(mockBook.getISBN(), retrievedBook.getISBN(), "Book ISBN should match the original"),
                () -> assertEquals(mockBook.getTitle(), retrievedBook.getTitle(), "Book title should match the original"),
                () -> assertEquals(mockBook.getAuthor(), retrievedBook.getAuthor(), "Book author should match the original"),
                () -> assertEquals(mockBook.getCategory(), retrievedBook.getCategory(), "Book category should match the original"),
                () -> assertEquals(mockBook.getSupplier(), retrievedBook.getSupplier(), "Book supplier should match the original"),
                () -> assertEquals(mockBook.getPurchasedPrice(), retrievedBook.getPurchasedPrice(), "Book purchased price should match the original"),
                () -> assertEquals(mockBook.getOriginalPrice(), retrievedBook.getOriginalPrice(), "Book original price should match the original"),
                () -> assertEquals(mockBook.getSellingPrice(), retrievedBook.getSellingPrice(), "Book selling price should match the original"),
                () -> assertEquals(mockBook.getStock(), retrievedBook.getStock(), "Book stock should match the original")
        );
    }




    @Test
    public void testDisplayAlert() {
        assertDoesNotThrow(() -> {
            // Run JavaFX-related code on the JavaFX application thread
            Platform.runLater(() -> bookController.displayAlert());
        });
    }
}
