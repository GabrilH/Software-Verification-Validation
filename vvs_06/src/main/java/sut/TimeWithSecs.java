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
 * @version $Id: TimeWithSecs.java 343 2016-04-18 11:05:27Z vv $
 */
public class TimeWithSecs extends Time {
	private int seconds;

	public TimeWithSecs() {
		seconds = 0;
	}

	public TimeWithSecs(int hours, int minutes, int seconds) {
		super(hours, minutes);
		this.seconds = seconds;
	}

	public int getSeconds() {
		return seconds;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TimeWithSecs) {
			TimeWithSecs t = (TimeWithSecs) o;
			return t.getHours() == getHours() && t.getMinutes() == getMinutes() && t.getSeconds() == getSeconds();
		}
		return false;
	}
}
