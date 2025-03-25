package sut;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * A queue of limited capacity.
 * 
 * @author Eduardo Marques
 * @version $Id: BoundedQueue.java 332 2016-04-03 15:17:09Z vv $
 */
public interface BoundedQueue<E> {
	/**
	 * Enqueue element at back of the queue. The queue must not be full.
	 * 
	 * @throws IllegalStateException
	 *             if queue is full.
	 */
	/*@
   	 public normal_behavior
       requires !isFull(); 
       ensures size() == \old(size()) + 1;
       ensures capacity() == \old(capacity());
       ensures elementAt(\old(size())) == data;
       ensures (\forall int i; i >= 0 && i < \old(size()) ==> elementAt(i) == \old(elementAt(i)));
   	 also
       public exceptional_behavior
       requires isFull();
       signals_only IllegalStateException;
	 @*/
	void enqueue(E data) throws IllegalStateException;

	/**
	 * Dequeue element from front of the queue. The queue must not be empty.
	 * 
	 * @throws IllegalStateException
	 *             if queue is empty.
	 */
	/*@
     public normal_behavior
       requires !isEmpty(); 
       ensures size() == \old(size()) - 1;
       ensures capacity() == \old(capacity());
       ensures \result == \old(elementAt(0));
       ensures (\forall int i; i >= 0 && i < size() ==> elementAt(i) == \old(elementAt(i+1)));
     also
     public exceptional_behavior
       requires isEmpty();
       signals_only IllegalStateException;
	 */
	E dequeue() throws IllegalStateException;
   
	/**
	 * Get size of the queue. 
	 * @return The number of used queue slots.
	 */
	/*@
    public normal_behavior 
       ensures \result >= 0;
	@*/
	/*@ pure @*/  int size();
   
   	/** 
   	 * Get capacity of the queue.
   	 * @return The maximum number of used queue slots.
   	 */
	/*@
    public normal_behavior 
       ensures \result > 0;
    @*/
	/*@ pure @*/  int capacity();
    
	/**
	 * Check if queue is empty.
	 * @return <code>true</code> iff queue is empty.
	 */
	/*@
     public normal_behavior
       ensures \result == (size() == 0);
	@*/
	/*@ pure @*/  boolean isEmpty();
   
	/**
	 * Check if queue is full.
	 * @return <code>true</code> iff queue is full.
	 */
	/*@
     public normal_behavior
       ensures \result == (size() == capacity());
	@*/
	/*@ pure @*/ boolean isFull();
     
	/**
	 * Get element at specified queue index.
	 * @param index Index (from <code>0</code> to <code>size()-1</code>)
	 * @return Index at specified queue position.
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	/*@
     public normal_behavior 
       requires index >= 0 && index < size();
     also
     public exceptional_behavior
       requires ! (index >= 0 && index < size());
       signals_only IndexOutOfBoundsException;
	@*/
	/*@pure */ E elementAt(int index) throws IndexOutOfBoundsException;
}
