package com.example.bookstore.test;

import com.example.bookstore.model.User;
import com.example.bookstore.view.AdminView;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;


public class AdminViewTest extends ApplicationTest {

    private AdminView adminView;
    private User admin;

    @Override
    public void start(Stage stage) {
        // Initialize a mock admin user
        admin = new User("John", "Doe", "123456789", "admin@example.com", "password", "Admin", 5000.0, null, true, true);
        adminView = new AdminView(admin);

        // Set up the stage
        Scene scene = adminView.execute(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testSellBookButtonExists() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Sell Book"));
    }

    @Test
    void testSellBookButtonAction() {
        // Click the Sell Book button
        clickOn("Sell Book");

        // Assert that the scene changes (example placeholder)
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("Enter the title of the book : "));
    }

    @Test
    void testAddBookButtonExists() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Sell Book"));
    }

    @Test
    void testAddBookButtonAction() {
        clickOn("Add Book");

        // Assert that the scene changes (example placeholder)
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
