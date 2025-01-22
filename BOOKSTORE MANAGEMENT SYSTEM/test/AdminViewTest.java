package com.example.bookstore.test;

import com.example.bookstore.view.AdminView;
import com.example.bookstore.model.User;

import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import view.AdminView;


public class AdminViewTest extends ApplicationTest {

    private AdminView adminView;
    private User admin;
    private static final long serialVersionUID = 1L;


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

    @BeforeEach
    void setUp() {
        // Additional setup can go here if needed
    }

    // Test for presence of all buttons
    @Test
    void testSellBookButtonExists() {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Sell Book"));
    }

    @Test
    void testAddBookButtonExists() {
        // Look for the button with the exact text "Add Book"
        FxAssert.verifyThat(lookup("Add Book").queryButton(), LabeledMatchers.hasText("Add Book"));
    }

    @Test
    void testSeeAllBooksButtonExists() {
        FxAssert.verifyThat(lookup("Books Data").queryButton(), LabeledMatchers.hasText("Books Data"));
    }

    @Test
    void testAddEmployeeButtonExists() {
        FxAssert.verifyThat(lookup("Add Employee").queryButton(), LabeledMatchers.hasText("Add Employee"));
    }


    @Test
    void testSeeAllEmployeesButtonExists() {
        FxAssert.verifyThat(lookup("Employees Data").queryButton(), LabeledMatchers.hasText("Employees Data"));
    }


    @Test
    void testStatisticsButtonExists() {
        FxAssert.verifyThat(lookup("Statistics").queryButton(), LabeledMatchers.hasText("Statistics"));
    }

    @Test
    void testLogoutButtonExists() {
        FxAssert.verifyThat(lookup("LOGOUT").queryButton(), LabeledMatchers.hasText("LOGOUT"));
    }

    // Test for button actions
    @Test
    void testSellBookButtonAction() {
        clickOn("Sell Book");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("Enter the title of the book : "));
    }

    @Test
    void testAddBookButtonAction() {
        clickOn("Add Book");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("ISBN")); // Adjust based on actual label text
    }

    @Test
    void testSeeAllBooksButtonAction() {
        clickOn("Books Data");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("ISBN")); // Adjusted to match the actual label text
    }


    @Test
    void testAddEmployeeButtonAction() {
        clickOn("Add Employee");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("First Name: ")); // Adjusted to match the actual label text
    }


    @Test
    void testSeeAllEmployeesButtonAction() {
        clickOn("Employees Data");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("First Name")); // Adjusted to match the actual label text
    }


    @Test
    void testStatisticsButtonAction() {
        clickOn("Statistics");
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("Purchases")); // Adjusted to match actual label text
    }

    @Test
    void testLogoutButtonAction() {
        clickOn("LOGOUT");

        // Simulate confirmation
        clickOn(ButtonType.YES.getText());

        // Assert that the login scene is displayed
        FxAssert.verifyThat(".label", LabeledMatchers.hasText("Successfully logged out!")); // Adjust as per actual UI
    }

    @Test
    void testLogoutButtonNoConfirmation() {
        clickOn("LOGOUT");

        // Simulate declining confirmation
        clickOn("No");

        // Assert that the current scene remains unchanged
        FxAssert.verifyThat(lookup("LOGOUT").queryButton(), LabeledMatchers.hasText("LOGOUT"));
    }

    /////////////////////////////////




}
