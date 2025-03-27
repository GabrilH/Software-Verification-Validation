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
        TST<Integer> st = new TST<Integer>();
        assertNull(st.longestPrefixOf(""));
    }

    @Test
    public void testWithoutContaining() {
        TST<Integer> st = new TST<Integer>();
        st.put("she", 1);
        assertEquals("", st.longestPrefixOf("shells"));
    }
    
    @Test
    public void testNormalCase() {
        TST<Integer> st = new TST<Integer>();
        st.put("shore", 1);
        st.put("sheet", 2);
        st.put("she", 3);
        assertEquals("she", st.longestPrefixOf("shells"));
    }
}
