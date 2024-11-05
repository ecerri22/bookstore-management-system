package com.example.bookstore.view;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.controller.ManagerController;
import com.example.bookstore.model.User;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibrarianView implements ShowView {
    private User librarian ;

    public LibrarianView(User librarian) {
        this.librarian=librarian;
    }

    @Override
    public Scene execute(Stage primaryStage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: antiquewhite");

        VBox sidebar = new VBox();
        sidebar.setSpacing(50);
        sidebar.setMinWidth(150);
        sidebar.setPadding(new Insets(60,20,20,20));
        borderPane.setLeft(sidebar);

        Button sellBook = new Button("Sell Book");
        sellBook.setMinWidth(150);
        sellBook.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");
        Button seeBooks = new Button("Books Data");
        seeBooks.setMinWidth(150);
        seeBooks.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        if(librarian.getCanAddBook()){
            Button addBookBtn = new Button("Add Book");
            addBookBtn.setMinWidth(150);
            addBookBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';");

            addBookBtn.setOnAction(e -> {
                AddBookView addBook = new AddBookView();
                Scene addBookScene = addBook.execute(primaryStage);
                borderPane.setCenter(addBookScene.getRoot());
            });
        }

        Button logout = new Button("LOGOUT");
        logout.setMinWidth(150);
        logout.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';");

        sellBook.setOnAction(e -> {
            SellBook sellbook=new SellBook(librarian);
            Scene sellBookSc=sellbook.execute(primaryStage);
            changeMainContent(sellBookSc.getRoot(), borderPane);
        });

        seeBooks.setOnAction(e -> {
            AdminController adminController = new AdminController();
            ManagerController managerController = new ManagerController();

            AllBooksView allBooksView = new AllBooksView(managerController, adminController);

            Scene allBooksViewScene = allBooksView.execute(primaryStage);
            borderPane.setCenter(allBooksViewScene.getRoot());
        });

        logout.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait();
            if (confirmation.getResult() == ButtonType.YES) {
                Alert info=new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Successfully logged out!");
                info.showAndWait();
                primaryStage.setScene(new LoginView().execute(primaryStage));
            };
        });

        sidebar.getChildren().addAll(sellBook, seeBooks, logout);

        if (librarian.getCanAddBook()) {
            Button addBook = new Button("ADD BOOK");

            addBook.setMinWidth(150);
            addBook.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';");

            addBook.setOnAction(e -> {
                AddBookView addBookView = new AddBookView();
                Scene addBookScene = addBookView.execute(primaryStage);
                changeMainContent(addBookScene.getRoot(), borderPane);
            });

            sidebar.getChildren().add(addBook);
        }

        Scene scene = new Scene(borderPane, 800, 600);

        return scene;
    }

    private void changeMainContent(Node content, BorderPane borderPane) {
        borderPane.setCenter(content);
    }

    public User getLibrarian(){
        return librarian;
    }

}