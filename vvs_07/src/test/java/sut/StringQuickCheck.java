package sut;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class StringQuickCheck {

    @Property(trials = 100)
    public void testStringLength(@From(generators.String40CharsGenerator.class) String str) {
        // Check if the string length is less than or equal to 40
        System.out.println("String: " + str);
    }
}
