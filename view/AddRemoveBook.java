package com.example.bookstore.view;

import com.example.bookstore.controller.AuthorController;
import com.example.bookstore.controller.BookController;
import com.example.bookstore.controller.CategoryController;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


public class AddRemoveBook implements com.example.bookstore.view.ShowView {
    AuthorController authorController = new AuthorController();
    private ObservableList<String> authorItems = FXCollections.observableArrayList();
    public Scene execute(Stage stage) {
        stage.setTitle("Add Book");

        Label isbn = new Label("ISBN");
        isbn.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField isbnField = new TextField();
        isbnField.setStyle("-fx-font-size:14");

        Label title = new Label("Title");
        title.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField titleField = new TextField();
        titleField.setStyle("-fx-font-size:14");

        Label category = new Label("Category");
        category.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        HBox hboxCateory = new HBox(10);
        ComboBox<String> comboBoxCategory = new ComboBox<>();
        comboBoxCategory.setStyle("-fx-font-size:14");
        CategoryController categoryController1 = new CategoryController();
        ObservableList<String> categoryItems= comboBoxCategory.getItems();
        updateComboBoxCategory(categoryItems, categoryController1.readCategories());
        StackPane stackpaneCategory = new StackPane();
        stackpaneCategory.getChildren().add(comboBoxCategory);


        Button buttonAddCategory = new Button("+");
        buttonAddCategory.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        hboxCateory.getChildren().addAll(stackpaneCategory, buttonAddCategory);

        Label supplier = new Label("Supplier");
        supplier.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField supplierField = new TextField();
        supplierField.setStyle("-fx-font-size:14");

        Label date = new Label("Purchased Date");
        date.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        DatePicker dateField = new DatePicker();
        dateField.setStyle("-fx-font-size:14");

        Label pPrice = new Label("Purchased Price");
        pPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField pPriceField = new TextField();
        pPriceField.setStyle("-fx-font-size:14");

        Label oPrice = new Label("Original Price");
        oPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField oPriceField = new TextField();
        oPriceField.setStyle("-fx-font-size:14");

        Label sPrice = new Label("Selling Price");
        sPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField sPriceField = new TextField();
        sPriceField.setStyle("-fx-font-size:14");

        Label authorLabel = new Label("Author");
        authorLabel.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        HBox hboxAuthor = new HBox(10);
        ComboBox<String> comboBoxAuthor = new ComboBox<>();
        comboBoxAuthor.setStyle("-fx-font-size:14");
        AuthorController authorController1 = new AuthorController();
        ObservableList<String> authorItems = comboBoxAuthor.getItems();
        updateComboBoxAuthors(authorItems, authorController1.readAuthors());
        StackPane stackpaneAuthor = new StackPane();
        stackpaneAuthor.getChildren().add(comboBoxAuthor);


        Button buttonAddAuthor = new Button("+");
        buttonAddAuthor.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        hboxAuthor.getChildren().addAll(stackpaneAuthor, buttonAddAuthor);

        Label stock = new Label("Stock");
        stock.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1);
        quantitySpinner.setStyle("-fx-font-size:14");
        VBox root = new VBox(10); // 10 is the spacing between elements
        root.getChildren().add(quantitySpinner);

        Button buttonToAddBooks = new Button("Add Book");
        buttonToAddBooks.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color:antiquewhite");
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(isbn, 0, 0);
        grid.add(isbnField, 1, 0);
        grid.add(title, 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(authorLabel, 0, 2);
        grid.add(hboxAuthor, 1, 2);
        grid.add(category, 0, 3);
        grid.add(hboxCateory, 1, 3);
        grid.add(supplier, 0, 4);
        grid.add(supplierField, 1, 4);
        grid.add(date, 0, 5);
        grid.add(dateField, 1, 5);
        grid.add(pPrice, 0, 6);
        grid.add(pPriceField, 1, 6);
        grid.add(oPrice, 0, 7);
        grid.add(oPriceField, 1, 7);
        grid.add(sPrice, 0,8);
        grid.add(sPriceField, 1, 8);
        grid.add(stock, 0, 9);
        grid.add(root, 1, 9);
        grid.add(buttonToAddBooks, 1, 10);

        //*************************************************************************************ADDING AUTHORS


        buttonAddAuthor.setOnAction(e -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Add Author");
            textInputDialog.getDialogPane().setContentText("Full Name: ");
            Optional<String> result = textInputDialog.showAndWait();
            TextField input = textInputDialog.getEditor();
            String authornames = input.getText();
            if(result.isEmpty()){
                return;
            }
            if (authornames.matches("^[a-zA-Z]+(?:[\\s]+[a-zA-Z]+)*$")) {
                boolean isRegistered = authorController.verify(authornames);
                authorController.printAuthorsToConsole();
                if (!isRegistered) {
                    Alert al = new Alert(AlertType.ERROR);
                    al.setHeaderText("The author is ALREADY added");
                    al.showAndWait();
                } else {
                    Alert al1 = new Alert(AlertType.INFORMATION);
                    al1.setHeaderText("The author is ADDED");
                    updateComboBoxAuthors(authorItems, authorController.readAuthors());
                    al1.showAndWait();
                }
            }
            else{
                Alert al2 = new Alert(AlertType.ERROR);
                al2.setHeaderText("Please enter a valid input ");
                al2.showAndWait();

            }
        });
        //**********************************************************************************ADDING CATEGORIES
        CategoryController categoryController = new CategoryController();

