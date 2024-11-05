package com.example.bookstore.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.example.bookstore.model.Bill;

public class BillController implements Serializable {
    private ArrayList<Bill> bills = new ArrayList();
    private File file = new File("bills.bin");

    public BillController() {
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException var2) {
                System.out.println("Error creating file: " + var2.getMessage());
            }
        }

        if (this.file.exists()) {
            this.readAllBills();
        }

    }

    public void readAllBills() {
        try {
            FileInputStream fis = new FileInputStream(this.file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.bills = (ArrayList)ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
        }

    }

    public void writeAllBills() {
        try {
            FileOutputStream fos = new FileOutputStream(this.file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.bills);
            oos.close();
            fos.close();
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
        }

    }

    public void addInAllBills(Bill bill) {
        this.bills.add(bill);
    }

    public ArrayList<Bill> getAllBills() {
        return this.bills;
    }
}
