package zdszeto.hw1;

import algs.days.day03.FixedCapacityStack;

/**
 * COPY this class into your USERID.hw1 package and complete the implementation
 * of reverseRepresentation.
 */
public class DigitRepresentation {

	static FixedCapacityStack<Integer> reverseRepresentation(int n, int b) {
		FixedCapacityStack<Integer> stack = new FixedCapacityStack<Integer>(32);
		while (n > 0) {
			int digit = n % b;
			stack.push(digit);
			n /= b;
		}
		return stack;
	}

	public static void main(String args[]) {
		System.out.println("b       21 in base b");
		System.out.println("--------------------");
		int N = 21;
		
		for (int b = 2; b <= 10; b++) {
			// FINISH THIS IMPLEMENTATION
			System.out.print(b + " ");
			FixedCapacityStack<Integer> stack = reverseRepresentation(N, b);
			while (!stack.isEmpty()) {
				System.out.print(stack.pop());
			}
			System.out.println();
		}
	}
}
