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

		final String[][] ADDRESSES = {
			// ADDRESS, DOOR, POSTAL_CODE, LOCALITY
			{"Rua da Liberdade, 1", "1A", "1000-001", "Lisboa"},
			{"Rua da Liberdade, 2", "2A", "1000-002", "Lisboa"}
		};
		
		// first: get all customers
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		final HtmlTable table = nextPage.getHtmlElementById("clients");

		// second: get the first customer's vat
		final HtmlTableRow row = table.getRow(1); // first row is the header
		final String VAT = row.getCell(2).asText();

		System.out.println("VAT: " + VAT);

		// third: get initial number of addresses for that customer with a GET request
		java.net.URL url = new java.net.URL(APPLICATION_URL+"GetCustomerPageController");
		WebRequest request = new WebRequest(url, HttpMethod.GET);

		// set the request parameters
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new NameValuePair("vat", VAT));
		request.setRequestParameters(requestParameters);
		
		HtmlPage reportPage = webClient.getPage(request);
		assertEquals(HttpMethod.GET, reportPage.getWebResponse().getWebRequest().getHttpMethod());
		assertEquals("Customer Info", reportPage.getTitleText());

		// get the table of addresses
		HtmlTable initialTableAddresses = null;
		int initialNumberOfAddresses = 0;
		try {
			initialTableAddresses = reportPage.getHtmlElementById("addressesTable");
			initialNumberOfAddresses = initialTableAddresses.getRowCount() - 1; // -1 for header
		} catch (ElementNotFoundException e) {
			System.out.println("No addresses table found, assuming initial number of addresses is 0.");
		}
		System.out.println("Initial table of addresses:");
		System.out.println("Num of addresses: " + initialNumberOfAddresses);
		System.out.println(initialTableAddresses.asText());
	
		// fourth: add address to customer via form
		for (String[] address : ADDRESSES) {
			HtmlAnchor addAddressLink = page.getAnchorByHref("addAddressToCustomer.html");
			HtmlPage addAddressPage = (HtmlPage) addAddressLink.openLinkInNewWindow();
			assertEquals("Enter Address", addAddressPage.getTitleText());

			// place data at form
			HtmlForm addAddressForm = addAddressPage.getForms().get(0);
			HtmlInput vatInput = addAddressForm.getInputByName("vat");
			vatInput.setValueAttribute(VAT);
			HtmlInput addressInput = addAddressForm.getInputByName("address");
			addressInput.setValueAttribute(address[0]);
			HtmlInput doorInput = addAddressForm.getInputByName("door");
			doorInput.setValueAttribute(address[1]);
			HtmlInput postalCodeInput = addAddressForm.getInputByName("postalCode");
			postalCodeInput.setValueAttribute(address[2]);
			HtmlInput localityInput = addAddressForm.getInputByName("locality");
			localityInput.setValueAttribute(address[3]);

			// submit form
			HtmlInput submit = addAddressForm.getInputByName("submit");
			reportPage = submit.click();
			assertEquals("Customer Info", reportPage.getTitleText());
		}

		// fifth: check if the number of addresses increased by two and that the added addresses are in the table

		// get the table of addresses
		final HtmlTable finalTableAddresses = reportPage.getHtmlElementById("addressesTable");
		final int finalNumberOfAddresses = finalTableAddresses.getRowCount() - 1; // -1 for header
		System.out.println("\nFinal table of addresses:");
		System.out.println("Num of addresses: " + finalNumberOfAddresses);
		System.out.println(finalTableAddresses.asText());
		assertEquals(initialNumberOfAddresses + ADDRESSES.length, finalNumberOfAddresses);

		// check if the addresses are in the table
		for (int i = 0; i < ADDRESSES.length; i++) {
			final HtmlTableRow rowAddress = finalTableAddresses.getRow(initialNumberOfAddresses + i + 1); // first row is the header
			assertEquals(ADDRESSES[i][0], rowAddress.getCell(0).asText());
			assertEquals(ADDRESSES[i][1], rowAddress.getCell(1).asText());
			assertEquals(ADDRESSES[i][2], rowAddress.getCell(2).asText());
			assertEquals(ADDRESSES[i][3], rowAddress.getCell(3).asText());
		}
	
	}

	// insert two new customers and check if all the information is properly
	// listed in the List All Customers use case;
	@Test
	public void insertTwoCustomersTest() throws IOException {

		final String[][] CUSTOMERS = {
			// {"VAT", "DESIGNATION", "PHONE"}
			{"240985532", "FCUL", "217500000"},
			{"264862198", "IST", "217500001"}
		};

		// first: insert two new customers
		for (String[] customer : CUSTOMERS) {
			HtmlAnchor addCustomerLink = page.getAnchorByHref("addCustomer.html");
			HtmlPage nextPage = (HtmlPage) addCustomerLink.openLinkInNewWindow();
			assertEquals("Enter Name", nextPage.getTitleText());

			// place data at form
			HtmlForm addCustomerForm = nextPage.getForms().get(0);
			HtmlInput vatInput = addCustomerForm.getInputByName("vat");
			vatInput.setValueAttribute(customer[0]);
			HtmlInput designationInput = addCustomerForm.getInputByName("designation");
			designationInput.setValueAttribute(customer[1]);
			HtmlInput phoneInput = addCustomerForm.getInputByName("phone");
			phoneInput.setValueAttribute(customer[2]);

			// submit form
			HtmlInput submit = addCustomerForm.getInputByName("submit");

			// check if report page includes the proper values
			HtmlPage reportPage = submit.click();
			String textReportPage = reportPage.asText();
			assertEquals("Customer Info", reportPage.getTitleText());
			assertTrue(textReportPage.contains(customer[1]));
			assertTrue(textReportPage.contains(customer[2]));
		}

		// second: check if the customers are in the list of all customers
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertEquals("Customers Info", nextPage.getTitleText());

		final HtmlTable tableAfterAdd = nextPage.getHtmlElementById("clients");
		System.out.println("Table after adding:");
		System.out.println(tableAfterAdd.asText());
		for (String[] customer : CUSTOMERS) {
			assertTrue(tableAfterAdd.asText().contains(customer[0]));
			assertTrue(tableAfterAdd.asText().contains(customer[1]));
			assertTrue(tableAfterAdd.asText().contains(customer[2]));
		}

		// third: remove the customers from the database to leave it in the original state
		// at index, goto Remove case use and remove the previous client
		HtmlAnchor removeCustomerLink = page.getAnchorByHref("RemoveCustomerPageController");
		nextPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();
		assertEquals("Enter VatNumber", nextPage.getTitleText());

		for (String[] customer : CUSTOMERS) {
			HtmlForm removeCustomerForm = nextPage.getForms().get(0);
			HtmlInput vatInput = removeCustomerForm.getInputByName("vat");
			vatInput.setValueAttribute(customer[0]);
			HtmlInput submit = removeCustomerForm.getInputByName("submit");
			HtmlPage reportPage = submit.click();
			assertEquals("Enter VatNumber", reportPage.getTitleText());
			assertFalse(reportPage.asText().contains(customer[0]));
		}

		// now check that the new clients were erased
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
	@Test
	public void insertSaleTest() throws IOException {

		// first: get all customers
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		final HtmlTable table = nextPage.getHtmlElementById("clients");

		// second: get the first customer's vat
		final HtmlTableRow row = table.getRow(1); // first row is the header
		final String VAT = row.getCell(2).asText();

		System.out.println("VAT: " + VAT);

		// third: at index, goto Insert new Sale case use and insert a new sale for that customer
		HtmlAnchor addSaleLink = page.getAnchorByHref("addSale.html");
		HtmlPage addSalePage = (HtmlPage) addSaleLink.openLinkInNewWindow();
		assertEquals("New Sale", addSalePage.getTitleText());

		// place data at form (only VAT is needed)
		HtmlForm addSaleForm = addSalePage.getForms().get(0);
		HtmlInput vatInput = addSaleForm.getInputByName("customerVat");
		vatInput.setValueAttribute(VAT);

		HtmlInput submit = addSaleForm.getInputByName("submit");
		HtmlPage reportPage = submit.click();

		// fourth: check if the sale is in the list of open sales for that customer
		assertEquals("Sales Info", reportPage.getTitleText());
		final HtmlTable salesTable = reportPage.getHtmlElementById("salesTable");

		System.out.println("Sales table:");
		System.out.println(salesTable.asText());

		// get last row of the table (last sale)
		final HtmlTableRow lastRow = salesTable.getRow(salesTable.getRowCount() - 1); // first row is the header
		assertEquals("O", lastRow.getCell(3).asText()); // status is open
		assertEquals(VAT, lastRow.getCell(4).asText()); // customer vat number is the same as the one used to insert the sale
	}

}