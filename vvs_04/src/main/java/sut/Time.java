package sut;

/**
 * Software Verification and Validation
 * 
 * A representation of time in hours and minutes.
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques
 * @version $Id: Time.java 313 2016-03-21 12:46:58Z vv $
 */
public class Time {
	private int hours;
	private int minutes;

	/**
	 * Constructor.
	 * 
	 * @param h   hours (from 0 to 23)
	 * @param m   minutes (from 0 to 59)
	 * @throws IllegalArgumentException if construction arguments are invalid
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

	/**
	 * Advance one minute to the current time.
	 */
	public void tick() {
		minutes = (minutes+1) % 60;
		if (minutes==0)
			hours = (hours+1) % 24;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (hours != other.hours)
			return false;
		if (minutes != other.minutes)
			return false;
		return true;
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
}
