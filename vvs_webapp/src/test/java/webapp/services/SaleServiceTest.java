package webapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class SaleServiceTest {
    
    private ICustomerService mockCustomerService;
    private ISaleService saleService;
    
    @Before
    public void setUp() {
        mockCustomerService = mock(ICustomerService.class);
        saleService = new SaleServiceImpl(mockCustomerService);
    }
    
    @Test
    public void testAddSale_ValidCustomer_AddsSuccessfully() throws ApplicationException {
        // Arrange
        int validVat = 123456789;
        CustomerDTO mockCustomer = new CustomerDTO(1, validVat, "Test Customer", 123456789);
        
        when(mockCustomerService.isValidVAT(validVat)).thenReturn(true);
        when(mockCustomerService.getCustomerByVat(validVat)).thenReturn(mockCustomer);
        
        // Act - this would normally throw exception if customer not found
        saleService.addSale(validVat);
        
        // Assert
        verify(mockCustomerService).getCustomerByVat(validVat);
    }
    
    @Test
    public void testAddSale_InvalidVat_ThrowsException() throws ApplicationException {
        // Arrange
        int invalidVat = 123;
        
        when(mockCustomerService.isValidVAT(invalidVat)).thenReturn(false);
        when(mockCustomerService.getCustomerByVat(invalidVat))
            .thenThrow(new ApplicationException("Invalid VAT number: " + invalidVat));
        
        // Act & Assert
        ApplicationException exception = assertThrows(
            ApplicationException.class,
            () -> saleService.addSale(invalidVat)
        );
        
        assertEquals("Customer with VAT " + invalidVat + " not found.", exception.getMessage());
    }
    
    @Test
    public void testGetSaleByCustomerVat_InvalidVat_ThrowsException() {
        // Arrange
        int invalidVat = 123;
        
        when(mockCustomerService.isValidVAT(invalidVat)).thenReturn(false);
        
        // Act & Assert
        ApplicationException exception = assertThrows(
            ApplicationException.class,
            () -> saleService.getSaleByCustomerVat(invalidVat)
        );
        
        assertEquals("Invalid VAT number: " + invalidVat, exception.getMessage());
    }
} 