package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import sut.TST;

public class TestTSTPut {
    
    @Test
    public void testWithNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> st = new TST<Integer>();
            st.put(null, 1);
        });
    }

    @Test
    public void testWithoutContaining() {
        TST<Integer> st = new TST<Integer>();
        st.put("she", 1);
        // nao tem assert?
    }
}
