package zdszeto.hw1;

import java.util.Arrays;

/**
 * COPY THIS CLASS into your USERID.hw1 and modify it by completing the implementation of the methods
 * that currently throw RuntimeExceptions.
 * 
 * A Staque is a unique structure that uses a single char array to provide both Stack and Queue behaviors.
 * 
 * You will need to add class attributes and some initialization code in the constructor.
 */
public class Staque {

	/** This contains the storage you will manage. */
	char storage[];
	final int separator;
	
	// YOU MAY ADD MORE CLASS ATTRIBUTES HERE
	int stackN; //how many elements are in the stack
	int top;    //index position of 'length char' of topmost element in stack
				//NOTE: when top == separator STACK MUST BE EMPTY
	
	
	
	int queueN; //how many elements are in the queue
	int head;   //first element in the queue
	int tail;   //last element in the queue

	/**
	 * Create the internal storage array to contain 2*size + 1 bytes.
	 * 
	 * @param size The number of bytes to use for the stack (or the queue).
	 */
	public Staque(int size) {
		if (size < 5 || size > 65536) {
			throw new RuntimeException("Invalid Size: " + size);
		}

		storage = new char[2 * size + 1];
		separator = size; // this is the midpoint.

		// YOU MAY ADD MORE HERE
		stackN = 0;
		queueN = 0;
		head = separator + 1;
		tail = head;

	}

	/** Returns whether the Staque has at least one element in its stack. */
	public boolean canPop() {
		return stackN != 0;
	}

	/** Returns whether the Staque has at least one element in its queue. */
	public boolean canDequeue() {
		return queueN != 0;
	}

	/**
	 * Returns whether there is enough memory to push the char[] array to its stack.
	 */
	public boolean canPush(char[] bytes) {
		return stackN + bytes.length + 1 <= separator;
	}

	/**
	 * Returns whether there is enough memory to enqueue the char[] array to its
	 * queue.
	 */
	public boolean canEnqueue(char[] bytes) {
		return bytes.length + queueN + 1 <= separator;
	}

	/**
	 * Push char[] array onto the stack.
	 * 
	 * @param bytes -- an array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if not enough room to store.
	 */
	void push(char bytes[]) {
		if (!canPush(bytes))
			throw new RuntimeException("Full Stack");

		for (int i = bytes.length - 1; i >= 0; i--) {
			storage[separator - 1 - stackN++] = bytes[i];
		}
		storage[separator - 1 - stackN++] = (char) bytes.length;
	}

	/** Helper method to push a single character. LEAVE AS IS. */
	void push(char ch) {
		push(new char[] { ch });
	}

	/**
	 * Pop the char[] array that is at the top of the stack and return it.
	 * 
	 * @return -- an array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if stack was empty.
	 */
	char[] pop() {
		if (!canPop())
			throw new RuntimeException("Empty Stack");

		int length = storage[separator - stackN];
		char[] result = new char[length];
		storage[separator - stackN--] = 0;
		
		for (int i = 0; i < length; i++) {
			result[i] = storage[separator - stackN];
			storage[separator - stackN--] = 0;
		}
		return result;
	}

	/**
	 * Enqueue char[] array to tail of the queue.
	 * 
	 * @param bytes -- an array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if not enough room to store.
	 */
	void enqueue(char bytes[]) {
		if (!canEnqueue(bytes))
			throw new RuntimeException("Full Queue");

		storage[tail++] = (char) bytes.length;
		
		for (int i = 0; i < bytes.length; i++) {
		    storage[tail++] = bytes[i];
		}
		
		queueN = tail - head;
	}

	/** Helper method to enqueue a single character. LEAVE AS IS. */
	void enqueue(char ch) {
		enqueue(new char[] { ch });
	}

	/**
	 * Dequeue char[] array from the head of the queue.
	 * 
	 * @return -- array of bytes at least 1 in length but no more than 255.
	 * @exception RuntimeException if queue is empty.
	 */
	char[] dequeue() {
		if (!canDequeue())throw new RuntimeException ("Empty Queue");
		
		int length = storage[head];
		char[] result = new char[length];
		storage[head++] = 0;
		
		for(int i = 0; i < length; i++) {
			result[i] = storage[head];
			storage[head++] = 0;
		}
		return result;
	}
	
	// NOTE: WHEN I ORIGINALLY RELEASED THE HOMEWORK, THE FOLLOWING THREE METHODS WERE MISSING.
	// SO COPY output(), same(), and main() INTO YOUR Staque.java FILE (TOWARDS THE END) AND THEN
	// YOU WILL HAVE THESE METHODS TO USE AS WELL. 
	
	/** Helper method to output storage. USE AS IS. */
	static void output(int frame, Staque stq, String note) {
		System.out.println(String.format("%5d:%s\t<%s>", frame, Arrays.toString(stq.storage), note));
	}
	
	/** Check if byte arrays are identical. USE AS IS. */
	static boolean same(char[] one, char[] two) {
		if (one.length != two.length) { return false; }
		for (int i = 0; i < one.length; i++) {
			if (one[i] != two[i]) { return false; }
		}
		return true;
	}
	
	/** Use as is to validate your implementation matches the output from the homework spec. */
	public static void main(String[] args) {
		System.out.println("Note that in the output, the encoded lengths may appear as a strange");
		System.out.println("character (like " + ((char) 7) + ") because of how characters appear.");
		System.out.println();
		
		Staque stq = new Staque(7);
		output(0, stq, "empty");
		
		char[] c1 = new char[] { 'M' };                    // these are char bytes[] to push/enqueue
		char[] c2 = new char[] { 'x', 'y' };
		char[] c3 = new char[] { 'a', 'b', 'c' };
		char[] c4 = new char[] { 'w', 'x', 'y', 'z' };
		
		stq.push(c3);
		output(1, stq, "pushed 3");
		
		stq.enqueue(c4);
		output(2, stq, "enqueued 4");
		
		stq.enqueue(c1);
		output(3, stq, "enqueued 1");
		
		stq.push(c2);
		output(4, stq, "pushed 2");
		
		char[] p1 = stq.pop();
		if (!same(p1,c2)) { throw new RuntimeException("Bytes pop'd didn't match!"); }
		
		char[] d1 = stq.dequeue();
		if (!same(d1,c4)) { throw new RuntimeException("Bytes pop'd didn't match!"); }
		
		output(5, stq, "pop'd and dequed");
	}
}