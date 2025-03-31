package sut.sale;

import java.util.*;

public class ProductCatalog {
	
	private static List<Product> products;
	
	static {
		// build catalog
		products = new LinkedList<Product>();
		for(ProductNames pn : ProductNames.values()) {
			products.add(new Product(pn.getProductId(), 
					                 pn.getDescription(),
					                 pn.getPrice()));
		}
	}

	public static void addProduct(Product product) {
		products.add(product);
	}

	public static Product getProduct(int productId) {
		for(Product product : products)
			if (product.getId() == productId)
				return product;
		return null;
	}	
}
