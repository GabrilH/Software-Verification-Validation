package generators;

import java.util.*;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class String40CharsGenerator extends Generator<String> {

    public static final int SIZE = 40;
    public static final String specialChars = ".-\\;:_@[]^/|";

    public String40CharsGenerator(Class<String> type) {
        super(type);
    }

    @Override
    public String generate(SourceOfRandomness src, GenerationStatus status) {
        int size = SIZE;
        StringBuilder sb = new StringBuilder(size);
        while (size-- > 0) {
            int choice = src.nextInt(4); // 0: lowercase, 1: uppercase, 2: digits, 3: special chars
            char c;
            if (choice == 0) {
            c = (char) (src.nextInt(26) + 'a'); // Lowercase letters
            } else if (choice == 1) {
            c = (char) (src.nextInt(26) + 'A'); // Uppercase letters
            } 
            else if (choice == 2) {
            c = (char) (src.nextInt(10) + '0'); // Digits
            } else {
            c = specialChars.charAt(src.nextInt(specialChars.length())); // Special characters
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
