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

import java.net.MalformedURLException;

import java.io.*;
import java.util.*;

public class TestIndex {
	
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";
	private static final int APPLICATION_NUMBER_USE_CASES = 11;

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
	
	@Test
	public void indexTest() throws Exception {
        assertEquals("WebAppDemo Menu", page.getTitleText());

        final String pageAsXml = page.asXml();
        assertTrue(pageAsXml.contains("<div class=\"w3-container w3-blue-grey w3-center w3-allerta\" id=\"body\">"));

        final String pageAsText = page.asText();
        assertTrue(pageAsText.contains("WebAppDemo Menu"));
	}
	
	@Test
	public void numberOfOptionsTest() throws Exception { 
         List<DomElement> inputs = page.getElementsById("botao2");  // get list of case uses
         assertTrue(inputs.size()==APPLICATION_NUMBER_USE_CASES);
	}
	
	@Test
	public void addCustomerClick() throws Exception {
		
		// get the button for inserting a new customer
		DomElement addCustomerButton = page.getElementsById("botao2").get(0);
		assertEquals("Insert new Customer", addCustomerButton.asText());
		// click on it
		HtmlPage addCustomerPage = addCustomerButton.click();
		
		// check if title is the one expected
		assertEquals("Enter Name", addCustomerPage.getTitleText());
		// original page is unchanged
        assertEquals("WebAppDemo Menu", page.getTitleText());
		
	}
	
	// alternatively, using Href
	@Test
	public void addCustomerHref() throws Exception {
		
		// get the link for inserting a new customer
		HtmlAnchor addCustomerLink = page.getAnchorByHref("addCustomer.html");
		// click on it
		HtmlPage nextPage = (HtmlPage) addCustomerLink.openLinkInNewWindow();
//		HtmlPage nextPage = addCustomerLink.click();
		
		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());
		// original page is unchanged
        assertEquals("WebAppDemo Menu", page.getTitleText());
		
	}

	/**
	 * Here we test two operations (insert & remove) in order to leave the database
	 * in the original state
	 * 
	 * @throws IOException
	 */
	@Test
	public void insertAndRemoveClientTest() throws IOException {
        final String VAT = "503183504";
        final String DESIGNATION = "FCUL";
        final String PHONE = "217500000";
		
		// get a specific link
		HtmlAnchor addCustomerLink = page.getAnchorByHref("addCustomer.html");
		// click on it
		HtmlPage nextPage = (HtmlPage) addCustomerLink.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());
		
		// get the page first form:
		HtmlForm addCustomerForm = nextPage.getForms().get(0);
		
		// place data at form
		HtmlInput vatInput = addCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(VAT);
		HtmlInput designationInput = addCustomerForm.getInputByName("designation");
		designationInput.setValueAttribute(DESIGNATION);
		HtmlInput phoneInput = addCustomerForm.getInputByName("phone");
		phoneInput.setValueAttribute(PHONE);
		// submit form
		HtmlInput submit = addCustomerForm.getInputByName("submit");
		
		// check if report page includes the proper values
		HtmlPage reportPage = submit.click();
		
		// ----- alternative, use a POST request:
		// (ref: https://stackoverflow.com/questions/21628614/)
//		java.net.URL requestURL = new java.net.URL(APPLICATION_URL+"AddCustomerPageController");
//		WebRequest request = new WebRequest(requestURL, HttpMethod.POST);
		
		// Option 1: Set request body
//		String formData = String.format("vat=%s&designation=%s&phone=%s", VAT, DESIGNATION, PHONE);
//		request.setRequestBody(formData);
		
		// Option 2: Set request parameters
//		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
//		requestParameters.add(new NameValuePair("vat", VAT));
//		requestParameters.add(new NameValuePair("designation", DESIGNATION));
//		requestParameters.add(new NameValuePair("phone", PHONE));
//		request.setRequestParameters(requestParameters);
		
//		HtmlPage reportPage = webClient.getPage(request);
		
		// ----- end alternative

		String textReportPage = reportPage.asText();
//		System.out.println(textReportPage);  // check if it is the report page
		assertEquals("Customer Info", reportPage.getTitleText());
		assertTrue(textReportPage.contains(DESIGNATION));
		assertTrue(textReportPage.contains(PHONE));
		
		// at index, goto Remove case use and remove the previous client
		HtmlAnchor removeCustomerLink = page.getAnchorByHref("RemoveCustomerPageController");
		nextPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();
		assertTrue(nextPage.asText().contains(VAT));
		
		HtmlForm removeCustomerForm = nextPage.getForms().get(0);
		vatInput = removeCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(VAT);
		submit = removeCustomerForm.getInputByName("submit");
		reportPage = submit.click();
		assertFalse(reportPage.asText().contains(VAT));
		
		// now check that the new client was erased
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertFalse(nextPage.asText().contains(VAT));
	}

	// not testing, just to show how to access tables inside the HTML
	@Test
	public void tablesTest() throws MalformedURLException {
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		
		final HtmlTable table = nextPage.getHtmlElementById("clients");
		System.out.println("---------------------------------");
		for (final HtmlTableRow row : table.getRows()) {
		    System.out.println("Found row");
		    for (final HtmlTableCell cell : row.getCells()) {
		       System.out.println("   Found cell: " + cell.asText());
		    }
		}
		System.out.println("---------------------------------");
	}
	
	// Eg of testing a GET request.
	// For a POST request cf. stackoverflow.com/questions/30687614
	@Test
	public void parametersGetTest() throws IOException {
		
		// Build a GET request
		java.net.URL url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
		WebRequest request = new WebRequest(url, HttpMethod.GET);

		// Set the request parameters
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new NameValuePair("vat", "197672337"));
		request.setRequestParameters(requestParameters);
		
		HtmlPage reportPage = webClient.getPage(request);
		assertEquals(HttpMethod.GET, reportPage.getWebResponse().getWebRequest().getHttpMethod());		
		
