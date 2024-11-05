package com.example.bookstore.controller;

import com.example.bookstore.model.User;

import java.io.*;
import java.util.ArrayList;

public class UserController {
    private ArrayList<User> users;
    private File file;

    public UserController() {
        users = new ArrayList<>();
        file = new File("allEmployees.bin");
        if (file.exists()) {
            readUsers();
        }
    }

    @SuppressWarnings("unchecked")
    private void readUsers() {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<User>) ois.readObject();
            fis.close();
            ois.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}