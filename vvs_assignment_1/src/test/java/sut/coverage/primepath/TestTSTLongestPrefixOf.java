package sut.coverage.primepath;

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
    public void testOneToTheLeftExit() {
        // [7,8,9,6,16] [6,7,8,9,6]
        TST<Integer> tst = new TST<>();
        tst.put("danone", 1);
        assert(tst.longestPrefixOf("ananas").equals(""));
    }

    @Test
    public void testOneToTheRightExit() {
        // [7,8,10,11,6,16] [6,7,8,10,11,6]
        TST<Integer> tst = new TST<>();
        tst.put("danone", 1);
        assert(tst.longestPrefixOf("fotografia").equals(""));
    }

    @Test
    public void testQueryHeadInMiddleNoMatch() {
        // [1,3,5,6,7,8,10,12,13,15] [7,8,10,12,13,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("aa", 1);
        assert(tst.longestPrefixOf("a").equals(""));
    }

    @Test
    public void testQueryHeadInMiddleMatch() {
        // [1,3,5,6,7,8,10,12,13,14,15] [7,8,10,12,13,14,15,6,16]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        assert(tst.longestPrefixOf("ana").equals("a"));
    }

    @Test
    public void testFirstTwoToTheLeft() {
        // [9,6,7,8,9] [8,9,6,7,8] [7,8,9,6,7] [1,3,5,6,7,8,9]
        TST<Integer> tst = new TST<>();
        tst.put("danone", 1);
        tst.put("caju", 2);
        tst.put("ban", 3);
        assert(tst.longestPrefixOf("banana").equals("ban"));
    }
    
    @Test
    public void testFirstTwoToTheRight() {
        // [11,6,7,8,10,11] [7,8,10,11,6,7] [8,10,11,6,7,8] [10,11,6,7,8,10] [1,3,5,6,7,8,10,11]
        TST<Integer> tst = new TST<>();
        tst.put("danone", 1);
        tst.put("eagan", 2);
        tst.put("foto", 3);
        assert(tst.longestPrefixOf("fotografia").equals("foto"));
    }

    @Test
    public void testRightThenLeftThenMiddle() {
        // [10,11,6,7,8,9] [9,6,7,8,10,12,13,15]
        TST<Integer> tst = new TST<>();
        tst.put("danone", 1);
        tst.put("foto", 2);
        tst.put("eag", 3);
        assert(tst.longestPrefixOf("eagan").equals("eag"));
    }

    @Test
    public void testLeftThenRightThenMiddle() {
        // [9,6,7,8,10,11] [11,6,7,8,10,12,13,15]
        TST<Integer> tst = new TST<>();
        tst.put("danone", 1);
        tst.put("ananas", 2);
        tst.put("ban", 3);
        assert(tst.longestPrefixOf("banana").equals("ban"));
    }

    @Test
    public void testMiddleNoMatchThenMiddleNoMatch() {
        // [6,7,8,10,12,13,15,6] [12,13,15,6,7,8,10,12] 
        // [13,15,6,7,8,10,12,13] [15,6,7,8,10,12,13,15]
        // [10,12,13,15,6,7,8,10] [8,10,12,13,15,6,7,8]
        // [7,8,10,12,13,15,6,7]
        TST<Integer> tst = new TST<>();
        tst.put("ana", 1);
        assert(tst.longestPrefixOf("ananas").equals("ana"));
    }

    @Test
    public void testMiddleMatchThenMiddleMatch() {
        // [6,7,8,10,12,13,14,15,6] [7,8,10,12,13,14,15,6,7]
        // [8,10,12,13,14,15,6,7,8] [13,14,15,6,7,8,10,12,13]
        // [12,13,14,15,6,7,8,10,12] [14,15,6,7,8,10,12,13,14]
        // [15,6,7,8,10,12,13,14,15] [10,12,13,14,15,6,7,8,10]
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        tst.put("ab", 2);
        assert(tst.longestPrefixOf("abana").equals("ab"));
    }

    @Test
    public void testRightThenMiddleMatchThenRight() {
        // [11,6,7,8,10,12,13,14,15] [12,13,14,15,6,7,8,10,11]
    }

    @Test
    public void testMiddleNoMatchThenRightThenMiddleMatchThenRight() {
        // [12,13,15,6,7,8,10,11]
        // [11,6,7,8,10,12,13,14,15]
        // [12,13,14,15,6,7,8,10,11]
        TST<Integer> tst = new TST<>();
        tst.put("danone", 1);
        tst.put("de",2);
        tst.put("dentes",2);
        assert(tst.longestPrefixOf("depois").equals("de"));
    }

    @Test
    public void testMiddleNoMatchThenLeftThenMiddleMatchThenLeft() {
        // [10,12,13,15,6,7,8,9]
        // [9,6,7,8,10,12,13,14,15]
        // [10,12,13,14,15,6,7,8,9]
        TST<Integer> tst = new TST<>();
        tst.put("desejo", 1);
        tst.put("danone", 2);
        tst.put("da", 3);
        assert(tst.longestPrefixOf("dados").equals("da"));
    }
}
