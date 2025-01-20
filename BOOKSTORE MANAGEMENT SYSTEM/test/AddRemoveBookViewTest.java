//package com.example.bookstore.test;
//
//import com.example.bookstore.view.AddRemoveBook;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.DialogPane;
//import javafx.scene.control.Label;
//import javafx.scene.input.KeyCode;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testfx.api.FxToolkit;
//import org.testfx.framework.junit5.ApplicationTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class AddRemoveBookViewTest extends ApplicationTest {
//
//    private AddRemoveBook addRemoveBook;
//
//    @BeforeEach
//    public void setup() throws Exception {
//        FxToolkit.registerPrimaryStage();
//        FxToolkit.setupApplication(() -> {
//            Stage stage = new Stage();
//            addRemoveBook = new AddRemoveBook();
//            stage.setScene(addRemoveBook.execute(stage));
//            stage.show();
//            return null;
//        });
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        FxToolkit.cleanupStages();
//    }
//
//    @Test
//    public void testAddAuthorSuccessfully() {
//        // Click the Add Author button
//        clickOn("+");
//
//        // Enter valid author name
//        write("Jane Austen");
//        press(KeyCode.ENTER).release(KeyCode.ENTER);
//
//        // Verify the author is added to the ComboBox
//        ComboBox<String> comboBoxAuthor = lookup(".combo-box").queryAs(ComboBox.class);
//        assertEquals("Jane Austen", comboBoxAuthor.getItems().get(comboBoxAuthor.getItems().size() - 1));
//    }
//
//    @Test
//    public void testAddCategorySuccessfully() {
//        // Click the Add Category button
//        clickOn("+");
//
//        // Enter valid category name
//        write("Fiction");
//        press(KeyCode.ENTER).release(KeyCode.ENTER);
//
//        // Verify the category is added to the ComboBox
//        ComboBox<String> comboBoxCategory = lookup(".combo-box").queryAs(ComboBox.class);
//        assertEquals("Fiction", comboBoxCategory.getItems().get(comboBoxCategory.getItems().size() - 1));
//    }
//
//    @Test
//    public void testAddBookWithValidData() {
//        // Fill in book details
//        clickOn("ISBN").write("978-3-16-148410-0");
//        clickOn("Title").write("Pride and Prejudice");
//        clickOn("Category").clickOn("Fiction");
//        clickOn("Supplier").write("Penguin Books");
//        clickOn("Purchased Date").write("2025-01-01");
//        clickOn("Purchased Price").write("15.99");
//        clickOn("Original Price").write("20.99");
//        clickOn("Selling Price").write("25.99");
//        clickOn("Author").clickOn("Jane Austen");
//        clickOn("Stock").type(KeyCode.DIGIT5);
//
//        // Click Add Book button
//        clickOn("Add Book");
//
//        // Verify book added confirmation dialog
//        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
//
//        String confirmationMessage = dialogPane.getChildren().stream()
//                .filter(node -> node instanceof Label)
//                .map(Label.class::cast)
//                .findFirst()
//                .map(Label::getText)
//                .orElse("");
//
//        assertEquals("The book is ADDED", confirmationMessage);
//
//        // Close dialog
//        clickOn("OK");
//    }
//
//    @Test
//    public void testAddBookWithInvalidData() {
//        // Leave some fields empty and click Add Book button
//        clickOn("ISBN").write("");
//        clickOn("Title").write("");
//        clickOn("Add Book");
//
//        // Verify error alert is displayed
//        DialogPane dialogPane = lookup(".dialog-pane").queryAs(DialogPane.class);
//        String errorMessage = dialogPane.getChildren().stream()
//                .filter(node -> node instanceof Label)
//                .map(Label.class::cast)
//                .findFirst()
//                .map(Label::getText)
//                .orElse("");
//
//        assertEquals("Enter valid data", errorMessage);
//
//        // Close dialog
//        clickOn("OK");
//    }
//}
