package sut.coverage.allcouplingusepaths;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import sut.TST;

public class TestTSTPut {
    
    
    @Test
    public void testKeyAllCUP() {
        // Test (key: 1 -> i)
        TST<Integer> tst = new TST<>();
        tst.put("ab", 5);
        assertEquals(Integer.valueOf(5) , tst.get("ab"));
    }

    @Test
    public void testValAllCUP() {
        // Test (val: 1 -> vii)
        TST<Integer> tst = new TST<>();
        tst.put("cc", 10);
        tst.put("dd", 5);
        tst.put("ee", 6);
        assertEquals(Integer.valueOf(6), tst.get("ee"));
    }

    @Test
    public void testX1AllCUP() {
        // Test (x: 3 -> ii)
        TST<Integer> tst = new TST<>();
        tst.put("aa", 10);
        assertEquals(Integer.valueOf(10), tst.get("aa"));
    }

    @Test
    public void testX2AllCUP() {
        // Test (x: 1 -> ii)
        TST<Integer> tst = new TST<>();
        tst.put("aa", 10);
        assertEquals(Integer.valueOf(10), tst.get("aa"));
    }

    @Test
    public void testD1AllCUP() {
        // Test (d: 1 -> i)
        TST<Integer> tst = new TST<>();
        tst.put("aa", 10);
        tst.put("ab", 5);
        assertEquals(Integer.valueOf(5), tst.get("ab"));
    }
        
    @Test
    public void testD2AllCUP() {
        // Test (d: 9 -> i)
        TST<Integer> tst = new TST<>();
        tst.put("ab", 5);
        tst.put("aa", 10);
        assertEquals(Integer.valueOf(10), tst.get("aa"));
    }

    @Test
    public void testX_1AllCUP() {
        // Test (x': v -> 11)
        TST<Integer> tst = new TST<>();
        tst.put("cc", 10);
        tst.put("bb", 6);
        tst.put("aa", 5);	
        assertEquals(Integer.valueOf(5), tst.get("aa"));
    }

    @Test
    public void testX_2AllCUP() {
        // Test (x': vii -> 11)
        TST<Integer> tst = new TST<>();
        tst.put("cc", 10);
        tst.put("aa", 6);
        tst.put("bb", 5);
        assertEquals(Integer.valueOf(5), tst.get("bb"));
    }

    @Test
    public void testX_3AllCUP() {
        // Test (x': ix -> 11)
        TST<Integer> tst = new TST<>();
        tst.put("cc", 10);
        tst.put("ccb", 6);
        assertEquals(Integer.valueOf(6), tst.get("cb"));
    }

    @Test
    public void testX_4AllCUP() {
        // Test (x': x -> 11)
        TST<Integer> tst = new TST<>();
        tst.put("cc", 10);
    }
}
