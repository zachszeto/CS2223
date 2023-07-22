package zdszeto.hw1;

import algs.days.day03.FixedCapacityStack;

/**
 * COPY this file into your USERID.hw1 package and complete its implementation.
 * 
 * You do not have to modify the main() method.
 */
public class StackConverter {

	public static int[] toArray(FixedCapacityStack<Integer> stack) {
		//Double Check how we find the size of the stack
		int size = 0; 
		FixedCapacityStack<Integer> sizeStack = new FixedCapacityStack<>(256);
		
		while(!stack.isEmpty()) {
			sizeStack.push(stack.pop());
			size++;
		}
		
		int[] arr = new int[size];

	        // Pop elements
	        for (int i = 0; i < size && !sizeStack.isEmpty(); i++) {
	            arr[i] = sizeStack.pop();
	        }

	        // Push elements
	        for (int i = size - 1; i >= 0; i--) {
	        	stack.push(arr[i]);
	        }
	        return arr;
	}
	
	public static void main(String[] args) {
		FixedCapacityStack<Integer> stack = new FixedCapacityStack<>(256);
		stack.push(926);
		stack.push(415);
		stack.push(31);
		
		int vals[] = StackConverter.toArray(stack);
		System.out.println("The following output must be [926, 415, 31] :" + java.util.Arrays.toString(vals));

		// note that you can still pop values
		System.out.println("shoud be 31:" + stack.pop());
		System.out.println("shoud be 415:" + stack.pop());
		System.out.println("shoud be 926:" + stack.pop());
	}
}
