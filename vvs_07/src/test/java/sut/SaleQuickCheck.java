package sut;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import sut.sale.Sale;

@RunWith(JUnitQuickcheck.class)
public class SaleQuickCheck {
    
    @Property(trials = 100)
    public void testSale(@From(generators.SaleGenerator.class) Sale sale) {
        
        System.out.println("Sale: " + sale);	   
    }
}
