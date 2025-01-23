package com.example.bookstore.test.integrationTests;

import com.example.bookstore.controller.AuthorController;
import com.example.bookstore.model.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTestingAuthor{

    private AuthorController authorController;
    private File testFile;

    @BeforeEach
    void setUp() {
        // Set up the file path for testing
        testFile = new File("authors.bin");

        // Ensure the file is clean before each test
        if (testFile.exists()) {
            testFile.delete(); // Delete any existing authors file
        }

        // Create a real instance of AuthorController
        authorController = new AuthorController();
    }

    @AfterEach
    void tearDown() {
        // Clean up the test file after each test to ensure isolation
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddAuthor() {
        // Create a new author
        Author newAuthor = new Author("John Doe");

        // Add the author to the controller
        authorController.addAuthor(newAuthor);

        // Write authors to the file
        authorController.writeAuthors();

        // Read the authors from the file
        ArrayList<Author> authors = authorController.readAuthors();

        // Verify the list contains 1 author
        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals("John Doe", authors.get(0).getFullName());
    }

    @Test
    void testVerifyAuthor() {
        // Verify a new author
        boolean isVerified = authorController.verify("John Doe");

        // Verify that the author was added
        assertTrue(isVerified);

        // Verify the file contains the new author
        ArrayList<Author> authors = authorController.readAuthors();
        assertEquals(1, authors.size());
        assertEquals("John Doe", authors.get(0).getFullName());

        // Try verifying an existing author (should fail)
        boolean isVerifiedAgain = authorController.verify("John Doe");
        assertFalse(isVerifiedAgain);
    }

    @Test
    void testReadEmptyFile() {
        // Ensure the file is empty before starting the test
        authorController.readAuthors();

        // Read authors from the empty file (should return empty list)
        ArrayList<Author> authors = authorController.readAuthors();

        // The list should be empty since no authors have been added
        assertNotNull(authors);
        assertEquals(0, authors.size());
    }

    @Test
    void testMultipleAuthorAdditions() {
        // Add multiple authors
        authorController.addAuthor(new Author("John Doe"));
        authorController.addAuthor(new Author("Jane Doe"));

        // Write authors to the file
        authorController.writeAuthors();

        // Read the authors from the file
        ArrayList<Author> authors = authorController.readAuthors();

        // Verify the list contains both authors
        assertNotNull(authors);
        assertEquals(2, authors.size());

        // Check individual author names
        assertEquals("John Doe", authors.get(0).getFullName());
        assertEquals("Jane Doe", authors.get(1).getFullName());
    }
}
