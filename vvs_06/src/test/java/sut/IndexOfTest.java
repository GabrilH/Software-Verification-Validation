package sut;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sut.IndexOf;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos
 * @version $Id: IndexOfTest.java 343 2016-04-18 11:05:27Z vv $
 */
public class IndexOfTest {
	
	@Test(expected=NullPointerException.class)
	public void testIndexOfNullArray (){
		int[] array = null;
		IndexOf.indexOf(array, 7);
	}
	
	@Test
	public void testIndexOfFinds (){
		int[] array = {5};
		assertEquals(IndexOf.indexOf(array, 5), 0);
	}
	
	@Test
	public void testIndexOfNotFinds (){
		int[] array = {5};
		assertEquals(IndexOf.indexOf(array, 7), -1);
	}
	
	@Test
	public void testIndexOfEmptyArray (){
		int[] array = {};
		assertEquals(IndexOf.indexOf(array, 7), -1);
	}
}
