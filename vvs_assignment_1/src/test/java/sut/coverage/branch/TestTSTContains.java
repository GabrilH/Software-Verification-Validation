package sut.coverage.branch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTContains {
    
    @Test
    public void testWithNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.contains(null);
        });
    }

    @Test
    public void testWithPut() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertTrue(tst.contains("she"), "contains key 'she' after put");
    }
}
