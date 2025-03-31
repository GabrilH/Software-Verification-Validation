package sut;

import static org.junit.Assert.*;
import org.junit.Test;

public class TimeEquals {
	
	@Test
	public void testSameObject() {
		Time time = new Time(5,20);
		Time time1 = time;

		assertTrue(time.equals(time1));
	}

	@Test
	public void testSameValue() {
		Time time = new Time(5,20);
		Time time1 = new Time(5,20);

		assertTrue(time.equals(time1));
	}

	@Test
	public void testDifferentValue() {
		Time time = new Time(5,20);
		Time time1 = new Time(5,21);

		assertFalse(time.equals(time1));
	}

	@Test
	public void testDifferentObject() {
		Time time = new Time();
		Object time1 = new Object();

		assertFalse(time.equals(time1));
	}
}
