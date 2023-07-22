package zdszeto.hw4;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Run in Shell as follows:
 * 
 * % java algs.days.day20.DepthFirstSearch tinyG.txt
 */
public class DepthFirstSearch {

	boolean marked[]; // which vertices have been seen already
	int count; // how many connected
	Graph g; // graph being searched
	String indent = "";

	public DepthFirstSearch(Graph g, int s) {
		marked = new boolean[g.V()];
		this.g = g;
		dfs(s);
	}

	public int count() {
		return count;
	} // number of vertices connected to s

	public boolean marked(int v) {
		return marked[v];
	}

	/** Continue DFS search over graph by visiting vertex v. */
	void dfs(int v) {
		indent += "  ";
		marked[v] = true; // we have now seen this vertex
		count++;

		// look into neighbors
		for (int w : g.adj(v)) {
			if (!marked[w]) {
				dfs(w);
			}
		}
		indent = indent.substring(2); // truncate indentation
	}

	// To check if two vertexes are connected
	public boolean areConnected(int v, int w) {
		return marked[v] && marked[w];
	}

}
