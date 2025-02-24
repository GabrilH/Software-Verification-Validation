package sut;

import java.math.BigDecimal;

/**
 * "xUnit Test Patterns, Refactoring Test Code", Gerard Meszaros,
 * Addison-Wesley, 2007.
 * 
 * Refactoring a Test.
 * 
 * Software Verification and Validation
 * Universidade de Lisboa
 * Faculdade de Ciências
 * Mestrado em Engenharia de Informática
 * 2015/2016
 * 
 * @author Gerard Meszaros, Vasco T. Vasconcelos
 * @version $Id: Customer.java 290 2016-03-13 14:23:37Z vv $
 */
public class Customer {

	private Address billingAddress;
	private int id;
	private String given;
	private String family;
	private BigDecimal discountPercentage;
	private Address shippingAddress;

	public Customer(int id, String given, String family, BigDecimal discountPercentage, Address billingAddress,
			Address shippingAddress) {
		this.id = id;
		this.given = given;
		this.family = family;
		this.discountPercentage = discountPercentage;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (discountPercentage == null) {
			if (other.discountPercentage != null)
				return false;
		} else if (!discountPercentage.equals(other.discountPercentage))
			return false;
		if (family == null) {
			if (other.family != null)
				return false;
		} else if (!family.equals(other.family))
			return false;
		if (given == null) {
			if (other.given != null)
				return false;
		} else if (!given.equals(other.given))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	public String getGiven() {
		return given;
	}

	public String getFamily() {
		return family;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}
}
