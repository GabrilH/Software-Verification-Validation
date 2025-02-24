package sut;

import java.math.BigDecimal;

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
 * @version $Id: Product.java 290 2016-03-13 14:23:37Z vv $
 */
public class Product {

	private int id;
	private String name;
	private BigDecimal unitPrice;

	public Product(int id, String name, BigDecimal unitPrice) {
		this.id = id;
		this.name = name;
		this.unitPrice = unitPrice;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (unitPrice == null) {
			if (other.unitPrice != null)
				return false;
		} else if (!unitPrice.equals(other.unitPrice))
			return false;
		return true;
	}
}
