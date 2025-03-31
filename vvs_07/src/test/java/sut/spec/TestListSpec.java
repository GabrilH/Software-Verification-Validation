package sut.spec;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;


/*
 * Use list specification to test java.util.LinkedList
 */

@RunWith(JUnitQuickcheck.class)
public class TestListSpec {
	
	private LinkedList<Integer> emptyList;
	
	@Before
	public void setup() {
		emptyList = new LinkedList<>();
	}

	@Property
	public void testGetFirst(LinkedList<Integer> list, int e) {
		list.addFirst(e);
		
		assertTrue("spec: getFirst (addFirst (L, E)) = E", list.getFirst() == e);
	}	
	
	@Property
	public void testRemoveFirst(LinkedList<Integer> list, int e) {
		@SuppressWarnings("unchecked")
		LinkedList<Integer> newList = (LinkedList<Integer>) list.clone();
		newList.addFirst(e);
		newList.removeFirst();
		
		assertTrue("spec: removeFirst (addFirst (L, E)) = L", list.equals(newList));
	}	
}












