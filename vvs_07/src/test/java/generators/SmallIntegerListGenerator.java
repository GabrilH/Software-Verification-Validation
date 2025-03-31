package generators;

import java.util.*;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class SmallIntegerListGenerator extends Generator<List<Integer>> {

	public static final int MAX_SIZE = 100;
	public static final int MAX_INT = 1000;

	public SmallIntegerListGenerator(Class<List<Integer>> type) {
		super(type);
	}
	
	@Override
	public List<Integer> generate(SourceOfRandomness src, GenerationStatus status) {
		int size = 1+src.nextInt(MAX_SIZE);
		
		LinkedList<Integer> list = new LinkedList<>(); 
		while(size-- > 0)
			list.add(src.nextInt(2*MAX_INT+1)-MAX_INT); // between [-1000,1000]  
		
		return list;
	}

}
