package zdszeto.hw2;

/**
 * COPY this class into your USERID.hw2
 * 
 * Responsible for allocating memory from within a designated block of chars.
 * 
 * Can reallocate memory (and copy existing chars to smaller or larger destination).
 * 
 * Can defragment available by combining neighboring regions together. ONLY possible if the blocks
 * of allocated memory appear in sorted order within the available list (worth five points on this question).
 * 
 * Can alert user that excess memory remains unfree'd
 * 
 * Address ZERO is always invalid.
 */
public class Memory {
	
	/** USE THIS StorageNode CLASS AS IS. */
	class StorageNode {
		int           addr;        // address into storage[] array
		int           numChars;    // how many chars are allocated
		
		StorageNode   next;        // the next StorageNode in linked list.
		
		/** Allocates a new Storage Node. */
		public StorageNode (int addr, int numChars) {
			this.addr = addr;
			this.numChars = numChars;
			this.next = null;
		}
		
		/** Allocates a new Storage Node and makes it head of the linked list, next. */ 
		public StorageNode (int addr, int numChars, StorageNode next) {
			this.addr = addr;
			this.numChars = numChars;
			this.next = next;
		}
		
		/** Helper toString() method. */
		public String toString() {
			return "[" + addr + " @ " + numChars + " = " + (addr+numChars-1) + "]";
		}
	}
	
	/** Storage of char[] that this class manages. */
	final char[] storage;
	
	// Linked list of available nodes
	private StorageNode available;
	
	//Linked list of allocated nodes
	private StorageNode allocated;
	
	int charsAllocated = 0;
	int charsAvailable = 0;
	
	public Memory(int N) {
		// memory address 0 is not valid, so make array N+1 in size and never use address 0.
		storage = new char[N+1];

		// DO MORE THINGS HERE
		available = new StorageNode(1,N);
		allocated = null;
		charsAvailable = N;
	}
	
	/** 
	 * Make a useful debug() method.
	 * 
	 * You should print information about the AVAILABLE memory chunks and the ALLOCATED memory chunks.
	 * 
	 * This will prove to be quite useful during debugging.
	 */
	public String toString() {
	    StringBuilder availAndAlloc = new StringBuilder();
	    availAndAlloc.append("AVAILABLE memory:\n");
	    for (StorageNode node = available; node != null; node = node.next) {
	    	availAndAlloc.append(node.toString() + " ");
	    }
	    availAndAlloc.append("\nALLOCATED memory:\n");
	    for (StorageNode node = allocated; node != null; node = node.next) {
	    	availAndAlloc.append(node.toString() + " ");
	    }
	    return availAndAlloc.toString();
	}
	
	/** 
	 * Report on # of StorageNode in allocated list (used for testing/debugging)
	 */
	public int blocksAllocated() {
		int blocksAlloc = 0;

		for (StorageNode allocNode = allocated; allocNode.next != null; allocNode = allocNode.next) {
			blocksAlloc++;
		}
		return blocksAlloc;
	}

	/**
	 * Report on # of StorageNode in available list (used for testing/debugging)
	 */
	public int blocksAvailable() {
		int blocksAvail = 0;

		for (StorageNode availNode = available; availNode.next != null; availNode = availNode.next) {
			blocksAvail++;
		}
		return blocksAvail;
	}

	/**
	 * Report on memory that was allocated but not free'd. Performance must be O(1).
	 */
	public int charsAllocated() {
		return charsAllocated;
	}
	
	/** 
	 * Report on available memory remaining to be allocated.
	 * Performance must be O(1).
	 */
	public int charsAvailable() {
		return charsAvailable;
	}
	
	/** 
	 * Return the char at the given address.
	 * Unprotected: can return char for any address of memory.
	  */
	public char getChar(int addr) {
		validateAllocated(addr);
		
		return storage[addr];
	}
	
	/** 
	 * Get char[] at the given address for given number of chars, if valid.
	 * Unprotected: can return char[] for any address of memory.
	 * Awkward that you do not have ability to know IN ADVANCE whether this many
	 * characters are stored there, but a runtime exception will tell you.
	 */
	public char[] getChars(int addr, int numChars) {
		validateAllocated(addr, numChars);
		
		 // Create a new array to hold the the Chars
	    char[] result = new char[numChars];
	    
	    //Copies Chars starting at addr to result
		for (int i = 0; i < numChars; i++) {
			result[i] = storage[addr+i];
		}
		return result;
	}
	
	/** 
	 * Determines if the current address is valid allocation. Throws Runtime Exception if not. 
	 * Performance proportional to number of allocated blocks.
	 */
	void validateAllocated(int addr) {
		for (StorageNode allocNode = allocated; allocNode != null; allocNode = allocNode.next) {
		    if (addr >= allocNode.addr && addr <= allocNode.addr + allocNode.numChars) {
		        return;
		    }
		}
		throw new RuntimeException("Not a Valid Allocation");
	}

