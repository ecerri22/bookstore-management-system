package com.example.bookstore.test.integrationTests;

import com.example.bookstore.controller.UserController;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IntegrationTestingUser {

    private UserController userController;
    private User mockUser;
    private File testFile;

    @BeforeEach
    void setUp() {
        // Create a real user
        mockUser = new User(
                "John",
                "Doe",
                "555-1234",
                "test@example.com",
                "password",
                "Admin",
                50000.0,
                new Date(),
                true,
                false
        );

        // Create a real list and add the mock user to the list
        ArrayList<User> usersList = new ArrayList<>();
        usersList.add(mockUser);

        // Prepare the file to save the users
        testFile = new File("testEmployees.bin");

        // Serialize the users list to the file
        try (FileOutputStream fos = new FileOutputStream(testFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(usersList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now, we can create the UserController and it should read the data from the file
        userController = new UserController() {
            @Override
            public void readUsers() {
                // Override readUsers to ensure it reads from our test file
                try {
                    FileInputStream fis = new FileInputStream(testFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    users = (ArrayList<User>) ois.readObject();
                    fis.close();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        userController.readUsers();
    }

    @AfterEach
    void tearDown() {
        // Clean up the test file after each test
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testLoginSuccess() {
        // Call the login method with correct credentials
        User user = userController.login("test@example.com", "password");

        // Manually compare user properties to avoid reference comparison
        assertEquals(mockUser.getEmail(), user.getEmail());
        assertEquals(mockUser.getPassword(), user.getPassword());

        // Add other fields here as necessary
    }

    @Test
    void testLoginFailure() {
        // Call the login method with incorrect credentials
        User user = userController.login("wrong@example.com", "password");

        // Assert that no user is returned
        assertNull(user);
    }
}
