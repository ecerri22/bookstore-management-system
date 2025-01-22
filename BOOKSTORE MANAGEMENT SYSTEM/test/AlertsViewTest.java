import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.Alerts;

import static org.junit.jupiter.api.Assertions.*;

public class AlertsViewTest extends ApplicationTest {

    private Alerts alerts;

    @Override
    public void start(Stage stage) {
        // Initialize Alerts View
        alerts = new Alerts();
        stage.setScene(alerts.execute(stage));
        stage.show();
    }

    @Test
    public void testBooksDataFilterExists() {
        // Ensure combo box for filters exists
        ComboBox<String> filtersComboBox = lookup(".combo-box").query();
        assertNotNull(filtersComboBox, "Filters ComboBox should exist");

        // Verify filter options
        assertEquals(3, filtersComboBox.getItems().size(), "Filters ComboBox should have 3 options");
        assertTrue(filtersComboBox.getItems().contains("Today"), "'Today' should be an option");
        assertTrue(filtersComboBox.getItems().contains("Total"), "'Total' should be an option");
        assertTrue(filtersComboBox.getItems().contains("Monthly"), "'Monthly' should be an option");
    }

    @Test
    public void testBooksDataFilterByTotal() {
        // Select 'Total' filter
        ComboBox<String> filtersComboBox = lookup(".combo-box").query();
        clickOn(filtersComboBox);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Click 'Display' button
        clickOn("Display");

        // Verify the displayed text for total books sold
        assertTrue(lookup(".text").queryText().getText().contains("Total Nr of Books Sold"),
                "Text should display total number of books sold");
    }

    @Test
    public void testBooksDataFilterByMonthly() {
        // Select 'Monthly' filter
        ComboBox<String> filtersComboBox = lookup(".combo-box").query();
        clickOn(filtersComboBox);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Set start and end dates
        DatePicker startDatePicker = lookup(".date-picker").nth(0).query();
        DatePicker endDatePicker = lookup(".date-picker").nth(1).query();

        clickOn(startDatePicker);
        write("01/01/2023");
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn(endDatePicker);
        write("01/31/2023");
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        // Click 'Display' button
        clickOn("Display");

        // Verify the displayed text for monthly books sold
        assertTrue(lookup(".text").queryText().getText().contains("Total Nr of Books Sold Monthly"),
                "Text should display total number of books sold monthly");
    }

    @Test
    public void testLibrarianDataExists() {
        // Ensure combo box for librarian exists
        ComboBox<String> librarianComboBox = lookup(".combo-box").nth(1).query();
        assertNotNull(librarianComboBox, "Librarian ComboBox should exist");

        // Check that the list contains valid librarian names
        assertTrue(librarianComboBox.getItems().size() > 0, "Librarian ComboBox should not be empty");
    }

    @Test
    public void testLibrarianDataSelection() {
        // Select a librarian from the combo box
        ComboBox<String> librarianComboBox = lookup(".combo-box").nth(1).query();
        clickOn(librarianComboBox);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Select 'Total' data type
        ComboBox<String> datePickComboBox = lookup(".combo-box").nth(2).query();
        clickOn(datePickComboBox);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Click 'Select' button
        clickOn("Select");

        // Verify that librarian data is displayed
        assertTrue(lookup(".text").nth(0).queryText().getText().contains("Total Nr of Books Sold"),
                "Text should display total number of books sold by the librarian");
        assertTrue(lookup(".text").nth(1).queryText().getText().contains("Total Nr of Bills"),
                "Text should display total number of bills by the librarian");
        assertTrue(lookup(".text").nth(2).queryText().getText().contains("Total Amount of Money made"),
                "Text should display total amount of money made by the librarian");
    }

    @Test
    public void testLibrarianMonthlyData() {
        // Select a librarian
        ComboBox<String> librarianComboBox = lookup(".combo-box").nth(1).query();
        clickOn(librarianComboBox);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Select 'Monthly' data type
        ComboBox<String> datePickComboBox = lookup(".combo-box").nth(2).query();
        clickOn(datePickComboBox);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        // Set start and end dates
        DatePicker startDatePicker = lookup(".date-picker").nth(2).query();
        DatePicker endDatePicker = lookup(".date-picker").nth(3).query();

        clickOn(startDatePicker);
        write("01/01/2023");
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn(endDatePicker);
        write("01/31/2023");
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        // Click 'Select' button
        clickOn("Select");

        // Verify monthly data for the librarian
        assertTrue(lookup(".text").nth(0).queryText().getText().contains("Nr of Books Sold Monthly"),
                "Text should display the number of books sold monthly by the librarian");
        assertTrue(lookup(".text").nth(1).queryText().getText().contains("Nr of Bills per Month"),
                "Text should display the number of bills per month by the librarian");
        assertTrue(lookup(".text").nth(2).queryText().getText().contains("Amount of Money made per Month"),
                "Text should display the amount of money made per month by the librarian");
    }
}
