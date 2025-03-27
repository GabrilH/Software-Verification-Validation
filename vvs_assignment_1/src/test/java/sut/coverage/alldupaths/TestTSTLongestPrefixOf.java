package sut.coverage.alldupaths;

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
        TST<Integer> tst = new TST<>();
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testLeftThenExit() {
        // [1,3,5,6,7,8,9,6,16] 
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testRightThenExit() {
        // [1,3,5,6,7,8,10,11,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("", tst.longestPrefixOf("d"));
    }
    
    @Test
    public void testMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("casa", 1);
        assertEquals("", tst.longestPrefixOf("c"));
    }

    @Test
    public void testMiddleNoMatchThenLeftThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,9,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("cb", 1);
        assertEquals("", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testMiddleMatchThenLeftThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,9,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        tst.put("ab", 2);
        assertEquals("a", tst.longestPrefixOf("aa"));
    }

    @Test
    public void testMiddleNoMatchThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,12,13,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("caa", 1);
        assertEquals("", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testMiddleMatchThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,10,12,13,15,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("cab", 2);
        assertEquals("c", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testLeftThenLeftThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,9,6,16]:
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("b", 2);
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testLeftThenRightThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,11,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("a", 2);
        assertEquals("", tst.longestPrefixOf("b"));
    }

    @Test
    public void testLeftThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,12,13,15,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("ba", 2);
        assertEquals("", tst.longestPrefixOf("b"));
    }
    
    @Test
    public void testLeftThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("a", 2);
        assertEquals("a", tst.longestPrefixOf("a"));
    }

    @Test
    public void testRightThenLeftThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,9,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("e", 2);
        assertEquals("", tst.longestPrefixOf("d"));
    }

    @Test
    public void testRightThenRightThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,11,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("d", 2);
        assertEquals("", tst.longestPrefixOf("e"));
    }

    @Test
    public void testRightThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,12,13,15,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("da", 2);
        assertEquals("", tst.longestPrefixOf("d"));	
    }

    @Test
    public void testRigthThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("d", 2);
        assertEquals("d", tst.longestPrefixOf("d"));
    }

    @Test
    public void testMiddleNoMatchThenRightThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,11,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("ca", 1);
        assertEquals("", tst.longestPrefixOf("cb"));
    }

    @Test
    public void testMiddleNoMatchThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("ca", 1);
        assertEquals("ca", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("c", tst.longestPrefixOf("c"));
    }
}
