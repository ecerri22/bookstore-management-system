package com.example.bookstore.test;

import com.example.bookstore.view.LoginView;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginViewTest extends ApplicationTest {

    private LoginView loginView;
    private Stage stage;

    @BeforeEach
    public void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> {
            stage = new Stage();
            loginView = new LoginView();
            stage.setScene(loginView.execute(stage));
            stage.show();
            return null;
        });
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Fill in valid login credentials
        clickOn("Email").write("valid@example.com");
        clickOn("Password").write("validpassword");

        // Click the Login button
        clickOn("Login");

        // Verify some UI change indicating successful login
        // Example: Check that the Login button becomes disabled (or similar UI change)
        Button loginButton = lookup("Login").queryAs(Button.class);
        assertTrue(loginButton.isDisabled(), "Login button should be disabled after successful login.");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Fill in invalid login credentials
        clickOn("Email").write("invalid@example.com");
        clickOn("Password").write("wrongpassword");

        // Click the Login button
        clickOn("Login");

        // Verify some UI change indicating failed login
        // Example: Check that the Login button is not disabled, or check other UI changes
        Button loginButton = lookup("Login").queryAs(Button.class);
        assertTrue(!loginButton.isDisabled(), "Login button should remain enabled after failed login.");
    }

    @Test
    public void testEmptyFields() {
        // Leave fields empty and click Login button
        clickOn("Login");

        // Verify UI behavior, such as button state or focus
        TextField emailField = lookup("Email").queryAs(TextField.class);
        PasswordField passwordField = lookup("Password").queryAs(PasswordField.class);

        // You can check that the email and password fields are still focused or something else indicative of empty fields
        assertTrue(emailField.isFocused(), "Email field should be focused when fields are empty.");
        assertTrue(passwordField.isFocused(), "Password field should be focused when fields are empty.");
    }

    @Test
    public void testLoginWithEmptyPassword() {
        // Fill in email but leave password empty
        clickOn("Email").write("valid@example.com");
        clickOn("Password").write("");

        // Click the Login button
        clickOn("Login");

        // Verify UI behavior, such as button state or focus
        Button loginButton = lookup("Login").queryAs(Button.class);
        assertTrue(!loginButton.isDisabled(), "Login button should remain enabled when password is empty.");
    }
}
