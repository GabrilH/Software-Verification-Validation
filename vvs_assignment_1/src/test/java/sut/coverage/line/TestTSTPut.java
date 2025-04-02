package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTPut {
    
    @Test
    public void testWithNullKey() {
        // lines 1,2,3
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.put(null, 1);
        });
    }

    @Test
    public void testWithoutContaining() {
        // lines 1,2,4,5,6
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertTrue(tst.contains("she"));
    }

    // é necessario?
    // @Test
    // public void testWithContaining() {
    //     TST<Integer> tst = new TST<Integer>();
    //     tst.put("she", 1);
    //     tst.put("she", 2);
    // }
}
