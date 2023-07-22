package zdszeto.hw1;

import algs.hw1.fixed.TwoDimensionalStorage;
import algs.hw1.fixed.ps.SearchPermutationArray;
import algs.hw1.fixed.ps.TrialPermutationSquare;
import edu.princeton.cs.algs4.StdOut;

/**
 * COPY this file into your USERID.hw1 package and complete the implementation 
 * of the 'less' and 'contains' methods.
 */
public class SolveSearch implements SearchPermutationArray {

	/**
	 * Given an {@link TwoDimensionalStorage} that contains a Permutation Array, compute the number
	 * of values in each row that are lesser than a specific value.
	 * 
	 * You can assume the TwoDimensionalStorage contains a Permutation Array of size with
	 * R rows and C columns.
	 * 
	 * Note that you can inspect the location of any (row, col) location in the 2-dimensional 
	 * TwoDimensionalStorage by using its 
	 * 
	 * @param array  - TwoDimensionalStorage that contains a Permutation Array
	 * @param target  - value for which you seek total number of smaller values on each row.
	 * @return int[] of integers representing count of values less than target on each row
	 */
	@Override
	public int[] less(TwoDimensionalStorage array, int value) {
		//throw new RuntimeException("Must Complete Implementation of less in SolveSearch");
		
	    int[] countArray = new int[array.numRows];
	    
	    for (int i = 0; i < array.numRows; i++) {
	        int count = 0;
	        
	        for (int j = 0; j < array.numColumns; j++) {
	            if (array.getValue(i, j) < value) count++;
	            else break;
	        }
	        countArray[i] = count;
	    }
	    return countArray;
	}


	/**
	 * Returns the row containing value in a TwoDimensionalStorage array that
	 * contains an R x C Permutation Array.
	 * 
	 * If 1 <= value <= R*C then a valid row will be returned, otherwise -1.
	 */
	@Override
	public int contains(TwoDimensionalStorage storage, int value) {
	    for (int i = 0; i < storage.numRows; i++) {
	        for (int j = 0; j < storage.numColumns; j++) {
	            if (storage.getValue(i, j) == value) return i;
	        }
	    }
	    return -1;
	}
	
	/** YOU DO NOT HAVE TO MODIFY THIS MAIN METHOD. RUN AS IS. */
	public static void main(String[] args) {
		// This code helps you evaluate if you have it working for a small example.
		int[][] values = new int[][] { 
			{ 1, 2, 4,  10, 11},
			{ 6, 7, 12, 13, 15},
			{ 3, 5, 8,  9,  14},
		};
		TwoDimensionalStorage sample = new TwoDimensionalStorage(values);
		SolveSearch me = new SolveSearch();
		int result[] = me.less(sample, 5);
		System.out.println("Should be 3 0 1: " + result[0] + " " + result[1] + " " + result[2]);
		int whichRow = me.contains(sample, 5);
		System.out.println("Should be 2: " + whichRow);

		// This code is used to ensure your code is robust enough to handle a small run.

		// compute and validate it works for small run.
		TrialPermutationSquare.runSampleTrial(new SolveSearch());

		// compute and validate for large run. Your algorithm must significantly outperform this result.
		// When I ran my naive solution, the result was 1082400000. You need to do better!
		TrialPermutationSquare.leaderBoard(new SolveSearch());
	}
}
