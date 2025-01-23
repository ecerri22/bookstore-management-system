package com.example.bookstore.test;

import com.example.bookstore.controller.ManagerController;
import com.example.bookstore.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTestingManager{

    private ManagerController managerController;
    private File testFile;

    @BeforeEach
    void setUp() {
        // Set up the file path for testing
        testFile = new File("allBooks.bin");

        // Ensure the file is clean before each test
        if (testFile.exists()) {
            testFile.delete(); // Delete any existing books file
        }

        // Create an instance of ManagerController
        managerController = new ManagerController();
    }

    @AfterEach
    void tearDown() {
        // Clean up the test file after each test to ensure isolation
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddBook() {
        // Create a new book
        Book newBook = new Book("1234567890", "Test Book", "John Doe", "Fiction", "BookStore",
                new Date(), 10.0, 20.0, 25.0, 100);

        // Add the book to the manager
        managerController.addInAllBooks(newBook);

        // Write books to the file
        managerController.writeAllBooks();

        // Read the books from the file
        ArrayList<Book> books = managerController.getAllBooks();

        // Verify that the list contains 1 book and the details match
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("1234567890", books.get(0).getISBN());
        assertEquals("Test Book", books.get(0).getTitle());
        assertEquals("John Doe", books.get(0).getAuthor());
        assertEquals("Fiction", books.get(0).getCategory());
    }

    @Test
    void testModifyBookDetails() {
        // Create and add a book
        Book newBook = new Book("1234567890", "Test Book", "John Doe", "Fiction", "BookStore",
                new Date(), 10.0, 20.0, 25.0, 100);
        managerController.addInAllBooks(newBook);
        managerController.writeAllBooks();

        // Modify the book details
        managerController.modifyTitle(newBook, "Updated Test Book");
        managerController.modifyAuthor(newBook, "Jane Doe");
        managerController.modifyStock(newBook, 150);

        // Read the updated books from the file
        ArrayList<Book> books = managerController.getAllBooks();

        // Verify that the book's title, author, and stock have been updated
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Updated Test Book", books.get(0).getTitle());
        assertEquals("Jane Doe", books.get(0).getAuthor());
        assertEquals(150, books.get(0).getStock());
    }

    @Test
    void testDeleteBook() {
        // Create and add a book
        Book newBook = new Book("1234567890", "Test Book", "John Doe", "Fiction", "BookStore",
                new Date(), 10.0, 20.0, 25.0, 100);
        managerController.addInAllBooks(newBook);
        managerController.writeAllBooks();

        // Delete the book
        managerController.isDeleted(newBook);

        // Read the books from the file
        ArrayList<Book> books = managerController.getAllBooks();

        // Verify that the book has been deleted
        assertNotNull(books);
        assertEquals(0, books.size());
    }

    @Test
    void testReadBooksFromFile() {
        // Add multiple books
        Book book1 = new Book("1234567890", "Book One", "Author One", "Category One", "Supplier One",
                new Date(), 15.0, 30.0, 35.0, 200);
        Book book2 = new Book("0987654321", "Book Two", "Author Two", "Category Two", "Supplier Two",
                new Date(), 20.0, 40.0, 45.0, 300);

        managerController.addInAllBooks(book1);
        managerController.addInAllBooks(book2);
        managerController.writeAllBooks();

        // Read the books from the file
        ArrayList<Book> books = managerController.getAllBooks();

        // Verify that both books were saved and read correctly
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("1234567890", books.get(0).getISBN());
        assertEquals("0987654321", books.get(1).getISBN());
    }

    @Test
    void testAddBookWithExistingIsbn() {
        // Create a new book with the same ISBN
        Book book1 = new Book("1234567890", "Book One", "Author One", "Category One", "Supplier One",
                new Date(), 15.0, 30.0, 35.0, 200);
        managerController.addInAllBooks(book1);
        managerController.writeAllBooks();

        // Create another book with the same ISBN
        Book book2 = new Book("1234567890", "Book Two", "Author Two", "Category Two", "Supplier Two",
                new Date(), 20.0, 40.0, 45.0, 300);

        // Try adding the second book (same ISBN as first book)
        boolean isAdded = managerController.isAdded(book2.getISBN(), book2.getTitle(), book2.getAuthor(),
                book2.getCategory(), book2.getSupplier(), book2.getPurchasedDate(),
                book2.getPurchasedPrice(), book2.getOriginalPrice(), book2.getSellingPrice(), book2.getStock());

        // Verify that the second book was added
        assertTrue(isAdded);

        // Read the books from the file and verify the list contains two different books
        ArrayList<Book> books = managerController.getAllBooks();
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("1234567890", books.get(0).getISBN());
        assertEquals("Book One", books.get(0).getTitle());
        assertEquals("Book Two", books.get(1).getTitle());
    }
}
