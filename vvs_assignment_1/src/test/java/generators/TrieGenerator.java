package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.TST;

public class TrieGenerator<T> extends Generator<TST<Integer>> {

    public static final int MIN_TRIE_SIZE = 0;
    public static final int MAX_TRIE_SIZE = KeyListGenerator.MAX_SIZE;

    private static final int MIN_VALUE = -100000;
    private static final int MAX_VALUE = 100000;

    public TrieGenerator(Class<TST<Integer>> type) {
        super(type);
    }

    @Override
    public TST<Integer> generate(SourceOfRandomness random, GenerationStatus status) {
        TST<Integer> trie = new TST<>();
        Generator<String> keyGenerator = gen().make(KeyGenerator.class);
        int size = random.nextInt(MIN_TRIE_SIZE, MAX_TRIE_SIZE);
        for (int i = 0; i < size; i++) {
            String key = keyGenerator.generate(random, status);
            int value = random.nextInt(MIN_VALUE, MAX_VALUE);
            trie.put(key, value);
        }
        return trie;
    }
}