//		System.out.println(reportPage.asText());
		assertTrue(reportPage.asXml().contains("JOSE FARIA"));
		assertFalse(reportPage.asXml().contains("LUIS SANTOS"));
		
		// to check GET parameter's
//		List<NameValuePair> parameters = reportPage.getWebResponse().getWebRequest().getRequestParameters();
//		for (NameValuePair parameter : parameters) {
//			System.out.println(parameter.getName() + " = " + parameter.getValue());
//		}
	}

	// insert two new addresses for an existing customer, then the table of
	// addresses of that client includes those addresses and its total row size
	// increases by two;
	@Test
	public void addTwoAddressesToCustomerTest() throws IOException {

		System.out.println("\n\n----------------- addTwoAddressesToCustomerTest -----------------");

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
		
		HtmlPage reportPage = webClient.getPage(getRequest);
		assertEquals(HttpMethod.GET, reportPage.getWebResponse().getWebRequest().getHttpMethod());
		assertEquals("Customer Info", reportPage.getTitleText());

		// get the table of addresses
		HtmlTable initialTableAddresses = null;
		int initialNumberOfAddresses = 0;
		try {
			initialTableAddresses = reportPage.getHtmlElementById("addressesTable");
			initialNumberOfAddresses = initialTableAddresses.getRowCount() - 1;
			System.out.println("Initial table of addresses:");
			System.out.println("Num of addresses: " + initialNumberOfAddresses);
			System.out.println(initialTableAddresses.asText());
		} catch (ElementNotFoundException e) {
			System.out.println("No addresses table found, assuming initial number of addresses is 0.");
		}

		// third: add address to customer
		HtmlPage clientInfoPage = null;
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

		System.out.println("\n\n----------------- insertTwoCustomersTest -----------------");

		final String[][] CUSTOMERS = {
			// {"VAT", "DESIGNATION", "PHONE"}
			{"240985532", "FCUL", "217500000"},
			{"264862198", "IST", "217500001"}
		};

		// first: insert two new customers
		for (String[] customer : CUSTOMERS) {
			addCustomer(customer[0], customer[1], customer[2]);
		}

		// second: check if the customers are in the list of all customers
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertEquals("Customers Info", nextPage.getTitleText());

		final HtmlTable tableAfterAdd = nextPage.getHtmlElementById("clients");
		System.out.println("\nTable after adding:");
		System.out.println(tableAfterAdd.asText());
		for (String[] customer : CUSTOMERS) {
			assertTrue(tableAfterAdd.asText().contains(customer[0]));
			assertTrue(tableAfterAdd.asText().contains(customer[1]));
			assertTrue(tableAfterAdd.asText().contains(customer[2]));
		}

		// third: remove the customers from the database to leave it in the original state
		for (String[] customer : CUSTOMERS)
			removeCustomer(customer[0]);

		// now check that the new clients were erased from List All Customers
		nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertEquals("Customers Info", nextPage.getTitleText());

		final HtmlTable tableAfterRemoving = nextPage.getHtmlElementById("clients");
		System.out.println("\nTable after removing:");
		System.out.println(tableAfterRemoving.asText());
		for (String[] customer : CUSTOMERS) {
			assertFalse(nextPage.asText().contains(customer[0]));
		}

	}

	// a new sale will be listed as an open sale for the respective customer;
	// after closing a sale, it will be listed as closed;
	@Test
	public void insertSaleCloseSaleTest() throws IOException {

		System.out.println("\n\n----------------- insertSaleCloseSaleTest -----------------");

		// first: get existing customer vat
		final String VAT = getExistingCustomerVAT();

		// second: add a new sale to the customer
		HtmlPage salesInfoPage = addSale(VAT);
		final HtmlTable cSalesTable = salesInfoPage.getHtmlElementById("salesTable");
		final HtmlTableRow insertedSale = cSalesTable.getRow(cSalesTable.getRowCount() - 1); 

		// third: close the sale
		HtmlAnchor closeSaleLink = page.getAnchorByHref("UpdateSaleStatusPageControler");
		HtmlPage closeSalePage = (HtmlPage) closeSaleLink.openLinkInNewWindow();
		assertEquals("Enter Sale Id", closeSalePage.getTitleText());

		// place data at form
		HtmlForm closeSaleForm = closeSalePage.getForms().get(0);
		HtmlInput saleIdInput = closeSaleForm.getInputByName("id");
		saleIdInput.setValueAttribute(insertedSale.getCell(0).asText()); // sale id is the first cell of the row
		HtmlInput submit = closeSaleForm.getInputByName("submit");
		closeSalePage = submit.click();

		// fourth: check if the sale is now closed
		assertEquals("Enter Sale Id", closeSalePage.getTitleText());
		closeSaleForm = closeSalePage.getForms().get(0);
		final HtmlTable salesTable = closeSalePage.getHtmlElementById("salesTable");
		final HtmlTableRow closedSale = salesTable.getRow(salesTable.getRowCount() - 1);
		assertEquals("C", closedSale.getCell(3).asText()); // status is closed
		assertEquals(VAT, closedSale.getCell(4).asText()); // customer vat number is the same as the one used to insert the sale
	}
	
	// create a new customer, create a new sale for her, insert a delivery for
	// that sale and then show the sale delivery. Check that all intermediate
	// pages have the expected information.
	@Test
	public void insertDeliveryTest() throws IOException {

		System.out.println("\n\n----------------- insertDeliveryTest -----------------");

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
	
	// adds a new customer to the database and checks if the information is properly
	// listed in the List All Customers use case;
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