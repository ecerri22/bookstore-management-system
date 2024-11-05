package com.example.bookstore.model;

import java.util.Date;

public class Administrator extends User{
    public Administrator(String FName, String LName, String phone, String email, String password, String role, double salary, Date birthday) {
        super(FName, LName, phone, email, password, role, salary, birthday);
    }
}