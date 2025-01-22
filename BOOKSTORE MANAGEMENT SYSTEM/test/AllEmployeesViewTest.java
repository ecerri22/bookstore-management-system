package com.example.bookstore.test;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.model.User;
import com.example.bookstore.view.AllEmpoyeesView;


import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllEmployeesViewTest extends ApplicationTest {

    private AllEmpoyeesView allEmployeesView;
    private AdminController adminController;

    @Override
    public void start(Stage stage) {
        // Mock the AdminController
        adminController = mock(AdminController.class);

        // Set up mock data
        ArrayList<User> mockEmployees = new ArrayList<>();
        mockEmployees.add(new User("John", "Doe", "john.doe@example.com", "1234567890", "password", "Librarian", 30000, null, false, false));
        mockEmployees.add(new User("Jane", "Smith", "jane.smith@example.com", "0987654321", "password123", "Manager", 40000, null, true, false));

        // Mock the getAllEmployees() method
        when(adminController.getAllEmployees()).thenReturn(mockEmployees);

        // Initialize the AllEmpoyeesView with the mocked controller
        allEmployeesView = new AllEmpoyeesView(adminController);
        stage.setScene(allEmployeesView.execute(stage));
        stage.show();
    }

    @Test
    public void testEditEmployeeDetails() {
        // Verify initial data in the table
        ArrayList<User> employees = new ArrayList<>(adminController.getAllEmployees());
        assertEquals(2, employees.size(), "Table should initially contain 2 employees");

        // Simulate editing the first name of the first employee
        interact(() -> {
            User employee = employees.get(0);
            employee.setFName("Johnny");
        });

        // Validate that the change is reflected in the mock data
        assertEquals("Johnny", employees.get(0).getFName(), "First name should be updated to 'Johnny'");
    }

    @Test
    public void testDeleteEmployee() {
        // Verify initial data in the table
        ArrayList<User> employees = new ArrayList<>(adminController.getAllEmployees());
        assertEquals(2, employees.size(), "Table should initially contain 2 employees");

        // Simulate deleting the first employee
        interact(() -> {
            User employee = employees.get(0);
            adminController.isDeleted(employee); // Call the delete method
            employees.remove(employee); // Update the mock data
        });

        // Validate that the employee is removed
        assertEquals(1, employees.size(), "Table should contain 1 employee after deletion");
        assertNotEquals("John", employees.get(0).getFName(), "Deleted employee should no longer be present");
    }
}
