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
 * Take each of the calls to a constructor, take the subsequent call to
 * registerTestObject, and use an Extract Method refactoring to define a
 * Creation Method (page 415). This will make the test a bit simpler to read and
 * write. The use of Creation Methods has another advantage: They encapsulate
 * the API of the SUT and reduce the test maintenance effort when the various
 * object constructors change by allowing us to modify only a single place
 * rather than having to change each test.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV10.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV10 {

	@Test
	public void testAddItemQuantity_severalQuantity() {
		// Set up fixture
		Address billingAddress = createAddress("1222 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
		Address shippingAddress = createAddress("1333 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
		Customer customer = createCustomer(99, "John", "Doe", new BigDecimal("30"), billingAddress, shippingAddress);
		Product product = createProduct(88, "SomeWidget", new BigDecimal("19.99"));
		Invoice invoice = createInvoice(customer);
		// Exercise SUT
		invoice.addItemQuantity(product, 5);
		// Verify outcome
		LineItem expected = new LineItem(invoice, product, 5, new BigDecimal("30"), new BigDecimal("69.965"));
		assertContainsExactlyOneLineItem(invoice, expected);
	}

	private Invoice createInvoice(Customer customer) {
		Invoice invoice = new Invoice(customer);
		registerTestObject(invoice);
		return invoice;
	}

	private Product createProduct(int i, String string, BigDecimal bigDecimal) {
		Product product = new Product(i, string, bigDecimal);
		registerTestObject(product);
		return product;
	}

	private Customer createCustomer(int i, String string, String string2, BigDecimal bigDecimal, Address billingAddress,
			Address shippingAddress) {
		Customer customer = new Customer(i, string, string2, bigDecimal, billingAddress, shippingAddress);
		registerTestObject(customer);
		return customer;
	}

	private Address createAddress(String string, String string2, String string3, String string4, String string5) {
		Address address = new Address(string, string2, string3, string4, string5);
		registerTestObject(address);
		return address;
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
