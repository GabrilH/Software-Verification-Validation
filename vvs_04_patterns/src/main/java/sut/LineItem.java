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
 * @version $Id: LineItem.java 290 2016-03-13 14:23:37Z vv $
 */
public class LineItem {

    private Invoice invoice;
    private Product product;
    private int quantity;
    private BigDecimal percentDiscount;
    private BigDecimal extendedPrice;

    public LineItem(Invoice invoice, Product product, int quantity, BigDecimal percentDiscount,
	    BigDecimal extendedPrice) {
	super();
	this.invoice = invoice;
	this.product = product;
	this.quantity = quantity;
	this.percentDiscount = percentDiscount;
	this.extendedPrice = extendedPrice;
    }

    public Invoice getInv() {
	return invoice;
    }

    public Product getProd() {
	return product;
    }

    public int getQuantity() {
	return quantity;
    }

    public Object getPercentDiscount() {
	return percentDiscount;
    }

    public Object getUnitPrice() {
	return product.getUnitPrice();
    }

    public Object getExtendedPrice() {
	return extendedPrice;
   }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	LineItem other = (LineItem) obj;
	if (invoice == null) {
	    if (other.invoice != null)
		return false;
	} else if (!invoice.equals(other.invoice))
	    return false;
	if (percentDiscount == null) {
	    if (other.percentDiscount != null)
		return false;
	} else if (!percentDiscount.equals(other.percentDiscount))
	    return false;
	if (product == null) {
	    if (other.product != null)
		return false;
	} else if (!product.equals(other.product))
	    return false;
	if (quantity != other.quantity)
	    return false;
	if (extendedPrice == null) {
	    if (other.extendedPrice != null)
		return false;
	} else if (!extendedPrice.equals(other.extendedPrice))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Product: " + product + ", Quantity: " + quantity + ", Discount: " + percentDiscount + ", Extended price: "
		+ extendedPrice;
    }
}
