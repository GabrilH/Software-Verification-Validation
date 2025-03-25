package sut.coverage.line;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import sut.TST;

public class TestTSTKeys {

    @Test
    public void testWithoutKeys() {
        TST<Integer> st = new TST<Integer>();
        Iterator<String> it = st.keys().iterator();
        assertTrue(it.hasNext() == false);
    }

    @Test
    public void testWithKeys() {
        TST<Integer> st = new TST<Integer>();
        st.put("she", 1);
        st.put("shore", 2);
        
        Iterator<String> it = st.keys().iterator();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }
}
