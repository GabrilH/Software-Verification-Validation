package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import sut.TST;

public class TestTSTContains {
    
    @Test
    public void testWithNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> st = new TST<Integer>();
            st.contains(null);
        });
    }

    @Test
    public void testNormalCase() {
        TST<Integer> st = new TST<Integer>();
        st.put("she", 1);
        assert(st.contains("she"));
    }
}
