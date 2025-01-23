package com.example.bookstore.test.unitTests;

import com.example.bookstore.controller.ManagerController;
import com.example.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UnitTestingManagerController {
    private ManagerController managerController;
    private ArrayList<Book> mockBookList;
    private Book mockBook;

    @BeforeEach
    void setUp() {
        // Mock the ArrayList used to hold books
        mockBookList = mock(ArrayList.class);

        // Create a mock Book object
        mockBook = mock(Book.class); // Mock the Book class

        // Create the ManagerController using the default constructor
        managerController = Mockito.spy(new ManagerController());

        // Override the methods to use our mocked list
        doReturn(mockBookList).when(managerController).getAllBooks(); // Mock getAllBooks method
        doNothing().when(managerController).readAllBooks(); // Mock readAllBooks method
        doNothing().when(managerController).writeAllBooks(); // Mock writeAllBooks method
    }

    @Test
    void testIsAdded() {
        // Arrange: Create a Book and mock `isEmailExists` method if needed
        String isbn = "1234";
        String title = "Test Book";
        String author = "Test Author";
        String category = "Fiction";
        String supplier = "Supplier Name";
        Date purchasedDate = new Date();
        double purchasedPrice = 10.0;
        double originalPrice = 15.0;
        double sellingPrice = 20.0;
        int stock = 100;

        // Act: Add a book using the `isAdded` method
        boolean result = managerController.isAdded(isbn, title, author, category, supplier, purchasedDate,
                purchasedPrice, originalPrice, sellingPrice, stock);

        // Assert: Verify that the `addInAllBooks` method was called to add the book
        assertTrue(result, "The book should be added successfully");
        verify(managerController, times(1)).addInAllBooks(any(Book.class)); // Verify that addInAllBooks was called once
    }

//    @Test        - removeIf is a method of List (or specifically ArrayList), and it doesn't return a value. Instead, it modifies the list in place by removing elements that satisfy a condition.
//    void testIsDeleted() {
//    }



    @Test
    void testFindBook_NonExistingBook() {
        // Arrange: Simulate that the book is not in the list
        doReturn(false).when(mockBookList).contains(mockBook);

        // Act: Try to find a non-existing book
        Book result = managerController.findBook(mockBook);

        // Assert: Verify the method returns null for a non-existing book
        assertNull(result, "The book should not be found in the list");
    }

    @Test
    void testModifyTitle() {
        // Arrange: Simulate modifying the book title
        String newTitle = "Updated Test Book";
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book title using the modifyTitle method
        boolean result = managerController.modifyTitle(mockBook, newTitle);

        // Assert: Verify the title was modified
        assertTrue(result, "The book title should be modified successfully");
        verify(mockBook, times(1)).setTitle(newTitle); // Verify that setTitle was called once with the new title
    }

    @Test
    void testReadBooks_FileNotFound() {
        // Arrange: Mock the file existence to simulate the file not being found
        when(managerController.getAllBooks()).thenReturn(new ArrayList<>()); // Return an empty list as the file doesn't exist

        // Act: Call the readAllBooks method
        managerController.readAllBooks();

        // Assert: Verify that no books were added since the file doesn't exist
        assertTrue(managerController.getAllBooks().isEmpty(), "The books list should be empty if no books are read");
    }

    @Test
    void testWriteBooks() {
        // Arrange: Mock the behavior of the writeAllBooks method to do nothing
        doNothing().when(managerController).writeAllBooks();

        // Act: Simulate writing books to the file
        managerController.writeAllBooks();

        // Assert: Verify the writeAllBooks method was called
        verify(managerController, times(1)).writeAllBooks();
    }



    @Test
    void testModifyISBN() {
        // Arrange: Simulate modifying the book ISBN
        String newISBN = "1234567890";
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book ISBN using the modifyISBN method
        boolean result = managerController.modifyISBN(mockBook, newISBN);

        // Assert: Verify the ISBN was modified
        assertTrue(result, "The book ISBN should be modified successfully");
        verify(mockBook, times(1)).setISBN(newISBN); // Verify that setISBN was called once with the new ISBN
    }


    @Test
    void testModifyAuthor() {
        // Arrange: Simulate modifying the book author
        String newAuthor = "New Author Name";
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book author using the modifyAuthor method
        boolean result = managerController.modifyAuthor(mockBook, newAuthor);

        // Assert: Verify the author was modified
        assertTrue(result, "The book author should be modified successfully");
        verify(mockBook, times(1)).setAuthor(newAuthor); // Verify that setAuthor was called once with the new author
    }


    @Test
    void testModifyCategory() {
        // Arrange: Simulate modifying the book category
        String newCategory = "New Category";
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book category using the modifyCategory method
        boolean result = managerController.modifyCategory(mockBook, newCategory);

        // Assert: Verify the category was modified
        assertTrue(result, "The book category should be modified successfully");
        verify(mockBook, times(1)).setCategory(newCategory); // Verify that setCategory was called once with the new category
    }

    @Test
    void testModifySupplier() {
        // Arrange: Simulate modifying the book supplier
        String newSupplier = "New Supplier";
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book supplier using the modifySupplier method
        boolean result = managerController.modifySupplier(mockBook, newSupplier);

        // Assert: Verify the supplier was modified
        assertTrue(result, "The book supplier should be modified successfully");
        verify(mockBook, times(1)).setSupplier(newSupplier); // Verify that setSupplier was called once with the new supplier
    }


    @Test
    void testModifyPurchasedDate() {
        // Arrange: Simulate modifying the book purchased date
        Date newPurchasedDate = new Date();
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book purchased date using the modifyPurchasedDate method
        boolean result = managerController.modifyPurchasedDate(mockBook, newPurchasedDate);

        // Assert: Verify the purchased date was modified
        assertTrue(result, "The book purchased date should be modified successfully");
        verify(mockBook, times(1)).setPurchasedDate(newPurchasedDate); // Verify that setPurchasedDate was called once with the new purchased date
    }

    @Test
    void testModifyPurchasedPrice() {
        // Arrange: Simulate modifying the book purchased price
        double newPurchasedPrice = 15.99;
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book purchased price using the modifyPurchasedPrice method
        boolean result = managerController.modifyPurchasedPrice(mockBook, newPurchasedPrice);

        // Assert: Verify the purchased price was modified
        assertTrue(result, "The book purchased price should be modified successfully");
        verify(mockBook, times(1)).setPurchasedPrice(newPurchasedPrice); // Verify that setPurchasedPrice was called once with the new purchased price
    }

    @Test
    void testModifyOriginalPrice() {
        // Arrange: Simulate modifying the book original price
        double newOriginalPrice = 20.99;

        // Ensure findBook returns mockBook when called
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book original price using the modifyOriginalPrice method
        boolean result = managerController.modifyOriginalPrice(mockBook, newOriginalPrice);

        // Assert: Verify the original price was modified
        assertTrue(result, "The book original price should be modified successfully");
        verify(mockBook, times(1)).setOriginalPrice(newOriginalPrice); // Verify that setOriginalPrice was called once with the new original price
    }


    @Test
    void testModifySellingPrice() {
        double newSellingPrice = 25.99;

        // Arrange: Mock the behavior of findBook to return the mockBook
        when(managerController.findBook(mockBook)).thenReturn(mockBook);

        // Act: Modify the selling price of the book
        boolean result = managerController.modifySellingPrice(mockBook, newSellingPrice);

        // Assert: Verify the setter method was called with the correct argument
        verify(mockBook, times(1)).setSellingPrice(newSellingPrice); // Verify the setter was called once with the new price
        verify(managerController, times(1)).writeAllBooks(); // Verify writeAllBooks was called once to save the changes
        assertTrue(result, "The selling price should be modified successfully");
    }


    @Test
    void testModifyStock() {
        // Arrange: Simulate modifying the book stock
        int newStock = 100;
        doReturn(mockBook).when(managerController).findBook(mockBook); // Mock findBook to return mockBook

        // Act: Modify the book stock using the modifyStock method
        boolean result = managerController.modifyStock(mockBook, newStock);

        // Assert: Verify the stock was modified
        assertTrue(result, "The book stock should be modified successfully");
        verify(mockBook, times(1)).setStock(newStock); // Verify that setStock was called once with the new stock
    }

}
