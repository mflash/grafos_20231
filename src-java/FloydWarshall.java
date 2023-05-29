import java.util.ArrayList;

/* 
 * Implementação do algoritmo de Floyd-Warshall usando matriz de adjacência
 */
public class FloydWarshall {
    private boolean temCicloNegativo; // tem ciclo negativo?
    private double[][] dist; // dist[v][w] = distancia do caminho mais curto de v->w
    private int[][] next; // next[v][w] = ultima aresta no caminho mais curto de v->w

    private long startTime, endTime;

    /**
     * Calcula uma arvore de caminhos mais curtos de todos os vertices para todos os
     * vertices
     * em um digrafo valorado {@code G}. Se houver um ciclo negativo, seta a flag e
     * retorna.
     * 
     * @param G o digrafo valorado
     */
    public FloydWarshall(AdjMatrixEdgeWeightedDigraph G) {
        int V = G.V(); // total de vertices
        dist = new double[V][V]; // inicialize todos com Double.POSITIVE_INFINITY
        next = new int[V][V]; // inicialize todos com -1

        temCicloNegativo = false;

        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++) {
                dist[i][j] = Double.POSITIVE_INFINITY;
                next[i][j] = -1;
            }

        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : G.adj(v)) {
                int u = e.from();
                int w = e.to();
                dist[u][w] = e.weight();
                next[u][w] = w;
            }
        }

        // Comeco do algoritmo...
        startTime = System.currentTimeMillis();

        // Loop de Floyd-Warshall
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
                if (dist[i][i] < 0) {
                    temCicloNegativo = true;
                    return;
                }
            }
        }

        // Fim do algoritmo
        endTime = System.currentTimeMillis();
    }

    public long tempoTotal() {
        return endTime - startTime;
    }

    /**
     * Tem ciclo negativo?
     * 
     * @return {@code true} se existe um ciclo negativo, e {@code false} caso não
     *         exista
     */
    public boolean temCicloNegativo() {
        return temCicloNegativo;
    }

    /**
     * Existe caminho entre o vertice {@code s} e o vertice {@code t}?
     * 
     * @param s o vertice de origem
     * @param t o vertice de destino
     * @return {@code true} se existe um caminho
     */
    public boolean temCaminho(int s, int t) {
        return dist[s][t] < Double.POSITIVE_INFINITY;
    }

    /**
     * Retorna o comprimento do caminho mais curto do vertice {@code s} ao vertice
     * {@code t}.
     * 
     * @param s o vertice de origem
     * @param t o vertice de destino
     * @return o comprimento do caminho mais curto de {@code s} a {@code t};
     *         {@code Double.POSITIVE_INFINITY} se não houver caminho
     * @throws UnsupportedOperationException se existir um ciclo negativo
     */
    public double dist(int s, int t) {
        if (temCicloNegativo())
            throw new UnsupportedOperationException("Existe um ciclo negativo!");
        return dist[s][t];
    }

    /**
     * Retorna o caminho mais curto de {@code s} a {@code t}.
     * 
     * @param s o vertice de origem
     * @param t o vertice de destino
     * @return o caminho mais curto de {@code s} a {@code t}
     *         como um ArrayList de inteiros, e {@code null} se não houver caminho
     * @throws UnsupportedOperationException se existir um ciclo negativo
     */
    public ArrayList<Integer> caminho(int s, int t) {
        if (temCicloNegativo())
            throw new UnsupportedOperationException("Existe um ciclo negativo!");
        if (!temCaminho(s, t))
            return null;

        ArrayList<Integer> path = new ArrayList<Integer>();

        // Adiciona vertices no caminho...
        path.add(s);
        while (s != t) {
            s = next[s][t];
            path.add(s);
        }

        return path;
    }

    /**
     * Testa a classe {@code FloydWarshall}
     * 
     */
    public static void main(String[] args) {

        /*
         * // Cria um grafo aleatorio com V vertices e E arestas
         * int V = 100;
         * int E = 100;
         * AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V);
         * for (int i = 0; i < E; i++) {
         * int v = StdRandom.uniform(V);
         * int w = StdRandom.uniform(V);
         * double weight = Math.round(100 * (StdRandom.uniform() - 0.15)) / 100.0;
         * if (v == w) G.addEdge(new DirectedEdge(v, w, Math.abs(weight)));
         * else G.addEdge(new DirectedEdge(v, w, weight));
         * }
         */

        // Carrega um grafo de um arquivo texto
        EdgeWeightedDigraph DG = new EdgeWeightedDigraph(new In("tinyEWG.txt"));
        System.out.println(DG.toDot());

        // ...e cria um AdjMatrixEdgeWeightedDigraph...
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(DG.V());
        for (DirectedEdge e : DG.edges())
            G.addEdge(e);

        StdOut.println(G);

        // Executa Floyd-Warshall
        FloydWarshall spt = new FloydWarshall(G);

        // Mostra todas as distâncias dos caminhos mais curtos
        StdOut.printf("  ");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%6d ", v);
        }
        StdOut.println();
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (spt.temCaminho(v, w))
                    StdOut.printf("%6.2f ", spt.dist(v, w));
                else
                    StdOut.printf("  Inf ");
            }
            StdOut.println();
        }

        // Exibe mensagem se houver ciclo negativo
        if (spt.temCicloNegativo()) {
            StdOut.println("Existe um ciclo negativo!");
        }

        // Exibe todos os caminhos
        else {
            for (int v = 0; v < G.V(); v++) {
                for (int w = 0; w < G.V(); w++) {
                    if (spt.temCaminho(v, w)) {
                        StdOut.printf("%d para %d (%5.2f)  ", v, w, spt.dist(v, w));
                        for (int e : spt.caminho(v, w))
                            StdOut.print(e + "  ");
                        StdOut.println();
                    } else {
                        StdOut.printf("%d para %d - sem caminho\n", v, w);
                    }
                }
            }
        }

        System.out.println("Tempo de Floyd-Warshall: " + spt.tempoTotal());

    }

}
