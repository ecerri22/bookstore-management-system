package com.example.bookstore.controller;

import com.example.bookstore.model.Author;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class AuthorController {
    private ArrayList<Author> author;
    private File file;
    public AuthorController(){
        author = new ArrayList<>();
        file = new File("authors.bin");
        if(file.exists()){
            readAuthors();
        }

    }
    public ArrayList<Author> readAuthors() {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            author = (ArrayList<Author>) ois.readObject();
            fis.close();
            ois.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return author;
    }
    public void writeAuthors() {
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(author);
            oos.close();
            fos.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addAuthor(Author u) {
        this.author.add(u);
    }
    public boolean verify(String lastName1) {
        for (Author author1 : author) {
            if (author1 != null && author1.getFullName().equals(lastName1)) {
                return false;
            }
        }
        Author a = new Author(lastName1);
        this.addAuthor(a);
        writeAuthors();
        return true;
    }
    public void printAuthorsToConsole() {
        System.out.println("Authors: " + author);
    }

}