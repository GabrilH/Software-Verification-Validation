package arrays;

// where's the bug? 

public class NumFinder {

	int smallest = Integer.MAX_VALUE;
	int largest = Integer.MIN_VALUE;

	public void find(int[] numbers) {
		for (int n : numbers) {
			if (n < smallest) {
				smallest = n;
			} else if (n > largest) {
				largest = n;
			}
		}
	}

	public static void main (String[] args) {
		NumFinder nf = new NumFinder();
		nf.find(new int[] {4, 25, 7, 9}); // what if is sorted?  

		System.out.println(nf.largest);
		System.out.println(nf.smallest);
	}
}


