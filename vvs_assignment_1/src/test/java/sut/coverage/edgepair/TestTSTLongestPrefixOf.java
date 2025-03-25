package sut.coverage.edgepair;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import sut.TST;

public class TestTSTLongestPrefixOf {
    
    @Test
    public void testWithNullQuery() {
        // [1,2]
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<>();
            tst.longestPrefixOf(null);
        });
    }

    @Test
    public void testWithEmptyQuery() {
        // [1,3,4]
        TST<Integer> tst = new TST<>();
        tst.longestPrefixOf("");
        assertNull(tst.longestPrefixOf(""));
    }

    @Test 
    public void testWithoutPuts() {
        // [1,3,5] [3,5,6] [5,6,16]
        TST<Integer> tst = new TST<>();
        assert(tst.longestPrefixOf("a").equals(""));
    }

    @Test
    public void testWithOnePutQueryHeadAtLeft() {
        // [5,6,7] [6,7,8] [7,8,9] [8,9,6] [9,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("b", 1);
        assert(tst.longestPrefixOf("a").equals(""));
    }

    @Test
    public void testWithOnePutQueryHeadAtRight() {
        // [7,8,10] [8,10,11] [10,11,6] [11,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        assert(tst.longestPrefixOf("b").equals(""));
    }

    @Test
    public void testWithOnePutQueryEqual() {
        // [8,10,12] [10,12,13] [12,13,14] [13,14,15] [14,15,6]
        // [15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        assert(tst.longestPrefixOf("a") == "a");
    }

    @Test 
    public void testWithOnePutQuerySubstring() {
        // [12,13,15] [13,15,6] [15,6,7]
        TST<Integer> tst = new TST<>();
        tst.put("app", 1);
        assert(tst.longestPrefixOf("apple").equals("app"));
    }

    @Test
    public void testThreePutsQueryBothRightAndLeft() {
        // [9,6,7] [11,6,7]
        TST<Integer> tst = new TST<>();
        tst.put("caju", 1);
        tst.put("ananas", 2);
        tst.put("ban", 3);
        assert(tst.longestPrefixOf("banana").equals("ban"));
    }
}
