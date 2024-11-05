package com.example.bookstore.model;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Librarian extends User {

    private final File file;
    private ArrayList<Bill> bills;


    public Librarian(String FName, String LName, String phone, String email, String password, String role, double salary, Date birthday, boolean canAddBook, boolean canAddBill) {
        super(FName,LName,phone,email, password,role,salary,birthday, canAddBook, canAddBill);
        setCanAddBill(true);
        bills=new ArrayList<>();
        file = new File("bills.bin");
    }


    public ArrayList<Bill> readBills() {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            bills = (ArrayList<Bill>) ois.readObject();
            fis.close();
            ois.close();

        } catch (Exception e) {
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
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(specificDate) == 0)
                cnt=bill.getBooksSold();
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