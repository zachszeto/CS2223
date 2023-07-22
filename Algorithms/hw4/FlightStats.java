package zdszeto.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.GPS;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class FlightStats {

	public static void main(String[] args) throws Exception {
		FilterAirport justLower48 = new FilterLower48();
		Information infoDelta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information infoSW = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		LinearProbingHashST<Integer, Integer> distDelta = new LinearProbingHashST<>();
		LinearProbingHashST<Integer, Integer> distSW = new LinearProbingHashST<>();

		DirectedEdge maxDelta = new DirectedEdge(0, 0, 0.0);
		DirectedEdge minDelta = new DirectedEdge(0, 0, 0.0);
		
		DirectedEdge maxSW = new DirectedEdge(0, 0, 0.0);
		DirectedEdge minSW = new DirectedEdge(0, 0, 0.0);

		Histogram deltaHistogram = new Histogram();
		Histogram swHistogram = new Histogram();

		//Delta
		int largestDelta = 0;
		int smallestDelta = Integer.MAX_VALUE;

		for (int i = 0; i < infoDelta.graph.V(); i++) { 
			String start = infoDelta.labels.get(i);

			for (int v : infoDelta.graph.adj(i)) { 

				String end = infoDelta.labels.get(v);
				int codes = (start + end).hashCode();
				GPS startGPS = infoDelta.positions.get(i);
				GPS endGPS = infoDelta.positions.get(v);
				int distance = (int) startGPS.distance(endGPS);

				if(i < v) {
					distDelta.put(codes, distance);
					deltaHistogram.record(distance);
				}
				

				if (distance > largestDelta) {
					largestDelta = distance;
					maxDelta = new DirectedEdge(i, v, 0.0);
				}

				if (distance < smallestDelta) {
					smallestDelta = distance;
					minDelta = new DirectedEdge(i, v, 0.0);
				}
			}
		}

		double sumDelta = 0;
		double numDetla = 0;

		for (int codes : distDelta.keys()) {
			sumDelta += distDelta.get(codes);
			numDetla++;
		}

		double avgDelta = sumDelta / numDetla;

		// Delta Info
		System.out.println("Delta average distance: " + avgDelta);
		System.out.println("Longest flight is from " + infoDelta.labels.get(maxDelta.from()) + " to "
				+ infoDelta.labels.get(maxDelta.to()) + ": " + largestDelta);
		System.out.println("Shortest flight is from " + infoDelta.labels.get(minDelta.from()) + " to "
				+ infoDelta.labels.get(minDelta.to()) + ": " + smallestDelta);

		// Delta Histogram
		System.out.println();
		deltaHistogram.report(500);
	
		//Southwest
		int largestSW = 0;
		int smallestSW = Integer.MAX_VALUE;

		for (int i = 0; i < infoSW.graph.V(); i++) { 
			String start = infoSW.labels.get(i);

			for (int v : infoSW.graph.adj(i)) {

				String end = infoSW.labels.get(v);
				int codes = (start + end).hashCode();
				GPS startGPS = infoSW.positions.get(i);
				GPS endGPS = infoSW.positions.get(v);
				int distance = (int) startGPS.distance(endGPS);

				if( i < v) {
					distSW.put(codes, distance);
					swHistogram.record(distance);
				}
				

				if (distance > largestSW) {
					largestSW = distance;
					maxSW = new DirectedEdge(i, v, 0.0);
				}

				if (distance < smallestSW) {
					smallestSW = distance;
					minSW = new DirectedEdge(i, v, 0.0);
				}
			}
		}

		double sumSW = 0;
		double numSW = 0;

		for (int codes : distSW.keys()) {
			sumSW += distSW.get(codes);
			numSW++;
		}

		double avgSW = sumSW / numSW;

		// Southwest Info
		System.out.println("\nSouthwest average distance: " + avgSW);
		System.out.println("Longest Flight is from " + infoSW.labels.get(maxSW.from()) + " to "
				+ infoSW.labels.get(maxSW.to()) + ": " + largestSW);
		System.out.println("Shortest flight is from " + infoSW.labels.get(minSW.from()) + " to "
				+ infoSW.labels.get(minSW.to()) + ": " + smallestSW);

		// Southwest Histogram
		System.out.println();
		swHistogram.report(500);

	}
}