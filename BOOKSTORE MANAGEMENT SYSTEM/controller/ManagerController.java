package com.example.bookstore.controller;

import com.example.bookstore.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class ManagerController {
    private ArrayList<Book> allBooks;
    private File file;

    public ManagerController() {
        allBooks = new ArrayList<>();
        file = new File("allBooks.bin");

        if (file.exists()) {
            readAllBooks();
        }
    }

    // Method to read all books from the file
    private void readAllBooks() {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            allBooks = (ArrayList<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading books: " + e.getMessage());
        }
    }

    // Method to write all books to the file
    private void writeAllBooks() {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(allBooks);
        } catch (IOException e) {
            System.err.println("Error writing books: " + e.getMessage());
        }
    }

    // Method to add a book to the list
    public void addInAllBooks(Book book) {
        this.allBooks.add(book);
    }

    // Method to add a book if it passes the required conditions
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

    // Method to delete a book from the list
    public boolean isDeleted(Book bookToDelete) {
        allBooks.remove(bookToDelete);
        writeAllBooks();
        return true;
    }

    // Method to retrieve all books
    public ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    // Find a book by ISBN (or other criteria if needed)
    public Optional<Book> findBook(Book bookToEdit) {
        return allBooks.stream().filter(book -> book.equals(bookToEdit)).findFirst();
    }

    // Modify book properties
    public boolean modifyISBN(Book bookToEdit, String modifyISBN) {
        return modifyBookField(bookToEdit, book -> book.setISBN(modifyISBN));
    }

    public boolean modifyTitle(Book bookToEdit, String modifyTitle) {
        return modifyBookField(bookToEdit, book -> book.setTitle(modifyTitle));
    }

    public boolean modifyAuthor(Book bookToEdit, String modifyAuthor) {
        return modifyBookField(bookToEdit, book -> book.setAuthor(modifyAuthor));
    }

    public boolean modifyCategory(Book bookToEdit, String modifyCategory) {
        return modifyBookField(bookToEdit, book -> book.setCategory(modifyCategory));
    }

    public boolean modifySupplier(Book bookToEdit, String modifySupplier) {
        return modifyBookField(bookToEdit, book -> book.setSupplier(modifySupplier));
    }

    public boolean modifyPurchasedDate(Book bookToEdit, Date modifyPurchasedDate) {
        return modifyBookField(bookToEdit, book -> book.setPurchasedDate(modifyPurchasedDate));
    }

    public boolean modifyPurchasedPrice(Book bookToEdit, double modifyPurchasedPrice) {
        return modifyBookField(bookToEdit, book -> book.setPurchasedPrice(modifyPurchasedPrice));
    }

    public boolean modifyOriginalPrice(Book bookToEdit, double modifyOriginalPrice) {
        return modifyBookField(bookToEdit, book -> book.setOriginalPrice(modifyOriginalPrice));
    }

    public boolean modifySellingPrice(Book bookToEdit, double modifySellingPrice) {
        return modifyBookField(bookToEdit, book -> book.setSellingPrice(modifySellingPrice));
    }

    public boolean modifyStock(Book bookToEdit, int modifyStock) {
        return modifyBookField(bookToEdit, book -> book.setStock(modifyStock));
    }

    // Helper method to modify a field in the book
    private boolean modifyBookField(Book bookToEdit, BookFieldModifier modifier) {
        Optional<Book> bookOptional = findBook(bookToEdit);
        if (bookOptional.isPresent()) {
            modifier.modify(bookOptional.get());
            writeAllBooks();
            return true;
        }
        return false;
    }

    // Functional interface to modify a book field
    @FunctionalInterface
    public interface BookFieldModifier {
        void modify(Book book);
    }
}
