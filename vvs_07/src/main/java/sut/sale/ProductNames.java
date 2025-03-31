package sut.sale;

// this enum simulates a database
public enum ProductNames {

	BANANA     (1, "banana",     100),
	ORANGE     (2, "orange",      50),
	TANGERINE  (3, "tangerine",   20),
	PEAR       (4, "pear",       150),
	APPLE      (5, "apple",       75),
	WATERMELON (6, "watermelon", 200);

	private final int productId;
	private final String description;
	private final int price;

	private ProductNames(int productId, String description, int price) {
		this.productId = productId;
		this.description = description;
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getProductId() {
		return productId;
	}

	public int getPrice() {
		return price;
	}

}