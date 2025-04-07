package generators;

import java.time.LocalDate;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.java.time.LocalDateGenerator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.sale.Sale;
import sut.sale.SaleItem;

public class SaleGenerator extends Generator<Sale> {

    private static int id = 0; // Static variable to keep track of the ID for each sale
    private int min = 0; // Minimum value for the range of items
    private int max = 10; // Maximum value for the range of items

    
    public SaleGenerator(Class<Sale> type) {
        super(type);
    }

    public void configure(InRange range) {
        min = range.minInt();
        max = range.maxInt();
    }

    @Override
    public Sale generate(SourceOfRandomness random, GenerationStatus status) {
        // Generate a unique ID for the sale
        int currentId = id++;
        LocalDate date = new LocalDateGenerator()
                        .generate(random, status)
                        .withYear(2000 + random.nextInt(0, 23)); // Generate a random date in the year 2000-2023
        
        Sale sale = new Sale(currentId, date);

        // Generate a random number of items (between 1 and 10) to add to the sale
        int numberOfItems = random.nextInt(min, max); // Random number of items between 1 and 10

        // Generate random SaleItems and add them to the sale
        for (int i = 0; i < numberOfItems; i++) {
            int productId = random.nextInt(1, 6); // Assuming product IDs are between 1 and 6
            int nItems = random.nextInt(1, 10); // Random number of items between 1 and 10
            sale.addItem(new SaleItem(i + 1, productId, nItems)); // Create a new SaleItem and add it to the sale
        }

        return sale;
    }    
}
