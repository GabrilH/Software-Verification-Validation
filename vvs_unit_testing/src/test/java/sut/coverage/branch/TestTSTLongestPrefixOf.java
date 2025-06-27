package sut.coverage.branch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTLongestPrefixOf {
    
    @Test
    public void testWithNullQuery() {
        //B1
        assertThrows(IllegalArgumentException.class, () -> {
            new TST<Integer>().longestPrefixOf(null);
        });
    }

    @Test
    public void testWithEmptyQuery() {
        //!B1, B2
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.longestPrefixOf(""));
    }

    @Test
    public void testLeftThenExit() {
        //!B1, !B2, B3, !B3, B4
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testRightThenExit() {
        //!B1, !B2, B3, !B3, !B4, B5
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("", tst.longestPrefixOf("d"));
    }

    @Test
    public void testMiddleMatchThenMiddleNoMatchThenExit() {
        //!B1, !B2, B3, !B3, !B4, !B5, B6, !B6
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        tst.put("cab", 2);
        assertEquals("c", tst.longestPrefixOf("ca"));
    }
}
