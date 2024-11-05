package com.example.bookstore.view;

import com.example.bookstore.controller.UserController;
import com.example.bookstore.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class LoginView implements ShowView {


    @Override
    public Scene execute (Stage primaryStage){
        GridPane grid=new GridPane();
        grid.setStyle("-fx-background-color: antiquewhite;  -fx-background-radius: 155; ");
        Scene scene=new Scene(grid,800,600);
        grid.setPadding(new Insets(15));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.setHgap(15);

        Label email =new Label("EMAIL : ");
        email.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        email.setStyle("-fx-effect:dropshadow(one-pass-box,MIDNIGHTBLUE,5,0.0,1,0);");
        grid.add(email,0,0);
        TextField emailF=new TextField();
        emailF.setStyle(" -fx-font-size:15px");
        grid.add(emailF,1,0);


        Label password =new Label("PASSWORD : ");
        password.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        password.setStyle("-fx-effect:dropshadow(one-pass-box,MIDNIGHTBLUE,5,0.0,1,0);");
        grid.add(password,0,1);
        PasswordField passwordF= new PasswordField();
        passwordF.setStyle(" -fx-font-size:15px");
        grid.add(passwordF, 1, 1);

        Button loginBtn=new Button("LOGIN");
        loginBtn.setStyle("-fx-background-color: midnightblue; -fx-text-fill: white; -fx-font-size: 16;  -fx-effect:dropshadow(one-pass-box,GRAY,8,0.0,2,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        grid.add(loginBtn,1,3);

        loginBtn.setOnAction(e -> {

            UserController uc = new UserController();
            User user = uc.login(emailF.getText(), passwordF.getText());
            if (user != null) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText("WELCOME " + user.getFName().toUpperCase() + " !");
                al.showAndWait();
                if(user.getRole().toLowerCase().equals("librarian")){
                    LibrarianView librarianView=new LibrarianView( user);
                    primaryStage.setScene(librarianView.execute(primaryStage));
                }

                else if (user.getRole().toLowerCase().equals("manager")){
                    ManagerView managerView=new ManagerView(user);
                    primaryStage.setScene(managerView.execute(primaryStage));
                }
                else
                {
                    AdminView adminView=new AdminView( user);
                    primaryStage.setScene(adminView.execute(primaryStage));
                }

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Enter the correct email and password");
                al.showAndWait();
            }
        });
        return scene;
    }
}



