package sut.coverage.allcouplingusepaths;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTPut {
    
    
    @Test
    public void testKeyAllCUC() {
        // 1. Test (key: 1 -> i)
        TST<Integer> tst = new TST<>();
        tst.put("ab", 5);
        assertEquals(Integer.valueOf(5) , tst.get("ab"));
    }

    @Test
    public void testValAllCUC() {
        // 2. Test (val: 1 -> vii)
        TST<Integer> tst = new TST<>();
        tst.put("cc", 10);
        tst.put("dd", 5);
        tst.put("ee", 6);
        assertEquals(Integer.valueOf(6), tst.get("ee"));
    }

    @Test
    public void testX1AllCUC() {
        // 3. Test (x: 3 -> ii)
        TST<Integer> tst = new TST<>();
        tst.put("aa", 10);
        assertEquals(Integer.valueOf(10), tst.get("aa"));
    }

    @Test
    public void testX2AllCUC() {
        // 3. Test (x: v -> 11)
        TST<Integer> tst = new TST<>();
        tst.put("cc", 10);
        tst.put("bb", 5);
        tst.put("aa", 5);	
        assertEquals(Integer.valueOf(5), tst.get("aa"));

    }
        
    @Test
    public void testDAllCUC() {
        // 4. Test (d: 9 -> i)
        TST<Integer> tst = new TST<>();
        tst.put("ab", 5);
        tst.put("aa", 10);
        assertEquals(Integer.valueOf(10), tst.get("aa"));
    }
}
