package com.example.bookstore.controller;

import com.example.bookstore.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class ManagerController {
    private ArrayList<Book> allBooks;
    private File file;

    public ManagerController(){
        allBooks = new ArrayList<>();
        file = new File("allBooks.bin");

        if(file.exists()){
            readAllBooks();
        }
    }

    @SuppressWarnings("unchecked")
    public void readAllBooks(){
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allBooks = (ArrayList<Book>) ois.readObject();

            fis.close();
            ois.close();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeAllBooks(){
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(allBooks);
            oos.close();
            fos.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addInAllBooks(Book book){
        this.allBooks.add(book);
    }

    public boolean isAdded(String isbn,
                           String title,
                           String author,
                           String category,
                           String supplier,
                           Date purchasedDate,
                           double purchasedPrice,
                           double originalPrice,
                           double sellingPrice,
                           int stock) {
        Book book = new Book(isbn,
                            title,
                            author,
                            category,
                            supplier,
                            purchasedDate,
                            purchasedPrice,
                            originalPrice,
                            sellingPrice,
                            stock);
        this.addInAllBooks(book);
        writeAllBooks();
        return true;
    }


    public boolean isDeleted(Book bookToDelete){
        allBooks.removeIf(user -> user.equals(bookToDelete));
        writeAllBooks();
        return true;
    }

    public ArrayList<Book> getAllBooks() {
        return allBooks;
    }
    public Book findBook(Book bookToEdit) {
        for(Book book : allBooks){
            if(book.equals(bookToEdit)){
                return book;
            }
        }
        return null;
    }

    public boolean modifyISBN(Book bookToEdit, String modifyISBN) {
        this.findBook(bookToEdit).setISBN(modifyISBN);
        writeAllBooks();
        return true;
    }

    public boolean modifyTitle(Book bookToEdit, String modifyTitle) {
        this.findBook(bookToEdit).setTitle(modifyTitle);
        writeAllBooks();
        return true;
    }

    public boolean modifyAuthor(Book bookToEdit, String modifyAuthor) {
        this.findBook(bookToEdit).setAuthor(modifyAuthor);
        writeAllBooks();
        return true;
    }

    public boolean modifyCategory(Book bookToEdit, String modifyCategory) {
        this.findBook(bookToEdit).setCategory(modifyCategory);
        writeAllBooks();
        return true;
    }

    public boolean modifySupplier(Book bookToEdit, String modifySupplier) {
        this.findBook(bookToEdit).setSupplier(modifySupplier);
        writeAllBooks();
        return true;
    }

    public boolean modifyPurchasedDate(Book bookToEdit, Date modifyPurchasedDate) {
        this.findBook(bookToEdit).setPurchasedDate(modifyPurchasedDate);
        writeAllBooks();
        return true;
    }

    public boolean modifyPurchasedPrice(Book bookToEdit, double modifyPurchasedPrice) {
        this.findBook(bookToEdit).setPurchasedPrice(modifyPurchasedPrice);
        writeAllBooks();
        return true;
    }

    public boolean modifyOriginalPrice(Book bookToEdit, double modifyOriginalPrice) {
        this.findBook(bookToEdit).setOriginalPrice(modifyOriginalPrice);
        writeAllBooks();
        return true;
    }

    public boolean modifySellingPrice(Book bookToEdit, double modifySellingPrice) {
        this.findBook(bookToEdit).setSellingPrice(modifySellingPrice);
        writeAllBooks();
        return true;
    }

    public boolean modifyStock(Book bookToEdit, int modifyStock) {
        this.findBook(bookToEdit).setStock(modifyStock);
        writeAllBooks();
        return true;
    }

}
