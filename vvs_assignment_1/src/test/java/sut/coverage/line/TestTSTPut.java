package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTPut {
    
    @Test
    public void testWithNullKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.put(null, 1);
        });
    }

    @Test
    public void testWithoutContaining() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        // nao tem assert? ou fazer get?
    }

    // é necessario?
    // @Test
    // public void testWithContaining() {
    //     TST<Integer> tst = new TST<Integer>();
    //     tst.put("she", 1);
    //     tst.put("she", 2);
    // }
}
