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
 * @version $Id: Average.java 370 2016-05-08 15:54:33Z vv $
 */
public class Average {
	public static int average(int[] v) {
		if (v == null)
			throw new IllegalArgumentException();
		int r = 0;
		if (v.length > 0) {
			for (int i = 0; i < v.length; i++)
				r = r + v[i];
			r = r / v.length;
		}
		return r;
	}
}
