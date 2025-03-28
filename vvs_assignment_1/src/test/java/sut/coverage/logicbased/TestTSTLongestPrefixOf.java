package sut.coverage.logicbased;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTLongestPrefixOf {
    
    @Test
    public void testWithNullQuery() {
        // P1, C1
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<>();
            tst.longestPrefixOf(null);
        });
    }

    @Test
    public void testWithEmptyQuery() {
        // !P1, !C1, P2, C2
        TST<Integer> tst = new TST<>();
        tst.longestPrefixOf("");
        assertNull(tst.longestPrefixOf(""));
    }

    @Test 
    public void testWithoutPuts() {
        // !P1, !C1, !P2, !C2
        // exit: !P3, !C3, C4
        TST<Integer> tst = new TST<>();
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testLeftThenExit() {
        // !P1, !C1, !P2, !C2 
        // 1st iteration: P3, C3, C4, P4, C5
        // exit: !P3, !C3, C4
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("", tst.longestPrefixOf("a"));
    }

    @Test
    public void testRightThenExit() {
        // !P1, !C1, !P2, !C2 
        // 1st iteration: P3, C3, C4, !P4, !C5, P5, C6
        // exit: !P3, !C3, C4
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("", tst.longestPrefixOf("d"));
    }

    @Test
    public void testMiddleMatchThenExit() {
        // !P1, !C1, !P2, !C2
        // 1st iteration: P3, C3, C4, !P4, !C5, !P5, !C6, P6, C7
        // exit: !P3, !C3, !C4
        TST<Integer> tst = new TST<>();
        tst.put("c", 1);
        assertEquals("c", tst.longestPrefixOf("c"));
    }

    @Test
    public void testMiddleNoMatchThenExit() {
        // !P1, !C1, !P2, !C2
        // 1st iteration: P3, C3, C4, !P4, !C5, !P5, !C6, !P6, !C7
        // exit: !P3, C3, !C4
        TST<Integer> tst = new TST<>();
        tst.put("casa", 1);
        assertEquals("", tst.longestPrefixOf("c"));
    }
}
