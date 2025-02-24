package sut;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * "xUnit Test Patterns, Refactoring Test Code", Gerard Meszaros,
 * Addison-Wesley, 2007.
 * 
 * Refactoring a Test.
 * 
 * Software Verification and Validation Universidade de Lisboa Faculdade de
 * Ciências Mestrado em Engenharia de Informática 2015/2016
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: Invoice.java 290 2016-03-13 14:23:37Z vv $
 */
public class Invoice {

	private Customer customer;

	private List<LineItem> items = new ArrayList<LineItem>();

	public Invoice(Customer customer) {
		this.customer = customer;
	}

	public void addItemQuantity(Product product, int quantity) {
		BigDecimal unitPrice = product.getUnitPrice();
		BigDecimal percentDiscount = customer.getDiscountPercentage();
		BigDecimal hundred = new BigDecimal(100);
		BigDecimal percentage = hundred.subtract(percentDiscount).divide(hundred);
		BigDecimal extendedPrice = new BigDecimal(quantity).multiply(unitPrice.multiply(percentage));
		items.add(new LineItem(this, product, quantity, percentDiscount, extendedPrice));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	public List<LineItem> getLineItems() {
		return items;
	}
}
