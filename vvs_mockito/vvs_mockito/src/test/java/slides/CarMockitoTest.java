package slides;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;

/** Mocking is the act of removing external dependencies from a unit test in order to create a 
    controlled environment around it. Typically, we mock all other classes that interact with 
    the class that we want to test. Common targets for mocking are:

	    Database connections,
	    Web services,
	    Classes that are slow ,
	    Classes with side effects, and
	    Classes with non-deterministic behavior.
    
    A stub is a fake class that comes with preprogrammed return values. 
    It’s injected into the class under test to give you absolute control over 
    what’s being tested as input. A typical stub is a database connection that 
    allows you to mimic any scenario without having a real database.

    A mock is a fake class that can be examined after the test is finished for 
    its interactions with the class under test. For example, you can ask it 
    whether a method was called or how many times it was called. Typical mocks 
    are classes with side effects that need to be examined, e.g. a class that 
    sends emails or sends data to another external service.
    
    ref: https://semaphoreci.com/community/tutorials/stubbing-and-mocking-with-mockito-2-and-junit
    
    Dummies and stubs are used to prepare the environment for testing. They are not used for
    verification. A dummy is employed to be passed as a value (e.g. as a parameter of a direct method
    call), while a stub passes some data to the SUT, substituting for one of its DOCs.
    
    The purpose of test spies and mocks is to verify the correctness of the communication between the
	SUT and DOCs. Yet they differ in how they are used in test code, and that is why they have distinct
	names.
	
	ref: Kaczanowsk - Practical Unit Testing with JUnit & Mockito (2013)
*/

class CarMockitoTest {

	// Mocking the DOC (Depended On Component) that the SUT (System Under Test) needs
	private Car myFerrari = mock(Car.class);

	@Test
	public void CarIsACarTest() {
		assertTrue(myFerrari instanceof Car);
	}

	@Test
	public void defaultBehaviourOfTest() {
		// by default mock objects return zeros and false from their attributes
		assertFalse(myFerrari.needsFuel(), "new test double should return false as boolean");
		assertEquals(0.0, myFerrari.getEngineTemperature(), 1.0e-3, "new test double should return 0.0 as double");
	}

	@Test
	public void stubbingTest() {
		assertFalse(myFerrari.needsFuel(), "new test double should return false as boolean");
		
		// we tell myFerrari what to do when asked about the fuel
		when(myFerrari.needsFuel()).thenReturn(true);
		
		assertTrue(myFerrari.needsFuel(), "after instructed test double should return what we want");
	}

	@Test
	public void stubbingExceptionTest() {
		when(myFerrari.needsFuel()).thenThrow(new RuntimeException());
		
	    assertThrows(RuntimeException.class, () -> {
	    	myFerrari.needsFuel();
	    });
	}
	
	/**
	 * for testing purposes we need to make sure that the SUT has executed certain methods
	 * of the DOCs (represented by test doubles). To do this we need to also verify whether 
	 * some methods of test doubles have been called. Or, to put it differently, we want to 
	 * verify the indirect outputs of the SUT.
	 */
	@Test
	public void verificationTest() {
		myFerrari.driveTo("Sweet home Alabama");
		myFerrari.needsFuel();
		
		// check if these methods were executed
		verify(myFerrari).driveTo("Sweet home Alabama");
		verify(myFerrari).needsFuel();
	}

	@Test
	public void verificationFailureTest() {
		myFerrari.needsFuel();
		
		verify(myFerrari, never()).getEngineTemperature(); 
	}

	@Test
	public void verificationFailureArgumentsTest() {
		myFerrari.driveTo("Sweet home Alabama");
		
		verify(myFerrari, never()).driveTo("Sweet home Honolulu");  // argument is different
	}
	
}






