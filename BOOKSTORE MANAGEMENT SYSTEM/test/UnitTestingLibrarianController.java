package com.example.bookstore.test;

import com.example.bookstore.controller.LibrarianController;
import com.example.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UnitTestingLibrarianController {

        private LibrarianController librarianController;
        private File tempFile;

        @BeforeEach
        void setUp() {
            // Use a temporary file for testing
            try {
                tempFile = File.createTempFile("testBooks", ".bin");
                tempFile.deleteOnExit(); // Automatically delete the file on exit
            } catch (IOException e) {
                fail("Failed to create temp file");
            }

            librarianController = new LibrarianController();
            librarianController.file = tempFile;
            librarianController.getBooks().clear();  // Ensure books list is empty
        }

        @Test
        void testReadBooks() {
            // Prepare test data
            ArrayList<Book> expectedBooks = new ArrayList<>();
            Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
            expectedBooks.add(book);

            // Write test data to the temporary file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                oos.writeObject(expectedBooks);
            } catch (IOException e) {
                fail("Failed to write test data to file");
            }

            // Create a new controller and call readBooks()
            librarianController = new LibrarianController();
            librarianController.file = tempFile; // Set temp file to new controller
            librarianController.readBooks(); // Read the books into the controller

            // Verify the data after reading
            assertEquals(1, librarianController.getBooks().size());
            assertEquals("Effective Java", librarianController.getBooks().get(0).getTitle());
        }

        @Test
        void testWriteBooks() {
            // Add a book to the controller
            Book book = new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming", "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100);
            librarianController.getBooks().add(book);

            // Write books to the temporary file
            librarianController.writeBooks();

            // Read the books directly from the temp file
            ArrayList<Book> writtenBooks = null;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
                writtenBooks = (ArrayList<Book>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                fail("Failed to read data from file");
            }

            // Verify the books written to the file
            assertEquals(1, writtenBooks.size());
            assertEquals("Effective Java", writtenBooks.get(0).getTitle());
        }


}
