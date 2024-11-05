package com.example.bookstore.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String FName, LName, phone, email, password, role;
    private double salary;
    private Date birthday;
    private boolean canAddBook, canAddBill;

    public User(String FName, String LName, String phone, String email, String password, String role, double salary, Date birthday, boolean canAddBook, boolean canAddBill) {
        this.FName = FName;
        this.LName = LName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
        this.salary = salary;
        this.birthday = birthday;
        this.canAddBook = canAddBook;
        this.canAddBill = canAddBill;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean getCanAddBill() {
        return canAddBill;
    }

    public void setCanAddBill(boolean canAddBill) {
        this.canAddBill = canAddBill;
    }

    public boolean getCanAddBook() {
        return canAddBook;
    }

    public void setCanAddBook(boolean canAddBook) {
        this.canAddBook = canAddBook;
    }
}