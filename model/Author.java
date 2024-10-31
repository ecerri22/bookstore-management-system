package com.example.bookstore.model;

import java.io.Serializable;

public class Author implements Serializable {
    String fullName;
    public Author(){

    }
    public Author(String name) {
        this.fullName = name;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}
