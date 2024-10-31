package com.example.bookstore;

import com.example.bookstore.view.LoginView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setScene((new LoginView()).execute(primaryStage));

        primaryStage.setTitle("BOOKSTORE");
        Image icon = new Image(getClass().getResource("./images/bookIcon.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


