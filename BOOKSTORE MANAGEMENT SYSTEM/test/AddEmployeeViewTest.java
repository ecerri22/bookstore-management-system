import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.AddEmployeeView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddEmployeeViewTest extends ApplicationTest {

    private AddEmployeeView addEmployeeView;

    @Override
    public void start(Stage stage) {
        addEmployeeView = new AddEmployeeView();
        Scene scene = addEmployeeView.execute(stage);
        if (scene.getRoot() == null) {
            System.err.println("Root node is null!");
        }
        stage.setScene(scene);
        stage.show();
    }


//    @Test
//    public void testUIElementsExist() {
//        assertNotNull(lookup("#firstNameField").query(), "First Name field should exist");
//        assertNotNull(lookup("#lastNameField").query(), "Last Name field should exist");
//        assertNotNull(lookup("#birthdayPicker").query(), "Birthday field should exist");
//        assertNotNull(lookup("#phoneField").query(), "Phone field should exist");
//        assertNotNull(lookup("#emailField").query(), "Email field should exist");
//        assertNotNull(lookup("#passwordField").query(), "Password field should exist");
//        assertNotNull(lookup("#roleComboBox").query(), "Role dropdown should exist");
//        assertNotNull(lookup("#salaryField").query(), "Salary field should exist");
//        assertNotNull(lookup("#canAddBookCheckBox").query(), "Can Add Book checkbox should exist");
//        assertNotNull(lookup("#canAddBillCheckBox").query(), "Can Add Bill checkbox should exist");
//        assertNotNull(lookup("#addEmployeeButton").query(), "Add Employee button should exist");
//    }

    @Test
    public void testValidationErrors() {
        clickOn("#addEmployeeButton");

        // Query the alert dialog
        DialogPane dialogPane = lookup(".dialog-pane").query();
        assertNotNull(dialogPane, "An error alert should be displayed");
        assertEquals("Please fill in all required fields", dialogPane.getHeaderText());

        // Close the alert
        clickOn(dialogPane.lookupButton(ButtonType.OK));
    }

    @Test
    public void testInvalidEmail() {
        clickOn("#firstNameField").write("John");
        clickOn("#lastNameField").write("Doe");
        clickOn("#emailField").write("invalid-email");
        clickOn("#addEmployeeButton");

        // Query the alert dialog
        DialogPane dialogPane = lookup(".dialog-pane").query();
        assertNotNull(dialogPane, "An error alert should be displayed");
        assertEquals("Please enter a valid email address", dialogPane.getHeaderText());

        // Close the alert
        clickOn(dialogPane.lookupButton(ButtonType.OK));
    }

    @Test
    public void testNegativeSalary() {
        clickOn("#firstNameField").write("John");
        clickOn("#lastNameField").write("Doe");
        clickOn("#emailField").write("john.doe@example.com");
        clickOn("#salaryField").write("-5000");
        clickOn("#addEmployeeButton");

        // Query the alert dialog
        DialogPane dialogPane = lookup(".dialog-pane").query();
        assertNotNull(dialogPane, "An error alert should be displayed");
        assertEquals("Please enter a valid salary", dialogPane.getHeaderText());

        // Close the alert
        clickOn(dialogPane.lookupButton(ButtonType.OK));
    }

    @Test
    public void testAddEmployeeSuccess() {
        clickOn("#firstNameField").write("John");
        clickOn("#lastNameField").write("Doe");
        clickOn("#birthdayPicker").write("01/01/1990");
        clickOn("#phoneField").write("1234567890");
        clickOn("#emailField").write("john.doe@example.com");
        clickOn("#passwordField").write("password123");
        clickOn("#roleComboBox").clickOn("Librarian");
        clickOn("#salaryField").write("5000");
        clickOn("#canAddBookCheckBox");
        clickOn("#canAddBillCheckBox");
        clickOn("#addEmployeeButton");

        // Query the success alert
        DialogPane dialogPane = lookup(".dialog-pane").query();
        assertNotNull(dialogPane, "A success alert should be displayed");
        assertEquals("Done", dialogPane.getHeaderText());

        // Close the alert
        clickOn(dialogPane.lookupButton(ButtonType.OK));
    }
}
