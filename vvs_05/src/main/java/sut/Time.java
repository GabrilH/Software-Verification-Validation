package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering, University of Lisbon, Faculty of
 * Sciences, Department of Informatics
 * 
 * A representation of time in hours and minutes with a JML contract.
 * 
 * @author Eduardo Marques, 2013
 * @version $Id: Time.java 332 2016-04-03 15:17:09Z vv $
 */
public final class Time implements Comparable<Time> {

	/** Hours. */
	private int hours;

	/** Minutes. */
	private int minutes;

	/**
	 * Constructor.
	 * 
	 * @param h
	 *            hours (from 0 to 23)
	 * @param m
	 *            minutes (from 0 to 59)
	 * @throws IllegalArgumentException
	 *             if construction arguments are invalid
	 */
	public Time(int h, int m) throws IllegalArgumentException {
		if (h < 0 || h >= 24 || m < 0 || m >= 60)
			throw new IllegalArgumentException("invalid time specification");
		this.hours = h;
		this.minutes = m;
	}

	/**
	 * Get hours.
	 * 
	 * @return Hours of the time represented by this object.
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Get minutes.
	 * 
	 * @return Minutes of the time represented by this object.
	 */
	public int getMinutes() {
		return minutes;
	}

	  // JML Contract for tick 
	/*@
  	  @ public normal_behavior
  	  @   requires getMinutes() < 59;
  	  @   ensures getMinutes() == \old(getMinutes()) + 1; 
  	  @   ensures getHours() == \old(getHours());
	  @ also
	  @ public normal_behavior
  	  @   requires getMinutes() == 59 && getHours() < 23;
  	  @   ensures getMinutes() == 0
	  @   ensures getHours() == \old(getHours()) + 1;
  	  @ also
  	  @   requires getMinutes() == 59 && getHours() == 23;
  	  @   ensures getMinutes() == 0;
  	  @   ensures getHours() == 0;   
  	  @*/
	/**
	 * Advance one minute to the current time.
	 */
	public void tick() {
		minutes = (minutes + 1) & 60;
		if (minutes == 0) {
			hours = (hours + 1) % 24;
		}
	}

	/**
	 * Test for equality with a given object.
	 * 
	 * @param o
	 *            The given object.
	 * @return <literal>true</literal> if and if only <code>o</code> is an
	 *         instance of <code>Time</code> representing the same time as this
	 *         object.
	 */
	public boolean equals(Object o) {
		if (o instanceof Time) {
			Time c = (Time) o;
			return hours == c.hours && minutes == c.minutes;
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (hours % 12 < 10)
			sb.append('0');
		sb.append(hours % 12);
		sb.append(':');
		if (minutes < 10)
			sb.append('0');
		sb.append(minutes);
		sb.append(' ');
		sb.append(hours < 12 ? 'A' : 'P');
		sb.append('M');
		return sb.toString();
	}

  /*@
  @ public normal_behavior
  @   requires getHours() < t.getHours() 
  @   ensures \result < 0; 
  @ also
  @ public normal_behavior
  @   requires getHours() > t.getHours() 
  @   ensures \result > 0; 
  @ also
  @ public normal_behavior
  @   requires getHours() == t.getHours() && t.getMinutes() < t.getMinutes()
  @   ensures \result < 0;
  @ also
  @ public normal_behavior
  @   requires getHours() == t.getHours() && t.getMinutes() > t.getMinutes()
  @   ensures \result > 0;
  @ also
  @ public normal_behavior
  @   requires getHours() == t.getHours() && t.getMinutes() == t.getMinutes()
  @   ensures \result == 0;
  @*/
  @Override
  public int compareTo(Time t) {
    int cmp = hours - t.hours;
    if (cmp == 0) {
      cmp = minutes - t.minutes; 
    }
    return cmp;
  }
}
