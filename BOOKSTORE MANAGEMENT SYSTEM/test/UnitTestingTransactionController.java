package com.example.bookstore.test;

import com.example.bookstore.controller.TransactionController;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnitTestingTransactionController {

    private File tempFile;
    private TransactionController transactionController;

    @BeforeEach
    void setup() throws IOException {
        // Create a temporary file for testing
        tempFile = Files.createTempFile("test_transactions", ".bin").toFile();

        // Use a new instance of TransactionController with the temp file
        transactionController = new TransactionController() {
            {
                file = tempFile;
            }
        };
    }

    @AfterEach
    void cleanup() {
        // Delete the temporary file after each test
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void testAddInAllTransactions() {
        transactionController.clearTransactions();
        // Create a transaction and add it
        Transaction transaction = new Transaction(
                new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                        "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100), 1);
        transactionController.addInAllTransactions(transaction);

        // Retrieve and validate transactions
        ArrayList<Transaction> transactions = transactionController.getAllTransactions();
        assertEquals(1, transactions.size());
        assertEquals(transaction, transactions.get(0));
    }

    @Test
    void testWriteAndReadAllTransactions() {
        transactionController.clearTransactions();
        // Create and add transactions
        Transaction transaction1 = new Transaction(
                new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                        "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100), 2);
        Transaction transaction2 = new Transaction(
                new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                        "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100), 1);

        transactionController.addInAllTransactions(transaction1);
        transactionController.addInAllTransactions(transaction2);

        // Write transactions to the file
        transactionController.writeAllTransactions();

        // Create a new TransactionController to read from the temp file
        TransactionController newController = new TransactionController() {
            {
                file = tempFile;
            }
        };
        newController.readAllTransactions();

        // Validate the transactions
        ArrayList<Transaction> transactions = newController.getAllTransactions();
        assertEquals(2, transactions.size());
    }

    @Test
    void testGetAllTransactionsWhenEmpty() {
        transactionController.clearTransactions();
        // Ensure no transactions exist initially
        ArrayList<Transaction> transactions = transactionController.getAllTransactions();
        assertTrue(transactions.isEmpty());
    }
}
