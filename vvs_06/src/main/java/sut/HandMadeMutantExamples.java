package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos
 * @version $Id: HandMadeMutantExamples.java 343 2016-04-18 11:05:27Z vv $
 */
public class HandMadeMutantExamples {
	public static int numZero(int[] x) {
		int count = 0;
		for (int i = 0; i < x.length; i++)
			if (x[i] == 0)
				count++;
		return count;
	}

	public static int numZero_mutant(int[] x) {
		int count = 0;
		for (int i = 1 /* mutation */; i < x.length; i++)
			if (x[i] == 0)
				count++;
		return count;
	}

	public static int min(int x, int y) {
		int v;
		if (x < y)
			v = x;
		else
			v = y;
		return v;
	}

	public static int min_mutant1(int x, int y) {
		int v;
		if (x >= y) /* MUTATION: from x < y to x >= y */
			v = x;
		else
			v = y;
		return v;
	}

	public static int min_mutant2(int x, int y) {
		int v;
		if (x <= y) /* MUTATION: from x < y to x <= y */
			v = x;
		else
			v = y;
		return v;
	}

	public static int min_mutant3(int x, int y) {
		int v;
		if (x < y)
			v = x;
		else
			v = -y; /* MUTATION: from v = y to v = -y */
		return v;
	}

	public static int indexOf(int[] array, int v) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == v) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf_m1(int[] array, int v) {
		for (int i = 0; i >= array.length; i++) {
			if (array[i] == v) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf_m2(int[] array, int v) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != v) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf_m3(int[] array, int v) {
		for (int i = 0; i < array.length; i--) {
			if (array[i] == v) {
				return i;
			}
		}
		return -1;
	}

}
