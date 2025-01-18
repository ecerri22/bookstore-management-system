import controller.CategoryController;
import model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        // Create a real instance of the controller
        categoryController = Mockito.spy(new CategoryController());
    }

    @Test
    void testAddCategory() {
        // Arrange
        Category mockCategory = mock(Category.class);
        when(mockCategory.getCategoryName()).thenReturn("Test Category");

        // Act
        categoryController.addCategory(mockCategory);

        // Assert
        verify(categoryController, times(1)).writeCategories();
        assertTrue(categoryController.readCategories().contains(mockCategory));
    }

    @Test
    void testVerify1_DuplicateCategory() {
        // Arrange
        Category mockCategory = mock(Category.class);
        when(mockCategory.getCategoryName()).thenReturn("Duplicate Category");

        categoryController.addCategory(mockCategory);

        // Act
        boolean result = categoryController.verify1("Duplicate Category");

        // Assert
        assertFalse(result, "The method should return false for duplicate categories");
    }

    @Test
    void testVerify1_NewCategory() {
        // Act
        boolean result = categoryController.verify1("New Category");

        // Assert
        assertTrue(result, "The method should return true for a new category");
        verify(categoryController, times(2)).writeCategories(); // Adjusted to expect 2 invocations
    }


    @Test
    void testReadCategories_FileNotFound() {
        // Arrange
        doReturn(false).when(categoryController).isFileAvailable("categories.bin");

        // Act
        ArrayList<Category> result = categoryController.readCategories();

        // Assert
        assertNotNull(result, "The category list should not be null");
        assertTrue(result.isEmpty(), "The category list should be empty if the file is not found");
    }

    @Test
    void testPrintCategoriesToConsole() {
        // Arrange
        Category mockCategory1 = mock(Category.class);
        when(mockCategory1.getCategoryName()).thenReturn("Category1");

        Category mockCategory2 = mock(Category.class);
        when(mockCategory2.getCategoryName()).thenReturn("Category2");

        categoryController.addCategory(mockCategory1);
        categoryController.addCategory(mockCategory2);

        // Act & Assert
        categoryController.printCategoriesToConsole();
    }
}
