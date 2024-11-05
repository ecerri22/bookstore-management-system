package com.example.bookstore.view;

import com.example.bookstore.model.User;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerView implements ShowView{
    private User manager;
    public ManagerView(User manager) {
        this.manager=manager;
    }

    @Override
    public Scene execute(Stage primaryStage) {
        return null;
    }
}
