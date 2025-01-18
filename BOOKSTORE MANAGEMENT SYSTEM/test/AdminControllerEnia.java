import controller.AdminController;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        adminController = Mockito.spy(new AdminController());
    }

    @Test
    void testIsAdded_ValidUser() {
        // Arrange
        String email = "test@example.com";
        when(adminController.isEmailExists(email)).thenReturn(false);

        // Act
        boolean result = adminController.isAdded(
                "John", "Doe", "1234567890", email, "password", "Librarian", 3000.0, new Date(), true, false
        );

        // Assert
        assertTrue(result, "The user should be added successfully");
        assertEquals(1, adminController.getAllEmployees().size(), "The employee should be added to the list");
    }

    @Test
    void testIsAdded_DuplicateEmail() {
        // Arrange
        String email = "duplicate@example.com";
        User mockUser = mock(User.class);
        when(mockUser.getEmail()).thenReturn(email);
        adminController.getAllEmployees().add(mockUser); // Simulate an existing employee with the email

        // Act
        boolean result = adminController.isAdded(
                "Jane", "Doe", "1234567890", email, "password", "Librarian", 3000.0, new Date(), true, false
        );

        // Assert
        assertFalse(result, "The user should not be added with a duplicate email");
        assertEquals(1, adminController.getAllEmployees().size(), "No additional user should be added");
    }

    @Test
    void testIsDeleted_UserExists() {
        // Arrange
        User mockUser = mock(User.class);
        adminController.getAllEmployees().add(mockUser);

        // Act
        boolean result = adminController.isDeleted(mockUser);

        // Assert
        assertTrue(result, "The user should be deleted successfully");
        assertFalse(adminController.getAllEmployees().contains(mockUser));
    }

    @Test
    void testFindEmployee_ExistingUser() {
        // Arrange
        User mockUser = mock(User.class);
        adminController.getAllEmployees().add(mockUser);

        // Act
        User result = adminController.findEmployee(mockUser);

        // Assert
        assertEquals(mockUser, result, "The method should find and return the existing user");
    }

    @Test
    void testFindEmployee_NonExistingUser() {
        // Arrange
        User mockUser = mock(User.class);

        // Act
        User result = adminController.findEmployee(mockUser);

        // Assert
        assertNull(result, "The method should return null for a non-existing user");
    }

    @Test
    void testModifyFName() {
        // Arrange
        User mockUser = mock(User.class);
        adminController.getAllEmployees().add(mockUser); // Simulate an existing user
        when(adminController.findEmployee(mockUser)).thenReturn(mockUser);

        // Act
        boolean result = adminController.modifyFName(mockUser, "NewFirstName");

        // Assert
        assertTrue(result, "The user's first name should be modified successfully");
        verify(mockUser, times(1)).setFName("NewFirstName"); // Verify the name was set
        assertTrue(adminController.getAllEmployees().contains(mockUser), "The modified user should still exist in the list");
    }

    @Test
    void testModifyEmail() {
        // Arrange
        User mockUser = mock(User.class);
        adminController.getAllEmployees().add(mockUser); // Simulate an existing user
        when(adminController.findEmployee(mockUser)).thenReturn(mockUser);

        // Act
        boolean result = adminController.modifyEmail(mockUser, "newemail@example.com");

        // Assert
        assertTrue(result, "The user's email should be modified successfully");
        verify(mockUser, times(1)).setEmail("newemail@example.com"); // Verify the email was set
        assertTrue(adminController.getAllEmployees().contains(mockUser), "The modified user should still exist in the list");
    }

    @Test
    void testReadEmployees_FileNotFound() {
        // Arrange
        // Mock the file availability check to return false, simulating the file not being found
        doReturn(false).when(adminController).isFileAvailable("allEmployees.bin");

        // Ensure the allEmployees list is initialized before testing
        ArrayList<User> employees = adminController.getAllEmployees();

        // Act
        // Check the employees list after the file check fails
        assertNotNull(employees, "The employees list should not be null");
        assertTrue(employees.isEmpty(), "The employees list should be empty if the file is not found");
    }
}
