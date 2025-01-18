package com.example.bookstore.test;

import com.example.bookstore.model.User;
import com.example.bookstore.view.SellBook;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class SellBookViewTest extends ApplicationTest {
    private SellBook sellBook;

    @Override
    public void start(Stage stage) {
        User testUser = new User("Jane", "Doe", "123456789", "librarian@example.com", "password123", "Librarian", 5000.0, null, true, true);
        sellBook = new SellBook(testUser);
        stage.setScene(sellBook.execute(stage));
        stage.show();
    }

    @Test
    void testAddBookButton() {
        clickOn("#book_titleF").write("Test Book");
        clickOn("#author_nameF").write("Test Author");
        clickOn("#qtyF .increment-arrow-button").clickOn();
        clickOn("#addBookBtn");

        // Additional assertions can go here
    }

    @Test
    void testCreateBillButton() {
        clickOn("#createBillBtn");

        // Add assertions for alert or bill creation
    }
}
