package zdszeto.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.AdjMatrixEdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.FloydWarshall;

public class LongestOfShortest {

	public static void main(String[] args) throws Exception {
		FilterAirport justLower48 = new FilterLower48();

		Information infoDelta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information infoSW = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		AdjMatrixEdgeWeightedDigraph graphDelta = new AdjMatrixEdgeWeightedDigraph(infoDelta.graph.V());
		AdjMatrixEdgeWeightedDigraph graphSW = new AdjMatrixEdgeWeightedDigraph(infoSW.graph.V());

		for (int i = 0; i < infoDelta.graph.V(); i++) {
			for (int v : infoDelta.graph.adj(i)) {
				graphDelta.addEdge(
						new DirectedEdge(i, v, infoDelta.positions.get(i).distance(infoDelta.positions.get(v))));
			}
		}

		for (int i = 0; i < infoSW.graph.V(); i++) {
			for (int v : infoSW.graph.adj(i)) {
				graphSW.addEdge(new DirectedEdge(i, v, infoSW.positions.get(i).distance(infoSW.positions.get(v))));
			}
		}

		FloydWarshall pathDelta = new FloydWarshall(graphDelta);
		FloydWarshall pathSW = new FloydWarshall(graphSW);

		DirectedEdge maxDistDelta = new DirectedEdge(0, 0, 0.0);
		double totalRatioDelta = 0;
		int numCompDelta = 0;

		for (int i = 0; i < infoDelta.graph.V(); i++) {
			for (int v = infoDelta.graph.V() - 1; v >= 0; v--) {
				if (pathDelta.dist(i, v) > maxDistDelta.weight() && pathDelta.hasPath(i, v)) {
					maxDistDelta = new DirectedEdge(i, v, pathDelta.dist(i, v));
				}

				if (pathDelta.hasPath(i, v) && i != v) {
					totalRatioDelta += pathDelta.dist(i, v)
							/ infoDelta.positions.get(i).distance(infoDelta.positions.get(v));
					numCompDelta++;
				}
			}
		}

		System.out.println("Test Delta : Total Flight Distance is " + maxDistDelta.weight() + " but airports are only "
				+ infoDelta.positions.get(maxDistDelta.from()).distance(infoDelta.positions.get(maxDistDelta.to())));
		System.out.println(pathDelta.path(maxDistDelta.from(), maxDistDelta.to()));

		System.out.println("Average Efficiency:" + totalRatioDelta / numCompDelta);

		DirectedEdge maxDistSW = new DirectedEdge(0, 0, 0.0);
		double totalRatioSW = 0;
		int numCompSW = 0;

		for (int i = 0; i < infoSW.graph.V(); i++) {
			for (int v = infoSW.graph.V() - 1; v >= 0; v--) {
				if (pathSW.dist(i, v) > maxDistSW.weight() && pathSW.hasPath(i, v)) {
					maxDistSW = new DirectedEdge(i, v, pathSW.dist(i, v));
				}

				if (pathSW.hasPath(i, v) && i != v) {
					totalRatioSW += pathSW.dist(i, v) / infoSW.positions.get(i).distance(infoSW.positions.get(v));
					numCompSW++;
				}
			}
		}

		System.out
				.println("\nTest SouthWest : Total Flight Distance is " + maxDistSW.weight() + " but airports are only "
						+ infoSW.positions.get(maxDistSW.from()).distance(infoSW.positions.get(maxDistSW.to())));
		System.out.println(pathSW.path(maxDistSW.from(), maxDistSW.to()));

		System.out.println("Average Efficiency:" + totalRatioSW / numCompSW);
	}
}