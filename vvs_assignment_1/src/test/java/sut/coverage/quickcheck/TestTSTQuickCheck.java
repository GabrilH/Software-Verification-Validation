package sut.coverage.quickcheck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.Collections;
import java.util.List;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import generators.KeyGenerator;
import generators.KeyListGenerator;
import generators.TrieGenerator;
import sut.TST;

@RunWith(JUnitQuickcheck.class)
public class TestTSTQuickCheck {
    
    @Property
    public void orderOfInsertDoesntChangeFinalValue(
        @From(TrieGenerator.class) TST<Integer> trie,
        @From(KeyListGenerator.class) List<String> keys,
        int value) {
        
        // Clone the trie to compare the final values
        TST<Integer> trieFinal = (TST<Integer>) trie.clone();

        for (int i = 0; i < keys.size(); i++) {
            trie.put(keys.get(i), value);
        }

        System.out.println("Initial (before shuffle) trie keys: " + trie.keys());

        // Shuffle the keys to test the order of insertion
        Collections.shuffle(keys);

        for (int i = 0; i < keys.size(); i++) {
            trieFinal.put(keys.get(i), value);
        }

        System.out.println("Final (after shuffle) trie keys: " + trieFinal.keys());

        assertTrue(trie.equals(trieFinal));
        System.out.println();
    }

    @Property
    public void afterDeleteTreeShouldBeEmpty(@From(TrieGenerator.class) TST<Integer> trie) {
        assumeTrue(trie.size() > 0);

        // Get trie keys
        Iterable<String> keys = trie.keys();
        System.out.println("Initial trie keys: " + keys);
         
        // Delete all keys from the trie
        for (String key : keys) {
            System.out.println("Deleting key: " + key);
            trie.deleteKey(key);
        }

        System.out.println("Trie keys after deleting all keys: " + trie.keys());

        // Create empty trie to compare with
        TST<Integer> emptyTrie = new TST<>();

        // If trie is empty, size should be 0 and equals to emptyTrie
        assertEquals(0, trie.size());
        assertTrue(emptyTrie.equals(trie));
        System.out.println();
    }

    @Property
    public void insertAndRemoveSameKeyPreservesInitialValue(
            @From(TrieGenerator.class) TST<Integer> trie,
            @From(KeyGenerator.class) String key,
            int value) {
        
        // Assume that the initial trie does not yet contain the key
        // otherwise it may happen that by putting and removing the same key
        // the resulted trie is not equal to the initial trie which still contains the key.
        assumeTrue(!trie.contains(key));

        // Clone the trie to compare the final values
        TST<Integer> trieInitial = trie.clone();
        System.out.println("Initial trie keys: " + trieInitial.keys());

        trie.put(key, value);
        System.out.println("Trie keys after inserting key: " + key + " -> " + trie.keys());

        trie.deleteKey(key);
        System.out.println("Trie keys after deleting key: " + key + " -> " + trie.keys());

        assertTrue(trie.equals(trieInitial));
        System.out.println();
    }

    @Property
    public void stricterPrefixReturnsSubset(
        @From(TrieGenerator.class) TST<Integer> trie,
        @From(KeyGenerator.class) String longPrefix) {
        
        System.out.println("Testing with longPrefix: " + longPrefix);
        Iterable<String> longPrefixKeys = trie.keysWithPrefix(longPrefix);

        // Useless to test when longPrefix doesn't match any key
        assumeTrue(longPrefixKeys.iterator().hasNext());

        // Create shorter prefixes from the long prefix
        // by removing the last character one by one.
        for (int i = longPrefix.length()-1; i > 0; i--) {
            String shortPrefix = longPrefix.substring(0, i);
            Iterable<String> shortPrefixKeys = trie.keysWithPrefix(shortPrefix);
            
            // For each key in the long prefix, we check if it is also in the short prefix.
            for (String key : longPrefixKeys) {
                boolean found = false;
                for (String shortKey : shortPrefixKeys) {
                    if (key.equals(shortKey)) {
                        System.out.println("Found key: " + key + " from longPrefixes: " + longPrefixKeys + " with shortPrefix: " + shortPrefix);
                        found = true;
                        break;
                    }
                }
                assertTrue(found);
            }
        }

        System.out.println();
    }
}
