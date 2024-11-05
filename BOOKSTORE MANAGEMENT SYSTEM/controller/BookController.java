package com.example.bookstore.controller;

import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;



public class BookController {
    private ArrayList<Book> book;
    protected ArrayList<Bill> bills;
    private File file1;
    private File file2;
    public BookController(){
        book = new ArrayList<>();
        file2 = new File("allBooks.bin");
        bills=new ArrayList<>();
        file1 = new File("bills.bin");
        if(file1.exists()){
            readBills();
        }
        if(file2.exists()){
            readBooks();
        }

    }
    public ArrayList<Book> readBooks() {
        try {
            FileInputStream fis2 = new FileInputStream(file2);
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            book= (ArrayList<Book>) ois2.readObject();
            fis2.close();
            ois2.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return book;
    }
    public ArrayList<Bill> readBills() {
        try {
            FileInputStream fis = new FileInputStream(file1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            bills = (ArrayList<Bill>) ois.readObject();
            fis.close();
            ois.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bills;
    }
    public void writeBooks() {
        try{
            FileOutputStream fos2 = new FileOutputStream(file2);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(book);
            oos2.close();
            fos2.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addBook(String isbn, String title, String author, String category, String supplier, Date purchasedDate, double purchasedPrice, double originalPrice, double sellingPrice, int stock) {
        Book newBook = new Book(isbn, title, author,category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);
        this.book.add(newBook);
    }
    public boolean verifyBook(String isbn, String title, String author, String category, String supplier, Date purchasedDate, double purchasedPrice, double originalPrice, double sellingPrice, int stock ) {
        for (Book bookInstance : book) {
            if (bookInstance != null && bookInstance.getISBN().equals(isbn) && purchasedPrice>=0 && sellingPrice>=0 && originalPrice>=0) {
                return false;
            }
        }
        this.addBook(isbn, title, author,category, supplier, purchasedDate, purchasedPrice, originalPrice, sellingPrice, stock);
        writeBooks();
        return true;
    }
    public void printBooksToConsole() {
        System.out.println("Book: " + book);
    }

    public int nrOfBooksSold() {
        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            cnt+=bill.getBooksSold();
        }

        return cnt;
    }
    public int nrOfBooksSoldDaily(Date specificDate) {
        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(specificDate) == 0)
                cnt+=bill.getBooksSold();
        }
        return cnt;
    }

    public int nrOfBooksSoldMonthly(Date start, Date end) {

        int cnt = 0;
        bills = readBills();
        for(Bill bill : bills) {
            if(bill.getDateCreated().compareTo(start) >= 0 && bill.getDateCreated().compareTo(end) <= 0)
                cnt+=bill.getBooksSold();
        }

        return cnt;
    }

    public void displayAlert() {
        for (Book book : book) {
            if (book != null && book.getStock() < 5) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Book Quantity Alert");
                alert.setHeaderText("Book with Quantity < 5");
                alert.setContentText("Book ISBN: " + book.getISBN() + " has a quantity of " + book.getStock());
                alert.showAndWait();
            }
        }
    }

    public boolean isISBNExists(String isbn) {
        for (Book book : book) {
            if (book.getISBN().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

}