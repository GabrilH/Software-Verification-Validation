package sut.coverage.line;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import sut.TST;

public class TestTSTGet {
    
    @Test
    public void testWithNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> st = new TST<Integer>();
            st.get(null);
        });
    }

    @Test
    public void testWithLengthZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> st = new TST<Integer>();
            st.put("she", 1);
            st.get("");
        });
    }

    @Test
    public void testWithoutPut() {
        TST<Integer> st = new TST<Integer>();
        assertNull(st.get("she"));
    }

    @Test
    public void testNormalCase() {
        TST<Integer> st = new TST<Integer>();
        st.put("she", 1);
        assert(st.get("she") == 1);
    }
}
