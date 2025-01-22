import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.StatsView;

import static org.junit.jupiter.api.Assertions.*;

public class StatsViewTest extends ApplicationTest {

    private StatsView statsView;

    @Override
    public void start(Stage stage) {
        statsView = new StatsView();
        stage.setScene(statsView.execute(stage));
        stage.show();
    }

    @Test
    public void testCalculateSalaries() {
        clickOn("Submit");
        assertNotNull(lookup("Total Salary:"), "Total salary should be calculated");
    }

    @Test
    public void testFilterByDate() {
        clickOn("#startDatePicker").write("01/01/2023");
        clickOn("#endDatePicker").write("12/31/2023");
        clickOn("Submit");

        assertTrue(lookup("Total Purchases").tryQuery().isPresent(), "Filtered purchases should display");
    }
}
