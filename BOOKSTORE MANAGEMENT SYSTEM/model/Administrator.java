package com.example.bookstore.model;

import java.util.Date;

public class Administrator extends com.example.bookstore.model.User {
    public Administrator(String FName, String LName, String phone, String email, String password, String role, double salary, Date birthday, boolean canAddBook, boolean canAddBill) {
        super(FName, LName, phone, email, password, role, salary, birthday, true, true);
    }
}