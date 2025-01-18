package com.example.bookstore.view;

import com.example.bookstore.controller.BillController;
import com.example.bookstore.controller.TransactionController;
import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SellBook implements ShowView {
    private ArrayList<Book> allBooks = new ArrayList<>();
    public ArrayList<Transaction> transactions = new ArrayList<>();
    private User user;
    public ArrayList<Bill> bills = new ArrayList<>();
    private final File file = new File("allBooks.bin");

    public SellBook(User user) {
        this.user = user;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
        if (file.exists()) {
            readAllBooks();
        }
    }

    private void readAllBooks() {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.allBooks = (ArrayList<Book>) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Book> getAllBooks() {
        return this.allBooks;
    }

    public Scene execute(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(60));
        grid.setVgap(17.0);
        grid.setHgap(17.0);

        // Book title
        Label book_title = new Label("Enter the title of the book : ");
        book_title.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField book_titleF = new TextField();
        book_titleF.setStyle("-fx-font-size:14");
        book_titleF.setId("book_titleF"); // Set ID for TestFX
        grid.add(book_title, 0, 0);
        grid.add(book_titleF, 1, 0);

        // Author name
        Label author_name = new Label("Enter the name of the author : ");
        author_name.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField author_nameF = new TextField();
        author_nameF.setStyle("-fx-font-size:14");
        author_nameF.setId("author_nameF"); // Set ID for TestFX
        grid.add(author_name, 0, 1);
        grid.add(author_nameF, 1, 1);

        // ISBN
        Label isbn = new Label("ISBN : ");
        isbn.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField isbnF = new TextField();
        isbnF.setStyle("-fx-font-size:14");
        isbnF.setDisable(true);
        isbnF.setId("isbnF"); // Set ID for TestFX
        grid.add(isbn, 0, 2);
        grid.add(isbnF, 1, 2);

        // Price
        Label price = new Label("PRICE : ");
        price.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField priceF = new TextField();
        priceF.setStyle("-fx-font-size:14");
        priceF.setDisable(true);
        priceF.setId("priceF"); // Set ID for TestFX
        grid.add(price, 0, 3);
        grid.add(priceF, 1, 3);

        // Quantity
        Label qty = new Label("QUANTITY : ");
        qty.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        Spinner<Integer> qtyF = new Spinner<>(0, 100, 0, 1);
        qtyF.setStyle("-fx-font-size:14");
        qtyF.setId("qtyF"); // Set ID for TestFX
        grid.add(qty, 0, 4);
        grid.add(qtyF, 1, 4);

        // Total Price
        Label totalPrice = new Label("TOTAL PRICE : ");
        totalPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField totalPriceF = new TextField();
        totalPriceF.setStyle("-fx-font-size:14");
        totalPriceF.setDisable(true);
        totalPriceF.setId("totalPriceF"); // Set ID for TestFX
        grid.add(totalPrice, 0, 5);
        grid.add(totalPriceF, 1, 5);

        // Add Book Button
        Button addBookBtn = new Button("ADD BOOK");
        addBookBtn.setId("addBookBtn"); // Set ID for TestFX
        addBookBtn.setStyle("-fx-background-color: whitesmoke; -fx-font-weight: bold;");

        addBookBtn.setOnAction(e -> {
            String bookTitle = book_titleF.getText().toLowerCase();
            String authorName = author_nameF.getText().toLowerCase();
            int quantity = qtyF.getValue();

            boolean bookFound = false;
            for (Book b : this.allBooks) {
                if (b.getTitle().toLowerCase().equals(bookTitle) && b.getAuthor().toLowerCase().equals(authorName) && quantity <= b.getStock()) {
                    bookFound = true;
                    b.setStock(b.getStock() - quantity);
                    isbnF.setText(b.getISBN());
                    priceF.setText(String.valueOf(b.getSellingPrice()));
                    totalPriceF.setText(String.valueOf(b.getSellingPrice() * quantity));

                    Transaction transaction = new Transaction(b, quantity);
                    this.transactions.add(transaction);
                    new TransactionController().addInAllTransactions(transaction);
                }
            }

            if (!bookFound) {
                Alert alert = new Alert(AlertType.ERROR, "Book NOT found. Enter the correct title and author.");
                alert.showAndWait();
            }
        });

        // Create Bill Button
        Button createBillBtn = new Button("CREATE BILL");
        createBillBtn.setId("createBillBtn"); // Set ID for TestFX
        createBillBtn.setStyle("-fx-background-color: darkorange; -fx-font-weight: bold;");

        createBillBtn.setOnAction(e -> {
            Bill bill = new Bill(transactions, user);
            this.bills.add(bill);
            new BillController().addInAllBills(bill);
            Alert alert = new Alert(AlertType.INFORMATION, "Bill created! Total: " + bill.getTotalAmount());
            alert.showAndWait();
        });

        // Layout for buttons
        HBox buttonBox = new HBox(20, addBookBtn, createBillBtn);
        grid.add(buttonBox, 1, 7);

        return new Scene(grid, 600, 400);
    }
}
