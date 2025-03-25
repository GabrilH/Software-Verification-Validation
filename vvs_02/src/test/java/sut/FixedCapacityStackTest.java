package sut;

import static org.junit.jupiter.api.Assertions.*;
import static sut.Palindrome.isPalindrome;

import org.junit.jupiter.api.Test;

import sut.FixedCapacityStack;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering
 * University of Lisbon
 * Faculty of Sciences
 * Department of Informatics
 * 
 * @author Diogo Poças
 * @version $Id: FixedCapacityStackTest.java 275 2016-02-28 22:59:09Z vv $
 */
public class FixedCapacityStackTest {

	@Test
	public void testInitToEmpty() {
		FixedCapacityStack stack = new FixedCapacityStack(1);
		assertTrue(stack.isEmpty(),"initialized stack is empty");
		assertFalse(stack.isFull(),"initialized stack is not full");
		assertEquals(1,stack.capacity(),"initialized stack with capacity 1");
		assertEquals(0,stack.size(),"initialized stack has size 0");
	}
	
	@Test
	public void testEmptyToExceptionPeek() {
		FixedCapacityStack stack = new FixedCapacityStack(1);
		assertThrows(IllegalStateException.class, () -> stack.peek());
	}

	@Test
	public void testEmptyToExceptionPop() {
		FixedCapacityStack stack = new FixedCapacityStack(1);
		assertThrows(IllegalStateException.class, () -> stack.pop());
	}
	
	@Test
	public void testEmptyToFullPush() {
		FixedCapacityStack stack = new FixedCapacityStack(1);
		stack.push("a");
		assertFalse(stack.isEmpty(),"pushing to empty stack is not empty");
		assertTrue(stack.isFull(),"pushing to empty stack is full");
		assertEquals(1,stack.capacity(),"initialized stack with capacity 1");
		assertEquals(1,stack.size(),"pushing to empty stack has size 1");
	}
	
	@Test
	public void testEmptyToMidPush() {
		FixedCapacityStack stack = new FixedCapacityStack(2);
		stack.push("a");
		assertFalse(stack.isEmpty(),"pushing to empty stack is not empty");
		assertFalse(stack.isFull(),"pushing to empty stack is not full");
		assertEquals(2,stack.capacity(),"initialized stack with capacity 2");
		assertEquals(1,stack.size(),"pushing to empty stack has size 1");
	}
	
	@Test
	public void testMidToEmptyPop() {
		FixedCapacityStack stack = new FixedCapacityStack(2);
		stack.push("a");
		stack.pop();
		assertTrue(stack.isEmpty(),"popping non-empty stack is empty");
		assertFalse(stack.isFull(),"popping non-empty stack is not full");
		assertEquals(2,stack.capacity(),"initialized stack with capacity 2");
		assertEquals(0,stack.size(),"popping non-empty stack has size 0");
	}
	
	@Test
	public void testMidToMidPeek() {
		FixedCapacityStack stack = new FixedCapacityStack(2);
		stack.push("a");
		stack.peek();
		assertFalse(stack.isEmpty(),"peeking non-empty stack is empty");
		assertFalse(stack.isFull(),"peeking non-empty stack is not full");
		assertEquals(2,stack.capacity(),"initialized stack with capacity 2");
		assertEquals(1,stack.size(),"popping non-empty stack has size 0");
	}
	
	@Test
	public void testMidToMidPop() {
		FixedCapacityStack stack = new FixedCapacityStack(3);
		stack.push("a");
		stack.push("b");
		stack.pop();
		assertFalse(stack.isEmpty(),"popping non-empty stack is not empty");
		assertFalse(stack.isFull(),"popping non-empty stack is not full");
		assertEquals(3,stack.capacity(),"initialized stack with capacity 3");
		assertEquals(1,stack.size(),"popping non-empty stack has size 1");
	}
}
