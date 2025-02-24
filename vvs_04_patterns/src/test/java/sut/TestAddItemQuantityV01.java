package sut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

/**
 * "xUnit Test Patterns, Refactoring Test Code", Gerard Meszaros,
 * Addison-Wesley, 2007.
 * 
 * Refactoring a Test.
 * 
 * This test is quite long1 and is much more complicated than it needs to be.
 * This Obscure Test (page 186) is difficult to understand because the sheer
 * number of lines in the test makes it hard to see the big picture. It also
 * suffers from a number of other problems that we will address individually.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: TestAddItemQuantityV01.java 290 2016-03-13 14:23:37Z vv $
 */
public class TestAddItemQuantityV01 {

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
				assertTrue("Invoice should have 1 item", false);
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
