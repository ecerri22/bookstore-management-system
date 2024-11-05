package com.example.bookstore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Transaction implements Serializable {
    private ArrayList<Book> books;
    private int quantity;
    private double price;
    private Date transactionDate;

    public Transaction(ArrayList<Book> books) {
        this.books = books;
        this.transactionDate = new Date();
    }

    public Transaction(Book book, int quantity) {
        this.quantity = quantity;
        this.price = book.getSellingPrice() * (double)quantity;
        this.books = new ArrayList();
        this.books.add(book);
        this.transactionDate = new Date();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public ArrayList<Book> getBooks() {
        return this.books;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }
}