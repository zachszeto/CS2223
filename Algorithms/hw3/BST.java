package zdszeto.hw3;

import edu.princeton.cs.algs4.Queue;

/**
 * MINIMAL BST that just stores integers as the key and values are eliminated.
 * Note that duplicate values can exist (i.e., this is not a symbol table).
 * 
 * COPY this file into your USERID.hw3 package and complete the final four methods 
 * in this class.
 */
public class BST {
	// root of the tree
	Node root;               
	
	// Use Node class as is without any change.
	class Node {
		int    key;          // SIMPLIFIED to just use int
		Node   left, right;  // left and right subtrees

		public Node(int key) {
			this.key = key;
		}
		
		public String toString() { return "[" + key + "]"; }
	}

	/** Check if BST is empty. */
	public boolean isEmpty() { return root == null; }

	/** Determine if key is contained. */ 
	public boolean contains(int key) { 
		return contains(root, key);
	}
	
	/** Recursive helper method for contains. */
	boolean contains(Node parent, int key) {
		if (parent == null) return false;
		
		if (key < parent.key) {
			return contains(parent.left, key);
		} else if (key > parent.key) {
			return contains(parent.right, key);
		} else {
			return true; // found it!
		}
	}
	
	/** Invoke add on parent, should it exist. */
	public void add(int key) { 
		root = add(root, key);
	}

	/** Recursive helper method for add. */
	Node add(Node parent, int key) {
		if (parent == null) {
			return new Node(key);
		}
		
		if (key <= parent.key) {
			parent.left = add(parent.left, key);
		} else if (key > parent.key) {
			parent.right = add(parent.right, key); 
		} 
		
		return parent;
	}
	
	// AFTER THIS POINT YOU CAN ADD CODE....
	// ----------------------------------------------------------------------------------------------------

	/** Return a new BST that is a structural copy of this current BST. */
	public BST copy() {
	    BST copy = new BST();
	    copy.root = copyNode(root);
	    return copy;
	}

	private Node copyNode(Node node) {
	    if (node == null) {
	        return null;
	    }
	    Node newNode = new Node(node.key);
	    newNode.left = copyNode(node.left);
	    newNode.right = copyNode(node.right);
	    return newNode;
	}
	
	/** Return the count of nodes in the BST whose key is even. */
	public int countIfEven() {
		// do some work here, but you also need a helper method....
		return countIfEven(root);
	}
	
	int countIfEven(Node aNode) {
		//recursive case
		if(aNode == null) {
			return 0;
		}
		
		int numEven = countIfEven(aNode.left) + countIfEven(aNode.right);
		
		if(aNode.key % 2 == 0) {
			numEven++;
		}
		return numEven;
	}
	/** Return a Queue<Integer> containing the depths for all nodes in the BST. */
	public Queue<Integer> nodeDepths() {
		Queue<Integer> q = new Queue<>();
		
		// do something here, but you also need a helper method....
		nodeDepth(root, 0, q);
		return q;
	}
	
	void nodeDepth(Node aNode, int depth, Queue<Integer> q) {
		if(aNode == null) {
			return;
		}
		
		q.enqueue(depth);
		
		nodeDepth(aNode.left, depth + 1, q);
	    nodeDepth(aNode.right, depth + 1, q);
	}
	
	/** Remove all leaf nodes that are odd. */
	public void removeLeafIfOdd() {
		// do some work here, but you also need a helper method....
		removeLeafIfOdd(root);
	}
	
	private Node removeLeafIfOdd(Node aNode) {
		if(aNode == null) {
			return null;
		}
		
		aNode.left = removeLeafIfOdd(aNode.left);
		aNode.right = removeLeafIfOdd(aNode.right);
		
		 if (aNode.left == null && aNode.right == null && aNode.key % 2 != 0) {
		        return null; // remove odd leaf node
		    }

		    return aNode;
	}
}
