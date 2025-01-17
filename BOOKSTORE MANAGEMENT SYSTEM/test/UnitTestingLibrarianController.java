package com.example.bookstore.test;

import com.example.bookstore.controller.LibrarianController;
import com.example.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UnitTestingLibrarianController {

    private LibrarianController librarianController;

    @BeforeEach
    void setUp() {
        librarianController = new LibrarianController();
    }

    @Test
    void testReadBooks() throws Exception {
        // Prepare test data
        ArrayList<Book> expectedBooks = new ArrayList<>();
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        expectedBooks.add(book);

        // Create a mocked InputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(expectedBooks);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        // Call the method
        librarianController.readBooks(bais);

        // Verify the data after reading
        assertEquals(1, librarianController.getBooks().size());
        assertEquals("Effective Java", librarianController.getBooks().get(0).getTitle());
    }

    @Test
    void testWriteBooks() throws Exception {
        // Add a book to the controller
        Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
        librarianController.getBooks().add(book);

        // Create a mocked OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Call the method
        librarianController.writeBooks(baos);

        // Read the data back to verify
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ArrayList<Book> writtenBooks;
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            writtenBooks = (ArrayList<Book>) ois.readObject();
        }

        // Verify the books written to the stream
        assertEquals(1, writtenBooks.size());
        assertEquals("Effective Java", writtenBooks.get(0).getTitle());
    }
}
