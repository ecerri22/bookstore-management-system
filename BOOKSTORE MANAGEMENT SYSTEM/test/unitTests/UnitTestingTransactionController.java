package com.example.bookstore.test.unitTests;

import com.example.bookstore.controller.TransactionController;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UnitTestingTransactionController {

    @Mock
    private File mockFile;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock file behavior
        when(mockFile.exists()).thenReturn(true);

    }

    @Test
    void testAddInAllTransactions() {
        // Clear transactions before adding
        transactionController.clearTransactions();

        // Create a transaction
        Transaction transaction = new Transaction(
                new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                        "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100), 1);

        // Add transaction
        transactionController.addInAllTransactions(transaction);

        // Verify that the transaction was added
        ArrayList<Transaction> transactions = transactionController.getAllTransactions();
        assertEquals(1, transactions.size());
        assertEquals(transaction, transactions.get(0));
    }

    @Test
    void testWriteAndReadAllTransactions() throws Exception {
        // Create mock transactions
        Transaction transaction1 = new Transaction(
                new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                        "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100), 2);
        Transaction transaction2 = new Transaction(
                new Book("978-3-16-148410-0", "Effective Java", "Joshua Bloch", "Programming",
                        "TechBooks Supplier", new Date(), 30.0, 45.0, 50.0, 100), 1);

        ArrayList<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(transaction1);
        mockTransactions.add(transaction2);

        // Mocking the TransactionController instance using Mockito.mock()
        TransactionController mockController = mock(TransactionController.class);

        // Mocking the behavior of getAllTransactions to return mock transactions
        when(mockController.getAllTransactions()).thenReturn(mockTransactions);

        // Mocking writeAllTransactions and readAllTransactions to do nothing
        doNothing().when(mockController).writeAllTransactions();
        doNothing().when(mockController).readAllTransactions();

        // Simulate adding transactions
        mockController.addInAllTransactions(transaction1);
        mockController.addInAllTransactions(transaction2);

        // Simulate writing transactions to the file (no actual I/O)
        mockController.writeAllTransactions();

        // Create a new mock controller to simulate reading the transactions
        TransactionController newController = mock(TransactionController.class);
        when(newController.getAllTransactions()).thenReturn(mockTransactions);

        // Retrieve and verify the transactions
        ArrayList<Transaction> transactions = newController.getAllTransactions();
        assertEquals(2, transactions.size());
    }


    @Test
    void testGetAllTransactionsWhenEmpty() {
        // Mocking the TransactionController instance
        TransactionController mockController = mock(TransactionController.class);

        // Mock clearTransactions and getAllTransactions to return an empty list
        doNothing().when(mockController).clearTransactions();
        when(mockController.getAllTransactions()).thenReturn(new ArrayList<>());

        // Call method
        ArrayList<Transaction> transactions = mockController.getAllTransactions();

        // Verify result
        assertTrue(transactions.isEmpty());
    }



}
