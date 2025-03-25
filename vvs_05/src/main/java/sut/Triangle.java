package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering, University of Lisbon, Faculty of
 * Sciences, Department of Informatics
 * 
 * Class containing a method to classify a triangle according to the triangle's
 * side lengths. In "Introduction to Software Testing" by Ammann & Offutt;
 * simplified version.
 * 
 * @author Eduardo Marques
 * @version $Id: Triangle.java 332 2016-04-03 15:17:09Z vv $
 */
public class Triangle {

	/**
	 * Classify s triangle.
	 * 
	 * @param a
	 *            First side length.
	 * @param b
	 *            Second side length.
	 * @param c
	 *            Third side length.
	 * @return Classification.
	 */
	public static TClass triangleType(int a, int b, int c) {
		if (a <= 0 || b <= 0 || c <= 0) /* p1 */
			return TClass.INVALID;
		if (a >= b + c || b >= a + c || c >= a + b) /* p2 */
			return TClass.INVALID;
		int count = 0;
		if (a == b) /* p3 */
			count++;
		if (a == c) /* p4 */
			count++;
		if (b == c) /* p5 */
			count++;
		if (count == 0) /* p6 */
			return TClass.SCALENE;
		if (count == 1) /* p7 */
			return TClass.ISOSCELES;
		return TClass.EQUILATERAL;
	}
}
