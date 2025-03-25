package sut.coverage.line;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTSize {
    
    @Test
    public void testSize() {
        TST<Integer> tst = new TST<>();
        assertEquals(0, tst.size());
    }
}
