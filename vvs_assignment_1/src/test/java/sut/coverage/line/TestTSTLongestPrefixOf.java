package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTLongestPrefixOf {
    
    @Test
    public void testWithNullQuery() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TST<Integer>().longestPrefixOf(null);
        });
    }

    @Test
    public void testWithEmptyQuery() {
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.longestPrefixOf(""));
    }

    @Test
    public void testWithoutContaining() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertEquals("", tst.longestPrefixOf("shells"));
    }
    
    @Test
    public void testNormalCase() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("shore", 1);
        tst.put("sheet", 2);
        tst.put("she", 3);
        assertEquals("she", tst.longestPrefixOf("shells"));
    }
}
