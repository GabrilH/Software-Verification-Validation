# WebApp Integration Tests Report - Software Verification and Validation (VVS)

**Author:** Gabriel Henriques (fc58182)

**Grade:** 8.5/10

## Introduction

This report details the comprehensive integration testing strategy implemented to verify the correct interaction between different components of a web application. Three complementary testing approaches were used: 
- HtmlUnit for end-to-end testing of complete user workflows through the UI layer
- DBSetup for validating database operations and data integrity
- Mockito for testing service layer interactions in isolation. 

Together, these testing methods provide thorough coverage of integration points while allowing targeted testing of specific components.

## End-to-end Testing

Five key test narratives were implemented using HtmlUnit inside the test class [HtmlUnitTests](src/test/java/vvs_webapp/HtmlUnitTests.java) of the package [vvs_webapp](src/test/java/vvs_webapp) to end-to-end test the SUT.

### Test Architecture

All tests share:
- a WebClient configuration
- a reference to the `index.html` page
- helper methods for common operations (adding/removing customers, creating sales, etc.)

Each test follows the same pattern:
- set up the necessary preconditions
- perform the actions being tested
- verify the intermediary steps and results with assertions
- clean up by removing any test data to leave the database in its original state

To leave the database in its original state, I created a temporary customer before each test, performed the actions to it and removed it in the end, which deletes on cascade its information (Sales, Addresses and Deliveries).

