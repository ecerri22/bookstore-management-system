package com.example.bookstore.view;

import com.example.bookstore.controller.AdminController;
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


public class AdminView implements ShowView {
    private User admin;

    public AdminView(User admin) {
        this.admin=admin;
    }

    @Override
    public Scene execute(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:antiquewhite");

        VBox sidebar = new VBox();
        sidebar.setSpacing(50);
        sidebar.setMinWidth(150);
        sidebar.setPadding(new Insets(60,20,20,20));

        borderPane.setLeft(sidebar);

        /*BOOKS*/
        Button sellBookBtn = new Button("Sell Book");
        sellBookBtn.setMinWidth(150);
        sellBookBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        Button addBookBtn = new Button("Add Book");
        Button seeAllBooksBtn = new Button("Books Data");
        addBookBtn.setMinWidth(150);
        addBookBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        seeAllBooksBtn.setMinWidth(150);
        seeAllBooksBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");
        /*EMPLOYEES*/
        Button addEmplBtn = new Button("Add Employee");
        Button seeAllEmplBtn = new Button("Employees Data");
        addEmplBtn.setMinWidth(150);
        addEmplBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        seeAllEmplBtn.setMinWidth(150);
        seeAllEmplBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");
        /*ALERTS*/
        Button statsBtn = new Button("Statistics");
        statsBtn.setMinWidth(150);
        statsBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        Button logoutBtn=new Button("LOGOUT");
        logoutBtn.setMinWidth(150);
        logoutBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 15;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana'; ");

        /*******************BOOKS VIEWS***************************/
        sellBookBtn.setOnAction(e -> {
            SellBook sellBook = new SellBook(admin);
            Scene sellBookScene = sellBook.execute(primaryStage);
            borderPane.setCenter(sellBookScene.getRoot());
        });

        addBookBtn.setOnAction(e -> {
            AddRemoveBook addRemoveBook = new AddRemoveBook();
            Scene addRemoveBookScene = addRemoveBook.execute(primaryStage);
            borderPane.setCenter(addRemoveBookScene.getRoot());
        });

        seeAllBooksBtn.setOnAction(e -> {
            ManagerController managerController = new ManagerController();

            AllBooksView allBooksView = new AllBooksView(managerController);

            Scene allBooksViewScene = allBooksView.execute(primaryStage);
            borderPane.setCenter(allBooksViewScene.getRoot());
        });

        /*******************EMPLOYEES VIEWS***************************/
        addEmplBtn.setOnAction(e -> {
            AddEmployeeView addEmployeeView = new AddEmployeeView();

            Scene addEmployeeViewScene = addEmployeeView.execute(primaryStage);

            borderPane.setCenter(addEmployeeViewScene.getRoot());
        });

        seeAllEmplBtn.setOnAction(e -> {
            AdminController adminController = new AdminController();

            AllEmpoyeesView allEmpList = new AllEmpoyeesView(adminController);

            Scene allEmpListScene = allEmpList.execute(primaryStage);
            borderPane.setCenter(allEmpListScene.getRoot());
        });

        /*******************STATS VIEW***************************/
        statsBtn.setOnAction(e -> {
            StatsView statsView = new StatsView();

            Scene statsScene = statsView.execute(primaryStage);

            borderPane.setCenter(statsScene.getRoot());
        });



        logoutBtn.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait();
            if (confirmation.getResult() == ButtonType.YES) {
                Alert info=new Alert(Alert.AlertType.INFORMATION);
                info.setHeaderText("Successfully logged out!");
                info.showAndWait();
                primaryStage.setScene(new LoginView().execute(primaryStage));
            };
        });

        sidebar.getChildren().addAll(sellBookBtn, addBookBtn, seeAllBooksBtn, addEmplBtn, seeAllEmplBtn, statsBtn,logoutBtn);

        Scene scene = new Scene(borderPane, 800, 600);
        return scene;
    }
}