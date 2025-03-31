package sut;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos
 * @version $Id: TimeCompareToTest.java 343 2016-04-18 11:05:27Z vv $
 */
public class TimeCompareToTest {

	@Test
	public void test1() {
		testCompareTo(1, 0, 0, 0, 1);
	}

	@Test
	public void test2() {
		testCompareTo(0, 0, 1, 0, -1);
	}

	@Test
	public void test3() {
		testCompareTo(0, 1, 0, 0, 1);
	}

	@Test
	public void test4() {
		testCompareTo(0, 0, 0, 1, -1);
	}

	@Test
	public void test5() {
		testCompareTo(0, 0, 0, 0, 0);
	}

	private void testCompareTo(int h1, int m1, int h2, int m2, int expected) {
		Time t1 = new Time(h1, m1);
		Time t2 = new Time(h2, m2);
		int c = t1.compareTo(t2);
		assertEquals("comparison result", expected, Integer.signum(c));
	}
}


