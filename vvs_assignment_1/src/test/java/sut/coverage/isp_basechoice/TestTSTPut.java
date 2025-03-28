package sut.coverage.isp_basechoice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import sut.TST;

public class TestTSTPut {

    private TST<Integer> tst;

    @BeforeEach
    void setUp() {
        tst = new TST<>();
    }

    // @Test
    // void testInsertNewKey() {
    //     tst.put("she", 1);
    //     assertTrue(tst.contains("she"), "Key missing");
    //     assertEquals(Integer.valueOf(1), tst.get("she"), "Wrong value");
    // }

    @Test
    void testInsertWithExistingKey() {
        tst.put("shore", 1);
        tst.put("shore", 2);
        assertEquals(Integer.valueOf(2), tst.get("shore"), "Update failed");
    }

    @Test
    void testInsertWithExistingPrefix() {
        tst.put("sea", 1);
        tst.put("seashells", 2);
        assertTrue(tst.contains("sea"), "Prefix missing");
        assertTrue(tst.contains("seashells"), "Key missing");
        assertEquals(Integer.valueOf(1), tst.get("sea"), "Wrong prefix value");
        assertEquals(Integer.valueOf(2), tst.get("seashells"), "Wrong value");
    }

    @Test
    void testInsertIntoEmptyTrie() {
        tst.put("sells", 1);
        assertTrue(tst.contains("sells"), "Key missing");
        assertEquals(Integer.valueOf(1), tst.get("sells"), "Wrong value");
    }

    @Test
    void testInsertSmallestLexicographically() {
        tst.put("shore", 1);
        tst.put("the", 2);
        tst.put("by", 3);
        tst.put("asphalt", 4);
        assertTrue(tst.contains("asphalt"), "Smallest key missing");
        assertEquals(Integer.valueOf(4), tst.get("asphalt"), "Wrong value");
    }

    @Test
    void testInsertLargestLexicographically() {
        tst.put("she", 1);
        tst.put("sells", 2);
        tst.put("sea", 3);
        tst.put("zebra", 4);
        assertTrue(tst.contains("zebra"), "Largest key missing");
        assertEquals(Integer.valueOf(4), tst.get("zebra"), "Wrong value");
    }

    @Test
    void testInsertTypicalKey() {
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
