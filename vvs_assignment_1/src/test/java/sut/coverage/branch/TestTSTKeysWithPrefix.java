package sut.coverage.branch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import sut.TST;

public class TestTSTKeysWithPrefix {
    
    @Test
    public void testWithNullPrefix() {
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.keysWithPrefix(null);
        });
    }

    @Test
    public void testWithEmptyPrefix() {
        TST<Integer> tst = new TST<Integer>();
        Iterator<String> it = tst.keysWithPrefix("").iterator();
        assertTrue(it.hasNext() == false);
    }

    @Test
    public void testNormalCase() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("shells", 1);
        tst.put("sheet", 2);
        tst.put("shore", 3);
        
        Iterator<String> it = tst.keysWithPrefix("she").iterator();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext() == false);
    }
}