        buttonAddCategory.setOnAction(e -> {
            TextInputDialog textInputDialogCategory = new TextInputDialog();
            textInputDialogCategory.setTitle("Add Category");
            textInputDialogCategory.getDialogPane().setContentText("Category Name: ");
            Optional<String> result1 = textInputDialogCategory.showAndWait();
            if(result1.isEmpty()){
                return;
            }
            TextField input = textInputDialogCategory.getEditor();
            String categoryNames = input.getText();

            if (categoryNames.matches("^[a-zA-Z]+(?:[\\s]+[a-zA-Z]+)*$")) {
                boolean isWritten = categoryController.verify1(categoryNames);

                categoryController.printCategoriesToConsole();
                if (categoryNames != null && !isWritten) {
                    Alert alCategory = new Alert(AlertType.ERROR);
                    alCategory.setHeaderText("The category is ALREADY added");

                    alCategory.showAndWait();
                } else {
                    Alert alCategory1 = new Alert(AlertType.INFORMATION);
                    updateComboBoxCategory(categoryItems, categoryController.readCategories());
                    alCategory1.showAndWait();
                }
            }
            else{
                Alert al4 = new Alert(AlertType.ERROR);
                al4.setHeaderText("Please enter a valid input ");
                al4.showAndWait();

            }
        });

        //**********************************************************************************ADDING BOOKS
        BookController bookController = new BookController();

            buttonToAddBooks.setOnAction(e->{
                String isbnInput = isbnField.getText();
                String titleInput = titleField.getText();
                String supplierInput = supplierField.getText();
                LocalDate pDateInput = dateField.getValue();
                Date pdateInputSelected = java.sql.Date.valueOf(pDateInput);
                String pPriceInput = pPriceField.getText();
                String oPriceInput = oPriceField.getText();
                String sPriceInput = sPriceField.getText();
                String authorSelected = comboBoxAuthor.getValue();
                String categorySelected = comboBoxCategory.getValue();
                int quantitySelected = quantitySpinner.getValue();

                // Validate inputs
                if (isbnInput.isEmpty() || titleInput.isEmpty() || pPriceInput.isEmpty() || oPriceInput.isEmpty() || sPriceInput.isEmpty() ||
                        !isNumeric(pPriceInput) || !isNumeric(oPriceInput) || !isNumeric(sPriceInput) || quantitySelected < 0) {
                    Alert alCategory = new Alert(AlertType.ERROR);
                    alCategory.setHeaderText("Enter valid data");
                    alCategory.showAndWait();
                    return;
                }

                double pPriceInputDouble = Double.parseDouble(pPriceInput);
                double oPriceInputDouble = Double.parseDouble(oPriceInput);
                double sPriceInputDouble = Double.parseDouble(sPriceInput);

                // Validate ISBN uniqueness
                boolean isISBNExists = bookController.isISBNExists(isbnInput);
                if (isISBNExists) {
                    Alert alCategory = new Alert(AlertType.ERROR);
                    alCategory.setHeaderText("The book with the given ISBN already exists");
                    alCategory.showAndWait();
                    return;
                }

            boolean verifyBook = bookController.verifyBook(isbnInput, titleInput, authorSelected,categorySelected, supplierInput, pdateInputSelected, pPriceInputDouble, oPriceInputDouble, sPriceInputDouble, quantitySelected);
            bookController.printBooksToConsole();
            if (!verifyBook) {
                Alert alCategory = new Alert(AlertType.ERROR);
                alCategory.setHeaderText("The book is ALREADY added");
                alCategory.showAndWait();
            }
            else {
                Alert alCategory1 = new Alert(AlertType.INFORMATION);
                alCategory1.setHeaderText("The book is ADDED");
                bookController.addBook(isbnInput, titleInput, authorSelected,categorySelected, supplierInput, pdateInputSelected, pPriceInputDouble, oPriceInputDouble, sPriceInputDouble, quantitySelected);
                alCategory1.showAndWait();
            }

        });
        return new Scene(grid);
    }
    private void updateComboBoxAuthors(ObservableList<String> items, ArrayList<Author> authors) {
        items.clear();
        for (Author author : authors) {
            items.add(author.getFullName());
        }
    }
    private void updateComboBoxCategory(ObservableList<String> items, ArrayList<Category> categories) {
        items.clear();
        for (Category category: categories) {
            items.add(category.getCategoryName());
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d*\\.?\\d+") && Double.parseDouble(str) >= 0;
    }
}
