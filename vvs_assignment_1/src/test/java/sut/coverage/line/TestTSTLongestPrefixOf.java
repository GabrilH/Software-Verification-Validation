package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTLongestPrefixOf {
    
    @Test
    public void testWithNullQuery() {
        // linhas 136, 137
        assertThrows(IllegalArgumentException.class, () -> {
            new TST<Integer>().longestPrefixOf(null);
        });
    }

    @Test
    public void testWithEmptyQuery() {
        // linhas 136, 138, 139
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.longestPrefixOf(""));
    }

    //para fazer line coverage
    //basta fazer este teste que passa pelo middle
    @Test
    public void testMiddleMatchThenExit() {
        // linhas 136, 138, 140, 141, 142, 143, 144, 
        // 145, 146, 148, 149, 150, 151, 154
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("c", tst.longestPrefixOf("c"));
    }
}
