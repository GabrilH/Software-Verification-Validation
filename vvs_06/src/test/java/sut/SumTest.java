package sut;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import sut.Sum;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos
 * @version $Id: SumTest.java 343 2016-04-18 11:05:27Z vv $
 */
public class SumTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testSumFirstNull() {
		Sum.sum(null, new int[1]);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSumSecondNull() {
		Sum.sum(new int[1], null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSumDifferentLengths() {
		Sum.sum(new int[1], new int[2]);
	}

	@Test
	public void testSumOrdinaryArrays() {
		int[] a = {1, 2, 3};
		int[] b = {4, 5, 6};
		int[] expected = {5, 7, 9};
		int[] result = Sum.sum(a, b);
		assertArrayEquals("a+b", expected, result);
	}
}
