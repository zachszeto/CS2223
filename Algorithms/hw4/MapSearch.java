package zdszeto.hw4;

import algs.hw4.map.GPS;

import algs.hw4.map.HighwayMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import algs.days.day20.DepthFirstSearchNonRecursive;

/**
 * Copy this class into USERID.hw4 and make changes.
 */
public class MapSearch {
	
	/** 
	 * This helper method returns the western-most vertex id in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int westernMostVertex(Information info) {
		int west = 0;
		for(int i :info.positions.keys()){
			if(info.positions.get(i).longitude < info.positions.get(west).longitude) west = i;
		}
		return west;
	}

	/** 
	 * This helper method returns the western-most vertex id in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int easternMostVertex(Information info) {
		int east = 0;
		for(int i :info.positions.keys()){
			if(info.positions.get(i).longitude > info.positions.get(east).longitude) east = i;
		}
		return east;
	}

	public static void main(String[] args) {
		Information info = HighwayMap.undirectedGraphFromResources("USA-lower48-natl-traveled.tmg");
		int west = westernMostVertex(info);
		int east = easternMostVertex(info);

		// DO SOME WORK HERE and have the output include things like this
		BreadthFirstPaths bfs = new BreadthFirstPaths(info.graph, west);
		DepthFirstSearchNonRecursive dfs = new DepthFirstSearchNonRecursive(info.graph, west);
		
		EdgeWeightedGraph graph = new EdgeWeightedGraph(info.graph.V());
		//Populating EdgeWeightedGraph
				for (int i = 0; i < info.graph.V(); i++) {
					for (int v : info.graph.adj(i)) {
						graph.addEdge(
								new Edge(i, v, info.positions.get(i).distance(info.positions.get(v))));
					}
				}
		
		DijkstraUndirectedSP dijk = new DijkstraUndirectedSP(graph, west);
		
		
		//BFS: Calculate Distance
		double totalBFS = 0;
		Integer last = -1;
		for(int i : bfs.pathTo(east)) {
			if (last == -1) {
				last = i;
			} else {
				totalBFS += info.positions.get(last).distance(info.positions.get(i));
				last = i;
			}

		}

		// DFS: Calculate Distance
		double totalDFS = 0;
		int count = -1;
		Integer last2 = -1;
		for (int i : dfs.pathTo(east)) {
			if (last2 == -1) {
				last2 = i;
			} else {
				totalDFS += info.positions.get(last2).distance(info.positions.get(i));
				last2 = i;
			}
			count++;
		}
		
		//Dijk: Calculate Edges
		int num = 0;
		for(Edge i : dijk.pathTo(east)) num++;
		
		System.out.println("BreadthFirst Search from West to East:");
		System.out.println("BFS: " +  "From " + info.labels.get(west) + " to " +  info.labels.get(east) + " has " + bfs.distTo(east) + " edges.");
		System.out.println("BFS provides answer that is: " + totalBFS + " miles.");
		
		System.out.println("\nDepthFirst Search from West to East:");
		System.out.println("DFS: " +  "From " + info.labels.get(west) + " to " +  info.labels.get(east) + " has " + count + " edges.");
		System.out.println("DFS provides answer that is: " + totalDFS + " miles.");
		
		System.out.println("\nShortest Distance via Dikstra: " + dijk.distTo(east) +" miles with " + num + " total edges.");
	}
}
