package slides;

import static slides.LineCounter.count;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.DataInput;
import java.io.IOException;

public class LineCounterTest {
	
	DataInput mock;
	
	@BeforeEach
	public void setup() throws IOException {
		// Create mock object
		mock = mock(DataInput.class);

		// Stub behavior: make sure readLine() returns certain values in succession.
		when(mock.readLine())
		.thenReturn("line 1")
		.thenReturn("") // line 2 will be blank
		.thenReturn("line 3")
		.thenReturn(null);
	}

	@Test
	public final void testCountBlanksConsidered() throws IOException {

		int lines = count(mock, false);

		assertEquals(3, lines, "non-blank line count failed");

		// You can also verify the interaction with the mock object:
		// below we verify readLine() has been called 4 times.
		// Change to another value to see what happens.
		verify(mock, times(4)).readLine();
	}

	@Test
	public final void testCountBlanksIgnored() throws IOException {

		int lines = count(mock, true);

		assertEquals(2, lines, "line count ignoring blanks failed");

		verify(mock, times(4)).readLine();
	}

	@Test
	public final void testExceptionalBehavior() throws IOException{
		// Setup mock object
		DataInput in = mock(DataInput.class);
		
		when(in.readLine()).thenThrow(new IOException());

		assertThrows(IOException.class, () -> {
	     	count(in, false); // Call method using mock object 
		});
	}

}
