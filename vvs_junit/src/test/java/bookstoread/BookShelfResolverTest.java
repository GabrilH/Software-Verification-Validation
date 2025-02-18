package bookstoread;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

@DisplayName("A bookshelf with Dependency Injection")
@ExtendWith(BooksResolver.class)
class BookShelfResolverTest {

	private BookShelf shelf;
	
	private Book effectiveJava;
	private Book codeComplete;
	private Book mythicalManMonth;
	private Book cleanCode;

	@BeforeEach
	void init(Map<String, Book> books) {  // parameter injected by BooksResolver
		shelf = new BookShelf();
		
		effectiveJava = books.get("Effective Java");
		codeComplete = books.get("Code Complete");
		mythicalManMonth = books.get("The Mythical Man-Month");
		cleanCode = books.get("Clean Code");
	}

	@Test
	void fourBooksTest() {
		shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
		assertEquals(4, shelf.books().size(), "should have four books");
	}

}
