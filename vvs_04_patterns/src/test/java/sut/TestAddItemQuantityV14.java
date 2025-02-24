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
 * One final point: Where did the value “69.96” come from? If this value comes
 * from the output of some reference system, we should say so. Because it was
 * just manually calculated and typed into the test, we can show the calculation
 * in the test for the test reader’s benefit.
 * 
 * Software Verification and Validation, Universidade de Lisboa, Faculdade de
 * Ciências, Mestrado em Engenharia de Informática
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id$
 */
public class TestAddItemQuantityV14 {

	private static final BigDecimal UNIT_PRICE = new BigDecimal("19.99");
	private static final BigDecimal DISCOUNT = new BigDecimal("30");
	private static final int QUANTITY = 5;

	@Test
	public void testAddItemQuantity_severalQuantity() {
		// Set up fixture
		Customer customer = createACustomer(DISCOUNT);
		Product product = createAProduct(UNIT_PRICE);
		Invoice invoice = createInvoice(customer);
		// Exercise SUT
		invoice.addItemQuantity(product, QUANTITY);
		// Verify outcome
		final BigDecimal hundred = new BigDecimal(100);
		final BigDecimal percentage = hundred.subtract(DISCOUNT).divide(hundred);
		final BigDecimal EXTENDED_PRICE = new BigDecimal(QUANTITY).multiply(UNIT_PRICE.multiply(percentage));
		LineItem expected = new LineItem(invoice, product, QUANTITY, DISCOUNT, EXTENDED_PRICE);
		assertContainsExactlyOneLineItem(invoice, expected);
	}

	private Product createAProduct(BigDecimal unitPrice) {
		BigDecimal uniqueId = getUniqueNumber();
		int id = uniqueId.toBigInteger().intValue();
		String uniqueString = uniqueId.toString();
		return new Product(id, uniqueString, unitPrice);
	}

	private Customer createACustomer(BigDecimal bigDecimal) {
		BigDecimal uniqueId = getUniqueNumber();
		int id = uniqueId.intValue();
		String name = uniqueId.toString();
		String family = getUniqueNumber().toString();
		Address billingAddress = createAnAddress();
		Address shippingAddress = createAnAddress();
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
