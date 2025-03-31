package sut.sale;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Sale {
	
	private int id;
	private LocalDate date;
	private List<SaleItem> items;
	
	public Sale(int id, LocalDate date) {
		this.id = id;
		this.date = date;
		this.items = new LinkedList<>();
	}

	public int getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<SaleItem> getItems() {
		return items;
	}

	public void addItem(SaleItem saleItem) {
		items.add(saleItem);
	}
	
	public int getTotal() {
		int total = 0;
		for(SaleItem item : items)
			total += item.getnItems() * ProductCatalog.getProduct(item.getProductId()).getPrice();
		return total;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[id: ");
		sb.append(id);
		sb.append(" date: ");
		sb.append(date.format(DateTimeFormatter.ofPattern("dd/MMM/uuuu")));
		sb.append(" items:");
		for(SaleItem item : items)
			sb.append(" "+item);
		sb.append("]");
		return sb.toString();
	}
}
