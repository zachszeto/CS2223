package zdszeto.hw1;

import algs.hw1.fixed.*;
import algs.hw1.fixed.er.Location;
import algs.hw1.fixed.er.SearchForRectangle;
import algs.hw1.fixed.er.TrialEmbeddedRectangle;

/**
 * COPY this class into your USERID.hw1 package and complete the implementation of search.
 * 
 * This method must return a {@link Location} object which records the (startr,startc,r,c) of the rectangle that you 
 * found within the EmbeddedRectangle.
 * 
 * For example, given an EmbeddedRectangle with 5 rows and 9 columns
 * might appear as below
 * 
 *    000000000
 *    000111110
 *    000111110
 *    000111110
 *    000111110
 *  
 * Your challenge will be to find the location of randomly generated rectangles within a Storage of suitable 
 * dimension. For the above Storage, your program should report (row=1, col=3, numRows=4, numCols=5).
 */
public class ComputeRectangle implements SearchForRectangle {

	@Override
	public Location search(TwoDimensionalStorage rect) {
		
		// Top Bound
		int topLo = 0;
		int topHi = rect.numRows / 2 - 1;
		while (topLo <= topHi) {
			int topMid = (topLo + topHi) / 2;
			if (rect.getValue(topMid, rect.numColumns / 2) == 0)
				topLo = topMid + 1;
			else
				topHi = topMid - 1;
		}
		
		// Bot Bound
		int botLo = rect.numRows / 2 + 1;
		int botHi = rect.numRows - 1;
		while (botLo <= botHi) {
			int botMid = (botLo + botHi) / 2;
			if (rect.getValue(botMid, rect.numColumns / 2) == 1)
				botLo = botMid + 1;
			else
				botHi = botMid - 1;
		}
		
		// Left Bound
		int leftLo = 0;
		int leftHi = rect.numColumns / 2 - 1;
		while (leftLo <= leftHi) {
			int leftMid = (leftLo + leftHi) / 2;
			if (rect.getValue(rect.numRows / 2, leftMid) == 0)
				leftLo = leftMid + 1;
			else
				leftHi = leftMid - 1;

		}
		
		// Right Bound
		int rightLo = rect.numColumns / 2 + 1;
		int rightHi = rect.numColumns - 1;
		while (rightLo <= rightHi) {
			int rightMid = (rightLo + rightHi) / 2;
			if (rect.getValue(rect.numRows / 2, rightMid) == 1)
				rightLo = rightMid + 1;
			else
				rightHi = rightMid - 1;
		}
		
		// Final Calcs
		
		
		int height = botLo - topLo;
		int width = rightLo - leftLo;
		return new Location(topLo, leftLo, height, width);
	}



	/** YOU DO NOT HAVE TO MODIFY THIS MAIN METHOD. RUN AS IS. */
	public static void main(String[] args) {
		// This code helps you evaluate if you have it working for a small example.
		int[][] values = new int[][] { 
			{ 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 1, 1 },
			{ 0, 0, 1, 1, 1, 1, 1 },
			{ 0, 0, 1, 1, 1, 1, 1 },
			{ 0, 0, 1, 1, 1, 1, 1 },
		};
		TwoDimensionalStorage sample = new TwoDimensionalStorage(values);
		SearchForRectangle me = new ComputeRectangle();
		Location loc = me.search(sample);
		System.out.println("Location 1 2 4 5 should be: " + loc);

		// This code is used to ensure your code is robust enough to handle a small run.

		// compute and validate it works for small run.
		TrialEmbeddedRectangle.runSampleTrial(new ComputeRectangle());

		// compute and validate for large run. Your algorithm must significantly outperform this result.
		// When I ran my naive solution, the result was 1082400000. You need to do better!
		TrialEmbeddedRectangle.leaderBoard(new ComputeRectangle());
	}
}
