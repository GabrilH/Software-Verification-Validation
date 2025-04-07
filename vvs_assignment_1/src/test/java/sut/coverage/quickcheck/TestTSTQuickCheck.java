package sut.coverage.quickcheck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import generators.TrieGenerator;
import sut.TST;

@RunWith(JUnitQuickcheck.class)
public class TestTSTQuickCheck {
    
    // @Property
    // public void testOrderOfInsertDontChangeFinalValue(@From(TrieGenerator.class) TST<Integer> trie) {
        

    // }

    @Property
    public void afterDeleteTreeShouldBeEmpty(@From(TrieGenerator.class) TST<Integer> trie) {
        assumeTrue(trie.size() > 0);
        System.out.println("Testing trie size: " + trie.size());
        System.out.println("Testing trie: " + trie.toString());
        trie.delete();
        assertEquals(0, trie.size());
    }

    // @Property
    // public void insertAndRemoveSameKeyPreservesInitialValue(
    //         @From(TrieGenerator.class) TST<Integer> trie,
    //         @From(KeyGeneratory.class) String key, Integer value) {

    //     TST<Integer> trieCopy = trie.copy();
    //     trie.put(key, value);
    //     trie.
    // }

    @Property
    public void stricterPrefixReturnsSubset(@From(TrieGenerator.class) TST<Integer> trie) {
        
        // To get a useful prefix to test with we get a key from the trie.
        // We assume that the trie has at least one key, so we can get a prefix.
        assumeTrue(trie.size() > 0);
        Iterable<String> keys = trie.keys();
        
        // We choose the first key as the prefix to test with.
        String longPrefix = keys.iterator().next();
        // Substring the prefix to get a shorter and more useful prefix.
        longPrefix = longPrefix.substring(0, longPrefix.length() - longPrefix.length() / 2);
        Iterable<String> longPrefixKeys = trie.keysWithPrefix(longPrefix);

        // Create shorter prefixes from the long prefix
        // by removing the last character one by one.
        for (int i = longPrefix.length(); i > 0; i--) {
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
                assertEquals(true, found);
            }
        }
    }
}
