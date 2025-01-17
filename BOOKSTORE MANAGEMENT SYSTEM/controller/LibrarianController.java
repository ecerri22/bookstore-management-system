package com.example.bookstore.controller;

import com.example.bookstore.model.Book;

import java.io.*;
import java.util.ArrayList;

public class LibrarianController {

    public File file;
    private ArrayList<Book> books = new ArrayList<>();

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void readBooks(InputStream inputStream) {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            books = (ArrayList<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeBooks(OutputStream outputStream) {
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
