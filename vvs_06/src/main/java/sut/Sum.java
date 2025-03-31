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
 * @version $Id: Sum.java 343 2016-04-18 11:05:27Z vv $
 */
public class Sum {

	public static int[] sum(int[] a, int[] b) {
		if (a == null || b == null || a.length != b.length)
			throw new IllegalArgumentException();
		int[] c = new int[a.length];
		for (int i = 0; i < a.length; i++)
			c[i] = a[i] + b[i];
		return c;
	}
}
