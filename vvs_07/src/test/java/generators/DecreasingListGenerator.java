package generators;

import java.util.*;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 * ref: https://stackoverflow.com/questions/36454149/
 */
public class DecreasingListGenerator extends Generator<List<Integer>> {
	
	private static final int MAX_VALUE = 1000;
    private static final GenerationStatus.Key<Integer> PREVIOUS_KEY = new GenerationStatus.Key<>("previous", Integer.class);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public DecreasingListGenerator() {
        super((Class<List<Integer>>) (Class) List.class);
    }
    
    public List<Integer> generate(SourceOfRandomness random, GenerationStatus status) {
        List<Integer> result = new ArrayList<>();

        int previous = status.valueOf(PREVIOUS_KEY).orElse(MAX_VALUE);
        int current = random.nextInt(previous);

        if (current > 0) {
            result.add(current);

            // use GenerationStatus#setValue(Key, Object) to pass values between generators
            status.setValue(PREVIOUS_KEY, current);
            // get one of the available generators using gen()
            Generator<List<Integer>> listGen = gen().make(DecreasingListGenerator.class);
            // status includes the actual max value defined above 
            result.addAll(listGen.generate(random, status));
            status.setValue(PREVIOUS_KEY, null);
        }

        return result;
    }

}
