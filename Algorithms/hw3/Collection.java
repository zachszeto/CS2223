package zdszeto.hw3;

import java.util.Random;

// these are used INTERNALLY
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.LinearProbingHashST;

/**
 * Maintains a collection of N random IDs.
 * 
 * You can request 
 * 
 * Collection coll = new Collection(64);       // a new collection of 64 random IDs
 * Bag<ID> out = coll.misses(16);              // these IDs are misses
 * Bag<ID> hits = hits(set, 64);               // these IDs are hits
 *
 * In this small example, I start with a set of 64 unique IDs. Using this set, I compute a Bag of IDs that 
 * are known NOT to be in set. I can also compute hits, a Bag of IDs that are known to BE in the set. 
 *
 * You will use this information when evaluating the use of LinearProbingHashST, SeparateChainingHashST,
 * Binary Trees, and AVL Trees. 
 * 
 * DO NOT COPY THIS CLASS INTO YOUR USERID.hw3
 */
public class Collection {

	// use this so you *SHOULD* be able to reproduce table
	static Random rnd = new Random(0);

	/** A set of unique IDs. */
	LinearProbingHashST<ID, Boolean> set;
	
	/**
	 * Create a known collection of unique IDs and make it possible later to
	 * produce (upon request) a collection of N IDs that are contained within this
	 * collection as well as a collection N IDs that are not contained within this collection. 
	 * 
	 * For reasonably-sized values of 'size', this is a reasonable implementation.
	 */
	public Collection(int number) {
		set = new LinearProbingHashST<>(number);
		while (number > 0) {
			ID id = new ID();
			if (!set.contains(id)) {
				number -= 1;
				set.put(id, true);
			}
		}
	}
	
	/**
	 * Reset count of all compares in the keys for this collection. 
	 * 
	 * Useful when trying to compare based on the number of comparisons when put/get.
	 */
	public void resetNumCompares() {
		for (ID id : set.keys()) {
			id.resetNumCompares();
		}
	}

	/** 
	 * Return Iterable with the ID objects in the set.
	 */
	public Iterable<ID> ids() {
		return set.keys();
	}

	/** Helper methods to report keys from the set. */
	public static void report(LinearProbingHashST<Collection, Boolean> set) {
		for (Collection id : set.keys()) {
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
	 * Return a Bag of IDs that are KNOWN TO NOT BE INCLUDED in this collection. 
	 * This is a reasonable implementation if parameter size is reasonably small.
	 * 
	 * There is a small chance the same ID might appear in the returned Bag, but this 
	 * isn't an important consideration.
	 */
	public Bag<ID> misses(int size) {
		Bag<ID> bag = new Bag<ID>();
		while (size > 0) {
			ID id = new ID();
			if (!set.contains(id)) {
				size -= 1;
				bag.add(id);
			}
		}

		return bag;
	}

	/**
	 * Return a Bag of IDs that are KNOWN TO BE INCLUDED.  Size must be <= valid.size()
	 * 
	 * The same ID might appear in the returned Bag, but this isn't an important consideration.
	 */
	public Bag<ID> hits(int size) {
		Bag<ID> bag = new Bag<ID>();

		// iterate through all N keys in valid. If you want just M of them, 
		// then choose each one with probability of M/N. If, by the end, this
		// has not been achieved, then simply go back to beginning and grab rest that are needed.
		double prob = (1.0*size)/set.size();
		for (ID id : set.keys()) {
			if (rnd.nextDouble() < prob) {
				bag.add(id);
			}

			if (bag.size() == size) { break; }   // break once done.
		}

		// if you get here and STILL need some more, don't mess around and just grab them
		if (bag.size() < size) { 
			for (ID id : set.keys()) {
				bag.add(id);

				if (bag.size() == size) { break; }   // break once done.
			}
		}

		return bag;
	}

	public static void main(String[] args) {
		Collection coll = new Collection(64);

		System.out.println("Not In");
		Bag<ID> out = coll.misses(16);         // give me 16 IDs not in my collection
		report(out);
		
		System.out.println("Hits");
		out = coll.hits(20);
		report(out);
	}

}
