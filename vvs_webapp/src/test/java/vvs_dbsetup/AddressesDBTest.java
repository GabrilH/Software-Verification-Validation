// package vvs_dbsetup;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assumptions.assumeFalse;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;

// import com.ninja_squad.dbsetup.DbSetupTracker;
// import com.ninja_squad.dbsetup.destination.Destination;
// import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

// import webapp.services.AddressDTO;
// import webapp.services.AddressesDTO;
// import webapp.services.CustomerService;

// public class AddressesDBTest {
    
//     private static Destination dataSource;
	
//     // the tracker is static because JUnit uses a separate Test instance for every test method.
//     private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
//     @BeforeAll
//     public static void setupClass() {
// //    	System.out.println("setup Class()... ");
    	
//     	startApplicationDatabaseForTesting();
// 		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
//     }

//     @Test
// 	public void addAddressTest() throws ApplicationException {

// 		assumeFalse(hasClient(503183504));
// 		CustomerService.INSTANCE.addCustomer(503183504, "FCUL", 217500000);
// 		assertTrue(hasClient(503183504));

// 		String address = "Rua da Escola";
// 		String door = "1";
// 		String postalCode = "1000";
// 		String locality = "Lisboa";

// 		String full_address = address + ";" + door + ";" + postalCode + ";" + locality;
		
// 		CustomerService.INSTANCE.addAddressToCustomer(503183504, full_address);
		
// 		AddressesDTO addressesDTO = CustomerService.INSTANCE.getAllAddresses(503183504);
		
// 		assertEquals(1, addressesDTO.addrs.size());
// 		AddressDTO addressDTO = addressesDTO.addrs.get(0);
// 		assertEquals(full_address, addressDTO.address.trim());
// 	}
// }
