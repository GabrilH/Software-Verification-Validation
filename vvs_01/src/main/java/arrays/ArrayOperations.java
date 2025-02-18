package arrays;
/**
 * Adapted from "Introduction to Software Testing", by Ammann and Offutt,
 * chapter 1 (pages 12 and 16)
 * 
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering
 * University of Lisbon
 * Faculty of Sciences
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos
 * @version $Id: ArrayOperations.java 253 2016-02-20 15:31:46Z vv $
 */
public class ArrayOperations {

	/**
	 * The number of zeros in an array.
	 * 
	 * @param x
	 *            An integer array
	 * @return The number of occurrences of 0 in x
	 * @exception NullPointerException
	 *                if x == null
	 */
	public static int numZeros(int[] x) {
		int count = 0;
		for (int i = 0; i < x.length; i++)
			if (x[i] == 0)
				count++;
		return count;
	}

	public static int findLast(int[] x, int y) {
		if (x == null)
			throw new NullPointerException();
		for (int i = x.length - 1; i >= 0; i--) {
			if (x[i] == y)
				return i;
		}
		return -1;
	}

	public static int lastZero(int[] x) {
		if (x == null)
			throw new NullPointerException();
		for (int i = 0; i < x.length; i++) {
			if (x[i] == 0)
				return i;
		}
		return -1;
	}

	public static int countPositive(int[] x) {
		if (x == null)
			throw new NullPointerException();
		int count = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i] >= 0)
				count++;
		}
		return count;
	}

	public static int oddOrPos(int[] x) {
		if (x == null)
			throw new NullPointerException();
		int count = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i] % 2 != 0 || x[i] > 0)
				count++;
		}
		return count;
	}
}
