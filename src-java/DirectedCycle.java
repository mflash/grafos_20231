/******************************************************************************
 *  Compilation:  javac DirectedCycle.java
 *  Execution:    java DirectedCycle
 *  Dependencies: Digraph.java DigraphGenerator.java
 *
 *  Detects whether a directed graph has a cycle
 *  
 ******************************************************************************/

/**
 *  The {@code DirectedCycle} class allows the detection of cycles in any directed graph
 *  The sample main method randomly generates digraphs and checks them
 *  
 *  @author Marcelo Cohen
 */
public class DirectedCycle {

	private Mark[] marked;
	private boolean cyclic;

	private enum Mark {
		WHITE, BLACK, GRAY
	};

	public DirectedCycle(Digraph g) {
		marked = new Mark[g.V()];
		for (int v = 0; v < g.V(); v++)
			marked[v] = Mark.WHITE;
		for (int v = 0; v < g.V(); v++) {
			if (marked[v] == Mark.WHITE)
				cyclic = dfs(g, v);
			if (cyclic)
				break;
		}
	}

	private boolean dfs(Digraph g, int v) {
		marked[v] = Mark.GRAY;
		for (int u : g.adj(v)) {
			if (marked[u] == Mark.GRAY)
				return true;
			else if (marked[u] == Mark.WHITE) {
				if (dfs(g, u))
					return true;
			}
		}
		marked[v] = Mark.BLACK;
		return false;
	}

	public boolean hasCycle() {
		return cyclic;
	}

	public static void main(String[] args) {
		// Uncomment the following lines to read the graph from a file
//		In in = new In("tinyDAG.txt");
//		Digraph g = new Digraph(in);
		
		// Random seed to always generate the same sequence
		DigraphGenerator.setRandomSeed(0);
		Digraph g = DigraphGenerator.simple(30, 30);

		System.out.println(g.toDot());
		System.out.println();

		DirectedCycle cycleDetector = new DirectedCycle(g);
		System.out.println("Has cycle? " + cycleDetector.hasCycle());

	}
}
