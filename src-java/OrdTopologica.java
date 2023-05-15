import java.util.LinkedList;

public class OrdTopologica {

    private Digraph g;

    private boolean[] marked;
    private LinkedList<Integer> reversePost;

    public OrdTopologica(Digraph g) {
        this.g = g;

        marked = new boolean[g.V()];

        reversePost = new LinkedList<>();
        for(int v=0; v<g.V(); v++)
            if(!marked[v])
                dfs(v);
    }

    private void dfs(int v) {
        System.out.println("Visitando: " + v);
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(w);
            }
        }
        reversePost.add(0, v);
    }

    public Iterable<Integer> ordemTopologica() {
        return reversePost;
    }

    public static void main(String[] args) {
        In arq = new In(args[0]);
        Digraph g = new Digraph(arq);
        System.out.println(g.toDot());
        OrdTopologica topo = new OrdTopologica(g);
        for(int v: topo.ordemTopologica())
            System.out.print(v+" ");
        System.out.println();
    }
}
