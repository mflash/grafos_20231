import java.util.ArrayList;

public class CaminhamentoProfundidade {

    private Graph g;
    private int start;

    private boolean[] marked;
    private int[] edgeTo;

    public CaminhamentoProfundidade(Graph g, int s) {
        this.g = g;
        this.start = s;

        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        dfs(start);
    }

    public boolean hasPathTo(int v) {
        return marked[v];
        // if(marked[v] == true)
        // return true;
        // else
        // return false;
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

    private void dfs(int v) {
        System.out.println("Visitando: " + v);
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(w);
            }
        }
    }

    public static void main(String[] args) {
        In arq = new In(args[0]);
        Graph g = new Graph(arq);
        OrdTopologica cp = new OrdTopologica(g, 12);
        for (int v = 0; v < g.V(); v++) {
            System.out.print(v + ": ");
            if (cp.hasPathTo(v)) {
                for (int w : cp.pathTo(v)) {
                    System.out.print(w + " ");
                }
                System.out.println();
            } else
                System.out.println("SEM CAMINHO");
        }
    }
}
