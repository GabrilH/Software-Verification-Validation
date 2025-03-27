package sut.coverage.branch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import sut.TST;

public class TestTSTKeys {

    @Test
    public void testWithoutKeys() {
        TST<Integer> tst = new TST<Integer>();
        Iterator<String> it = tst.keys().iterator();
        assertTrue(it.hasNext() == false);
    }

    @Test
    public void testWithKeys() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        tst.put("shore", 2);
        
        Iterator<String> it = tst.keys().iterator();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }
}
