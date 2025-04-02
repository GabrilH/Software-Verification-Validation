package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTGet {
    
    @Test
    public void testWithNullKey() {
        // lines 1,2,3
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.get(null);
        });
    }

    @Test
    public void testWithKeyLengthZero() {
        // lines 1,2,4,5
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.put("she", 1);
            tst.get("");
        });
    }

    @Test
    public void testWithKeyAndWithoutPut() {
        // lines 1,2,4,6,7,8
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.get("she"));
    }

    @Test
    public void testWithKeyAndWithPut() {
        // lines 1,2,4,6,7,9
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertEquals(Integer.valueOf(1), tst.get("she"));
    }
}
