package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTSize {
    
    @Test
    public void testSize() {
        // lines 1,2
        TST<Integer> tst = new TST<>();
        assertEquals(0, tst.size());
    }
}
