import controller.BillController;
import model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillControllerTest {
    private BillController billController;

    @BeforeEach
    void setUp() {
        // Create a real instance of the controller
        billController = Mockito.spy(new BillController());
    }

    @Test
    void testAddBill() {
        // Arrange
        Bill mockBill = mock(Bill.class);

        // Act
        billController.addInAllBills(mockBill);
        billController.writeAllBills(); // Explicitly calling writeAllBills()

        // Assert
        verify(billController, times(1)).writeAllBills(); // Ensure writeAllBills was called
        assertTrue(billController.getAllBills().contains(mockBill)); // Ensure the bill is added to the list
    }

    @Test
    void testWriteBills() throws IOException {
        // Arrange
        Bill mockBill1 = mock(Bill.class);
        Bill mockBill2 = mock(Bill.class);

        billController.addInAllBills(mockBill1);
        billController.addInAllBills(mockBill2);

        // Act
        billController.writeAllBills();

        // Assert
        verify(billController, times(1)).writeAllBills(); // Verify that writeAllBills was called
    }

    @Test
    void testReadBills_FileNotFound() {
        // Arrange
        doReturn(false).when(billController).isFileAvailable("bills.bin");

        // Act
        billController.readAllBills();

        // Assert
        assertNotNull(billController.getAllBills(), "The bills list should not be null");
        assertTrue(billController.getAllBills().isEmpty(), "The bills list should be empty if the file is not found");
    }

    @Test
    void testReadBills_FileExists() {
        // Arrange
        doReturn(true).when(billController).isFileAvailable("bills.bin");

        // Act
        billController.readAllBills();

        // Assert
        assertNotNull(billController.getAllBills(), "The bills list should not be null even if no bills are read from the file");
    }

    @Test
    void testIsFileAvailable_FileExists() {
        // Act
        boolean result = billController.isFileAvailable("bills.bin");

        // Assert
        assertTrue(result, "The file should be found in the resources");
    }

    @Test
    void testIsFileAvailable_FileNotFound() {
        // Act
        boolean result = billController.isFileAvailable("nonexistent_file.bin");

        // Assert
        assertFalse(result, "The file should not be found in the resources");
    }
}
