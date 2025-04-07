package sut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.util.List;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class PrimeFactorsQuickCheck {
    
    // @Property(trials = 1000)
    // public void testFactorsProduct(@InRange(minInt = 1) int i) {
    //     System.out.println("Testing number: " + i);
    //     List<Integer> factors = PrimeFactors.factor(i);
    //     int product = factors.stream().reduce(1, (a, b) -> a * b);
    //     System.out.println("Factors: " + factors + ", Product: " + product);
    //     assertEquals("Product of prime factors does not equal original number", i, product);
    // }

    // @Property(trials = 1000)
    // public void testFactorsArePrime(@InRange(minInt = 1) int i) {
    //     System.out.println("Testing number: " + i);
    //     List<Integer> factors = PrimeFactors.factor(i);
    //     System.out.println("Factors: " + factors);
    //     for (int factor : factors) {
    //         assertEquals("Factor is not prime", true, PrimeFactors.isPrime(factor));
    //     }
    // }

    @Property(trials = 1000)
    public void testEvenNumberHasFactor2(@InRange(minInt = 1, maxInt = 100000) int i) {
        i = i*2; // Ensure i is even
        System.out.println("Testing even number: " + i);
        List<Integer> factors = PrimeFactors.factor(i);
        System.out.println("Factors: " + factors);
        assertEquals("Even number does not have 2 as a factor", true, factors.contains(2));
    }
}
