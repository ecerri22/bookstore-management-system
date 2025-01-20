package com.example.bookstore.test;

import com.example.bookstore.controller.ManagerController;
import com.example.bookstore.model.Book;
import com.example.bookstore.view.AllBooksView;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllBooksViewTest extends ApplicationTest {

    private ManagerController managerController;
    private AllBooksView allBooksView;

    @BeforeEach
    public void setup() throws Exception {
        managerController = new ManagerController();
        allBooksView = new AllBooksView(managerController);
        Stage stage = new Stage();
        Scene scene = allBooksView.execute(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testEditBookDetails() {
        // Simulate selecting a book
        Book selectedBook = new Book("978-3-16-148410-0", "Pride and Prejudice", "Jane Austen", "Fiction", "Penguin Books",
                java.sql.Date.valueOf("2025-01-01"), 15.99, 20.99, 25.99, 5);
        managerController.addInAllBooks(selectedBook);

        // Simulate clicking "Edit" button
        clickOn("Edit");

        // Simulate editing the ISBN field
        clickOn("ISBN");
        write("978-3-16-148410-1");
        press(KeyCode.ENTER);

        // Verify that the ISBN was updated correctly
        assertEquals("978-3-16-148410-1", selectedBook.getISBN());
    }

    @Test
    public void testDeleteBook() {
        // Simulate adding a book
        Book bookToDelete = new Book("978-3-16-148410-0", "Pride and Prejudice", "Jane Austen", "Fiction", "Penguin Books",
                java.sql.Date.valueOf("2025-01-01"), 15.99, 20.99, 25.99, 5);
        managerController.addInAllBooks(bookToDelete);

        // Simulate selecting the book in the table and clicking "Delete"
        clickOn("Delete");

        // Verify that the book is removed from the table
        assertFalse(managerController.getAllBooks().contains(bookToDelete));
    }

    @Test
    public void testEditCategory() {
        // Simulate adding a book
        Book selectedBook = new Book("978-3-16-148410-0", "Pride and Prejudice", "Jane Austen", "Fiction", "Penguin Books",
                java.sql.Date.valueOf("2025-01-01"), 15.99, 20.99, 25.99, 5);
        managerController.addInAllBooks(selectedBook);

        // Simulate clicking "Edit" button
        clickOn("Edit");

        // Simulate editing the category
        clickOn("Category");
        write("Classic");
        press(KeyCode.ENTER);

        // Verify that the category was updated
        assertEquals("Classic", selectedBook.getCategory());
    }

    @Test
    public void testAddBook() {
        // Simulate adding a new book
        clickOn("ISBN").write("978-3-16-148410-0");
        clickOn("Title").write("Pride and Prejudice");
        clickOn("Author").write("Jane Austen");
        clickOn("Category").write("Fiction");
        clickOn("Supplier").write("Penguin Books");
        clickOn("Purchased Date").write("2025-01-01");
        clickOn("Purchased Price").write("15.99");
        clickOn("Original Price").write("20.99");
        clickOn("Selling Price").write("25.99");
        clickOn("Stock").write("5");

        // Click on the "Add Book" button
        clickOn("Add Book");

        // Verify the confirmation message
        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
        String dialogText = ((Label) dialogPane.getChildren().get(0)).getText();  // Assuming the message is in the first Label

        assertTrue(dialogText.contains("The book is ADDED"));
    }

    @Test
    public void testInvalidAddBook() {
        // Simulate leaving some fields empty
        clickOn("ISBN").write("");
        clickOn("Title").write("");
        clickOn("Add Book");

        // Verify the error message
        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
        String dialogText = ((Label) dialogPane.getChildren().get(0)).getText();  // Assuming the message is in the first Label

        assertTrue(dialogText.contains("Enter Valid Data"));
    }
}
