package sut.coverage.primepath;

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
    public void testLeftThenLeftThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,9,6,16]:
        // LeftThenLeftThenExit
        //
        // [1,3,5,6,7,8,9], [7,8,9,6,7], 
        // [7,8,9,6,16], [8,9,6,7,8], 
        // [6,7,8,9,6], [9,6,7,8,9]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("b", 2);
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testRightThenRightThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,11,6,16]	
        // RightThenRightThenExit
        // 
        // [1,3,5,6,7,8,10,11], [8,10,11,6,7,8], 
        // [7,8,10,11,6,7], [7,8,10,11,6,16], 
        // [6,7,8,10,11,6], [10,11,6,7,8,10], 
        // [11,6,7,8,10,11]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("d", 2);
        assertEquals("", tst.longestPrefixOf("e"));
    }

    @Test
    public void testLeftThenRightThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,11,6,16]	
        // LeftThenRightThenExit
        //
        // [1,3,5,6,7,8,9], [9,6,7,8,10,11], 
        // [7,8,10,11,6,16], [6,7,8,10,11,6], 
        // [7,8,9,6,7], [8,9,6,7,8], [6,7,8,9,6]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("a", 2);
        assertEquals("", tst.longestPrefixOf("b"));
    }

    @Test
    public void testRightThenLeftThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,9,6,16]	
        // RightThenLeftThenExit
        //
        // [1,3,5,6,7,8,10,11], [8,10,11,6,7,8], 
        // [7,8,10,11,6,7], [6,7,8,10,11,6], 
        // [10,11,6,7,8,9], [7,8,9,6,16], [6,7,8,9,6]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("e", 2);
        assertEquals("", tst.longestPrefixOf("d"));
    }

    @Test
    public void testRightThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,12,13,15,6,16]	
        // RightThenMiddleNoMatchThenExit
        //
        // [7,8,10,12,13,15,6,16], [1,3,5,6,7,8,10,11], 
        // [6,7,8,10,12,13,15,6], [11,6,7,8,10,12,13,15], 
        //[8,10,11,6,7,8], [7,8,10,11,6,7], [6,7,8,10,11,6], 
        // [10,11,6,7,8,10]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("da", 2);
        assertEquals("", tst.longestPrefixOf("d"));	
    }

    @Test
    public void testMiddleNoMatchThenRightThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,11,6,16]	
        // MiddleNoMatchThenRightThenExit
        //
        // [1,3,5,6,7,8,10,12,13,15], [7,8,10,12,13,15,6,7], 
        // [8,10,12,13,15,6,7,8], [6,7,8,10,12,13,15,6], 
        // [12,13,15,6,7,8,10,11], [10,12,13,15,6,7,8,10], 
        // [7,8,10,11,6,16], [6,7,8,10,11,6]
        TST<Integer> tst = new TST<>();
        tst.put("ca", 1);
        assertEquals("", tst.longestPrefixOf("cb"));
    }

    @Test
    public void testMiddleNoMatchThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,12,13,15,6,16]
        // MiddleNoMatchThenMiddleNoMatchThenExit
        //
        // [1,3,5,6,7,8,10,12,13,15], [7,8,10,12,13,15,6,7], 
        // [7,8,10,12,13,15,6,16], [8,10,12,13,15,6,7,8], 
        // [6,7,8,10,12,13,15,6], [12,13,15,6,7,8,10,12], 
        // [13,15,6,7,8,10,12,13], [15,6,7,8,10,12,13,15], 
        // [10,12,13,15,6,7,8,10]
        TST<Integer> tst = new TST<>();
        tst.put("caa", 1);
        assertEquals("", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testLeftThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,12,13,15,6,16]	
        // LeftThenMiddleNoMatchThenExit
        //
        // [7,8,10,12,13,15,6,16], [9,6,7,8,10,12,13,15], 
        // [6,7,8,10,12,13,15,6], [1,3,5,6,7,8,9], [7,8,9,6,7], 
        // [8,9,6,7,8], [6,7,8,9,6]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("ba", 2);
        assertEquals("", tst.longestPrefixOf("b"));
    }

    @Test
    public void testMiddleNoMatchThenLeftThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,9,6,16]
        // MiddleNoMatchThenLeftThenExit
        // 
        // [1,3,5,6,7,8,10,12,13,15], [7,8,10,12,13,15,6,7], 
        // [8,10,12,13,15,6,7,8], [6,7,8,10,12,13,15,6], 
        // [10,12,13,15,6,7,8,9], [7,8,9,6,16], [6,7,8,9,6]
        TST<Integer> tst = new TST<>();
        tst.put("cb", 1);
        assertEquals("", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testRigthThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,12,13,14,15,6,16]
        // RigthThenMiddleMatchThenExit
        //
        // [7,8,10,12,13,14,15,6,16], [6,7,8,10,12,13,14,15,6], 
        // [11,6,7,8,10,12,13,14,15], [1,3,5,6,7,8,10,11], 
        // [8,10,11,6,7,8], [7,8,10,11,6,7], [6,7,8,10,11,6], 
        // [10,11,6,7,8,10]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("d", 2);
        assertEquals("d", tst.longestPrefixOf("d"));
    }

    @Test
    public void testMiddleMatchThenRightThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,10,11,6,16]	
        // MiddleMatchThenRightThenExit
        //
        // [1,3,5,6,7,8,10,12,13,14,15], [6,7,8,10,12,13,14,15,6], 
        // [7,8,10,12,13,14,15,6,7], [8,10,12,13,14,15,6,7,8], 
        // [12,13,14,15,6,7,8,10,11], [10,12,13,14,15,6,7,8,10], 
        // [7,8,10,11,6,16], [6,7,8,10,11,6]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("ca", 2);
        assertEquals("c", tst.longestPrefixOf("cd"));
    }

    @Test
    public void testMiddleNoMatchThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,12,13,14,15,6,16]	
        // MiddleNoMatchThenMiddleMatchThenExit
        //
        // [1,3,5,6,7,8,10,12,13,15], [7,8,10,12,13,14,15,6,16], 
        // [6,7,8,10,12,13,14,15,6], [15,6,7,8,10,12,13,14,15], 
        // [7,8,10,12,13,15,6,7], [8,10,12,13,15,6,7,8], 
        // [6,7,8,10,12,13,15,6], [12,13,15,6,7,8,10,12], 
        // [13,15,6,7,8,10,12,13], [10,12,13,15,6,7,8,10]
        TST<Integer> tst = new TST<>();
        tst.put("ca", 1);
        assertEquals("ca", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testMiddleMatchThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,10,12,13,14,15,6,16]
        // MiddleMatchThenMiddleMatchThenExit
        //
        // [1,3,5,6,7,8,10,12,13,14,15], [7,8,10,12,13,14,15,6,16], 
        // [6,7,8,10,12,13,14,15,6], [7,8,10,12,13,14,15,6,7], 
        // [8,10,12,13,14,15,6,7,8], [13,14,15,6,7,8,10,12,13], 
        // [12,13,14,15,6,7,8,10,12], [14,15,6,7,8,10,12,13,14],
        // [15,6,7,8,10,12,13,14,15], [10,12,13,14,15,6,7,8,10]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("ca", 2);
        assertEquals("ca", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testMiddleMatchThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,10,12,13,15,6,16]	
        // MiddleMatchThenMiddleNoMatchThenExit
        //
        // [1,3,5,6,7,8,10,12,13,14,15], [6,7,8,10,12,13,14,15,6], 
        // [7,8,10,12,13,14,15,6,7], [8,10,12,13,14,15,6,7,8], 
        // [13,14,15,6,7,8,10,12,13], [12,13,14,15,6,7,8,10,12],
        // [10,12,13,14,15,6,7,8,10], [7,8,10,12,13,15,6,16], 
        // [6,7,8,10,12,13,15,6], [15,6,7,8,10,12,13,15]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("cab", 2);
        assertEquals("c", tst.longestPrefixOf("ca"));
    }

    @Test
    public void testLeftThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,12,13,14,15,6,16]
        // LeftThenMiddleMatchThenExit
        // 
        // [7,8,10,12,13,14,15,6,16], [6,7,8,10,12,13,14,15,6], 
        // [9,6,7,8,10,12,13,14,15], [1,3,5,6,7,8,9], [7,8,9,6,7], 
        // [8,9,6,7,8], [6,7,8,9,6]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("a", 2);
        assertEquals("a", tst.longestPrefixOf("a"));
    }

    @Test
    public void testMiddleMatchThenLeftThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,9,6,16]
        // MiddleMatchThenLeftThenExit
        // 	
        // [1,3,5,6,7,8,10,12,13,14,15], [6,7,8,10,12,13,14,15,6], 
        // [7,8,10,12,13,14,15,6,7], [8,10,12,13,14,15,6,7,8], 
        // [10,12,13,14,15,6,7,8,9], [7,8,9,6,16], [6,7,8,9,6]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        tst.put("ab", 2);
        assertEquals("a", tst.longestPrefixOf("aa"));
    }
}
