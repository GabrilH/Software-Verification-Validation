package sut;

import java.util.*;
import java.util.stream.IntStream;

public class PrimeFactors {

	public static List<Integer> factor(int i) {
		List<Integer> primeFactors = new ArrayList<>();
		int divisor = 1;
		double squareRoot = Math.sqrt(i);
		
		while (i > 1) {
			divisor++;
			while (i % divisor == 0) {
				primeFactors.add(divisor);
				i /= divisor;
			}
			if (divisor > squareRoot)
				divisor = i - 1;
		}
		return primeFactors;
	}
	
	public static boolean isPrime(int number) {
	    return number > 1 && 
	           IntStream.rangeClosed(2, (int) Math.sqrt(number))
	                    .noneMatch(i -> number % i == 0);
	}
}
