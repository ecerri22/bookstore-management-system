package com.example.bookstore.view;

import com.example.bookstore.controller.ManagerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class AddBookView implements ShowView {
    @Override
    public Scene execute(Stage primaryStage) {
        ///////////////////////////////STYLING/////////////////////
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color:antiquewhite");
        grid.setPadding(new Insets(60));
        grid.setVgap(13);
        grid.setHgap(13);

        Label isbn = new Label("ISBN: ");
        isbn.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField isbnF = new TextField();
        isbnF.setStyle("-fx-font-size:14");
        grid.add(isbn, 0, 0);
        grid.add(isbnF, 1, 0);

        Label bookTitle = new Label("Title: ");
        bookTitle.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField bookTitleF = new TextField();
        bookTitleF.setStyle("-fx-font-size:14");
        grid.add(bookTitle, 0, 1);
        grid.add(bookTitleF, 1, 1);

        Label authorName = new Label("Author: ");
        authorName.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField authorNameF = new TextField();
        authorNameF.setStyle("-fx-font-size:14");
        grid.add(authorName, 0, 2);
        grid.add(authorNameF, 1, 2);

        Label category = new Label("Category: ");
        category.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        ComboBox categoryF = new ComboBox<String>();
        categoryF.setStyle("-fx-font-size:14");
        categoryF.getItems().addAll("Mystery", "Fantasy", "Thriller");
        grid.add(category, 0, 3);
        grid.add(categoryF, 1, 3);

        Label supplier = new Label("Supplier ");
        supplier.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField supplierF = new TextField();
        supplierF.setStyle("-fx-font-size:14");
        grid.add(supplier, 0, 4);
        grid.add(supplierF, 1, 4);

        Label purchasedDate = new Label("Purchased Date ");
        purchasedDate.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        DatePicker purchasedDateF = new DatePicker();
        purchasedDateF.setStyle("-fx-font-size:14");
        grid.add(purchasedDate, 0, 5);
        grid.add(purchasedDateF, 1, 5);

        Label purchasedPrice = new Label("Purchased Price ");
        purchasedPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        Spinner<Integer> purchasedPriceF = new Spinner<>(0, 100, 0, 1);
        purchasedPriceF.setStyle("-fx-font-size:14");
        grid.add(purchasedPrice, 0, 6);
        grid.add(purchasedPriceF, 1, 6);

        Label originalPrice = new Label("Original Price ");
        originalPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        Spinner<Integer> originalPriceF = new Spinner<>(0, 100, 0, 1);
        originalPriceF.setStyle("-fx-font-size:14");
        grid.add(originalPrice, 0, 7);
        grid.add(originalPriceF, 1, 7);

        Label sellingPrice = new Label("Selling Price ");
        sellingPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        Spinner<Integer> sellingPriceF = new Spinner<>(0, 100, 0, 1);
        sellingPriceF.setStyle("-fx-font-size:14");
        grid.add(sellingPrice, 0, 8);
        grid.add(sellingPriceF, 1, 8);

        Label stock = new Label("Stock ");
        stock.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        Spinner<Integer> stockF = new Spinner<>(0, 100, 0, 1);
        stockF.setStyle("-fx-font-size:14");
        grid.add(stock, 0, 9);
        grid.add(stockF, 1, 9);


        Button addBookBtn = new Button("ADD BOOK");
        addBookBtn.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        HBox hBox = new HBox(addBookBtn);
        hBox.setPadding(new Insets(40,0,0,0));
        grid.add(hBox, 1, 10);

        ///////////////////////////////FUNCTION/////////////////////

        ManagerController mc = new ManagerController();

        addBookBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String isbnVal = isbnF.getText();
                String titleVal = bookTitleF.getText();
                String authorVal = authorNameF.getText();
                String categoryVal = (String) categoryF.getValue();
                String supplierVal = supplierF.getText();
                LocalDate purchasedDateValInLocal = purchasedDateF.getValue();
                Date purchasedDateVal = java.sql.Date.valueOf(purchasedDateValInLocal.atStartOfDay().toLocalDate());
                double purchasedPriceVal = purchasedPriceF.getValue();
                double originalPriceVal = originalPriceF.getValue();
                double sellingPriceVal = sellingPriceF.getValue();
                int stockVal = stockF.getValue();

                boolean BookIsAdded = mc.isAdded(isbnVal,
                                                titleVal,
                                                authorVal,
                                                categoryVal,
                                                supplierVal,
                                                purchasedDateVal,
                                                purchasedPriceVal,
                                                originalPriceVal,
                                                sellingPriceVal,
                                                stockVal);

                if(!BookIsAdded){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("There was an error adding the book");
                    errorAlert.setContentText("Something went wrong");
                    errorAlert.show();
                } else {
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setHeaderText("Done");
                    success.showAndWait();
                    success.close();
                }
            }
        });

        return new Scene(grid);
    }
}
