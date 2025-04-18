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
        assertFalse(it.hasNext(),"has no keys");
    }
}
