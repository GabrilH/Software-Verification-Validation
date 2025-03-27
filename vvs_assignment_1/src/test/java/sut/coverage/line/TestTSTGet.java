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
    public void testWithLengthZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.put("she", 1);
            tst.get("");
        });
    }

    @Test
    public void testWithoutPut() {
        TST<Integer> tst = new TST<Integer>();
        assertNull(tst.get("she"));
    }

    @Test
    public void testNormalCase() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        assert(tst.get("she") == 1);
    }
}
