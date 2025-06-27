package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTLongestPrefixOf {
    
    @Test
    public void testWithNullQuery() {
        // lines 1,2,3
        assertThrows(IllegalArgumentException.class, () -> {
            new TST<Integer>().longestPrefixOf(null);
        });
    }

    @Test
    public void testWithEmptyQuery() {
        // lines 1,2,4,5
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.longestPrefixOf(""));
    }

    @Test
    public void testMiddleMatchThenExit() {
        // lines 1,2,4,6-20
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("c", tst.longestPrefixOf("c"));
    }

}
