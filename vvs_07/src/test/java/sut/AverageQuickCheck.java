package sut;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import generators.SmallIntegerListGenerator;

/**
 * Property-based testing (PBT) with JUnit QuickCheck
 * 
 * A property is a proposition that must be true at a given context
 * Operations over types must follow a given set of properties (eg, + must be commutative)
 * 
 * Check:
 *    http://blog.xebia.com/property-based-testing-java-junit-quickcheck-part-1-basics/
 *    http://blog.xebia.com/property-based-testing-java-junit-quickcheck-part-2-generators/
 *    
 * @author jpn
 */

@RunWith(JUnitQuickcheck.class)
public class AverageQuickCheck {

	@Before
	public void setUp() {
	}

	@Property
	public void testShuffle(int[] xs) {	
		assertEquals("average fails after shuffling", Average.average(xs), Average.average(shuffle(xs)));
	}
	
	private int[] shuffle(int[] xs) {
		// convert to List
		List<Integer> list = Arrays.stream(xs).boxed().collect(Collectors.toList()); 
		Collections.shuffle(list);
		// reconvert to int[]
		return list.stream().mapToInt(Integer::intValue).toArray();
	}
	
	@Property(trials = 50)  // default is 100
	public void testNegative(int[] xs) {
		int[] negatives = Arrays.stream(xs).map(i -> -i).toArray();

		assertEquals("-avg(xs) != avg(-xs)", -Average.average(xs), Average.average(negatives));
	}

	@Property
	public void testDoublingVectorSize(@InRange(minInt = -1000, maxInt = 1000) int[] xs) {
		
		/* @InRange does something like:
		 *   foreach(i in xs):
		 * 	   assumeThat(i, greaterThanOrEqualTo(-1000));
   		 *     assumeThat(i, lessThanOrEqualTo(1000));
		 */
		
		int[] xsTwice = IntStream.concat(Arrays.stream(xs), 
				                         Arrays.stream(xs)).toArray();
		assertEquals("avg(xs++xs) != avg(xs)", Average.average(xs), Average.average(xsTwice));
	}
	
	@Property
	public void testDoublingVectorSize2(@From(SmallIntegerListGenerator.class) List<Integer> list) {
		int[] xs = list.stream().mapToInt(i->i).toArray();
		
		int[] xsTwice = IntStream.concat(Arrays.stream(xs), 
                Arrays.stream(xs)).toArray();
        assertEquals("avg(xs++xs) != avg(xs)", Average.average(xs), Average.average(xsTwice));
	}

}
