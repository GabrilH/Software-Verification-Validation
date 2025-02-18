package bookstoread;

import java.util.*;

public class BookShelf {
	
	List<Book> books;
	
	public BookShelf() {
		books = new LinkedList<>();
	}
	
	public List<Book> books() {
		return books;
	}

	public void add(Book... booksToAdd) {
		Arrays.stream(booksToAdd).forEach(books::add);
	}
	
	public boolean contains(String bookTitle) {
		return books.stream().anyMatch(b -> b.getTitle().equals(bookTitle));
	}
}
