package com.example.bookstore.test;


import com.example.bookstore.controller.UserController;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UnitTestingUserController {

    private UserController userController;
    private User mockUser;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
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

        ArrayList<User> usersList = new ArrayList<>();
        usersList.add(mockUser);

        userController = new UserController() {
            @Override
            public void readUsers() {
                try {
                    Field usersField = UserController.class.getDeclaredField("users");
                    usersField.setAccessible(true);
                    usersField.set(this, usersList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        userController.readUsers();
    }

    @Test
    void testLoginSuccess() {
        User user = userController.login("test@example.com", "password");
        assertEquals(mockUser, user);
    }

    @Test
    void testLoginFailure() {
        User user = userController.login("wrong@example.com", "password");
        assertNull(user);
    }
}
