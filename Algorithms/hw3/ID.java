package zdszeto.hw3;

import java.util.Random;

// these are used INTERNALLY
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.LinearProbingHashST;

/**
 * Helper class for working within the StudentID domain.
 * 
 * LinearProbingHashST<Integer, Boolean> set = valid(64);
 * Bag<Integer> out = misses(set, 16);
 * Bag<Integer> hits = hits(set, 64);
 *
 * In this small example, I start with a set of 64 unique IDs. Using this set, I compute a Bag of IDs that 
 * are known NOT to be in set. I can also compute hits, a Bag of IDs that are known to BE in the set. 
 *
 * DO NOT COPY THIS CLASS. USE AS IS
 */
public class ID implements Comparable<ID> {

	// use this so you *SHOULD* be able to reproduce table
	static Random rnd = new Random(0);

	int value;        // the value of the ID 
	int numCompares;  // the number of times it was compared

	/** Construct a random ID. */
	public ID () {
		value = randomID();
	}

	/** Make an exact copy (and set #compares to 0). */
	public ID(int v) {
		this.value = v;
		this.numCompares = 0;
	}

	/** Make an exact copy (including #compares). */
	public ID(ID d) {
		this.value = d.value;
		this.numCompares = d.numCompares;
	}

	/** Return # of compares. */
	public int numCompares() {
		return numCompares;
	}

	/** Reset # of compares. */
	public void resetNumCompares() {
		numCompares = 0;
	}

	/**
	 * This will not only compare the two IDs properly, it will also increment (twice!) the number
	 * of compares, once for the first value and once for the other value. As such, you will have
	 * twice as many as actually occurred, so in the end be sure to divide the total sum by 2
	 * to get the total number of comparisons.
	 */
	@Override
	public int compareTo(ID other) {
		numCompares++;
		other.numCompares++;

		return value - other.value;
	}

	/** Must have a valid hashCode() to work with. Delegate to int. */
	public int hashCode() {
		return value;
	}

	/** Proper equals method just as important. */
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof ID) {
			ID other = (ID) o;

			numCompares++;
			other.numCompares++;
			return value == other.value;
		}

		return false;
	}

	public String toString() {
		return Integer.toString(value);
	}

	/** Generate a random id. */
	public static int randomID() {
		// from 00000000 to 99999999
		int r = rnd.nextInt(100000000);
		int b = 1 + rnd.nextInt(8);
		return b*100000000 + r;
	}

	/** Helper methods to report keys from the set. */
	public static void report(LinearProbingHashST<ID, Boolean> set) {
		for (ID id : set.keys()) {
			System.out.println(id);
		}
	}

	/** Helper methods to report keys from the bag. */
	public static void report(Bag<ID> bag) {
		for (ID id : bag) {
			System.out.println(id);
		}
	}

	/**
	 * Create a known collection of unique IDs and returns them as a LinearProbingHashST<>().
	 * The boolean value associated with each id is unimportant. 
	 * 
	 * For reasonably-sized values of 'size', this is a reasonable implementation.
	 */
	public static LinearProbingHashST<ID, Boolean> valid(int size) {
		LinearProbingHashST<ID, Boolean> st = new LinearProbingHashST<>(size);
		while (size > 0) {
			ID id = new ID();
			if (!st.contains(id)) {
				size -= 1;
				st.put(id, true);
			}
		}

		return st;
	}
}
