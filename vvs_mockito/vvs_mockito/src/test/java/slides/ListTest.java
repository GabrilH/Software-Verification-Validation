package slides;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.*;

// ref: https://static.javadoc.io/org.mockito/mockito-core/2.13.0/org/mockito/Mockito.html
class ListTest {
	
	// mocking a List (the SUT already exists in the Java API)
	@SuppressWarnings("unchecked")
	private List<String> mockedList = mock(List.class);

	@Test
	void verifyTest() {
		//using mock object
		mockedList.add("one");
		mockedList.clear();

		//verification
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}

	@Test
	void stubTest() {
		// Stubbing
		// Once stubbed, the method will always return a stubbed value, regardless of how many times it is called. 
		when(mockedList.get(0)).thenReturn("first");
		when(mockedList.get(1)).thenThrow(new RuntimeException());

		//following prints "first"
		assertEquals("first", mockedList.get(0));

		//following throws runtime exception
	    assertThrows(RuntimeException.class, () -> {
	    	mockedList.get(1);
	    });

		//following returns "null" because get(999) was not stubbed
	    assertNull(mockedList.get(999));

		//Although it is possible to verify a stubbed invocation, usually it's just redundant
		//If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
		//If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
		verify(mockedList).get(0);
	}
	
	@Test
	void argumentMatcherTest() {
		 //stubbing using built-in anyInt() argument matcher (allows flexible verification or stubbing)
		 when(mockedList.get(anyInt())).thenReturn("element");

		 //following prints "element"
		 assertEquals("element", mockedList.get(999));

		 //you can also verify using an argument matcher
		 verify(mockedList).get(anyInt());
		 
		 mockedList.add("hello world");

		 verify(mockedList).add(eq("hello world"));

		 //argument matchers can also be written as Java 8 Lambdas
		 verify(mockedList).add(argThat(str -> str.length() > 5));
		 
		 // ref. https://static.javadoc.io/org.mockito/mockito-core/2.13.0/org/mockito/ArgumentMatchers.html
		 //      https://static.javadoc.io/org.mockito/mockito-core/2.13.0/org/mockito/hamcrest/MockitoHamcrest.html
	}
	
	@Test
	void invocationsTest() {
		//using mock
		 mockedList.add("once");

		 mockedList.add("twice");
		 mockedList.add("twice");

		 mockedList.add("three times");
		 mockedList.add("three times");
		 mockedList.add("three times");

		 //following two verifications work exactly the same - times(1) is used by default
		 verify(mockedList).add("once");
		 verify(mockedList, times(1)).add("once");

		 //exact number of invocations verification
		 verify(mockedList, times(2)).add("twice");
		 verify(mockedList, times(3)).add("three times");

		 //verification using never(). never() is an alias to times(0)
		 verify(mockedList, never()).add("never happened");

		 //verification using atLeast()/atMost()
		 verify(mockedList, atLeastOnce()).add("three times");
		 verify(mockedList, atLeast(2)).add("three times");
		 verify(mockedList, atMost(5)).add("three times");
	}
	
	@Test
	void notHappenVerificationTest() {
		//verify that mock was not interacted
		//verifyZeroInteractions(mockedList);

		 mockedList.add("one");

		 //ordinary verification
		 verify(mockedList).add("one");

		 //verify that method was never called on a mock
		 verify(mockedList, never()).add("two");
	}
	
	@Test
	void consecutiveCallsTest() {
		when(mockedList.get(anyInt())).thenReturn("one", "two", "three");
		assertEquals("one",   mockedList.get(1));
		assertEquals("two",   mockedList.get(5));
		assertEquals("three", mockedList.get(2));
	}
	
	@Test
	void stubVoidMethodTest() {
		doThrow(new RuntimeException()).when(mockedList).clear();

	    assertThrows(RuntimeException.class, () -> {
	    	mockedList.clear();
	    });		   
	}
	
	@Test
	void spyRealObjsTest() {
		List<String> list = new LinkedList<>();
		List<String> spy = spy(list);

		//optionally, you can stub out some methods:
		when(spy.size()).thenReturn(100);

		//using the spy calls *real* methods
		spy.add("one");
		spy.add("two");

		//prints "one" - the first element of a list
		assertEquals("one", spy.get(0));

		//size() method was stubbed - 100 is printed
		assertEquals(100, spy.size());

		//optionally, you can verify
		verify(spy).add("one");
		verify(spy).add("two");
	}

}
