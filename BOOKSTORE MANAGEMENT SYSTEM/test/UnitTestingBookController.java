package com.example.bookstore.test;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Librarian;
import com.example.bookstore.model.Transaction;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTestingBookController {

    private BookController bookController;
    private Librarian librarian = new Librarian("John", "Doe", "123456789", "test@example.com", "password", "Librarian", 5000.0, new Date(), true, true);

    @BeforeEach
    public void setUp() {
        // Setup the BookController before each test
        new JFXPanel();
        bookController = new BookController();
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

        bookController.addBook(isbn, title, author, category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);

        // Assert
        assertEquals(1, bookController.getBooks().size());
        Book book = bookController.getBooks().get(0);
        assertEquals(isbn, book.getISBN());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
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

        boolean result = bookController.verifyBook(isbn, title, author, category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);

        // Assert
        assertFalse(result);  // Because the book already exists
    }

    @Test
    public void testIsISBNExists() {
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

        boolean exists = bookController.isISBNExists(isbn);

        // Assert
        assertTrue(exists);  // The book with the given ISBN should exist
    }

    @Test
    public void testNrOfBooksSold() {
        // Arrange
        Book book1 = new Book("123456", "Book 1", "Author 1", "Category 1", "Supplier 1", new Date(), 10.0, 15.0, 12.0, 10);
        Book book2 = new Book("654321", "Book 2", "Author 2", "Category 2", "Supplier 2", new Date(), 20.0, 25.0, 22.0, 5);

        // Create Transactions for the books sold
        Transaction transaction1 = new Transaction(book1, 3);  // 3 copies of book1 sold
        Transaction transaction2 = new Transaction(book2, 2);  // 2 copies of book2 sold

        // Create a Bill with the transactions
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        Bill bill = new Bill(transactions, librarian);
        bookController.getBills().add(bill);

        int totalBooksSold = bookController.nrOfBooksSold();

        // Assert
        assertEquals(5, totalBooksSold);  // 3 + 2 books sold in total
    }

    @Test
    public void testNrOfBooksSoldDaily() {
        // Arrange
        Date specificDate = new Date();  // Assuming the current date
        Book book1 = new Book("123456", "Book 1", "Author 1", "Category 1", "Supplier 1", specificDate, 10.0, 15.0, 12.0, 10);
        Book book2 = new Book("654321", "Book 2", "Author 2", "Category 2", "Supplier 2", specificDate, 20.0, 25.0, 22.0, 5);

        // Create Transactions for the books sold on the specific date
        Transaction transaction1 = new Transaction(book1, 3);  // 3 copies of book1 sold
        Transaction transaction2 = new Transaction(book2, 2);  // 2 copies of book2 sold

        // Create a Bill with the transactions on the specific date
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);
        Bill bill = new Bill(transactions, librarian);
        bookController.getBills().add(bill);

        int booksSold = bookController.nrOfBooksSoldDaily(specificDate);

        // Assert
        assertEquals(5, booksSold);  // 3 + 2 books sold on the specific date
    }

    @Test
    public void testWriteAndReadBooks() {
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

        // Add a book
        bookController.addBook(isbn, title, author, category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);

        // Write books to file
        bookController.writeBooks();

        // Create a new BookController instance and read books from file
        BookController newController = new BookController();
        newController.readBooks();

        // Assert
        assertEquals(1, newController.getBooks().size());
        Book book = newController.getBooks().get(0);
        assertEquals(isbn, book.getISBN());
        assertEquals(title, book.getTitle());
    }

    @Test
    public void testDisplayAlert() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> bookController.displayAlert());
        });
    }
}
