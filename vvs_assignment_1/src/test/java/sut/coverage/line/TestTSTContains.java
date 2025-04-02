package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTContains {
    
    @Test
    public void testWithNullKey() {
        // lines 1,2,3
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.contains(null);
        });
    }

    @Test
    public void testWithPut() {
        //lines 1,2,4
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertTrue(tst.contains("she"), "contains key 'she' after put");
    }
}
