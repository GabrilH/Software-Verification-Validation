package vvs_dbsetup;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import static vvs_dbsetup.DBSetupUtils.*;
import webapp.services.*;

public class SalesDBTest {

    private static Destination dataSource;
	
    // the tracker is static because JUnit uses a separate Test instance for every test method.
    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
    @BeforeAll
    public static void setupClass() {
//    	System.out.println("setup Class()... ");
    	
    	startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
	@BeforeEach
	public void setup() throws SQLException {
//		System.out.print("setup()... ");
		
		Operation initDBOperations = Operations.sequenceOf(
			  DELETE_ALL
			, INSERT_CUSTOMER_SALE_DATA
			);
		
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);
		
        // Use the tracker to launch the DBSetup. This will speed-up tests 
		// that do not change the DB. Otherwise, just use dbSetup.launch();
        dbSetupTracker.launchIfNecessary(dbSetup);
//		dbSetup.launch();
	}

    private boolean hasClient(int vat) throws ApplicationException {	
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();
		
		for(CustomerDTO customer : customersDTO.customers)
			if (customer.vat == vat)
				return true;			
		return false;
	}

	// after deleting a certain costumer, its sales should be removed from the database;
	@Test
	public void deleteCustomerSalesAreDeletedTest() throws ApplicationException {
		System.out.println("deleteCustomerSalesAreDeletedTest()... ");
		
		// first: assume that customer exists
		assumeTrue(hasClient(197672337));
		
		// second: assume that customer has sales
		int num_sales = SaleService.INSTANCE.getSaleByCustomerVat(197672337).sales.size();
		assumeTrue(num_sales > 0);

		// third: delete customer
		CustomerService.INSTANCE.removeCustomer(197672337);
		assertFalse(hasClient(197672337));

		// fourth: check if customer sales are deleted
		SalesDTO salesDTO = SaleService.INSTANCE.getSaleByCustomerVat(197672337);
		assertEquals(0, salesDTO.sales.size());
	}

    // adding a new sale increases the total number of all sales by one;
	@Test
	public void addSaleIncreasesSalesNumberTest() throws ApplicationException {
		System.out.println("addSaleIncreasesSalesNumberTest()... ");
		
		// first: get all sales
		SalesDTO salesDTO = SaleService.INSTANCE.getAllSales();
		int initialSize = salesDTO.sales.size();

		// second: add a new sale
        assumeTrue(hasClient(197672337));
		SaleService.INSTANCE.addSale(197672337);
		
		// third: check if the number of sales increased
		salesDTO = SaleService.INSTANCE.getAllSales();
		assertEquals(initialSize + 1, salesDTO.sales.size());
	}

    // a sale should start with status 'O' (open) when created
    @Test
    public void newSaleHasOpenStatusTest() throws ApplicationException {
        System.out.println("newSaleHasOpenStatusTest()... ");
        
        // first: ensure customer exists
        assumeTrue(hasClient(197672337));
        
        // second: create new sale
        SaleService.INSTANCE.addSale(197672337);
        
        // third: get the customer's sales and check most recent one
        SalesDTO salesDTO = SaleService.INSTANCE.getSaleByCustomerVat(197672337);
        SaleDTO mostRecentSale = salesDTO.sales.get(salesDTO.sales.size() - 1);
        
        // fourth: verify the sale status is 'O'
        assertEquals("O", mostRecentSale.statusId);
    }

    // a sale's total should start at 0.0 when created
    @Test 
    public void newSaleHasZeroTotalTest() throws ApplicationException {
        System.out.println("newSaleHasZeroTotalTest()... ");
        
        // first: ensure customer exists
        assumeTrue(hasClient(197672337));
        
        // second: create new sale
        SaleService.INSTANCE.addSale(197672337);
        
        // third: get the customer's sales and check most recent one
        SalesDTO salesDTO = SaleService.INSTANCE.getSaleByCustomerVat(197672337);
        SaleDTO mostRecentSale = salesDTO.sales.get(salesDTO.sales.size() - 1);
        
        // fourth: verify the sale total is 0.0
        assertEquals(0.0, mostRecentSale.total, 0.001);
    }
}
