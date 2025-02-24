package sut;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
 * Cleaning Up the Fixture Teardown Logic
 * 
 * In this test, the finally block calls the deleteObject method on each of the
 * objects created by the test. Unfortunately, this code suffers from a fatal
 * flaw. Have you noticed it yet?
 * 
 * Things could go wrong during the teardown itself. What happens if the first
 * call to deleteObject throws an exception? As coded here, none of the other
 * calls to deleteObject would be executed.
 * 
 * The best solution is to use a Fresh Fixture (page 311) but to avoid writ- ing
 * teardown code for every test. To do so, we can use an in-memory fixture that
 * is automatically garbage collected.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV07.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV07 {

	@Test
	public void testAddItemQuantity_severalQuantity() {
		Address billingAddress = null;
		Address shippingAddress = null;
		Customer customer = null;
		Product product = null;
		Invoice invoice = null;
		try {
			// Set up fixture
			setUp();
			billingAddress = new Address("1222 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
			registerTestObject(billingAddress);
			shippingAddress = new Address("1333 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
			registerTestObject(shippingAddress);
			customer = new Customer(99, "John", "Doe", new BigDecimal("30"), billingAddress, shippingAddress);
			registerTestObject(customer);
			product = new Product(88, "SomeWidget", new BigDecimal("19.99"));
			registerTestObject(product);
			invoice = new Invoice(customer);
			registerTestObject(invoice);
			// Exercise SUT
			invoice.addItemQuantity(product, 5);
			// Verify outcome
			LineItem expected = new LineItem(invoice, product, 5, new BigDecimal("30"), new BigDecimal("69.965"));
			assertContainsExactlyOneLineItem(invoice, expected);
		} finally {
			// Teardown
			tearDown();
		}
	}

	private List<Object> testObjects;

	private void setUp() {
		testObjects = new ArrayList<Object>();
	}

	private void registerTestObject(Object testObject) {
		testObjects.add(testObject);
	}

	private void tearDown() {
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
