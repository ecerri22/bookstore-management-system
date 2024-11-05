package com.example.bookstore.view;

import com.example.bookstore.controller.BillController;
import com.example.bookstore.controller.TransactionController;
import com.example.bookstore.model.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class SellBook implements ShowView {
    private ArrayList<Book> allBooks = new ArrayList();
    private ArrayList<Transaction> transactions = new ArrayList();
    private User user;
    private ArrayList<Bill> bills = new ArrayList();
    private File file = new File("allBooks.bin");

    public SellBook(User user) {
        this.user=user;
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException var2) {
                System.out.println("Error creating file: " + var2.getMessage());
            }
        }

        if (this.file.exists()) {
            this.readAllBooks();
        }

    }

    private void readAllBooks() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.allBooks = (ArrayList)ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
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
        Label book_title = new Label("Enter the title of the book : ");
        book_title.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField book_titleF = new TextField();
        book_titleF.setStyle("-fx-font-size:14");
        grid.add(book_title, 0, 0);
        grid.add(book_titleF, 1, 0);
        Label author_name = new Label("Enter the name of the author : ");
        author_name.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField author_nameF = new TextField();
        author_nameF.setStyle("-fx-font-size:14");
        grid.add(author_name, 0, 1);
        grid.add(author_nameF, 1, 1);
        Label isbn = new Label("ISBN : ");
        isbn.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField isbnF = new TextField();
        isbnF.setStyle("-fx-font-size:14");
        isbnF.setDisable(true);
        grid.add(isbn, 0, 2);
        grid.add(isbnF, 1, 2);
        Label price = new Label("PRICE : ");
        price.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField priceF = new TextField();
        priceF.setStyle("-fx-font-size:14");
        priceF.setDisable(true);
        grid.add(price, 0, 3);
        grid.add(priceF, 1, 3);
        Label qty = new Label("QUANTITY : ");
        qty.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        Spinner<Integer> qtyF = new Spinner(0, 100, 0, 1);
        qtyF.setStyle("-fx-font-size:14");
        grid.add(qty, 0, 4);
        grid.add(qtyF, 1, 4);
        Label totalPrice = new Label("TOTAL PRICE : ");
        totalPrice.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField totalPriceF = new TextField();
        totalPriceF.setStyle("-fx-font-size:14");
        totalPriceF.setDisable(true);
        grid.add(totalPrice, 0, 5);
        grid.add(totalPriceF, 1, 5);
        Button addBookBtn = new Button("ADD BOOK");
        addBookBtn.setStyle("-fx-background-color: whitesmoke; -fx-text-fill: black; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,DARKORANGE,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");

        addBookBtn.setOnAction((e) -> {
            try {
                System.out.println("Button clicked!");
                String bookTitle = book_titleF.getText().toLowerCase();
                String authorName = author_nameF.getText().toLowerCase();
                int quantity = (Integer)qtyF.getValue();
                boolean bookFound = false;
                this.allBooks = this.getAllBooks();
                Iterator iterator = this.allBooks.iterator();

                while(iterator.hasNext()) {
                    Book b = (Book)iterator.next();
                    if (b.getTitle().toLowerCase().equals(bookTitle) && b.getAuthor().toLowerCase().equals(authorName) && quantity <= b.getStock()) {
                        bookFound = true;
                        b.setStock(b.getStock() - quantity);
                        isbnF.setDisable(false);
                        priceF.setDisable(false);
                        totalPriceF.setDisable(false);
                        isbnF.setText(b.getISBN());
                        isbnF.setEditable(false);
                        priceF.setText(String.valueOf(b.getSellingPrice()));
                        priceF.setEditable(false);
                        totalPriceF.setText(String.valueOf(b.getSellingPrice() * (double) quantity));
                        totalPriceF.setEditable(false);

                        Transaction t = new Transaction(b, quantity);
                        this.transactions.add(t);
                        TransactionController tc = new TransactionController();
                        tc.addInAllTransactions(t);
                        tc.writeAllTransactions();

                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(allBooks);
                            oos.close();
                            fos.close();

                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }

                        if (b.getStock() == 0) {
                            Alert al = new Alert(AlertType.INFORMATION);
                            al.setHeaderText("This book is out of stock");
                            al.showAndWait();
                        }
                    }
                }

                if (!bookFound) {
                    Alert al = new Alert(AlertType.ERROR);
                    al.setHeaderText("Book NOT found. Enter the correct title and name of author");
                    al.showAndWait();                }
            } catch (Exception var16) {
                var16.printStackTrace();
            }

        });
        Button createBill = new Button("CREATE BILL");
        createBill.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        createBill.setOnAction((e) -> {
            Bill bill = new Bill(this.transactions,  this.user);
            bill.print();
            this.bills.add(bill);
            BillController bc = new BillController();
            bc.addInAllBills(bill);
            bc.writeAllBills();
            Alert al=new Alert(AlertType.INFORMATION);
            al.setHeaderText("Printing bill\n" + "Total price: " + bill.getTotalAmount());
            al.showAndWait();
        });
        HBox hBox = new HBox(new Node[]{addBookBtn, createBill});
        hBox.setPadding(new Insets(40,0,0,0));
        hBox.setSpacing(30);
        grid.add(hBox, 1, 7);
        grid.setStyle("-fx-background-color:antiquewhite");
        return new Scene(grid);
    }
}