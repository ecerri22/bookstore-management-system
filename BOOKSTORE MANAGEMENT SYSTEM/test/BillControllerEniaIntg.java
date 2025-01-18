package com.example.bookstore.test;

import com.example.bookstore.model.Bill;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BillIntegrationTest {
    private User mockUser;
    private ArrayList<Transaction> mockTransactions;

    @BeforeEach
    void setUp() {
        // Mock User
        mockUser = new User("John", "Doe", "123456789", "john@example.com", "password", "Seller", 3000.0, new Date(), true, true);

        // Mock Transactions
        mockTransactions = new ArrayList<>();
        Book book1 = new Book("1234567890", "Book Title 1", "Author 1", "Fiction", "Supplier 1", new Date(), 10.0, 15.0, 20.0, 50);
        Book book2 = new Book("0987654321", "Book Title 2", "Author 2", "Non-Fiction", "Supplier 2", new Date(), 20.0, 25.0, 30.0, 30);

        Transaction transaction1 = new Transaction(book1, 2); // Quantity: 2
        Transaction transaction2 = new Transaction(book2, 3); // Quantity: 3

        mockTransactions.add(transaction1);
        mockTransactions.add(transaction2);
    }

    @Test
    void testBillCreation() {
        Bill bill = new Bill(mockTransactions, mockUser);

        // Verify bill details
        assertEquals(5, bill.getBooksSold(), "The total books sold should be 5.");
        assertEquals(130.0, bill.getTotalAmount(), "The total amount should be $130.0."); // Updated to match correct calculation
        assertNotNull(bill.getDateCreated(), "The date should not be null.");
        assertEquals("John Doe Seller", bill.getSeller(), "The seller name should match.");
    }

    @Test
    void testPrintBill() {
        Bill bill = new Bill(mockTransactions, mockUser);
        bill.print();

        // Verify the bill file was created
        File billFile = new File("Bill1.txt"); // Assuming this is the first bill created
        assertTrue(billFile.exists(), "The bill file should be created.");

        // Clean up the file after the test
        if (billFile.exists()) {
            assertTrue(billFile.delete(), "The bill file should be deleted after the test.");
        }
    }

    @Test
    void testTransactionsHandling() {
        Bill bill = new Bill(mockTransactions, mockUser);

        // Verify transactions
        ArrayList<Transaction> transactions = bill.getTransactions();
        assertNotNull(transactions, "Transactions should not be null.");
        assertEquals(2, transactions.size(), "There should be 2 transactions.");
        assertEquals("Book Title 1", transactions.get(0).getBooks().get(0).getTitle(), "The title of the first book should match.");
        assertEquals(20.0, transactions.get(0).getBooks().get(0).getSellingPrice(), "The price of the first book should match.");
    }
}
