import java.util.ArrayList;
import java.util.LinkedList;

public class CaminhamentoLargura {

    private Graph g;
    private int start;

    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public CaminhamentoLargura(Graph g, int s) {
        this.g = g;
        this.start = s;

        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];
        bfs(start);
    }

    public boolean hasPathTo(int v) {
        return marked[v];
        // if(marked[v] == true)
        // return true;
        // else
        // return false;
    }

    public int getDist(int v) {
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        ArrayList<Integer> path = new ArrayList<>();
        if (!hasPathTo(v))
            return path;
        while (v != start) // enquanto não chegar no primeiro
        {
            path.add(0, v); // adiciona no início
            v = edgeTo[v];
        }
        path.add(0, start);
        return path;
    }

    private void bfs(int v) {
        LinkedList<Integer> fila = new LinkedList<>();
        fila.add(v);
        distTo[v] = 0;
        while (!fila.isEmpty()) {
            v = fila.removeFirst();
            marked[v] = true;
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    fila.add(w);
                }
            }
        }
    }

    public static void main(String[] args) {
        In arq = new In(args[0]);
        Graph g = new Graph(arq);
        CaminhamentoLargura cp = new CaminhamentoLargura(g, 0);
        for (int v = 0; v < g.V(); v++) {
            System.out.print(v + ": ");
            if (cp.hasPathTo(v)) {
                for (int w : cp.pathTo(v)) {
                    System.out.print(w + " (" + cp.getDist(w) + ") ");
                }
                System.out.println();
            } else
                System.out.println("SEM CAMINHO");
        }
    }
}
