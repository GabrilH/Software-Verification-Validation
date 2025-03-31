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
 * @version $Id: IndexOf.java 343 2016-04-18 11:05:27Z vv $
 */
public class IndexOf {
	
	public static int indexOf(int[] array, int v) {
		// (m1) i < array.length ---> i >= array.length
		// (m2) array[i] == v ---> array[i] != v
		// (m3) i++ ---> i--
		for (int i = 0; i < array.length; i++)
			if (array[i] == v)
				return i;
		return -1;
	}
}