	/**
	 * Determines if the current address is valid allocation for the given number of
	 * characters.
	 */
	void validateAllocated(int addr, int numChar) {
		for(int i = addr; i < addr+numChar; i++) {
			validateAllocated(i);
		}
	}

	/**
	 * Internally allocates given memory if possible and return its starting
	 * address.
	 * 
	 * Must ZERO out all memory that is allocated.
	 * 
	 * @param numChars number of consecutive char to be allocated
	 */
	public int alloc(int numChars) throws RuntimeException {
		// Will be updated later
		int addr = 1;

		charsAllocated += numChars;
		charsAvailable -= numChars;

		StorageNode availNode = available;
		StorageNode allocNode = allocated;
		StorageNode transferNode = null;

		if (allocNode == null) {
			allocated = new StorageNode(addr, numChars);
			available.addr += numChars;
			available.numChars -= numChars;
		} else {
			for (StorageNode curr = available; curr != null; curr = curr.next) {
				if (curr.numChars >= numChars) {
					break;
				}
				transferNode = availNode;
				availNode = availNode.next;
			}

			while (allocNode.next != null) {
				allocNode = allocNode.next;
			}

			// Update the addr
			addr = availNode.addr;

			//Update the available Linked List
			allocNode.next = new StorageNode(addr, numChars);
			availNode.addr += numChars;
			availNode.numChars -= numChars;
			
			if (availNode.numChars <= 0) {
				if (transferNode != null) {
					transferNode.next = availNode.next;
				} else {
					available = availNode;
				}
			}
		}

		// Zeros out the allocated space in storage
		for (int i = addr; i < addr + numChars; i++) {
			storage[i] = 0;
		}

		// updates available to reflect newly allocated stuff
		if (available.numChars <= 0) {
			available = null;
		}
		return addr;
	}
	
	/** Reallocate to larger space and copy existing chars there, while free'ing the old memory. */
	public int realloc(int addr, int newSize) {
		StorageNode allocNode = allocated;
		for (; allocNode.next != null && allocNode.addr != addr; ) {
		    allocNode = allocNode.next;
		}
		
		char[] transfer = this.getChars(addr, addr+Math.min(newSize, allocNode.numChars));
		this.free(addr);
		int new_a = alloc(newSize);
		this.setChars(new_a, transfer);
		
		return new_a;
		
	}
	
	/** 
	 * Internally allocates sufficient memory in which to copy the given char[]
	 * array and return the starting address of memory.
	 * @param chars - the characters to be copied into the new memory
	 * @return address of memory that was allocated
	 */
	public int copyAlloc(char[] chars) {
		int spaceNeeded = chars.length;
		int addr = alloc(spaceNeeded);
		
		 // Copy the chars into the new memory
	    for (int i = 0; i < spaceNeeded; i++) {
	        storage[addr + i] = chars[i];
	    }
	    
	    return addr;
	}
	
	/** 
	 * Free the memory currently associated with address and add back to 
	 * available list.
	 * 
	 * if addr is not within a range of allocated memory, then FALSE is returned.
	 */
	public boolean free(int addr) {
		boolean inRange = false;
		StorageNode prev = null;
		StorageNode curr = allocated;

		
		
		try {
			validateAllocated(addr);
		}catch (RuntimeException error) {
			return false;
		}
		
		// Looks through allocated to see if the given addr is within the range of
		// allocated memory. If it is then inRange is set to true otherwise we check the
		// next nodes
		while (curr != null) {
			if (curr.addr == addr) {
				inRange = true;
				break;
			}
			prev = curr;
			curr = curr.next;
		}
		
		charsAllocated -= curr.numChars;
		charsAvailable += curr.numChars;
	
		// If addr isnt within the range of allocated memory return false
		if (!inRange)
			return false;

		//// Remove the StorageNode from the allocated linked list
		if (prev == null) {
			allocated = curr.next;
		} else {
			prev.next = curr.next;
		}

		 // Insert the StorageNode into the available linked list in ascending order of address
	    StorageNode prevAvail = null;
	    StorageNode currAvail = available;

	    //Moves through the available until the current node's address is more than the node we want to insert. Then we know that node should be insert right before the current node
	    while (currAvail != null && currAvail.addr < addr) {
	        prevAvail = currAvail;
	        currAvail = currAvail.next;
	    }

	    //If the spot before the current node is empty (aka null) then we can just insert the node
	    if (prevAvail == null) {
	        available = new StorageNode(addr, curr.numChars);
	        available.next = currAvail;
	    //If there is something there we just add it to the next spot
	    } else {
	        prevAvail.next = new StorageNode(addr, curr.numChars);
	        prevAvail.next.next = currAvail;
	    }

		// Merge adjacent nodes in the available list
		StorageNode availableNode = available;
		while (availableNode != null && availableNode.next != null) {
			if (availableNode.addr + availableNode.numChars == availableNode.next.addr) {
				// merge the two nodes
				availableNode.numChars += availableNode.next.numChars;
				availableNode.next = availableNode.next.next;
			} else {
				availableNode = availableNode.next;
			}
		}

		// Merge adjacent nodes in the allocated list
		StorageNode allocatedNode = allocated;
		while (allocatedNode != null && allocatedNode.next != null) {
			if (allocatedNode.addr + allocatedNode.numChars == allocatedNode.next.addr) {
				// merge the two nodes
				allocatedNode.numChars += allocatedNode.next.numChars;
				allocatedNode.next = allocatedNode.next.next;
			} else {
				allocatedNode = allocatedNode.next;
			}
		}
		return true;
	}
	
