package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering
 * University of Lisbon
 * Faculty of Sciences
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos
 * @version $Id: Palindrome.java 275 2016-02-28 22:59:09Z vv $
 */
public class Palindrome {

	/**
	 * Check if a given string is a palindrome.
	 * <p>
	 * A palindrome is a string that is the same when read right-to-left. For
	 * instance <code>"abba"</code> is a palindrome.
	 * </p>
	 * 
	 * @param s
	 *            input string
	 * @return true iff the input string is a pangram
	 * @throws NullPointerException
	 *             if input string is null <br/>
	 */
	public static boolean isPalindrome(String s) {
		if (s == null) {
			throw new NullPointerException();
		}
		int left = 0;
		int right = s.length() - 1;
		boolean result = true;
		while (left < right && result) {
			if (s.charAt(left) != s.charAt(right)) {
				result = false;
			}
			left++;
			right--;
		}
		return result;
	}
	
	/* Blocks:
	 * 1: if (s==null)
	 * 2: throw ...
	 * 3: left=0, right=s.length()-1, result=true
	 * 4: while (left < right && result)
	 * 5: if (s.charAt(left) != s.charAt(right))
	 * 6: result = false
	 * 7: left++, right--
	 * 8: return result
	 */
	
	/* Test Requirements:
	 * NC: 1, 2, 3, 4, 5, 6, 7, 8
	 * EC: NC + 1-2, 1-3, 3-4, 4-5, 4-8, 5-6, 5-7, 6-7, 7-4
	 * EPC: EC + 1-3-4, 3-4-5, 3-4-8, 4-5-6, 4-5-7, 
	 *           5-6-7, 5-7-4, 6-7-4, 7-4-5, 7-4-8
	 * PPC: 1-2, 1-3-4-8, 1-3-4-5-6-7, 1-3-4-5-7, 4-5-6-7-4, 4-5-7-4,
	 *      5-6-7-4-5, 5-6-7-4-8, 5-7-4-5, 5-7-4-8,
	 *      6-7-4-5-6, 7-4-5-6-7, 7-4-5-7
	 */
}
