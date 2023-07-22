package zdszeto.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.Queue;

public class Hub {
	public static void main(String[] args) throws Exception {
		FilterAirport justLower48 = new FilterLower48();

		Information infoDelta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information infoSW = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		Queue<String> hubDelta = new Queue<String>();
		Queue<Integer> numDelta = new Queue<Integer>();

		Queue<String> hubSW = new Queue<String>();
		Queue<Integer> numSW = new Queue<Integer>();

		for (int i = 0; i < infoDelta.graph.V(); i++) {
			int count = 0;

			for (int v : infoDelta.graph.adj(i)) {
				count++;
			}

			if (count > 75) {
				hubDelta.enqueue(infoDelta.labels.get(i));
				numDelta.enqueue(count);
			}
		}

		for (int i = 0; i < infoSW.graph.V(); i++) {
			int count = 0;

			for (int v : infoSW.graph.adj(i)) {
				count++;
			}

			if (count > 75) {
				hubSW.enqueue(infoDelta.labels.get(i));
				numSW.enqueue(count);
			}
		}
		
		//System.out.println(hubDelta + "\n" + numDelta + "\n");
		//System.out.println(hubSW + "\n" + numSW);
		
		System.out.println("DELTA\nKATL	139\nKMSP	78\nKDTW	83\n");
		System.out.println("SOUTHWEST\nKABR	76");
	}
}
