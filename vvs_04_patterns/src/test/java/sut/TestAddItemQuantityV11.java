package sut;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sut.Address;
import sut.Customer;
import sut.Invoice;
import sut.LineItem;
import sut.Product;

/**
 * "xUnit Test Patterns, Refactoring Test Code", Gerard Meszaros,
 * Addison-Wesley, 2007.
 * 
 * Refactoring a Test.
 * 
 * Cleaning Up the Fixture Setup
 * 
 * This fixture setup logic still suffers from several problems. The first
 * problem is that it is difficult to tell how the fixture is related to the
 * expected outcome of the test. Do the customer’s particulars affect the
 * outcome in some way? Does the customer’s address affect the outcome? What is
 * this test really verifying?
 * 
 * The other problem is that this test exhibits Hard-Coded Test Data (see
 * Obscure Test). Given that our SUT persists all objects we create in a
 * database, the use of Hard-Coded Test Data may result in an Unrepeatable Test,
 * an Interacting Test, or a Test Run War (see Erratic Test) if any of the
 * fields of the customer, product, or invoice must be unique.
 * 
 * We can solve this problem by generating a unique value for each test and then
 * using that value to seed the attributes of the objects we create for the
 * test. This approach will ensure that the test creates different objects each
 * time the test is run.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV11.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV11 {

	@Test
	public void testAddItemQuantity_severalQuantity() {
		// Set up fixture
		Address billingAddress = createAnAddress();
		Address shippingAddress = createAnAddress();
		Customer customer = createACustomer(new BigDecimal("30"), billingAddress, shippingAddress);
		Product product = createAProduct(new BigDecimal("19.99"));
		Invoice invoice = createInvoice(customer);
		// Exercise SUT
		invoice.addItemQuantity(product, 5);
		// Verify outcome
		LineItem expected = new LineItem(invoice, product, 5, new BigDecimal("30"), new BigDecimal("69.965"));
		assertContainsExactlyOneLineItem(invoice, expected);
	}

	private Product createAProduct(BigDecimal unitPrice) {
		BigDecimal uniqueId = getUniqueNumber();
		int id = uniqueId.toBigInteger().intValue();
		String uniqueString = uniqueId.toString();
		return new Product(id, uniqueString, unitPrice);
	}

	private Customer createACustomer(BigDecimal bigDecimal, Address billingAddress, Address shippingAddress) {
		BigDecimal uniqueId = getUniqueNumber();
		int id = uniqueId.intValue();
		String name = uniqueId.toString();
		String family = getUniqueNumber().toString();
		Customer customer = new Customer(id, name, family, bigDecimal, billingAddress, shippingAddress);
		registerTestObject(customer);
		return customer;
	}

	private Address createAnAddress() {
		String string = getUniqueNumber().toString();
		String string2 = getUniqueNumber().toString();
		String string3 = getUniqueNumber().toString();
		String string4 = getUniqueNumber().toString();
		String string5 = getUniqueNumber().toString();
		Address address = new Address(string, string2, string3, string4, string5);
		registerTestObject(address);
		return address;
	}

	private Invoice createInvoice(Customer customer) {
		Invoice invoice = new Invoice(customer);
		registerTestObject(invoice);
		return invoice;
	}

	private static int lastNumber = 0;

	private BigDecimal getUniqueNumber() {
		return new BigDecimal(lastNumber++);
	}

	private List<Object> testObjects;

	@Before
	public void setUp() {
		testObjects = new ArrayList<Object>();
	}

	private void registerTestObject(Object testObject) {
		testObjects.add(testObject);
	}

	@After
	public void tearDown() {
		for (Object o : testObjects)
			try {
				deleteObject(o);
			} catch (RuntimeException e) {
				// Nothing to do; we just want to make sure
				// we continue on to the next object in the list
			}
	}

	private void assertContainsExactlyOneLineItem(Invoice invoice, LineItem expected) {
		List<LineItem> lineItems = invoice.getLineItems();
		assertEquals("number of items", 1, lineItems.size());
		LineItem actualItem = lineItems.get(0);
		assertEquals("invoice", expected, actualItem);
	}

	private void deleteObject(Object o) {
		// TODO Auto-generated method stub
	}
}
