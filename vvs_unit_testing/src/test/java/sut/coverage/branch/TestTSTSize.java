package sut.coverage.branch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTSize {
    
    @Test
    public void testSize() {
        // No branches
        TST<Integer> tst = new TST<>();
        assertEquals(0, tst.size());
    }
}
