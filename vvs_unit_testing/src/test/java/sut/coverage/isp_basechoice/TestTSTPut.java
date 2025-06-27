package sut.coverage.isp_basechoice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import sut.TST;

public class TestTSTPut {

    // 1. Trie already includes the new key
        // True - Key exists
        // False - The key is not in the trie.

    // 2. Trie already includes some new key prefix
        // True - Some prefix of the new key exists
        // False- No prefix exists

    // 3. Trie is empty
        // True - Empty Trie
        // False - Non-empty Trie

    // 4. Lexicographic properties of the new key
        // Smallest key
        // Largest key
        // Typical key

    // Base Choice
        // 1. False - The key is not in the trie.
        // 2. False - No prefix exists
        // 3. False - Non-empty Trie
        // 4. Typical key

    private TST<Integer> tst;

    @BeforeEach
    public void setUp() {
        tst = new TST<>();
    }

    @Test
    public void testBaseChoice() {
        // 1. False - The key is not in the trie.
        // 2. False - No prefix exists
        // 3. False - Non-empty Trie
        // 4. Typical key
        tst.put("zebra", 1);
        tst.put("apple", 2);
        tst.put("seashells", 3);
        assertEquals(Integer.valueOf(3), tst.get("seashells"));
    }
    
    @Test
    public void testKeyExists() {
        // -> 1. True - Key exists
        // 2. False - No prefix exists
        // 3. False - Non-empty Trie
        // 4. Typical key
        tst.put("zebra", 1);
        tst.put("apple", 2);
        tst.put("seashells", 3);
        tst.put("seashells", 4);
        assertEquals(Integer.valueOf(4), tst.get("seashells"));
    }

    @Test
    public void testKeyPrefixExists() {
        // 1. False - The key is not in the trie.
        // -> 2. True - Some prefix of the new key exists
        // 3. False - Non-empty Trie
        // 4. Typical key
        tst.put("zebra", 1);
        tst.put("apple", 2);
        tst.put("sea", 3);
        tst.put("seashells", 4);
        assertEquals(Integer.valueOf(4), tst.get("seashells"));
    }

    @Test
    public void testEmptyTrie() {
        // 1. False - The key is not in the trie.
        // 2. False - No prefix exists
        // -> 3. True - Empty Trie
        // 4. Irrelevant
        tst.put("seashells", 1);
        assertEquals(Integer.valueOf(1), tst.get("seashells"));
    }
    
    @Test
    public void testKeyIsTheSmallest() {
        // 1. False - The key is not in the trie.
        // 2. False - No prefix exists
        // 3. False - Non-empty Trie
        // -> 4. Smallest key
        tst.put("zebra", 1);
        tst.put("apple", 2);
        tst.put("aaaaapple", 3);
        assertEquals(Integer.valueOf(3), tst.get("aaaaapple"));
    }

    @Test
    public void testKeyIsTheLargest() {
        // 1. False - The key is not in the trie.
        // 2. False - No prefix exists
        // 3. False - Non-empty Trie
        // -> 4. Largest key
        tst.put("zebra", 1);
        tst.put("apple", 2);
        tst.put("zzzzzzebra", 3);
        assertEquals(Integer.valueOf(3), tst.get("zzzzzzebra"));
    }
}
