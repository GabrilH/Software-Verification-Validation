package sut.sale;

public class SaleItem {
	
	private int id;
	private int productId;
	private int nItems;
	
	public SaleItem(int id, int productId, int nItems) {
		this.id = id;
		this.productId = productId;
		this.nItems = nItems;
	}

	public int getId() {
		return id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getnItems() {
		return nItems;
	}

	public void setnItems(int nItems) {
		this.nItems = nItems;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(ProductCatalog.getProduct(productId).getDesignation());
		sb.append(",");
		sb.append(nItems);
		sb.append(")");
		return sb.toString();
	}
}
