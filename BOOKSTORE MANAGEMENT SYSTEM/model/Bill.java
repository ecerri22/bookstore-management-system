package com.example.bookstore.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class Bill implements Serializable {
    private ArrayList<Transaction> transactions;
    private double totalAmount = 0;
    private Date dateCreated;
    public String seller;
    private static int id;

    public Bill(ArrayList<Transaction>transactions, User user){
        this.id++;
        this.transactions=transactions;
        this.dateCreated=new Date();
        this.seller= user.getFName() + " " + user.getLName() + " " + user.getRole();
        for(Transaction t:transactions){
            totalAmount+=t.getPrice();
        }
    }


    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getBooksSold() {
        int nrOfBooksSold=0;
        for(Transaction transaction:transactions){
            nrOfBooksSold+= transaction.getQuantity();
        }
        return nrOfBooksSold;
    }

    public void print() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Create a bill.txt file
        try (PrintWriter writer = new PrintWriter(new FileWriter("Bill" + id + ".txt"))) {
            writer.println("------------------------------");
            writer.println("          BOOKSTORE BILL        ");
            writer.println("------------------------------");
            writer.println("Date: " + dateFormat.format(dateCreated));
            writer.println(seller);
            writer.println("------------------------------");

            for (Transaction transaction : transactions) {
                writer.println("Book: " + transaction.getBooks().get(0).getTitle());
                writer.println("Author: " + transaction.getBooks().get(0).getAuthor());
                writer.println("Quantity: " + transaction.getQuantity());
                writer.println("Price per book: $" + transaction.getBooks().get(0).getSellingPrice());
                writer.println("Subtotal: $" + transaction.getPrice());
                writer.println("------------------------------");
            }

            writer.println("Total Amount: $" + totalAmount);
            writer.println("Number of Books Sold: " + getBooksSold());
            writer.println("------------------------------");
            System.out.println("Bill printed successfully. Check Bill"  + id + ".txt");
        } catch (IOException e) {
            System.out.println("Error printing bill: " + e.getMessage());
        }
    }
}

