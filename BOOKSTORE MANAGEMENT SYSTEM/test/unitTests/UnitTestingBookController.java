package com.example.bookstore.test.unitTests;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Librarian;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTestingBookController {

    @Spy // Spy ensures we only mock dependencies, not the method being tested
    private BookController bookController;

    @Mock
    private Librarian librarian;

    @BeforeAll
    public static void initJavaFX() {
        new JFXPanel();
    }

    @BeforeEach
    public void setUp() {
        bookController.getBooks().clear();
    }

    @Test
    public void testAddBook() {
        doCallRealMethod().when(bookController).addBook(anyString(), anyString(), anyString(), anyString(), anyString(), any(), anyDouble(), anyDouble(), anyDouble(), anyInt());

        bookController.addBook("123456", "Test Book", "Test Author", "Fiction", "Test Supplier", new Date(), 10.0, 15.0, 12.0, 10);
        assertEquals(1, bookController.getBooks().size());
    }

    @Test
    public void testIsISBNExists() {
        doCallRealMethod().when(bookController).isISBNExists(anyString());
        bookController.getBooks().add(new Book("123456", "Test Book", "Test Author", "Fiction", "Test Supplier", new Date(), 10.0, 15.0, 12.0, 10));
        assertTrue(bookController.isISBNExists("123456"));
    }

    @Test
    public void testWriteAndReadBooks() throws Exception {
        doCallRealMethod().when(bookController).writeBooks();
        doCallRealMethod().when(bookController).readBooks();

        File tempFile = File.createTempFile("testBooks", ".bin");
        tempFile.deleteOnExit();
        bookController.file2=tempFile;

        bookController.getBooks().add(new Book("123456789", "Test Title", "Test Author", "Test Category", "Test Supplier", new Date(), 50.0, 60.0, 70.0, 10));
        bookController.writeBooks();
        assertFalse(bookController.readBooks().isEmpty());
    }
}
