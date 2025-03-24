package sut;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques, Joao Neto (JUnit 5 adaptation)
 * @version $Id: BoundedArrayQueueTest.java 332 2016-04-03 15:17:09Z vv $
 */
public class BoundedArrayQueueTest {

	@Test
	public void testEnqueueWhenQueueIsNotFull() {
		BoundedArrayQueue<String> q = new BoundedArrayQueue<>(2);
		q.enqueue("XPTO");
		assertFalse(q.isEmpty(), "not empty");
		assertFalse(q.isFull(), "not full");
		assertEquals(1, q.size(), "size");
		assertEquals(2, q.capacity(), "capacity");
		assertEquals("XPTO", q.elementAt(0), "elementAt");
	}

	@Test
	public void testEnqueueWhenQueueIsFull() {
		BoundedArrayQueue<String> q = new BoundedArrayQueue<>(2);
		q.enqueue("XPTO 1");
		q.enqueue("XPTO 2");

		assertThrows(IllegalStateException.class, () -> {
			q.enqueue("XPTO 3");
		});
		
		assertFalse(q.isEmpty(), "not empty");
		assertTrue(q.isFull(), "full");
		assertEquals(2, q.size(), "size");
		assertEquals(2, q.capacity(), "capacity");
		assertEquals("XPTO 1", q.elementAt(0), "elementAt");
		assertEquals("XPTO 2", q.elementAt(1), "elementAt");
	}
}
