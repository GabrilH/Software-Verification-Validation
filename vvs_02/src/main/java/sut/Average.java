package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering
 * University of Lisbon
 * Faculty of Sciences
 * Department of Informatics
 * 
 * @author Eduardo Marques, Vasco T. Vasconcelos, Diogo Poças
 * @version $Id: Average.java 275 2020-02-28$
 */
public class Average {
	/**
	 * Calculates the average of all elements in an integer array.
	 * 
	 * @param v
	 *            Array of values.
	 * @return The average of all values in v.
	 * @throws IllegalArgumentException
	 *             if v is null or has length 0
	 */
	public static int average(int[] v) {
		if (v == null || v.length == 0)
			throw new IllegalArgumentException();
		int r = 0;
		for (int i = 0; i < v.length; i++) {
			r = r + v[i];
		}
		r = r / v.length;
		return r;
	}
	
	/* Blocks:
	 * 1: if (v==null || v.length==0)
	 * 2: throw ...
	 * 3: r=0, i=0
	 * 4: i<v.length
	 * 5: r=r+v[i], i++
	 * 6: r=r/v.length
	 * 7: return r
	 */
	
	/* PPC:
	 * 1-2
	 * 1-3-4-5
	 * 1-3-6-7 <- infeasible
	 * 4-5-4
	 * 5-4-5
	 * 5-4-6-7
	 */
}
