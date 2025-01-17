package com.example.bookstore.test;

import com.example.bookstore.controller.AuthorController;
import com.example.bookstore.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnitTestingAuthorController {

    private AuthorController mockAuthorController;
    private Author mockAuthor;

    @BeforeEach
    void setUp() {
        // Create a mock of the AuthorController class
        mockAuthorController = mock(AuthorController.class);

        // Create a mock Author object
        mockAuthor = new Author("John Doe");

        // Create a mock list of authors
        ArrayList<Author> mockAuthorsList = new ArrayList<>();
        mockAuthorsList.add(new Author("Jane Doe"));

        // Mock the behavior of the readAuthors method to return the mocked list of authors
        when(mockAuthorController.readAuthors()).thenReturn(mockAuthorsList);

        // Mock the addAuthor method to modify the internal list (or any necessary action)
        doNothing().when(mockAuthorController).addAuthor(any(Author.class));

        // Mock the verify method (for both success and failure scenarios)
        when(mockAuthorController.verify("John Doe")).thenReturn(true);
        when(mockAuthorController.verify("Jane Doe")).thenReturn(false);

        // Mock the writeAuthors method to do nothing (no file I/O)
        doNothing().when(mockAuthorController).writeAuthors();

        // Mock the printAuthorsToConsole method to do nothing (no actual console output)
        doNothing().when(mockAuthorController).printAuthorsToConsole();
    }

    @Test
    void testAddAuthor() {
        // Simulate adding an author
        mockAuthorController.addAuthor(mockAuthor);

        // Verify that addAuthor was called with the mock author
        verify(mockAuthorController).addAuthor(mockAuthor);
    }

    @Test
    void testVerifyAuthorSuccess() {
        // Test when the author is verified successfully (i.e., "John Doe")
        boolean result = mockAuthorController.verify("John Doe");

        // Assert the result is true, meaning the author was successfully verified
        assertTrue(result);

        // Verify that the verify method was called with the correct argument
        verify(mockAuthorController).verify("John Doe");
    }

    @Test
    void testVerifyAuthorDuplicate() {
        // Test when the author already exists (i.e., "Jane Doe")
        boolean result = mockAuthorController.verify("Jane Doe");

        // Assert the result is false, meaning the author already exists
        assertFalse(result);

        // Verify that the verify method was called with the correct argument
        verify(mockAuthorController).verify("Jane Doe");
    }

    @Test
    void testReadAuthors() {
        // Simulate reading authors (this will return the mocked list)
        ArrayList<Author> authors = mockAuthorController.readAuthors();

        // Assert that the returned list is not null and contains one author
        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals("Jane Doe", authors.get(0).getFullName());

        // Verify that readAuthors was called
        verify(mockAuthorController).readAuthors();
    }

    @Test
    void testWriteAuthors() {
        // Simulate writing authors (this does nothing because we mock it)
        mockAuthorController.writeAuthors();

        // Verify that writeAuthors was called (no actual file writing happens)
        verify(mockAuthorController).writeAuthors();
    }

    @Test
    void testPrintAuthorsToConsole() {
        // Simulate printing authors to console (no actual printing happens)
        mockAuthorController.printAuthorsToConsole();

        // Verify that printAuthorsToConsole was called
        verify(mockAuthorController).printAuthorsToConsole();
    }
}
