package calc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("<= Testing Simple Calculator =>")
class CalculatorTest {

	Calculator calculator;

	@BeforeEach
	public void setUp() {
		calculator = new Calculator();
	}

	@Test
	public void simpleTest() {
		int expected = 6;
		int actual   = calculator.evaluate("1+2+3");
		assertEquals(expected, actual);
	}

	@Test
	public void nullArgumentTest() {
		assertThrows(NumberFormatException.class, () -> {
			calculator.evaluate("a+1");
		});
	}

    //////////////////////////////////////////
	// in @CsvSource use ' ' to represent strings with commas 
	@ParameterizedTest(name = "check if {0} equals {1}")
	@CsvSource({ 
        "200,    200", 
		"1+2,      3", 
		"0+0,      0",
		"00+000,   0",
		"0+-1+0,  -1",
		"100+-100, 0",
		"1+1+1+1+2+3+4+5+1+2+3+4+5, 33"
	})
	// also possible to place args into a file with @CsvFileSource(resources="file-path.csv")
	public void expressionTest(String expression, int expected) {
		int actual = calculator.evaluate(expression);
		assertEquals(expected, actual);
	}
	
	////////////////////////////////////////////
	// JUnit's repeated test are useful to implement random tests
	@RepeatedTest(value=10)
	public void randomTest() {
		int a = r.nextInt(1000)-500; 
		int b = r.nextInt(1000);
		
		int expected = a+b;
		int actual   = calculator.evaluate(a+"+"+b);
		assertEquals(expected, actual, a+"+"+b);
	}
	
	private Random r = new Random();
	
    //////////////////////////////////////////
	// Tests can have a maximum time to execute
	
	@Test
	public void timingABigTest() {
		int a = 123456; 
		int b = 900000;
		
		int expected = a+b;
		int actual   = assertTimeoutPreemptively(
				          Duration.of(1500, ChronoUnit.MICROS),
				          () -> calculator.evaluate(a+"+"+b));
		assertEquals(expected, actual, a+"+"+b);
	}

}
