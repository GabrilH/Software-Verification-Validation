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
 * A simple problem to fix is the obtuse assertion on the very last line.
 * Calling assertTrue with an argument of false should always result in a test
 * failure, so why don’t we say so directly? Let’s change this to a call to
 * fail.
 * 
 * We can think of this move as an Extract Method [Fowler] refactoring, because
 * we are replacing the Stated Outcome Assertion (see Assertion Method on page
 * 362) with a hard-coded parameter with a more intent-revealing call to a
 * Single Outcome Assertion (see Assertion Method) method that encapsulates the
 * call.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV02.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV02 {

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
				LineItem actItem = lineItems.get(0);
				assertEquals("inv", invoice, actItem.getInv());
				assertEquals("prod", product, actItem.getProd());
				assertEquals("quant", 5, actItem.getQuantity());
				assertEquals("discount", new BigDecimal("30"), actItem.getPercentDiscount());
				assertEquals("unit price", new BigDecimal("19.99"), actItem.getUnitPrice());
				assertEquals("extended", new BigDecimal("69.965"), actItem.getExtendedPrice());
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
