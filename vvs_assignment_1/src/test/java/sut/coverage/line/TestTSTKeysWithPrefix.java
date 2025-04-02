package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import sut.TST;

public class TestTSTKeysWithPrefix {
    
    @Test
    public void testWithNullPrefix() {
        // lines 1,2,3
        assertThrows(IllegalArgumentException.class, () -> {
            TST<Integer> tst = new TST<Integer>();
            tst.keysWithPrefix(null);
        });
    }

    @Test
    public void testWithEmptyPrefix() {
        // lines 1,2,4,5,6,7
        TST<Integer> tst = new TST<Integer>();
        Iterator<String> it = tst.keysWithPrefix("").iterator();
        assertFalse(it.hasNext(),"has no keys with empty prefix");
    }

    @Test
    public void testWithKeyLength1() {
        // lines 1,2,4,5,6,8,9,10,11
        TST<Integer> tst = new TST<Integer>();
        tst.put("a", 1);
        
        Iterator<String> it = tst.keysWithPrefix("a").iterator();
        assertTrue(it.hasNext(), "has 1 key with prefix 'a'");
        it.next();
        assertFalse(it.hasNext(), "has no more keys");
    }

    @Test
    public void testTwoKeysWithPrefix() {
        // lines 1,2,4,5,6,8,10,11
        TST<Integer> tst = new TST<Integer>();
        tst.put("shells", 1);
        tst.put("sheet", 2);
        tst.put("shore", 3);
        
        Iterator<String> it = tst.keysWithPrefix("she").iterator();
        assertTrue(it.hasNext(), "has 1 key with prefix 'she'");
        it.next();
        assertTrue(it.hasNext(), "has 2 keys with prefix 'she'");
        it.next();
        assertFalse(it.hasNext(), "has no more keys");
    }
}
