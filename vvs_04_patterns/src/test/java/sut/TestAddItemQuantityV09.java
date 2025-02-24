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
 * Cleaning Up the Fixture Teardown Logic
 * 
 * Do we need to declare the variables and initialize them to null, only to
 * reinitialize them later? This action was needed with the original test
 * because they had to be accessible in the finally block; now that we have
 * removed this block, we can combine the declaration with the initialization.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV09.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV09 {

	@Test
	public void testAddItemQuantity_severalQuantity() {
		// Set up fixture
		Address billingAddress = new Address("1222 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
		registerTestObject(billingAddress);
		Address shippingAddress = new Address("1333 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
		registerTestObject(shippingAddress);
		Customer customer = new Customer(99, "John", "Doe", new BigDecimal("30"), billingAddress, shippingAddress);
		registerTestObject(customer);
		Product product = new Product(88, "SomeWidget", new BigDecimal("19.99"));
		registerTestObject(product);
		Invoice invoice = new Invoice(customer);
		registerTestObject(invoice);
		// Exercise SUT
		invoice.addItemQuantity(product, 5);
		// Verify outcome
		LineItem expected = new LineItem(invoice, product, 5, new BigDecimal("30"), new BigDecimal("69.965"));
		assertContainsExactlyOneLineItem(invoice, expected);
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
