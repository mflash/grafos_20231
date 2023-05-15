import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StringDigraph {
	private static final String NEWLINE = System.getProperty("line.separator");

    private class DEdge {
        public DEdge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
        public int v1;
        public int v2;
    }
    private Map<String, Integer> dicNames;
    private ArrayList<String> dicIndices;
    private ArrayList<DEdge> directedEdges;
    private int totalVert;

    public StringDigraph() {
        dicNames = new HashMap<>();
        dicIndices = new ArrayList<>();
        directedEdges = new ArrayList<>();
        totalVert = 0;
    }

    public StringDigraph(String filename) {
        this();
        In in = new In(filename);
        while(in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split("/");
            int s = addVertex(fields[0]);
            for(String f: fields) {
                if(f.equals(fields[0])) continue;
                int v = addVertex(f);
                addEdge(s, v);
            }
        }
    }

    public int addVertex(String name) {
        if(dicNames.containsKey(name))
            return dicNames.get(name);
        dicNames.put(name, totalVert++);
        dicIndices.add(name);
        return totalVert - 1;
    }

    public String getName(int index) {
        return dicIndices.get(index);
    }

    public int getIndex(String name) {
        return dicNames.get(name);
    }

    public void addEdge(int v1, int v2) {
        directedEdges.add(new DEdge(v1,v2));
    }

    public Digraph convertToDigraph() {
        Digraph g = new Digraph(totalVert);
        for (var edge : directedEdges) {
            g.addEdge(edge.v1, edge.v2);
        }        
        return g;
    }

    public String toDot() {
		StringBuilder s = new StringBuilder();
		s.append("digraph {" + NEWLINE);
		s.append("rankdir = LR;"+NEWLINE);
		s.append("node [shape = circle];"+NEWLINE);
		for (var e: directedEdges) {
            String n1 = getName(e.v1);
            String n2 = getName(e.v2);
            s.append(String.format("\"%s\" -> \"%s\""+NEWLINE,
                n1, n2));
		}
		s.append("}");
		return s.toString();
    }

    public static void main(String[] args) {
        /*
        StringDigraph sg = new StringDigraph();
        int v0 = sg.addVertex("A");
        int v1 = sg.addVertex("B");
        int v2 = sg.addVertex("C");
        sg.addEdge(v0, v1);
        sg.addEdge(v1, v2);
        sg.addEdge(v2, v0);
        */
        StringDigraph sg = new StringDigraph(args[0]);
        Digraph g = sg.convertToDigraph();
        System.out.println(g.toDot());
        System.out.println(sg.toDot());
    }
}
