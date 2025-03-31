package sut.coverage.isp_basechoice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import sut.TST;

public class TestTSTPut {

    // 1. Trie already includes the new key
        // Key exists
        // The key is not in the trie.

    // Trie already includes some new key prefix
        // Some prefix of the new key exists
        // No prefix exists

    // Trie is empty
        // Empty Trie
        // Non-empty Trie

    // Lexicographic properties of the new key
        // Smallest key
        // Largest key
        // Typical key

    private TST<Integer> tst;

    @BeforeEach
    public void setUp() {
        tst = new TST<>();
    }
    
    @Test
    public void testKeyAlreadyExists() {
    // 1. Trie already includes the new key
        // Key exists
        tst.put("shore", 1);
        tst.put("shore", 2);
        assertEquals(Integer.valueOf(2), tst.get("shore"), "Update failed");
    }

    @Test
    public void testKeyDoesNotYetExists() {
    // 1. Trie already includes the new key
        // The key is not in the trie.
        assertFalse(tst.contains("shore"), "Key should not exist");
        tst.put("shore", 1);
        assertTrue(tst.contains("shore"), "Key should exist after insertion");
    }

    @Test
    public void testKeyPrefixExists() {
    // 2. Trie already includes some new key prefix
        // Some prefix of the new key exists
        tst.put("sea", 1);
        tst.put("seashells", 2);
        assertEquals(Integer.valueOf(1), tst.get("sea"), "Wrong prefix value");
        assertEquals(Integer.valueOf(2), tst.get("seashells"), "Wrong value for seashells");
    }

    @Test
    public void testKeyPrefixDoesNotExist() {
    // 3. Trie already includes some new key prefix
        // No prefix exists
        tst.put("sand", 1);
        assertFalse(tst.contains("seashells"), "Prefix should not exist");
        tst.put("seashells", 2);
        assertTrue(tst.contains("seashells"), "Key should exist after insertion");
    }

    @Test
    public void testEmptyTrie() {
    // 3. Trie is empty
        // Empty Trie
        assertFalse(tst.contains("shore"), "Key should not exist in empty trie");
        tst.put("shore", 1);
        assertTrue(tst.contains("shore"), "Key should exist after insertion in empty trie");
    }

    @Test
    public void testNonEmptyTrie() {
    // 3. Trie is empty
        // Non-empty Trie
        tst.put("sea", 1);
        assertTrue(tst.contains("sea"), "Key should exist in non-empty trie");
        tst.put("shore", 2);
        assertTrue(tst.contains("shore"), "Key should exist after insertion in non-empty trie");
    }

    @Test
    void testInsertSmallestLexicographically() {
    // 4. Lexicographic properties of the new key
        // Smallest key
        tst.put("shore", 1);
        tst.put("the", 2);
        tst.put("by", 3);
        tst.put("asphalt", 4);
        assertTrue(tst.contains("asphalt"), "Smallest key missing");
        assertEquals(Integer.valueOf(4), tst.get("asphalt"), "Wrong value");
    }

    @Test
    void testInsertLargestLexicographically() {
    // 4. Lexicographic properties of the new key
        // Largest key
        tst.put("she", 1);
        tst.put("sells", 2);
        tst.put("sea", 3);
        tst.put("zebra", 4);
        assertTrue(tst.contains("zebra"), "Largest key missing");
        assertEquals(Integer.valueOf(4), tst.get("zebra"), "Wrong value");
    }

    @Test
    void testInsertTypicalKey() {
    // 4. Lexicographic properties of the new key
        // Typical key
        tst.put("she", 1);
        tst.put("sells", 2);
        tst.put("sea", 3);
        tst.put("sand", 4);
        assertTrue(tst.contains("sand"), "Key missing");
        assertEquals(Integer.valueOf(4), tst.get("sand"), "Wrong value");
    }

    // @Test
    // void testRemoveKeyWithNullValue() {
    //     tst.put("shore", 1);
    //     tst.put("shore", null);
    //     assertFalse(tst.contains("shore"), "Key not removed");
    // }

    // @Test
    // void testPutNullKeyThrowsException() {
    //     Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    //         tst.put(null, 1);
    //     });
    //     assertEquals("calls put() with null key", exception.getMessage(), "Wrong exception message");
    // }

}
