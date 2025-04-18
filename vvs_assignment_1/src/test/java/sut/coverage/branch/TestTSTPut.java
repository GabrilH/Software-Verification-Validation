package sut.coverage.branch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTPut {
    
    @Test
    public void testWithNullKey() {
        // B1
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.put(null, 1);
        });
    }

    @Test
    public void testWithoutContaining() {
        // !B1, B2
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertEquals(Integer.valueOf(1), tst.get("she"));
    }
    
    @Test
    public void testWithContaining() {
        // !B1, !B2
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        tst.put("she", 2);
        assertEquals(Integer.valueOf(2), tst.get("she"));
    }
}
