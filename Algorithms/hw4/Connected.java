package zdszeto.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class Connected {

	public static boolean areConnected(Graph g, int v, int w) {
		DepthFirstSearch dfs = new DepthFirstSearch(g, v);
		return dfs.marked(w);
	}

	public static void main(String[] args) throws Exception {
		FilterAirport justLower48 = new FilterLower48();
		Information infoDelta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information infoSW = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);
		Graph graphDelta = infoDelta.graph;
		Graph graphSW = infoSW.graph;
		Queue<String> connectedDelta = new Queue<String>();
		Queue<String> connectedSW = new Queue<String>();
		
		for (int i = 0; i < infoDelta.graph.V(); i++) {
			if (!areConnected(graphDelta, 24, i))
				connectedDelta.enqueue(infoDelta.labels.get(i));
		}

		for (int i = 0; i < infoSW.graph.V(); i++) {
			if (!areConnected(graphSW, 24, i))
				connectedSW.enqueue(infoSW.labels.get(i));
		}
		String notConnected = "";
		if(connectedDelta.isEmpty()) {
			notConnected = "SouthWest";
		} else {
			notConnected = "Delta";
		}
		
		//System.out.println(connectedDelta + "\n" + connectedSW);
		System.out.println(
				"The name of the airline is " + notConnected + "\nThe airports that cannot be reached from KBOS using Delta are:\n" + connectedDelta + connectedSW);
	}
}
