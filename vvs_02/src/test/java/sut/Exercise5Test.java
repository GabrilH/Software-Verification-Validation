package sut;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static sut.Exercise5.f1;


/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering
 * University of Lisbon
 * Faculty of Sciences
 * Department of Informatics
 * 
 * @author Diogo Poças
 * @version $Id: OccurrencesTest.java 276 2022-02-28 $
 */
public class Exercise5Test {

	@Test
	public final void testf1f2f4() {
		int n = 4;
		int z = f1(n);
		assertEquals(2, z, "f1(4) equals 2");
	}
	
	@Test
	public final void testf1f3f3() {
		int n = 5;
		int z = f1(n);
		assertEquals(120, z, "f1(5) equals 120");
	}
	
	@Test
	public final void testf1f2f3() {
		int n = 0;
		int z = f1(n);
		assertEquals(1, z, "f1(0) equals 1");
	}
}
