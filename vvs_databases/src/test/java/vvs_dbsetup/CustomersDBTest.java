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

public class CustomersDBTest {

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
			, INSERT_CUSTOMER_ADDRESS_DATA
			);
		
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);
		
        // Use the tracker to launch the DBSetup. This will speed-up tests 
		// that do not change the DB. Otherwise, just use dbSetup.launch();
        dbSetupTracker.launchIfNecessary(dbSetup);
//		dbSetup.launch();
	}
	
	@Test
	public void queryCustomerNumberTest() throws ApplicationException {
//		System.out.println("queryCustomerNumberTest()... ");
		
		// read-only test: unnecessary to re-launch setup after test has been run
		dbSetupTracker.skipNextLaunch();
		
		int expected = NUM_INIT_CUSTOMERS;
		int actual   = CustomerService.INSTANCE.getAllCustomers().customers.size();
		
		assertEquals(expected, actual);
	}

	@Test
	public void addCustomerSizeTest() throws ApplicationException {
//		System.out.println("addCustomerSizeTest()... ");

		CustomerService.INSTANCE.addCustomer(503183504, "FCUL", 217500000);
		int size = CustomerService.INSTANCE.getAllCustomers().customers.size();
		
		assertEquals(NUM_INIT_CUSTOMERS+1, size);
	}
	
	private boolean hasClient(int vat) throws ApplicationException {	
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();
		
		for(CustomerDTO customer : customersDTO.customers)
			if (customer.vat == vat)
				return true;			
		return false;
	}
	
	@Test
	public void addCustomerTest() throws ApplicationException {
//		System.out.println("addCustomerTest()... ");

		assumeFalse(hasClient(503183504));
		CustomerService.INSTANCE.addCustomer(503183504, "FCUL", 217500000);
		assertTrue(hasClient(503183504));
	}
	
	@Test
	public void addAddressTest() throws ApplicationException {
//		System.out.println("addAddressTest()... ");

		assumeFalse(hasClient(503183504));
		CustomerService.INSTANCE.addCustomer(503183504, "FCUL", 217500000);
		assertTrue(hasClient(503183504));

		String address = "Rua da Escola";
		String door = "1";
		String postalCode = "1000";
		String locality = "Lisboa";

		String full_address = address + ";" + door + ";" + postalCode + ";" + locality;
		
		CustomerService.INSTANCE.addAddressToCustomer(503183504, full_address);
		
		AddressesDTO addressesDTO = CustomerService.INSTANCE.getAllAddresses(503183504);
		
		assertEquals(1, addressesDTO.addrs.size());
		AddressDTO addressDTO = addressesDTO.addrs.get(0);
		assertEquals(full_address, addressDTO.address.trim());
	}
}
