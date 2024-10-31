package com.example.bookstore.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String ISBN;
    private String title;
    private String author;
    private String category;
    private String supplier;
    private Date purchasedDate;
    private double purchasedPrice;
    private double originalPrice;
    private double sellingPrice;
    private int stock;

    public Book(String isbn, String title, String author, String category, String supplier, Date purchasedDate, double purchasedPrice, double originalPrice, double sellingPrice, int stock) {
        this.ISBN = isbn;
        this.title = title;
        this.category = category;
        this.supplier = supplier;
        this.purchasedDate = purchasedDate;
        this.purchasedPrice = purchasedPrice;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.author = author;
        this.stock = stock;
    }

    public Book() {

    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", supplier='" + supplier + '\'' +
                ", purchasedDate=" + purchasedDate +
                ", purchasedPrice=" + purchasedPrice +
                ", originalPrice=" + originalPrice +
                ", sellingPrice=" + sellingPrice +
                ", author='" + author + '\'' +
                ", stock=" + stock +
                '}';
    }
}

