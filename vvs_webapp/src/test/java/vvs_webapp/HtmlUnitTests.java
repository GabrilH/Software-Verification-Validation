package vvs_webapp;

import static org.junit.Assert.*;
import org.junit.*;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import java.io.*;
import java.util.*;
public class HtmlUnitTests {
    
    private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";

	private static WebClient webClient;
	private static HtmlPage page;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		webClient = new WebClient(BrowserVersion.getDefault());
		
		// possible configurations needed to prevent JUnit tests to fail for complex HTML pages
        webClient.setJavaScriptTimeout(15000);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        
		page = webClient.getPage(APPLICATION_URL);
		assertEquals(200, page.getWebResponse().getStatusCode()); // OK status
	}
	
	@AfterClass
	public static void takeDownClass() {
		webClient.close();
	}

    // insert two new addresses for an existing customer, then the table of
	// addresses of that client includes those addresses and its total row size
	// increases by two;
	@Test
	public void addTwoAddressesToCustomerTest() throws IOException {

		System.out.println("\n----------------- addTwoAddressesToCustomerTest -----------------");

		final String[][] ADDRESSES = {
			// ADDRESS, DOOR, POSTAL_CODE, LOCALITY
			{"Rua da Liberdade, 1", "1A", "1000-001", "Lisboa"},
			{"Rua da Liberdade, 2", "2A", "1000-002", "Lisboa"}
		};
		
		// first: get existing customer vat
		final String VAT = getExistingCustomerVAT();

		// second: get initial number of addresses for that customer with a GET request
		java.net.URL url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
		WebRequest getRequest = new WebRequest(url, HttpMethod.GET);

		// set the request parameters
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new NameValuePair("vat", VAT));
		getRequest.setRequestParameters(requestParameters);
		
		HtmlPage clientInfoPage = webClient.getPage(getRequest);
		assertEquals(HttpMethod.GET, clientInfoPage.getWebResponse().getWebRequest().getHttpMethod());
		assertEquals("Customer Info", clientInfoPage.getTitleText());

		// get the table of addresses
		HtmlTable initialTableAddresses = null;
		int initialNumberOfAddresses = 0;
		try {
			initialTableAddresses = clientInfoPage.getHtmlElementById("addressesTable");
			initialNumberOfAddresses = initialTableAddresses.getRowCount() - 1;
			System.out.println("Initial table of addresses:");
			System.out.println("Num of addresses: " + initialNumberOfAddresses);
			System.out.println(initialTableAddresses.asText());
		} catch (ElementNotFoundException e) {
			System.out.println("No addresses table found, assuming initial number of addresses is 0.");
		}

		// third: add address to customer
		for (String[] address : ADDRESSES)
			clientInfoPage = addAddressToCustomer(VAT, address[0], address[1], address[2], address[3]);

		// fourth: check if the number of addresses increased by two
		// (other checks are done in the addAddressToCustomer method)
		final HtmlTable finalTableAddresses = clientInfoPage.getHtmlElementById("addressesTable");
		final int finalNumberOfAddresses = finalTableAddresses.getRowCount() - 1;
		System.out.println("\nFinal Num of addresses: " + finalNumberOfAddresses);
		assertEquals(initialNumberOfAddresses + ADDRESSES.length, finalNumberOfAddresses);
	}

	// insert two new customers and check if all the information is properly
	// listed in the List All Customers use case;
	@Test
	public void insertTwoCustomersTest() throws IOException {

		System.out.println("\n----------------- insertTwoCustomersTest -----------------");

		final String[][] CUSTOMERS = {
			// {"VAT", "DESIGNATION", "PHONE"}
			{"503183504", "FCUL", "217500000"},
			{"264862198", "IST", "217500001"}
		};

		// first: insert two new customers
		for (String[] customer : CUSTOMERS) {
			addCustomer(customer[0], customer[1], customer[2]);
		}

		// second: check if the customers are in the list of all customers
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage customersInfoPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertEquals("Customers Info", customersInfoPage.getTitleText());

		final HtmlTable tableAfterAdd = customersInfoPage.getHtmlElementById("clients");
		System.out.println("\nTable after adding:");
		System.out.println(tableAfterAdd.asText());
		for (String[] customer : CUSTOMERS) {
			assertTrue(tableAfterAdd.asText().contains(customer[0]));
			assertTrue(tableAfterAdd.asText().contains(customer[1]));
			assertTrue(tableAfterAdd.asText().contains(customer[2]));
		}

		// end: remove the customers from the database to leave it in the original state
		for (String[] customer : CUSTOMERS)
			removeCustomer(customer[0]);
	}

	// a new sale will be listed as an open sale for the respective customer;
    @Test
    public void insertSaleTest() throws IOException {

        System.out.println("\n----------------- insertSaleTest -----------------");

        // first: add temporary customer
		final String VAT = addTempCustomer();

        // second: add a new sale to the customer
		HtmlPage salesInfoPage = addSale(VAT);
		final HtmlTable cSalesTable = salesInfoPage.getHtmlElementById("salesTable");
		final HtmlTableRow insertedSale = cSalesTable.getRow(cSalesTable.getRowCount() - 1); 

        // third: check if the sale is listed as open
        assertEquals("O", insertedSale.getCell(3).asText());
        assertEquals(VAT, insertedSale.getCell(4).asText());

		// end: remove the customer from the database to leave it in the original state
		removeCustomer(VAT);
    }


	// after closing a sale, it will be listed as closed;
	@Test
	public void closeSaleTest() throws IOException {

		System.out.println("\n----------------- closeSaleTest -----------------");

		// first: add temporary customer
		final String VAT = addTempCustomer();

		// second: add a new sale to the customer
		HtmlPage salesInfoPage = addSale(VAT);
		final HtmlTable cSalesTable = salesInfoPage.getHtmlElementById("salesTable");
		final HtmlTableRow insertedSale = cSalesTable.getRow(cSalesTable.getRowCount() - 1); 
		String saleId = insertedSale.getCell(0).asText();

		// third: close the sale
		closeSale(saleId);

		// end: remove the customer from the database to leave it in the original state
		removeCustomer(VAT);
	}
	
	// create a new customer, create a new sale for her, insert a delivery for
	// that sale and then show the sale delivery. Check that all intermediate
	// pages have the expected information.
	@Test
	public void insertDeliveryTest() throws IOException {

		System.out.println("\n----------------- insertDeliveryTest -----------------");

		final String VAT = "238720268";
		final String DESIGNATION = "Jorge Ferreira";
		final String PHONE = "217512345";

		final String ADDRESS = "Largo do Campo Pequeno, 1";
		final String DOOR = "20";
		final String POSTAL_CODE = "1755-020";
		final String LOCALITY = "Porto";

		// first: create a new customer
		addCustomer(VAT, DESIGNATION, PHONE);

		// second: add an address to that customer
		addAddressToCustomer(VAT, ADDRESS, DOOR, POSTAL_CODE, LOCALITY);

		// third: create a new sale for that customer
		addSale(VAT);

		// fourth: go to the page Add Sale Delivery Page by GET request
		java.net.URL url = new java.net.URL(APPLICATION_URL + "AddSaleDeliveryPageController");
		WebRequest getRequest = new WebRequest(url, HttpMethod.GET);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new NameValuePair("vat", VAT));
		getRequest.setRequestParameters(requestParameters);
		HtmlPage addSaleDeliveryPage = webClient.getPage(getRequest);
		assertEquals("Enter Name", addSaleDeliveryPage.getTitleText());

		// fifth: get most recent sale id and last address id
		final HtmlTable salesTable = addSaleDeliveryPage.getHtmlElementById("salesTable");
		final String saleId = salesTable.getRow(salesTable.getRowCount() - 1).getCell(0).asText();
		final HtmlTable addressesTable = addSaleDeliveryPage.getHtmlElementById("addressesTable");
		final String addressId = addressesTable.getRow(addressesTable.getRowCount() - 1).getCell(0).asText();

		System.out.println("\nInfo to delivery");
		System.out.println("Sale id: " + saleId);
		System.out.println("Address id: " + addressId);

		// sixth: insert a new delivery for that sale and address via POST request

		// Set the request body with the necessary information
		WebRequest postRequest = new WebRequest(url, HttpMethod.POST);
		String formData = String.format("sale_id=%s&addr_id=%s", saleId, addressId);
		postRequest.setRequestBody(formData);

		// Execute the request and get the response page
		HtmlPage salesDeliveryInfoPage = webClient.getPage(postRequest);
		final HtmlTable salesDeliveryTable = salesDeliveryInfoPage.getHtmlElementById("salesDeliveryTable");
		final HtmlTableRow insertedDelivery = salesDeliveryTable.getRow(salesDeliveryTable.getRowCount() - 1);	

		// Prints
		System.out.println("\nSales delivery table after adding: ");
		System.out.println(insertedDelivery.asText());
		System.out.println(salesDeliveryTable.asText());

		// Validate the response
		assertEquals("Sales Info", salesDeliveryInfoPage.getTitleText());
		assertEquals(saleId, insertedDelivery.getCell(1).asText());
		assertEquals(addressId, insertedDelivery.getCell(2).asText());

		// end: remove the customer from the database to leave it in the original state
		removeCustomer(VAT);
	}

	/*
	 * Helper methods to be used in the tests.
	 */

	// adds a temporary customer to the database and returns its VAT number;
	private String addTempCustomer() throws IOException {
		
		final String VAT = "207169276";
		final String DESIGNATION = "TEMP";
		final String PHONE = "966666666";

		addCustomer(VAT, DESIGNATION, PHONE);

		return VAT;
	}
	
	// adds a new customer to the database and checks if the information is properly
	// listed in the Customer Info page;
	private HtmlPage addCustomer(String vat, String designation, String phone) throws IOException {
		// Build a POST request
		java.net.URL url = new java.net.URL(APPLICATION_URL + "AddCustomerPageController");
		WebRequest request = new WebRequest(url, HttpMethod.POST);

		// Set the request body with the necessary information
		String formData = String.format("vat=%s&designation=%s&phone=%s", vat, designation, phone);
		request.setRequestBody(formData);

		// Execute the request and get the response page
		HtmlPage clientInfoPage = webClient.getPage(request);
		String pageAsText = clientInfoPage.asText();

		// Prints
		System.out.println("Added customer with VAT: " + vat + ", designation: " + designation + ", phone: " + phone);

		// Validate the response
		assertEquals("Customer Info", clientInfoPage.getTitleText());
		assertTrue(pageAsText.contains(designation));
		assertTrue(pageAsText.contains(phone));

		return clientInfoPage;
	}

	// adds a new sale to the database and checks if the information is properly
	// listed in the List All Sales use case;
	private HtmlPage addSale(String vat) throws IOException {
		// Build a POST request
		java.net.URL url = new java.net.URL(APPLICATION_URL + "AddSalePageController");
		WebRequest request = new WebRequest(url, HttpMethod.POST);

		// Set the request body with the necessary information
		String formData = String.format("customerVat=%s", vat);
		request.setRequestBody(formData);

		// Execute the request and get the response page
		HtmlPage salesInfoPage = webClient.getPage(request);
		final HtmlTable cSalesTable = salesInfoPage.getHtmlElementById("salesTable");
		final HtmlTableRow insertedSale = cSalesTable.getRow(cSalesTable.getRowCount() - 1);

		// Prints
		System.out.println("\nSales table after adding: ");
		System.out.println(insertedSale.asText());
		System.out.println(cSalesTable.asText());

		// Validate the response
		assertEquals("Sales Info", salesInfoPage.getTitleText());
		assertEquals("O", insertedSale.getCell(3).asText());
		assertEquals(vat, insertedSale.getCell(4).asText());

		return salesInfoPage;
	}

	private HtmlPage closeSale(String saleId) throws IOException {
		// Build a POST request
		java.net.URL url = new java.net.URL(APPLICATION_URL + "UpdateSaleStatusPageControler");
		WebRequest request = new WebRequest(url, HttpMethod.POST);

		// Set the request body with the necessary information
		String formData = String.format("id=%s", saleId);
		request.setRequestBody(formData);

		// Execute the request and get the response page
		HtmlPage closeSalePage = webClient.getPage(request);
		final HtmlTable salesTable = closeSalePage.getHtmlElementById("salesTable");
		final HtmlTableRow closedSale = salesTable.getRow(salesTable.getRowCount() - 1);

		// Prints
		System.out.println("Closed sale with ID: " + saleId);

		// Validate the response
		assertEquals("Enter Sale Id", closeSalePage.getTitleText());
		assertEquals("C", closedSale.getCell(3).asText());
		assertEquals(saleId, closedSale.getCell(0).asText());

		return closeSalePage;
	}

	// adds a new address to the database and checks if the information is properly
	// listed in the Customer Info page;
	private HtmlPage addAddressToCustomer(String vat, String address, String door, String postalCode, String locality) throws IOException {

		// Build a POST request
		java.net.URL url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
		WebRequest request = new WebRequest(url, HttpMethod.POST);

		// Set the request body with the necessary information
		String formData = String.format("vat=%s&address=%s&door=%s&postalCode=%s&locality=%s", vat, address, door, postalCode, locality);
		request.setRequestBody(formData);

		// Execute the request and get the response page
		HtmlPage clientInfoPage = webClient.getPage(request);
		HtmlTable addressesTable = clientInfoPage.getHtmlElementById("addressesTable");
		final HtmlTableRow insertedAddress = addressesTable.getRow(addressesTable.getRowCount() - 1);

		// Prints
		System.out.println("\nAddresses table after adding: ");
		System.out.println(insertedAddress.asText());
		System.out.println(addressesTable.asText());

		// Validate the response
		assertEquals("Customer Info", clientInfoPage.getTitleText());
		assertEquals(address, insertedAddress.getCell(0).asText());
		assertEquals(door, insertedAddress.getCell(1).asText());
		assertEquals(postalCode, insertedAddress.getCell(2).asText());
		assertEquals(locality, insertedAddress.getCell(3).asText());

		return clientInfoPage;
	}

	// removes a customer from the database and checks if the information is properly
	// removed from the Remove Customer page;
	private HtmlPage removeCustomer(String vat) throws IOException {
		// Build a POST request
		java.net.URL url = new java.net.URL(APPLICATION_URL + "RemoveCustomerPageController");
		WebRequest request = new WebRequest(url, HttpMethod.POST);

		// Set the request body with the necessary information
		String formData = String.format("vat=%s", vat);
		request.setRequestBody(formData);

		// Execute the request and get the response page
		HtmlPage removeCustomerPage = webClient.getPage(request);

		// Prints
		System.out.println("Removed customer with VAT: " + vat);

		// Validate the response
		assertEquals("Enter VatNumber", removeCustomerPage.getTitleText());
		assertFalse(removeCustomerPage.asText().contains(vat));

		return removeCustomerPage;
	}

	// gets the VAT of an existing customer from the List All Customers page;
	private String getExistingCustomerVAT() throws IOException {
		final int INDEX = 1;
		// first: get all customers
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		final HtmlTable table = nextPage.getHtmlElementById("clients");

		// second: get the i'th customer's vat
		final HtmlTableRow row = table.getRow(INDEX); // first row is the header
		String vat = row.getCell(2).asText();
		System.out.println("Selected VAT: " + vat);
		return vat;
	}
}
