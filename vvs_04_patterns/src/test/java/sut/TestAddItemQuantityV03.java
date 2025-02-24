package sut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

/**
 * "xUnit Test Patterns, Refactoring Test Code", Gerard Meszaros,
 * Addison-Wesley, 2007.
 * 
 * Refactoring a Test.
 * 
 * Of course, this set of assertions suffers from several more problems. For
 * example, why do we need so many of them? It turns out that many of these
 * assertions are testing fields set by the constructor for the LineItem, which
 * is itself covered by another unit test. So why repeat these assertions here?
 * It will just create more test code to maintain when the logic changes.
 *
 * One solution is to use a single assertion on an Expected Object (see State
 * Verification on page 462) instead of one assertion per object field.
 *
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV03.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV03 {

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
			if (lineItems.size() == 1) {
				LineItem expected = new LineItem(invoice, product, 5, new BigDecimal("30"), new BigDecimal("69.965"));
				LineItem actualItem = lineItems.get(0);
				assertEquals("invoice", expected, actualItem);
			} else {
				fail("Invoice should have 1 item");
			}
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
