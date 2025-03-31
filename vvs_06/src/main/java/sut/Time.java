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
 * @version $Id: Time.java 343 2016-04-18 11:05:27Z vv $
 */
public class Time implements Comparable<Time> {
	private int hours;
	private int minutes;

	public Time() {
		hours = 0;
		minutes = 0;
	}

	public Time(int hours, int minutes) {
		this.hours = hours;
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	@Override
	public int compareTo(Time t) {
		int c = getHours() - t.getHours();
		if (c == 0) {
			c = getMinutes() - t.getMinutes();
		}
		return c;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Time) {
			Time t = (Time) o;
			return t.getHours() == getHours() && t.getMinutes() == getMinutes();
		}
		return false;
	}
}