	/** 
	 * Set char, but only if it is properly contained as an allocated segment of memory. 
	 * Performance proportional to number of allocated blocks.
	 * @exception if the addr is not within address of memory that was formerly allocated.
	 */
	public void setChar(int addr, char value) throws RuntimeException {
		validateAllocated(addr);
		storage[addr] = value;
		
	}
	
	/** 
	 * Set consecutive char values starting with addr to contain the char values passed in, but only if 
	 * the full range is properly contained as an allocated segment of memory. 
	 * Performance proportional to number of allocated blocks.
	 * @exception if the addr is not within address of memory that was formerly allocated.
	 */
	public void setChars(int addr, char[] values) throws RuntimeException {
		validateAllocated(addr, values.length);
		for(int i = addr; i < addr+values.length; i++) {
			setChar(i, values[i-addr]);
		}
	}
	
	// ================================================================================================================
	// ======================================== EVERYTHING ELSE BELOW REMAINS AS IS ===================================
	// ================================================================================================================
	
	/** 
	 * Sets int, but only if it is properly contained as an allocated segment of memory. 
	 * Performance proportional to number of allocated blocks.
	 * USE AS IS.
	 * @exception if the addr is not within address of memory that was formerly allocated with sufficient space
	 */
	public void setInt(int addr, int value) throws RuntimeException {
		validateAllocated(addr, 4);
		setChar(addr,   (char)((value & 0xff000000) >> 24));
		setChar(addr+1, (char)((value & 0xff0000) >> 16));
		setChar(addr+2, (char)((value & 0xff00) >> 8));
		setChar(addr+3, (char)(value & 0xff));
	}
	
	/** 
	 * Return the 4-chars at the given address as an encoded integer.
	 * Performance proportional to number of allocated blocks.
	 * USE AS IS.
	 */
	public int getInt(int addr) {
		validateAllocated(addr, 4);
		return (getChar(addr) << 24) + (getChar(addr+1) << 16) + (getChar(addr+2) << 8) + getChar(addr+3);
	}
	
	/**
	 * Allocate new memory large enough to contain room for an array of numbers and copy
	 * numbers[] into the memory, returning the address of the first char.
	 * 
	 * USE AS IS.
	 * 
	 * Because int values are 32-bits, this means that the total number of char allocated
	 * will be 4*numbers.length
	 * 
	 * @param numbers   The int[] values to be copied into the newly allocated storage.
	 */
	public int copyAlloc(int[] numbers) {
		int addr = alloc(4*numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			setInt(addr+4*i, numbers[i]);
		}
		
		return addr;
	}
	
	/**
	 * Return the string which is constructed from the sequence of char from addr
	 * for len characters.
	 * USE AS IS.
	 */
	public String createString(int addr, int len) {
		return String.valueOf(storage, addr, addr+len-1);
	}
	
	/**
	 * Return those allocated nodes whose allocated char[] matches the pattern of char[] passed in.
	 * ONLY COMPLETE FOR BONUS
	 * @param pattern
	 */
	public java.util.Iterator<StorageNode> match(char[] pattern) {
		throw new RuntimeException("BONUS IMPLEMENTATION");
	}
	
	/** This sample program recreates the linked list example from Q2 on the homework. */
	public static void main(String[] args) {
		Memory mem = new Memory(32);
		
		             mem.alloc(2);   // don't use address in this small example...
		int first  = mem.alloc(8);
		             mem.alloc(3);
		int third  = mem.alloc(8);
		             mem.alloc(3);
		int second = mem.alloc(8);
		
		mem.setInt(first, 178);   // first node stores 178
		mem.setInt(second, 992);  // second node stores 992
		mem.setInt(third, 194);   // third node stores 194
		
		mem.setInt(first+4, second);    // have next pointer for first to point to second
		mem.setInt(second+4, third);    // have next pointer for second to point to third
		mem.setInt(third+4, 0);         // have next pointer for third to be 0 (END OF LIST)
		
		// How to loop through list?
		System.out.println("Numbers should print in order from 178 -> 992 -> 194");
		int addr = first;
		while (addr != 0) {
			int value = mem.getInt(addr);    // get value of node pointed to by addr.
			System.out.println(value);
			
			addr = mem.getInt(addr+4);       // advance to next one in the list
		}
		
		System.out.println("Allocated bytes should be 32: " + mem.charsAllocated());
		System.out.println("Available bytes should be 0: " + mem.charsAvailable());
	}
}