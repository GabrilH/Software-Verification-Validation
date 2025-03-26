package sut.coverage.alldupaths;

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
        // [1,3,5,6,16]
        TST<Integer> tst = new TST<>();
        assert(tst.longestPrefixOf("a").equals(""));
    }

    @Test
    public void testLeftThenExit() {
        // [1,3,5,6,7,8,9,6,16] 
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assert(tst.longestPrefixOf("a").equals(""));
    }

    @Test
    public void testRightThenExit() {
        // [1,3,5,6,7,8,10,11,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assert(tst.longestPrefixOf("d").equals(""));
    }
    
    @Test
    public void testMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("casa", 1);
        assert(tst.longestPrefixOf("c").equals(""));
    }

    @Test
    public void testMiddleNoMatchThenLeftThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,9,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("cb", 1);
        assert(tst.longestPrefixOf("ca").equals(""));
    }

    @Test
    public void testMiddleMatchThenLeftThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,9,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        tst.put("ab", 2);
        assert(tst.longestPrefixOf("aa").equals("a"));
    }

    @Test
    public void testMiddleNoMatchThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,12,13,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("caa", 1);
        assert(tst.longestPrefixOf("ca").equals(""));
    }

    @Test
    public void testMiddleMatchThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,7,8,10,12,13,15,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("cab", 2);
        assert(tst.longestPrefixOf("ca").equals("c"));
    }

    @Test
    public void testLeftThenLeftThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,9,6,16]:
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("b", 2);
        assert(tst.longestPrefixOf("a").equals(""));
    }

    @Test
    public void testLeftThenRightThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,11,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("a", 2);
        assert(tst.longestPrefixOf("b").equals(""));
    }

    @Test
    public void testLeftThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,12,13,15,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("ba", 2);
        assert(tst.longestPrefixOf("b").equals(""));
    }
    
    @Test
    public void testLeftThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,9,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("a", 2);
        assert(tst.longestPrefixOf("a").equals("a"));
    }

    @Test
    public void testRightThenLeftThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,9,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("e", 2);
        assert(tst.longestPrefixOf("d").equals(""));
    }

    @Test
    public void testRightThenRightThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,11,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("d", 2);
        assert(tst.longestPrefixOf("e").equals(""));
    }

    @Test
    public void testRightThenMiddleNoMatchThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,12,13,15,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("da", 2);
        assert(tst.longestPrefixOf("d").equals(""));	
    }

    @Test
    public void testRigthThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,11,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("d", 2);
        assert(tst.longestPrefixOf("d").equals("d"));
    }

    @Test
    public void testMiddleNoMatchThenRightThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,11,6,16]	
        TST<Integer> tst = new TST<>();
        tst.put("ca", 1);
        assert(tst.longestPrefixOf("cb").equals(""));
    }

    @Test
    public void testMiddleNoMatchThenMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,15,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("ca", 1);
        assert(tst.longestPrefixOf("ca").equals("ca"));
    }

    @Test
    public void testMiddleMatchThenExit() {
        // [1,3,5,6,7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assert(tst.longestPrefixOf("c").equals("c"));
    }
}
