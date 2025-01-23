package com.example.bookstore.test.firstPhase;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

//boundary value testing - all types
class BvtDaniela {
    private AdminController adminController = new AdminController();

    // Valid boundary date cases
    @ParameterizedTest(name="ModifyBday({0}, {1}, {2}) expected result: {3}")
    @DisplayName("Testing Normal BVT for modifyBday")
    @CsvSource({
            "1, 1, 2024, true",
            "28, 2, 2024, true",
            "29, 2, 2024, true",
            "31, 12, 2024, true",
            "1, 1, 0000, true",
            "31, 12, 9999, true",
    })
    void testNormalBVT(int day, int month, int year, boolean expectedResult) {
        User user = new User("John", "Doe", "12345", "john@example.com", "password", "Manager", 5000, new Date(), true, true);
        Date testDate = new Date(year - 1900, month - 1, day);
        boolean result = adminController.modifyBday(user, testDate);
        assertEquals(expectedResult, result);
    }

    // Robust Boundary Value Testing (RBVT)
    @ParameterizedTest(name="ModifyBday({0}, {1}, {2}) expected result: {3}")
    @DisplayName("Testing Robust BVT for modifyBday")
    @CsvSource({
            "0, 1, 2024, false",
            "31, 4, 2024, false",
            "30, 2, 2024, false",
            "32, 1, 2024, false",
            "1, 13, 2024, false",
            "1, 1, -100, false",
            "31, 12, 2024, true",
            "29, 2, 2023, false",
            "31, 6, 2024, false",
    })
    void testRobustBVT(int day, int month, int year, boolean expectedResult) {
        User user = new User("John", "Doe", "12345", "john@example.com", "password", "Manager", 5000, new Date(), true, true);
        Date testDate = new Date(year - 1900, month - 1, day);
        boolean result = adminController.modifyBday(user, testDate);
        assertEquals(expectedResult, result);
    }

    // Worst-case Boundary Value Testing (WBVT)
    @Test
    @DisplayName("Worst Case BVT for modifyBday")
    void testWorstCaseBVT() {
        User user = new User("John", "Doe", "12345", "john@example.com", "password", "Manager", 5000, new Date(), true, true);

        Date epochStart = new Date(0);
        assertTrue(adminController.modifyBday(user, epochStart));

        Date maxDate = new Date(9999 - 1900, 11, 31);
        assertTrue(adminController.modifyBday(user, maxDate));

        Date invalidDate1 = new Date(-100, 0, 1);
        assertFalse(adminController.modifyBday(user, invalidDate1));

        Date invalidDate2 = new Date(10000 - 1900, 11, 31);
        assertFalse(adminController.modifyBday(user, invalidDate2));

        Date invalidDate3 = new Date(9999 - 1900, 12, 32);
        assertFalse(adminController.modifyBday(user, invalidDate3));
    }

    // Testing for Out-of-bounds Dates
    @Test
    @DisplayName("Testing for Out-of-bounds Dates for modifyBday")
    void testOutOfBounds() {
        User user = new User("John", "Doe", "12345", "john@example.com", "password", "Manager", 5000, new Date(), true, true);
        assertFalse(adminController.modifyBday(user, new Date(2024 - 1900, 0, 32)));
        assertFalse(adminController.modifyBday(user, new Date(2024 - 1900, 1, 30)));
        assertFalse(adminController.modifyBday(user, new Date(2024 - 1900, 3, 31)));
        assertFalse(adminController.modifyBday(user, new Date(2023 - 1900, 1, 29)));
        assertFalse(adminController.modifyBday(user, new Date(2024 - 1900, 6, 31)));
    }
}
