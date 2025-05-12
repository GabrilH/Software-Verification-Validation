// package vvs_dbsetup;

// import org.junit.jupiter.api.BeforeAll;

// import com.ninja_squad.dbsetup.DbSetupTracker;
// import com.ninja_squad.dbsetup.destination.Destination;
// import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

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
// }
