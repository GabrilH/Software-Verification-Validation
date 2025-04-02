package sut.coverage.line;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTKeysThatMatch {
    
    @Test
    public void testEmptyKeys() {
        // lines 1,2,3,4
        TST<Integer> tst = new TST<Integer>();
        Iterator<String> it = tst.keysThatMatch("sh...").iterator();
        assertFalse(it.hasNext(), "has no keys");
    }
}
