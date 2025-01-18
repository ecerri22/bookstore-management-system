package com.example.bookstore.controller;

import com.example.bookstore.model.Category;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class CategoryController {
    private ArrayList<Category> category;
    private File file1;
    public CategoryController(){
        category = new ArrayList<>();
        file1 = new File("categories.bin");
        if(file1.exists()){
            readCategories();
        }

    }
    public ArrayList<Category> readCategories() {
        try {
            FileInputStream fis1 = new FileInputStream(file1);
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            category= (ArrayList<Category>) ois1.readObject();
            fis1.close();
            ois1.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return category;
    }
    public void writeCategories() {
        try{
            FileOutputStream fos1 = new FileOutputStream(file1);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(category);
            oos1.close();
            fos1.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void addCategory(Category u) {
        this.category.add(u);
        writeCategories();
    }
    public boolean verify1(String categoryName1) {
        for (Category category1 : category) {
            if (category1 != null && category1.getCategoryName().equals(category1)) {
                return false;
            }
        }
        Category a = new Category(categoryName1);
        this.addCategory(a);
        writeCategories();
        return true;
    }
    public void printCategoriesToConsole() {
        System.out.println("Categories: " + category);
    }

    // Check if the file exists in the resources folder
    public boolean isFileAvailable(String fileName) {
        return getClass().getClassLoader().getResource(fileName) != null;
    }
}
