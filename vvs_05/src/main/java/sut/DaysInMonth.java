package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * Class containing a method to determine the days of a given month in a given
 * year.
 * 
 * @author Eduardo Marques, 2014
 * @version $Id: DaysInMonth.java 332 2016-04-03 15:17:09Z vv $
 */
public class DaysInMonth {

	public static boolean isLeapYear(int y) {
		return y % 400 == 0 || (y % 4 == 0 && y % 100 != 0);
	}

	/**
	 * Get number of days in a month.
	 * 
	 * @param m
	 *            Month.
	 * @param y
	 *            Year.
	 * @return Number of days of given month and year
	 */
	public static int daysInMonth(int m, int y) {
		if (m <= 0 || m > 12)
			throw new IllegalArgumentException("Invalid month: " + m);
		if (m == 2) {
			if (isLeapYear(y))
				return 29;
			else
				return 28;
		}
		if (m <= 7) {
			if (m % 2 == 1)
				return 31;
			return 30;
		}
		if (m % 2 == 0)
			return 31;
		return 30;

	}
	
	// PREDICATES AND CLAUSES
	//
	// p1: c1 || c2         ; c1: m <= 0, c2: m > 12
	// p2: c3               ; c3: m == 2;
	// p3: c4 || (c5 && c6) ; c4: y % 400 == 0, c5: y % 4 == 0, c6: y % 100 != 0;
	// p4: c7               ; c7: m <= 7;
	// p5: c8               ; c8: m % 2 == 1;
	// p6: c9               ; c9: m % 2 == 0
	
	// TR(PC) = {p1, ~p1, p2, ~p2, ..., p6, ~p6}
	
	// TR(CC) = {c1, ~c1, c2, ~c2, ...}
	
	// For TR(CoC) = {c1&c2, c1&~c2, ~c1&c2, ~c1&~c2, c3, ~c3, ...} 
	// some will be infeasible, like c1&c2
	
	// REACHABILITY PREDICATES
	//
	// p [predicate] r(p) [reachability predicate]
	// p1   r(p1) = true
	// p2   r(p2) = r(p1) && (!p1) = m >= 1 && m <= 12
	// p3   r(p3) = r(p2) && p2 = (!p1 && p2) = (m >= 1 && m <= 12 && m == 2) = (m == 2)
	// p4   r(p4) = r(p2) && !p2 = !p1 && !p2 = (m >= 1 && m <= 12 && m != 2) = m in [1..12]\2
	// p5   r(p5) = r(p4) && p4 == !p1 && !p2 && p4 == (m >= 1 && m <= 12 && m != 2) && m <= 7 = m in [1..7]\2
	// p6   r(p6) = r(p4) && !p4 == !p1 && !p2 && !p4 == (m >= 1 && m <= 12 && m != 2) && m > 7 = m in [8..12]

	// DETERMINATION PREDICATES (for criteria based on active clauses)
	//
	// c [active clause] d(c) [determination predicate]
	// c1 !c2 = !(m > 12) = m <= 12
	// c2 !c1 = ! (m <= 0) = m > 0
	// c4 !(c5 && c6) == !c5 || !c6 == (y % 4 != 0) || (y % 100 == 0)
	// c5 !c4 && c6 == (y % 400 != 0) && (y % 100 != 0)
	// c6 !c4 && c5 == (y % 400 != 0) && (y % 4 == 0)
	// c3,c7,c8,c9 true
}
