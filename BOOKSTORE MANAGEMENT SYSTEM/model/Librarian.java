package com.example.bookstore.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Librarian extends User {

    private final File file;
    public ArrayList<Bill> bills;


    public Librarian(String FName, String LName, String phone, String email, String password, String role, double salary, Date birthday, boolean canAddBook, boolean canAddBill) {
        super(FName,LName,phone,email, password,role,salary,birthday, canAddBook, canAddBill);
        setCanAddBill(true);
        bills=new ArrayList<>();
        file = new File("bills.bin");
    }


    public ArrayList<Bill> readBills() {
        try {
            // Check if the file exists and throw an IllegalStateException if not
            if (!file.exists()) {
                throw new IllegalStateException("File is missing");
            }

            // Read the bills from the file
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            bills = (ArrayList<Bill>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IllegalStateException e) {
            // Rethrow the exception to ensure it's propagated properly
            throw e;
        } catch (Exception e) {
            // Catch other exceptions and print the message
            System.out.println(e.getMessage());
        }
        return bills;
    }

    public int nrOfBills() {
        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            cnt++;
        }

        return cnt;
    }

    public int nrOfBills(Date specificDate) {

        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(specificDate) == 0)
                cnt++;
        }

        return cnt;
    }

    public int nrOfBills(Date start, Date end) {

        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(start) >= 0 && bill.getDateCreated().compareTo(end) <= 0)
                cnt++;
        }

        return cnt;
    }
    public int nrOfBooks() {
        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            cnt+=bill.getBooksSold();
        }

        return cnt;
    }

    public int nrOfBooks(Date specificDate) {
        int cnt = 0;
        bills = readBills();
        if (specificDate== null) throw new IllegalArgumentException("Date can't be null");
        LocalDate localSpecificDate = specificDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (Bill bill : bills) {
            LocalDate billDate = bill.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (billDate.isEqual(localSpecificDate)) {
                cnt += bill.getBooksSold();
            }
        }
        return cnt;
    }

    public int nrOfBooks(Date start, Date end) {

        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(start) >= 0 && bill.getDateCreated().compareTo(end) <= 0)
                cnt+=bill.getBooksSold();
        }

        return cnt;
    }

    public double moneyMade() {
        double amount = 0;

        bills = readBills();
        for(Bill bill : bills) {
            amount+=bill.getTotalAmount();
        }

        return amount;
    }

    public double moneyMade(Date specificDate) {
        double amount = 0;

        bills = readBills();
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(specificDate) == 0)
                amount=bill.getTotalAmount();
        }

        return amount;
    }

    public double moneyMade(Date start, Date end) {

        double amount = 0;

        bills = readBills();
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(start) >= 0 && bill.getDateCreated().compareTo(end) <= 0)
                amount += bill.getTotalAmount();
        }

        return amount;
    }

}