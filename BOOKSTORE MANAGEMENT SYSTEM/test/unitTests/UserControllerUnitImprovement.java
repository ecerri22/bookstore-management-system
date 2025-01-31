package com.example.bookstore.test.unitTests;

import com.example.bookstore.controller.UserController;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserControllerUnitImprovement {

    @InjectMocks
    private UserController userController;

    @Mock
    private ArrayList<User> mockUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user1 = new User("John", "Doe", "123-456-7890", "user1@example.com", "password123",
                "Admin", 50000.0, new Date(1990, 5, 15), true, false);
        User user2 = new User("Jane", "Smith", "987-654-3210", "user2@example.com", "password456",
                "Employee", 40000.0, new Date(1992, 8, 25), false, true);

        ArrayList<User> mockUsersList = new ArrayList<>();
        mockUsersList.add(user1);
        mockUsersList.add(user2);

        Mockito.when(mockUsers.size()).thenReturn(mockUsersList.size());
        Mockito.when(mockUsers.get(0)).thenReturn(user1);
        Mockito.when(mockUsers.get(1)).thenReturn(user2);

        userController = Mockito.mock(UserController.class, Mockito.CALLS_REAL_METHODS);
        userController.users = mockUsersList;
    }

    @Test
    void testLoginWithValidCredentials() {
        User result = userController.login("user1@example.com", "password123");
        assertNotNull(result);
        assert(result.getEmail().equals("user1@example.com"));
    }

    @Test
    void testLoginWithInvalidCredentials() {
        User result = userController.login("user1@example.com", "wrongpassword");
        assertNull(result);
    }

    @Test
    void testLoginWithAnotherValidUser() {
        User result = userController.login("user2@example.com", "password456");
        assertNotNull(result);
        assert(result.getEmail().equals("user2@example.com"));
    }
}
