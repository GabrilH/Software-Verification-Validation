package sut;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
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
 * Why do we have an if statement in a test? If there are several paths through
 * a test, how do we know which one is actually being executed? It would be a
 * lot better if we could eliminate this Conditional Test Logic (page 200).
 * Luckily for us, the pattern Guard Assertion (page 490) is designed to handle
 * exactly this case. We simply use a Replace Conditional with Guard Clause
 * [Fowler] refactoring to replace the if ... else fail() ... sequence with an
 * assertion on the same condition.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV05a.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV05a {

	@Test
	public void testAddItemQuantity_severalQuantity() {
		Address billingAddress = null;
		Address shippingAddress = null;
		Customer customer = null;
		Product product = null;
		Invoice invoice = null;
		try {
			// Set up fixture
			billingAddress = new Address("1222 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
			shippingAddress = new Address("1333 1st St SW", "Calgary", "Alberta", "T2N 2V2", "Canada");
			customer = new Customer(99, "John", "Doe", new BigDecimal("30"), billingAddress, shippingAddress);
			product = new Product(88, "SomeWidget", new BigDecimal("19.99"));
			invoice = new Invoice(customer);
			// Exercise SUT
			invoice.addItemQuantity(product, 5);
			// Verify outcome
			List<LineItem> lineItems = invoice.getLineItems();
			assertEquals("number of items", 1, lineItems.size());
			LineItem expected = new LineItem(invoice, product, 5, new BigDecimal("30"), new BigDecimal("69.965"));
			LineItem actualItem = lineItems.get(0);
			assertEquals("invoice", expected, actualItem);
		} finally {
			// Teardown
			deleteObject(invoice);
			deleteObject(product);
			deleteObject(customer);
			deleteObject(billingAddress);
			deleteObject(shippingAddress);
		}
	}

	private void deleteObject(Object o) {
		// TODO Auto-generated method stub
	}
}
