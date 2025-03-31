package generators;

import java.util.List;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class DecreasingListGeneratorTest {

	@Property(trials=15)
	public void testListShow(@From(DecreasingListGenerator.class) List<Integer> list) {
		System.out.println(list);
	}

}
