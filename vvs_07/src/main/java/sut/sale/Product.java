package sut.sale;

public class Product {
	private int id;
	private String designation;
	private int price;
	
	public Product(int id, String designation, int price) {
		this.id = id;
		this.designation = designation;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(id);
		sb.append(",");
		sb.append(designation);
		sb.append(",");
		sb.append(price);
		sb.append(")");
		return sb.toString();
	}
}
