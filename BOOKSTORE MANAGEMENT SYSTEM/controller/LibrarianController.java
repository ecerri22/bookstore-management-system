package com.example.bookstore.controller;

import com.example.bookstore.model.Book;

import java.io.*;
import java.util.ArrayList;

public class LibrarianController {
    private ArrayList<Book> books;

    public File file;

    public LibrarianController(){
        books=new ArrayList<>();
        file = new File("books.bin");
        if (file.exists()) {
            readBooks();
        }
    }

    public void readBooks() {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            books = (ArrayList<Book>) ois.readObject();
            fis.close();
            ois.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeBooks() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(books);
            oos.close();
            fos.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books=books;
    }
}
