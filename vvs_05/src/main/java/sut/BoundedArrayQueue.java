package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * A bounded queue implemented with an array.
 * 
 * @author Eduardo Marques
 * @version $Id: BoundedArrayQueue.java 332 2016-04-03 15:17:09Z vv $
 */
public class BoundedArrayQueue<E> implements BoundedQueue<E> {

	/** Queue buffer. */
	private E buffer[];

	/** Queue size. */
	private int size;

	/** Front position. */
	private int front;

	/** Tail position. */
	private int rear;

	/**
	 * Constructor. The queue is initialized with the specified capacity and in
	 * a non-frozen state.
	 * 
	 * @param n
	 *            Queue capacity.
	 * @throws IllegalArgumentException
	 *             for a non-positive capacity.
	 */
	@SuppressWarnings("unchecked")
	public BoundedArrayQueue(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException("Invalid queue capacity!");
		}
		buffer = (E[]) new Object[n];
		size = 0;
		front = 0;
		rear = n - 1;
	}

	@Override
	public void enqueue(E data) throws IllegalStateException {		
		rear = (rear + 1) % buffer.length;
		buffer[rear] = data;
		size++;
	}

	@Override
	public E dequeue() throws IllegalStateException {
		if (isEmpty()) {
			throw new IllegalStateException("queue is empty");
		}
		E data = buffer[front];
		buffer[front] = null;
		front = (front + 1) % buffer.length;
		size--;
		return data;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int capacity() {
		return buffer.length;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean isFull() {
		return size == buffer.length;
	}

	@Override
	public E elementAt(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		}
		return buffer[(front + index) % buffer.length];
	}
}
