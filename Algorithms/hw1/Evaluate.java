package zdszeto.hw1;

import algs.days.day03.FixedCapacityStack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * COPY this class into your package, which must have USERID has its root.
 *
 * Code from p. 129 of Sedgewick (4ed), slightly modified to use FixedCapacityStack
 * 
 * To run in Eclipse, you will need to enter your input into the Console window directly. 
 * After you press return, nothing appears to happen. This is because you need to "close" the
 * StdIn. 
 *
 * This is done on a PC by pressing Control-z.
 * 
 * On a Macintosh (I am not making this up), to terminate the input, click the mouse anywhere else in Eclipse
 * (typically just back in the source code or in the package explorer), then click BACK in the console window
 * and press control-d (not Command-d).
 * 
 */
public class Evaluate {
	public static void main(String[] args) {
		FixedCapacityStack<String> ops = new FixedCapacityStack<String>(100);
		FixedCapacityStack<Double> vals = new FixedCapacityStack<Double>(100);

		while (!StdIn.isEmpty()) {
			// Read token. push if operator.
			String s = StdIn.readString();
			if (s.equals ("(")) { /* do nothing */ }
			else if (s.equals ("+")) { ops.push(s); }
			else if (s.equals ("-")) { ops.push(s); }
			else if (s.equals ("*")) { ops.push(s); }
			else if (s.equals ("/")) { ops.push(s); }
			else if (s.equals ("sqrt")) { ops.push(s); }
			else if (s.equals ("mod")) { ops.push(s); }
			else if (s.equals ("choose")) { ops.push(s); }
			else if (s.equals (")")) {
				// pop, evaluate, and push result if token is ")".
				String op = ops.pop();
				double v = vals.pop();
				if (op.equals("+")) { v = vals.pop() + v; }
				else if (op.equals("-")) { v = vals.pop() - v; }
				else if (op.equals("*")) { v = vals.pop() * v; }
				else if (op.equals("/")) { v = vals.pop() / v; }
				else if (op.equals("sqrt")) { v = Math.sqrt(v); }
				else if (op.equals("mod")) { v = vals.pop() % v; }
				else if (op.equals("choose")) { 
					double m = vals.pop();
					v = factorial(m) / ((factorial(v) * factorial(m - v))); }
				vals.push(v);
			} 
			
		
			else {
				// Token no operator or paren; must be double value to push
				vals.push(Double.parseDouble(s));
			}
		}
		
		StdOut.print(vals.pop());
		
		// set a breakpoint on this line and you can observe the state
		// of the ops stack and vals stack. You will need this for your 
		// answer.
		
	}
	
	public static double factorial(double n) {
		if (n == 0) {
			return 1;
		} else {
			return (n*factorial(n-1));
		}
	}
}
