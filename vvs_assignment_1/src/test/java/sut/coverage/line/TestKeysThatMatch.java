package sut.coverage.line;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.Test;

import sut.TST;

public class TestKeysThatMatch {
    
    @Test
    public void testNormalCase() {
        TST<Integer> tst = new TST<Integer>();
        tst.put("she", 1);
        tst.put("shore", 2);
        tst.put("sheet", 3);

        Iterator<String> it = tst.keysThatMatch("sh...").iterator();
        assert(it.hasNext());
        it.next();
        assert(it.hasNext());
        it.next();
        assert(it.hasNext() == false);
    }
}