### 1. Adding Addresses to Existing Customer
**Method:** [addTwoAddressesToCustomerTest()](src/test/java/vvs_webapp/HtmlUnitTests.java#L45-L101)
- Retrieves an existing customer's VAT number
- Gets initial count of customer addresses
- Adds two new addresses with predefined data
- Verifies the address table now includes both new addresses
- Confirms the total row count increased by exactly two

### 2. Customer Insertion
**Method:** [insertTwoCustomersTest()](src/test/java/vvs_webapp/HtmlUnitTests.java#L103-L138)
- Adds two new customers with VAT, designation, and phone details
- Navigates to the "List All Customers" page
- Verifies all customer information appears correctly in the list
- Cleans up by removing the test customers

### 3. Sale Creation
**Method:** [insertSaleTest()](src/test/java/vvs_webapp/HtmlUnitTests.java#L140-L160)
- Creates a temporary customer
- Adds a new sale for this customer
- Verifies the sale appears with status "O" (Open)
- Confirms the sale is properly associated with the customer's VAT number
- Cleans up by removing the temporary customer

### 4. Sale Closure
**Method:** [closeSaleTest()](src/test/java/vvs_webapp/HtmlUnitTests.java#L163-L183)
- Creates a temporary customer
- Adds a new sale to the customer
- Retrieves the sale ID
- Closes the sale
- Verifies the sale status changes to "C" (Closed)
- Cleans up by removing the temporary customer

### 5. Delivery Creation
**Method:** [insertDeliveryTest()](src/test/java/vvs_webapp/HtmlUnitTests.java#L185-L254)
- Creates a new customer with complete details
- Adds an address to the customer
- Creates a new sale for the customer
- Navigates to the delivery creation page
- Retrieves the previously inserted sale ID and address ID
- Creates a delivery connecting the sale and address
- Verifies the delivery appears correctly in the delivery table


## Database Testing

Three test classes were implemented using DbSetup to test the database operations: [CustomersDBTest](src/test/java/vvs_dbsetup/CustomersDBTest.java), [SalesDBTest](src/test/java/vvs_dbsetup/SalesDBTest.java), and [SaleDeliveriesDBTest](src/test/java/vvs_dbsetup/SaleDeliveriesDBTest.java). The tests are supported by a utility class [DBSetupUtils](src/test/java/vvs_dbsetup/DBSetupUtils.java) that handles database setup and provides common operations.

### Database Setup

The `DBSetupUtils` class provides:
- Constants for database connection
- Operations for cleaning the database (`DELETE_ALL`)
- Predefined test data including customers, sales, addresses, and deliveries
- Combined operations like `INSERT_CUSTOMER_SALE_DATA`, `INSERT_CUSTOMER_ADDRESS_DATA` and `INSERT_CUSTOMER_ADDRESS_SALE_DATA`

Each test class uses a similar setup strategy:
- `@BeforeAll`: Connects to the test database
- `@BeforeEach`: Resets the database and loads appropriate test data

### Customer Tests

**Method:** [addCustomerWithExistingVATTest()](src/test/java/vvs_dbsetup/CustomersDBTest.java#L96-L113)

Tests that the SUT prevents adding a customer with an existing VAT number
1. Retrieves all existing customers
2. Attempts to add each customer again with the same data
3. Verifies that an `ApplicationException` is thrown for each attempt
4. Verifies that the number of customers matches the initial count

**Method:** [updateCustomerContactTest()](src/test/java/vvs_dbsetup/CustomersDBTest.java#L115-L136)

Tests that customer contact information is properly updated
1. Retrieves all existing customers
2. Updates the phone number for all customers to a new value
3. Verifies that all customers now have the new phone number

**Method:** [deleteAllCustomersTest()](src/test/java/vvs_dbsetup/CustomersDBTest.java#L138-L154)

Tests that deleting all customers results in an empty customer list
1. Retrieves all existing customers
1. Deletes all customers one by one
2. Verifies that the customer list is empty after deletion

**Method:** [deleteCustomerTest()](src/test/java/vvs_dbsetup/CustomersDBTest.java#L156-L182)

Tests that a deleted customer can be added back without exceptions
1. Saves the initial list of customers
2. Deletes all customers and verifies the list is empty
3. Adds all customers back with their original information
4. Verifies that the number of customers matches the initial count

---
### Sales Tests

**Method:** [deleteCustomerSalesAreDeletedTest()](src/test/java/vvs_dbsetup/SalesDBTest.java#L63-L82)

Tests that deleting a customer also removes its associated sales
1. Verifies that a specific customer exists and has sales
2. Deletes the customer
3. Confirms the customer no longer exists
4. Verifies that no sales remain for that customer's VAT number

**Method:** [addSaleIncreasesSalesNumberTest()](src/test/java/vvs_dbsetup/SalesDBTest.java#L84-L100)

Tests that adding a sale increases the total count by one
1. Gets the initial count of all sales
2. Adds a new sale for an existing customer
3. Verifies that the total count has increased by exactly one

**Method:** [newSaleHasOpenStatusTest()](src/test/java/vvs_dbsetup/SalesDBTest.java#L102-L119)

Tests that a newly created sale has the "Open" status ('O')
1. Creates a new sale for an existing customer
2. Retrieves the sales for that customer
3. Verifies that the most recent sale has status 'O'

**Method:** [newSaleHasZeroTotalTest()](src/test/java/vvs_dbsetup/SalesDBTest.java#L121-L139)

Tests that a newly created sale has a total of 0.0
1. Creates a new sale for an existing customer
2. Retrieves the sales for that customer
3. Verifies that the most recent sale has a total of 0.0

---
### Sale Deliveries Tests

**Method:** [getSaleDeliveriesForCustomerTest()](src/test/java/vvs_dbsetup/SaleDeliveriesDBTest.java#L55-L64)

Tests retrieval of sale deliveries for a specific customer
- Verifies that the expected number of deliveries (2) are returned

**Method:** [addNewSaleDeliveryTest()](src/test/java/vvs_dbsetup/SaleDeliveriesDBTest.java#L66-L88)

Tests adding a new delivery for a sale
1. Gets an existing sale and address for a customer
2. Records the initial number of deliveries
3. Adds a new sale delivery
4. Verifies that the number of deliveries has increased by one

## Service Layer Mocking

The current service layer implementation presents significant challenges for unit testing:

- The services are implemented as Java enums with singleton pattern:
  ```java
  public enum SaleService {
      INSTANCE;
      // implementation...
  }
  ```

- This design presents several barriers to mocking:
  1. Java enums cannot be extended or instantiated by Mockito
  2. The singleton pattern with static INSTANCE references creates hard-coded dependencies
  3. No dependency injection is possible with this design pattern

### Refactoring for Mockability

To enable proper unit testing with Mockito, the following refactoring would be needed:

1. Create Service interface to define contracts for the Service

```java
// Example of Sale Service interface
public interface ISaleService {

    SalesDTO getSaleByCustomerVat(int vat) throws ApplicationException;

    SalesDTO getAllSales() throws ApplicationException;

    // more methods...
}
```
    

2. Change from the current singleton implementation to a regular class that implements the ISaleService interface:
```java
// Example of Sale Service refactoring
public class SaleService implements ISaleService {

    @Override
    public SalesDTO getSaleByCustomerVat (int vat) throws ApplicationException {
		if (!isValidVAT (vat))
			throw new ApplicationException ("Invalid VAT number: " + vat);
    // more implementation...
```

3. Update all code that previously used SaleService.INSTANCE to instead use dependency injection:

```java
// Example for GetSalePageController
@WebServlet("/GetSalePageController")
public class GetSalePageController extends PageController{
	private static final long serialVersionUID = 1L;
    private final ISaleService saleService;
    
    // Dependency injection constructor 
    public GetSalePageController(ISaleService saleService) {
        this.saleService = saleService;
    }
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SalesHelper sh = new SalesHelper();
		request.setAttribute("salesHelper", sh);
		
		try{
			String vat = request.getParameter("customerVat");
			if (isInt(sh, vat, "Invalid VAT number")) {
				int vatNumber = intValue(vat);
				SalesDTO sdto = saleService.getSaleByCustomerVat(vatNumber);
				sh.fillWithSales(sdto.sales);
				request.getRequestDispatcher("SalesInfo.jsp").forward(request, response);
			}
		} catch (ApplicationException e) {
			sh.addMessage("It was not possible to fulfill the request: " + e.getMessage());
			request.getRequestDispatcher("CustomerError.jsp").forward(request, response); 
		}
	}

}
```

### Mockito Test Example

Here's an example of how to test a module that depends on SaleService using Mockito:

```java
class GetSalePageControllerTest {
    
    private GetSalePageController controller;
    
    ISaleService saleService = mock(ISaleService.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    
    private static final String VAT_STRING = "274187949";
    private static final int VAT_INT = 274187949;
    private static final SalesDTO SALES = new SalesDTO(List.of(
            new SaleDTO(1, new Date(), 0.0, "O", VAT_INT)
        ));
    
    @BeforeEach
    public void init() {
        controller = new GetSalePageController(saleService);
    }
    
    @Test
    public void spyTest() throws Exception {   
             
        // Set up the behavior of our mocks
        when(saleService.getSaleByCustomerVat(VAT_INT)).thenReturn(SALES);
        when(request.getParameter("customerVat")).thenReturn(VAT_STRING);
        when(request.getRequestDispatcher("SalesInfo.jsp")).thenReturn(dispatcher);
        
        // Act
        controller.process(request, response);
        
        // Verify that the SUT is behaving as expected
        verify(saleService).getSaleByCustomerVat(VAT_INT);
        verify(request).getRequestDispatcher("SalesInfo.jsp");
        verify(dispatcher).forward(request, response);
    }
}
```

## SUT Modifications

**Modifications related to BackLog bug fixes**

- **Bug [VVS_PROJ2_01-1]**: Lack of VAT verification when creating Sale Delivery

    A data integrity issue was discovered in the Sale Delivery creation process:

    - Fault Location: `SaleService.addSaleDelivery()` method

    - Fault: Missing validation to ensure Sale and Address belong to the same customer

    - Failure: System allowed creating deliveries where a sale from one customer could be delivered to another customer's address

    - Estimated Effort: 45 minutes

    The fault propagates through the following sequence (RIP model):
    - **Reachability**: The fault is reached when a user attempts to create a delivery with a sale id of one user and an address id of another's
       1. User submits a POST request to AddSaleDeliveryPageController with sale_id and addr_id parameters
       2. The controller calls SaleService.INSTANCE.addSaleDelivery(sale_id, addr_id)
       3. The service method retrieves the sale record, but doesn't do any checks regarding the address or the sale-address relation
    - **Infection**: The database state becomes infected when:
       1. SaleService.addSaleDelivery() creates a new SaleDeliveryRowDataGateway without checking VAT numbers match
       2. The gateway executes an INSERT statement into the saledelivery table
       3. A delivery record is created linking a sale to an address belonging to a different customer
    - **Propagation**: The infected state propagates through the application in these ways:
       1. When GetSaleDeliveryPageController retrieves deliveries, it shows incorrect customer-sale-address relationships
       2. The SalesDeliveryHelper displays the mismatched data in the UI
       3. Any business logic relying on correct customer-sale-address relationships may produce incorrect results

    The fault was resolved by:
    1. Adding a new method `getAddressById()` to `AddressRowDataGateway` to retrieve address information
    2. Modifying `SaleService.addSaleDelivery()` to verify that both the sale and address belong to the same customer by comparing their VAT numbers
    3. Throwing an `ApplicationException` if the VAT numbers don't match

    This fix ensures proper data integrity by preventing cross-customer deliveries, which can be verified by the `insertDeliveryTest` test case.

- **Bug [VVS_PROJ2_01-3]**: Sale delivery allows multiple addresses for a single Sale

    A data integrity issue was discovered in the Sale Delivery creation process:

    - Fault Location: `SaleService.addSaleDelivery()` method

    - Fault: Missing validation to prevent multiple delivery addresses for the same sale

    - Failure: System allowed a single sale to be associated with multiple delivery addresses, potentially causing delivery confusion

    - Estimated Effort: 45 minutes

    The fault propagates through the following sequence (RIP model):
    - **Reachability**: The fault is reached when a user attempts to create a delivery to a sale that already has one:
       1. User submits a POST request to AddSaleDeliveryPageController with sale_id and addr_id parameters
       2. The controller calls SaleService.INSTANCE.addSaleDelivery(sale_id, addr_id)
       3. The service method retrieves the sale and address records but doesn't check for existing deliveries with the same sale id
    - **Infection**: The database state becomes infected when:
       1. SaleService.addSaleDelivery() creates a new SaleDeliveryRowDataGateway
       2. The gateway executes an INSERT statement into the saledelivery table
       3. A new delivery record is created for a sale that already has one
    - **Propagation**: The infected state propagates through the application in these ways:
       1. GetSaleDeliveryPageController retrieves multiple deliveries for the same sale
       2. SalesDeliveryHelper displays multiple deliveries to same sale in ShowSalesDelivery.jsp
       3. Business logic that assumes one delivery per sale may produce incorrect results

    The fault was resolved by:
    1. Adding a new method `updateAddressId()` to `SaleDeliveryRowDataGateway` to modify existing delivery addresses
    2. Modifying `SaleService.addSaleDelivery()` to check if a sale already has a delivery
    3. If a delivery exists, updating its address instead of creating a new one
    4. If no delivery exists, creating a new one as before

    This fix ensures that each sale can only have one delivery address at a time, which can be verified by the `insertDeliveryTest` test case.

- **Bug [VVS_PROJ2_01-12]**: Customer deletion does not cascade to related records

    A fault was discovered in the database schema design that manifested as a failure in referential integrity. Specifically:

    - Fault Location: Database schema foreign key constraints between Customer table and its dependent tables (Sales, Addresses, Deliveries)

    - Fault: Missing ON DELETE CASCADE constraints on foreign key relationships

    - Failure: When executing customer deletion operations, orphaned records remained in dependent tables, violating referential integrity

    - Estimated Effort: 30 minutes

    The fault propagates through the following sequence (RIP model):
    - **Reachability**: The fault is reached when a user attempts to delete a customer that has more information linked to them
       1. User clicks "Remove Existing Customer" link in index.html
       2. Request is handled by RemoveCustomerPageController
       3. Controller extracts VAT number from request parameters
       4. Controller calls CustomerService.INSTANCE.removeCustomer(vatNumber)
       5. Service layer calls CustomerRowDataGateway.removeCustomer()
       6. Gateway executes DELETE SQL statement on customer table
    - **Infection**: The database state becomes infected when:
        1. The customer record is deleted from the CUSTOMER table
        2. Due to missing ON DELETE CASCADE constraints, related records in dependent tables remain orphaned
    - **Propagation**: The infected state propagates through the application in these ways:
        1. When GetSalePageController tries to retrieve sales for the deleted customer's VAT, it finds orphaned sales
        2. When GetSaleDeliveryPageController tries to retrieve deliveries for the deleted customer's VAT, it finds orphaned deliveries
        3. Any business logic relying on customer-sale-address relationships may produce incorrect results
        4. The UI may display inconsistent data when showing related records

    The issue was resolved by:
    1. Adding ON DELETE CASCADE constraints to all foreign key relationships referencing the Customer table in `createDDLHSQLDB.sql`
    2. Modifying table drop order in schema to respect referential integrity in `dropDDLHSQLDB.sql`

    This fix ensures proper cascading deletion behavior, which is verified by the `deleteCustomerSalesAreDeletedTest` test case.

**Minor modifications**
    
- To facilitate element selection in HtmlUnit tests, HTML table elements were given unique identifiers across the following JSP pages:

    - `CustomerInfo.jsp` and `addSaleDelivery.jsp`: 
        - Added `id="addressesTable`" to address tables
    - `SalesInfo.jsp`, `CloseSale.jsp`, and `addSaleDelivery.jsp`: 
        - Added `id="salesTable"` to sales tables  
    - `ShowSalesDelivery.jsp` and `SalesDeliveryInfo.jsp`: 
        - Added `id="salesDeliveryTable"` to sales delivery tables

## Conclusion

The testing implementation successfully validated the web application's functionality and uncovered several critical issues that were resolved. The most significant findings were:

1. Data integrity vulnerabilities in the delivery system that could allow cross-customer deliveries and multiple delivery addresses per sale
2. Referential integrity issues in the database schema that left orphaned records after customer deletion
3. Architectural limitations in the service layer that hindered proper unit testing

These findings led to important improvements in the application's reliability and maintainability. The bug fixes were documented in the shared backlog system, contributing to the overall quality of the project.

The combination of different testing approaches provided a robust validation of the application's behavior, while the implemented test utilities and patterns established a maintainable testing framework for future development.