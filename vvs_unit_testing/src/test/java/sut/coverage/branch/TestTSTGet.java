package sut.coverage.branch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTGet {
    
    @Test
    public void testWithNullKey() {
        // B1
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.get(null);
        });
    }

    @Test
    public void testWithKeyLengthZero() {
        // !B1, B2
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.put("she", 1);
            tst.get("");
        });
    }

    @Test
    public void testWithKeyAndWithoutPut() {
        // !B1, !B2, B3
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.get("she"));
    }

    @Test
    public void testWithKeyAndWithPut() {
        // !B1, !B2, !B3
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertEquals(Integer.valueOf(1), tst.get("she"));
    }
}
