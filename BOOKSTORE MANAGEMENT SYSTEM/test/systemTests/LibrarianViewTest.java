package com.example.bookstore.test.systemTests;

import com.example.bookstore.model.User;
import com.example.bookstore.view.LibrarianView;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;


public class LibrarianViewTest extends ApplicationTest {

    private LibrarianView librarianView;
    private User librarian;

    @Override
    public void start(Stage stage) {
        // Initialize a mock librarian user
        librarian = new User("Jane", "Doe", "123456789", "librarian@example.com", "password123", "Librarian", 5000.0, null, true, true);
        librarianView = new LibrarianView(librarian);

        // Set up the stage
        Scene scene = librarianView.execute(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testSellBookButtonExists() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Add Book"));
    }

    @Test
    void testSellBookButtonAction() {
        // Click the "Sell Book" button
        clickOn("Sell Book");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("Enter the title of the book : "));
    }

    @Test
    void testSeeBooksButtonExists() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Add Book"));
    }

    @Test
    void testSeeBooksButtonAction() {
        // Click the "Books Data" button
        clickOn("Books Data");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("ISBN"));
    }

    @Test
    void testAddBookButtonExists() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Add Book"));
    }

    @Test
    void testAddBookButtonAction() {
        clickOn("Add Book");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("ISBN"));
    }

    @Test
    void testLogoutButtonAction() {
        clickOn("LOGOUT");

        // Simulate confirmation
        clickOn(ButtonType.YES.getText());

        // Assert that the login scene is displayed
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("Successfully logged out!"));
    }
}
