package generators;

import java.util.List;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class KeyListGenerator extends Generator<List<String>> {

    public static final int MAX_SIZE = 20;

    public KeyListGenerator(Class<List<String>> type) {
        super(type);
    }

    @Override
    public List<String> generate(SourceOfRandomness random, GenerationStatus status) {
        
        int size = random.nextInt(1, MAX_SIZE);
        Generator<String> keyGenerator = gen().make(KeyGenerator.class);

        List<String> list = new java.util.ArrayList<>(size);
        while (size-- > 0)
            list.add(keyGenerator.generate(random, status));

        return list;
    }
    
}
