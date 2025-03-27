package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering University of Lisbon Faculty of
 * Sciences Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos
 * @version $Id: Palindrome.java 284 2016-03-05 22:12:09Z vv $
 */
public class Palindrome {
	/**
	 * Check if a given string is a palindrome.
	 *
	 * A palindrome is a string that is the same when read right-to-left. For
	 * instance <code>"abba"</code> is a palindrome.
	 * 
	 * @param s
	 *            input string
	 * @return true iff the input string is a pangram
	 * @throws NullPointerException
	 *             if input string is null
	 */
	public static boolean isPalindrome(String s) {
		if (s == null)
			throw new NullPointerException();
		int left = 0;
		int right = s.length() - 1;
		boolean result = true;
		while (left < right && result == true) {
			if (s.charAt(left) != s.charAt(right)) {
				result = false;
			}
			left++;
			right--;
		}
		return result;
	}
	
	/** See slides for CFG
	 *  var  | defs | uses
	 * s     | 0    | (0,1), (0,2), 2, (5,6), (5,7)
	 * left  | 2,7  | (3,4), (3,5), (5,6), (5,7), 7
	 * right | 2,7  | (3,4), (3,5), (5,6), (5,7), 7
	 * result| 2,6  | (3,4), (3,4), 4
	 */
	
	/**
	 *  var  | n  n' | path
	 * ------+-------+----------------------
	 * s     | 0  1  | [0,1]
	 *       |    2  | [0,2]
	 *       |    6  | [0,2,3,5,6]
	 *       |    7  | [0,2,3,5,7]
	 * ------+-------+----------------------
	 * left  | 2  4  | [2,3,4]
	 * (same |    5  | [2,3,5]
	 * as    |    6  | [2,3,5,6]
	 * right)|    7  | [2,3,5,6,7] [2,3,5,7]
	 *       |-------+----------------------
	 *       | 7  4  | [7,3,5]
	 *       |    5  | [7,3,5]
	 *       |    6  | [7,3,5,6]
	 *       |    7  | [7,3,5,7] [7,3,5,6,7]
	 * ------+-------+----------------------
	 * result| 2  4  | [2,3,4]
	 *       |    5  | [2,3,5]
	 *       |-------+----------------------
	 *       | 6  4  | [6,7,3,4]
	 *       |    5  | [6,7,3,5] <-- INFEASIBLE!
	 */
	
	/**
	 *      Test  | input | exp. output | test path
	 *  ----------+-------+-------------|------------------
	 *   ADC   t1 | "ab"  | false       | [0,2,3,5,6,7,3,4]
	 *  ----------+-------+-------------+------------------
	 *  +AUC   t2 | null  | NPExc       | [0,1]
	 *         t3 | "a"   | true        | [0,2,3,4]
	 *         t4 | "abca"| false       | [0,2,3,5,7,3,5,6,7,3,4]
	 *  ----------+-------+-------------+------------------
	 *  +ADUPC t5 | "abba"| true        | [0,2,3,5,7,3,5,7,3,4]
	 */
}
