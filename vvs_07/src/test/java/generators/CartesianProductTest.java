package generators;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.ValuesOf;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

enum Ternary { YES, NO, MAYBE }

// @ValuesOf force the generation to run through every value in the type’s domain
@RunWith(JUnitQuickcheck.class)
public class CartesianProductTest {

	@Property(trials=6)
	public void testCartesianProduct(@ValuesOf boolean b, @ValuesOf Ternary t) {
		System.out.println(b+":"+t);
	}

}


