package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTGet {
    
    @Test
    public void testWithNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.get(null);
        });
    }

    @Test
    public void testWithKeyLengthZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.put("she", 1);
            tst.get("");
        });
    }

    @Test
    public void testWithKeyAndWithoutPut() {
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.get("she"));
    }

    @Test
    public void testWithKeyAndWithPut() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assertEquals(Integer.valueOf(1), tst.get("she"));
    }
}
