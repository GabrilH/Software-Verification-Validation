package vvs_dbsetup;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	// the SUT does not allow to add a new client with an existing VAT;
	@Test
	public void addCustomerWithExistingVATTest() throws ApplicationException {
		System.out.println("addCustomerWithExistingVATTest()... ");

		// first: get all customers
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();

		// second: try to add all customers again
		for (CustomerDTO c : customersDTO.customers) {
			assertThrows(ApplicationException.class, () -> {
				CustomerService.INSTANCE.addCustomer(c.vat, c.designation, c.phoneNumber);
			});
		}
		
		// third: check if customers number is the same
		assertEquals(NUM_INIT_CUSTOMERS, CustomerService.INSTANCE.getAllCustomers().customers.size());	
	}

	// after the update of a costumer contact, that information should be properly saved;
	@Test
	public void updateCustomerContactTest() throws ApplicationException {
		System.out.println("updateCustomerContactTest()... ");

		final int PHONENUMBER = 961234567;

		// first: get all customers
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();

		// second: update all customers
		for (CustomerDTO c : customersDTO.customers) {
			CustomerService.INSTANCE.updateCustomerPhone(c.vat, PHONENUMBER);
		}

		// third: check if the update was successful
		customersDTO = CustomerService.INSTANCE.getAllCustomers();
		
		for (CustomerDTO c : customersDTO.customers) {
			assertEquals(PHONENUMBER, c.phoneNumber);
		}
	}
	
	// after deleting all costumers, the list of all customers should be empty;
	@Test
	public void deleteAllCustomersTest() throws ApplicationException {
		System.out.println("deleteAllCustomersTest()... ");

		// first: get all customers
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();

		// second: delete all customers
		for (CustomerDTO c : customersDTO.customers) {
			CustomerService.INSTANCE.removeCustomer(c.vat);
		}

		customersDTO = CustomerService.INSTANCE.getAllCustomers();
		
		assertEquals(0, customersDTO.customers.size());
	}

	// after deleting a certain costumer, it’s possible to add it back without lifting exceptions;
	@Test
	public void deleteCustomerTest() throws ApplicationException {
		System.out.println("deleteCustomerTest()... ");
		
		// first: get all customers
		final CustomersDTO initialCustomers = CustomerService.INSTANCE.getAllCustomers();

		// second: delete all customers
		for (CustomerDTO c : initialCustomers.customers) {
			CustomerService.INSTANCE.removeCustomer(c.vat);
		}

		// third: check if Customers is empty
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();
		assertEquals(0, customersDTO.customers.size());

		// fourth: add all customers again
		for (CustomerDTO c : initialCustomers.customers) {
			CustomerService.INSTANCE.addCustomer(c.vat, c.designation, c.phoneNumber);
		}

		// fifth: check if all customers are back
		customersDTO = CustomerService.INSTANCE.getAllCustomers();
		assertEquals(NUM_INIT_CUSTOMERS, customersDTO.customers.size());
	}
}
