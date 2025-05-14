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

public class SaleDeliveriesDBTest {
    

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
			, INSERT_CUSTOMER_ADDRESS_SALE_DATA
			);
		
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);
		
        // Use the tracker to launch the DBSetup. This will speed-up tests 
		// that do not change the DB. Otherwise, just use dbSetup.launch();
        dbSetupTracker.launchIfNecessary(dbSetup);
//		dbSetup.launch();
	}

    @Test
    public void getSaleDeliveriesForCustomerTest() throws ApplicationException {
		
		// read-only test: unnecessary to re-launch setup after test has been run
		dbSetupTracker.skipNextLaunch();
        
        SalesDeliveryDTO deliveries = SaleService.INSTANCE.getSalesDeliveryByVat(197672337);

        assertEquals(2, deliveries.sales_delivery.size());
    }

    @Test 
    public void addNewSaleDeliveryTest() throws ApplicationException {

        final int C_VAT = 168027852;

        // first: get sales from customer
        SalesDTO sales = SaleService.INSTANCE.getSaleByCustomerVat(C_VAT);
        assumeTrue(sales.sales.size() > 0);
        int saleId = sales.sales.get(0).id;

        // second: get addresses from customer
        AddressesDTO addresses = CustomerService.INSTANCE.getAllAddresses(C_VAT);
        assumeTrue(addresses.addrs.size() > 0);
        int addressId = addresses.addrs.get(0).id;

        // third: add sale delivery
        int initialDeliveries = SaleService.INSTANCE.getSalesDeliveryByVat(C_VAT).sales_delivery.size();
        SaleService.INSTANCE.addSaleDelivery(saleId, addressId);

        // fourth: get deliveries from customer
        SalesDeliveryDTO deliveries = SaleService.INSTANCE.getSalesDeliveryByVat(C_VAT);
        assertEquals(initialDeliveries+1, deliveries.sales_delivery.size());
    }
}
