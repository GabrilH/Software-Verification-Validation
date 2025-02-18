package bookstoread;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("<= BookShelf Specification =>")  // Customizes class names for test reports
class BookShelfTest {
	
	private BookShelf shelf;
	
	@BeforeEach
	public void setup() {
		shelf = new BookShelf();
	}

	@Test
	@DisplayName("is empty when no book is added to it")
	public void shelfEmptyWhenNoBookAddedTest() throws Exception {
		assertTrue(shelf.books().isEmpty(), () -> "BookShelf should be empty."); // eval of error msg is lazy
	}
	
	@Test
	public void checkTitleWithOnlyOneBookTest() {
		// Testing pre-conditions
		// A failing assumption does not mean a test is failing, but simply that the test won’t 
		// provide any relevant information, so it doesn’t need to run.
		assumeTrue(shelf.books().isEmpty());
		assumeFalse(!shelf.books().isEmpty());  // same thing
		
		shelf.add(new Book("Software Patterns"));
		assertTrue(shelf.contains("Software Patterns"));
	}

	@Test
	public void checkTitlesLengthTest() {
		shelf.add(new Book("The Phoenix Project"), new Book("Java 8 in Action"));
		assertEquals(2, shelf.books.size(), () -> "should only have two books");
	}
	
	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@ValueSource(strings = { "Book A", "Book B", "Book C" })
	public void checkTitlesTest(String bookTitle) {
		shelf.add(new Book(bookTitle));
		assertEquals(1, shelf.books.size(), () -> "should only have one books");
	}
	
	@Disabled("still to do...")
	@Test
	public void todoTest() {
		fail("should not execute this test yet");
	}
	
	@AfterEach
	public void tearDown() {
		shelf = null;
	}
}
