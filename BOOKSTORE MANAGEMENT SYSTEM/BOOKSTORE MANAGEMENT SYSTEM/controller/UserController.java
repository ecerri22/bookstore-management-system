package com.example.bookstore.controller;

import com.example.bookstore.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

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

    private void writeUsers() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addUser(User u) {
        this.users.add(u);
    }

    public boolean registerUser(String FName, String LName, String phone, String email, String password, String role, double salary, Date birthday) {
        User u = new User(FName,LName,phone,email, password,role,salary,birthday);
        this.addUser(u);
        writeUsers();
        return true;
    }

    public void print() {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
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