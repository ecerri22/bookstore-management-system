package com.example.bookstore.view;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.controller.ManagerController;
import com.example.bookstore.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class AllBooksView implements ShowView {
    private ManagerController managerController;
    private AdminController adminController;
    TableView<Book> allBooksTable = new TableView<>();

    public AllBooksView(ManagerController managerController, AdminController adminController) {
        this.managerController = managerController;
        this.adminController = adminController;
    }

    public void editSelectedCell(TablePosition<Book, ?> position, TableView<Book> allBooksTable) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Cell");
        dialog.setHeaderText(null);

        Book selectedBook = allBooksTable.getSelectionModel().getSelectedItem();



        if (selectedBook != null) {
            int rowIndex = position.getRow();
            String columnHeader = position.getTableColumn().getText();

            switch (columnHeader) {
                case "ISBN":
                    dialog.setContentText("Enter new ISBN:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifyISBN(selectedBook, newValue);
                    });
                    break;
                case "Title":
                    dialog.setContentText("Enter new title:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifyTitle(selectedBook, newValue);
                    });
                    break;
                case "Author":
                    dialog.setContentText("Enter new author:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifyAuthor(selectedBook, newValue);
                    });
                    break;
                case "Purchased Date":
                    DatePicker datePicker = new DatePicker();
                    dialog.getDialogPane().setContent(datePicker);
                    dialog.setTitle("Edit Purchased date");

                    dialog.showAndWait().ifPresent(result -> {
                        LocalDate newDate = datePicker.getValue();
                        if (newDate != null) {
                            Date newValue = java.sql.Date.valueOf(newDate);
                            managerController.modifyPurchasedDate(selectedBook, newValue);
                        }
                    });
                    break;
                case "Supplier":
                    dialog.setContentText("Enter new supplier:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifySupplier(selectedBook, newValue);
                    });
                    break;
                case "Category":
                    dialog.setContentText("Enter new category:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifyCategory(selectedBook, newValue);
                    });
                    break;
                case "Purchased Price":
                    dialog.setContentText("Enter new price:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifyPurchasedPrice(selectedBook, Double.parseDouble(newValue));
                    });
                    break;
                case "Original Price":
                    dialog.setContentText("Enter new price:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifyOriginalPrice(selectedBook, Double.parseDouble(newValue));
                    });
                    break;
                case "Selling Price":
                    dialog.setContentText("Enter new price:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifySellingPrice(selectedBook, Double.parseDouble(newValue));
                    });
                    break;
                case "Stock":
                    dialog.setContentText("Change stock:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        managerController.modifyStock(selectedBook, Integer.parseInt(newValue));
                    });
                    break;
            }
        }
        allBooksTable.refresh();
    }

    @Override
    public Scene execute(Stage primaryStage) {
        TableView<Book> allBooksTable = new TableView<>();
        allBooksTable.setEditable(true);

        ObservableList<Book> books = FXCollections.observableArrayList(managerController.getAllBooks());
        allBooksTable.setItems(books);

        TableColumn<Book, String> isbn = new TableColumn<>("ISBN");
        isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<Book, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> author = new TableColumn<>("Author");
        author.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, LocalDate> category = new TableColumn<>("Category");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, String> supplier = new TableColumn<>("Supplier");
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Book, LocalDate> purchasedDate = new TableColumn<>("Purchased Date");
        purchasedDate.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));

        TableColumn<Book, Double> purchasedPrice = new TableColumn<>("Purchased Price");
        purchasedPrice.setCellValueFactory(new PropertyValueFactory<>("purchasedPrice"));

        TableColumn<Book, Double> originalPrice = new TableColumn<>("Original Price");
        originalPrice.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));

        TableColumn<Book, Double> sellingPrice = new TableColumn<>("Selling Price");
        sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        TableColumn<Book, Integer> stock = new TableColumn<>("Stock");
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        allBooksTable.getColumns().addAll(isbn, title, author, category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);
        allBooksTable.setStyle("-fx-background-color:transparent; -fx-font-size: 13; -fx-font-family:'Verdana';");
        VBox vbox = new VBox();
        vbox.getChildren().add(allBooksTable);
        vbox.setPadding(new Insets(50,20,20,20));
        vbox.setStyle("-fx-background-color:antiquewhite");

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 13;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        editButton.setMinWidth(70);
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 13;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        deleteButton.setMinWidth(70);

        deleteButton.setOnAction(e -> {
            Book selectedBook = allBooksTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                managerController.isDeleted(selectedBook);
                books.remove(selectedBook);
            }
        });

        editButton.setOnAction(e -> {
            TablePosition<Book, ?> position = allBooksTable.getSelectionModel().getSelectedCells().get(0);
            editSelectedCell(position, allBooksTable);
        });

        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(40,0,0,0));
        buttonBox.setSpacing(30);
        buttonBox.getChildren().addAll(editButton, deleteButton);
        vbox.getChildren().add(buttonBox);

        return new Scene(vbox);
    }
}