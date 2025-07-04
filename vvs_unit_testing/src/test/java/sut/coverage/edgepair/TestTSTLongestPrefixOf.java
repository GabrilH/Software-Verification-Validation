package sut.coverage.edgepair;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
        // [1,3,5,6,16]
        //
        // [1,3,5], [3,5,6], [5,6,16]
        TST<Integer> tst = new TST<>();
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testMiddleMatchThenLeftThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,9,6,16]
        // MiddleMatchThenLeftThenExit
        //
        // [1,3,5], [3,5,6], [5,6,7], [6,7,8], [7,8,9], 
        // [7,8,10], [8,9,6], [8,10,12], [9,6,16], [10,12,13], 
        // [12,13,14], [13,14,15], [14,15,6], [15,6,7]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        tst.put("ab", 2);
        assertEquals("a", tst.longestPrefixOf("aa"));
    }

    @Test
    public void testRightThenLeftThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,9,6,16]
        // RightThenLeftThenExit
        // 
        // [1,3,5], [3,5,6], [5,6,7], [6,7,8], [7,8,9], 
        // [7,8,10], [8,9,6], [8,10,11], [9,6,16], 
        // [10,11,6], [11,6,7]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("e", 2);
        assertEquals("", tst.longestPrefixOf("d"));
    }

    @Test
    public void testLeftThenRightThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,11,6,16]
        // LeftThenRightThenExit
        // 
        // [1,3,5], [3,5,6], [5,6,7], [6,7,8], 
        // [7,8,9], [7,8,10], [8,9,6], [8,10,11], 
        // [9,6,7], [10,11,6], [11,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("a", 2);
        assertEquals("", tst.longestPrefixOf("b"));
    }

    @Test
    public void testMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,16]
        // MiddleNoMatchThenExit
        //
        // [1,3,5], [3,5,6], [5,6,7], [6,7,8], 
        // [7,8,10], [8,10,12], [10,12,13], 
        // [12,13,15], [13,15,6], [15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("casa", 1);
        assertEquals("", tst.longestPrefixOf("c"));
    }
}
