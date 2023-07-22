package zdszeto.hw4;

import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import algs.hw4.map.FilterAirport;
import java.util.ArrayList;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class Overlap {
	public static void main(String[] args) throws Exception {
		LinearProbingHashST<Integer, String> keyDelta = new LinearProbingHashST<Integer, String>();
		LinearProbingHashST<Integer, String> keySW = new LinearProbingHashST<Integer, String>();
		LinearProbingHashST<Integer, String> result = new LinearProbingHashST<Integer, String>();
		FilterAirport justLower48 = new FilterLower48();
		Information infoDelta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information infoSW = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		// Converting SW codes to an Symbol Table
		for (Integer i : infoSW.labels.keys()) {
			keySW.put(i, infoSW.labels.get(i));
		}

		// Converting Delta codes to an array list
		for (Integer i : infoDelta.labels.keys()) {
			keyDelta.put(i, infoDelta.labels.get(i));
		}

		// Comparing to see if a code is in SW but not Delta
		for (Integer i : keySW.keys()) {
			   boolean found = false;
			   for (Integer v : keyDelta.keys()) {
			       if (infoSW.labels.get(i).equals(infoDelta.labels.get(v))) {
			           found = true;
			           break;
			       }
			   }
			   if (!found) {
			       result.put(i, infoSW.labels.get(i));
			   }
			}

		// Printing results
		for (Integer i : result.keys()) {
			System.out.println(result.get(i));
		}
	}
}
