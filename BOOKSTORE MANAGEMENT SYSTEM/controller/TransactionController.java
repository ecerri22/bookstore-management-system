package com.example.bookstore.controller;

import com.example.bookstore.model.Transaction;

import java.io.*;
import java.util.ArrayList;

public class TransactionController implements Serializable {
    private ArrayList<Transaction> transactions = new ArrayList();
    public File file = new File("transactions.bin");

    public TransactionController() {
        if (!this.file.exists()) {
            try {
                boolean created = this.file.createNewFile();
                if (created) {
                    System.out.println("File created successfully");
                } else {
                    System.out.println("File already exists");
                }
            } catch (IOException var2) {
                System.out.println("Error creating file: " + var2.getMessage());
            }
        }

        if (this.file.exists()) {
            this.readAllTransactions();
        }

    }

    public void readAllTransactions() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.transactions = (ArrayList)ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
        }

    }

    public void writeAllTransactions() {
        try {
            FileOutputStream fos = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.transactions);
            oos.close();
            fos.close();
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
        }

    }

    public void clearTransactions() {
        // Clear the transactions list
        this.transactions.clear();
        // Write the empty list to the file
        writeAllTransactions();
    }

    public void addInAllTransactions(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public ArrayList<Transaction> getAllTransactions() {
        return this.transactions;
    }
}