package com.example.bookstore.view;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.controller.ManagerController;
import com.example.bookstore.model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerView implements ShowView {
    private User manager;

    public ManagerView(User manager) {
        initialize();
        this.manager = manager;
    }

    public void initialize() {
        BookController bookStock0 = new BookController();
        bookStock0.displayAlert();
    }

    @Override
    public Scene execute(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: antiquewhite");

        VBox sidebar = new VBox();
        sidebar.setSpacing(50);
        sidebar.setMinWidth(150);
        sidebar.setPadding(new Insets(60, 20, 20, 20));
        borderPane.setLeft(sidebar);

        Button seeBooksbutton = new Button("See All Books");
        seeBooksbutton.setMinWidth(150);
        seeBooksbutton.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        Button addRemoveBookbutton = new Button("Add Book");
        addRemoveBookbutton.setMinWidth(150);
        addRemoveBookbutton.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        Button alertsbutton = new Button("Alerts");
        alertsbutton.setMinWidth(150);
        alertsbutton.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        Button logout = new Button("LOGOUT");
        logout.setMinWidth(150);
        logout.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        seeBooksbutton.setOnAction(e -> {
            ManagerController managerController = new ManagerController();
            AllBooksView allBooksView = new AllBooksView(managerController);

            // Get the root node from the Scene instead of the Scene itself
            Scene allBooksViewScene = allBooksView.execute(primaryStage);
            borderPane.setCenter(allBooksViewScene.getRoot());
        });

        if (manager.getCanAddBill()) {
            Button addBill = new Button("Sell Book");

            addBill.setMinWidth(150);
            addBill.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';");

            addBill.setOnAction(e -> {
                SellBook sellBook = new SellBook(manager);
                Scene sellBookSc = sellBook.execute(primaryStage);
                borderPane.setCenter(sellBookSc.getRoot());
            });
            sidebar.getChildren().add(addBill);
        }

        addRemoveBookbutton.setOnAction(e -> {
            AddRemoveBook addRemoveBook = new AddRemoveBook();
            Scene addRemoveBookScene = addRemoveBook.execute(primaryStage);
            borderPane.setCenter(addRemoveBookScene.getRoot());
        });

        alertsbutton.setOnAction(e -> {
            Alerts alerts = new Alerts();
            Scene alertsScene = alerts.execute(primaryStage);
            borderPane.setCenter(alertsScene.getRoot());
        });

        logout.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait();
            if (confirmation.getResult() == ButtonType.YES) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Successfully logged out!");
                info.showAndWait();
                primaryStage.setScene(new LoginView().execute(primaryStage));
            }
        });

        sidebar.getChildren().addAll(seeBooksbutton, addRemoveBookbutton, alertsbutton, logout);

        Scene scene = new Scene(borderPane, 920, 600);
        return scene;
    }
}
