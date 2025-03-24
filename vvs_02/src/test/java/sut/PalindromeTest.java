package sut;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static sut.Palindrome.isPalindrome;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering
 * University of Lisbon
 * Faculty of Sciences
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos, João Neto, Diogo Poças
 * @version $Id: PalindromeTest.java 276 2022-02-28 $
 */
public class PalindromeTest {

	// Two infeasible requirements: [5,6,7,4,5] and [6,7,4,5,6]

	@Test
	public final void testWithNullArgument() {
		// [1,2]
		assertThrows(NullPointerException.class, () -> {
			isPalindrome(null);
	    });
		
	}

	@Test
	public final void testWithEmptyString() {
		// [1,3,4,8]
		String s = "";
		assertTrue(isPalindrome(s), "empty string not a palindrome?");
	}

	@Test
	public final void testWithLen2StringAA() {
		// [1,3,4,5,7]
		// [4,5,7,4]
		// [5,7,4,8]
		String s = "aa";
		assertTrue(isPalindrome(s), "aa not a palindrome?");
	}

	@Test
	public final void testWithLen2StringAB() {
		// [1,3,4,5,6,7]
		// [4,5,6,7,4]
		// [5,6,7,4,8]
		String s = "ab";
		assertFalse(isPalindrome(s), "ab is a palindrome?");
	}

	@Test
	public final void testWithLen2StringABCA() {
		// [1,3,4,5,7] -repeat
		// [4,5,7,4] -repeat
		// [4,5,6,7,4] -repeat
		// [5,7,4,5]
		// [5,6,7,4,8] -repeat
		// [7,4,5,6,7]
		String s = "abca";
		assertFalse(isPalindrome(s), "abca is a palindrome?");
	}

	@Test
	public final void testWithLen2StringABBA() {
		// [1,3,4,5,7] -repeat
		// [4,5,7,4] -repeat
		// [5,7,4,8] -repeat
		// [7,4,5,7]
		String s = "abba";
		assertTrue(isPalindrome(s), "abba is a palindrome?");
	}
}
