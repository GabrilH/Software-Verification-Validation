package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class KeyGenerator extends Generator<String> {
    
    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = ".-\\;:_@[]^/|}{";
    private static final String ALL_MY_CHARS = LOWERCASE_CHARS
            + UPPERCASE_CHARS + NUMBERS + SPECIAL_CHARS;
    public static final int MAX_LENGTH = 20;

    public KeyGenerator() {
        super(String.class);
    }

    @Override
    public String generate(SourceOfRandomness random, GenerationStatus status) {
        int length = random.nextInt(1, MAX_LENGTH);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALL_MY_CHARS.length());
            sb.append(ALL_MY_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
