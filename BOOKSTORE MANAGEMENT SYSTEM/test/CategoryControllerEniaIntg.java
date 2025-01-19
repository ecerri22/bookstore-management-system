import controller.CategoryController;
import model.Category;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryControllerEniaIntg {
    private CategoryController categoryController;
    private final String TEST_FILE = "categories.bin";

    @BeforeEach
    void setUp() {
        // Initialize the controller and ensure it uses the test file
        categoryController = new CategoryController() {
            @Override
            public boolean isFileAvailable(String fileName) {
                return new File(TEST_FILE).exists();
            }

            @Override
            public void writeCategories() {
                // Read the categories and write them to the test file
                ArrayList<Category> currentCategories = readCategories();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_FILE))) {
                    oos.writeObject(currentCategories);
                } catch (IOException e) {
                    System.out.println("Error writing test categories: " + e.getMessage());
                }
            }

            @Override
            public ArrayList<Category> readCategories() {
                // Read categories from the test file
                ArrayList<Category> categories = new ArrayList<>();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEST_FILE))) {
                    categories = (ArrayList<Category>) ois.readObject();
                } catch (Exception e) {
                    System.out.println("Error reading test categories: " + e.getMessage());
                }
                return categories;
            }
        };
    }

    @AfterEach
    void tearDown() {
        // Delete the test file after each test
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            assertTrue(testFile.delete(), "Failed to delete test file");
        }
    }

    @Test
    void testAddCategory() {
        Category newCategory = new Category("Fiction");
        categoryController.addCategory(newCategory);

        ArrayList<Category> categories = categoryController.readCategories();
        assertNotNull(categories, "Categories list should not be null");
        assertTrue(categories.contains(newCategory), "The new category should be added to the list");
    }

    @Test
    void testReadCategories() throws IOException, ClassNotFoundException {
        // Prepare a sample test file
        ArrayList<Category> sampleCategories = new ArrayList<>();
        sampleCategories.add(new Category("Science"));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_FILE))) {
            oos.writeObject(sampleCategories);
        }

        // Read categories using the controller
        ArrayList<Category> categories = categoryController.readCategories();
        assertNotNull(categories, "Categories list should not be null");
        assertEquals(1, categories.size(), "There should be one category in the list");
        assertEquals("Science", categories.get(0).getCategoryName(), "The category name should match");
    }

    @Test
    void testVerifyAndAddCategory() {
        // Add a unique category
        boolean isAdded = categoryController.verify1("History");
        assertTrue(isAdded, "The category should be added successfully");

        // Try adding the same category again
        boolean isDuplicate = categoryController.verify1("History");
        assertFalse(isDuplicate, "Duplicate categories should not be added");
    }

    @Test
    void testPrintCategoriesToConsole() {
        // Add categories
        categoryController.addCategory(new Category("Math"));
        categoryController.addCategory(new Category("Art"));

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        categoryController.printCategoriesToConsole();

        String expectedOutput = "Categories: [Category{categoryName='Math'}, Category{categoryName='Art'}]";
        assertTrue(outContent.toString().trim().contains(expectedOutput), "Console output should match expected categories");

        // Restore console output
        System.setOut(System.out);
    }

    @Test
    void testIsFileAvailable() throws IOException {
        // Create a test file
        File testFile = new File(TEST_FILE);
        assertTrue(testFile.createNewFile(), "Test file should be created");

        // Check file availability
        assertTrue(categoryController.isFileAvailable(TEST_FILE), "File should be available");
    }
}
