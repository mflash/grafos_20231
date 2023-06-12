public class CaminhoCritico {

    public CaminhoCritico(String arq) {

        In in = new In(arq);
        int totalTarefas = in.readInt();
        int totalVerts = totalTarefas * 2 + 2;
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(totalVerts);
        int start = totalVerts - 2; // ex: 20
        int end = totalVerts - 1; // ex: 21
        int tarefaAtual = 0;
        while (in.hasNextLine()) {
            double duracao = in.readDouble();
            int vertInicialTarefa = tarefaAtual;
            int vertFinalTarefa = tarefaAtual + totalTarefas;
            DirectedEdge e = new DirectedEdge(vertInicialTarefa,
                    vertFinalTarefa, duracao);
            g.addEdge(e);
            int totalDeps = in.readInt();
            for (int dep = 0; dep < totalDeps; dep++) {
                int tarefaDep = in.readInt();
                e = new DirectedEdge(vertFinalTarefa, tarefaDep, 0);
                g.addEdge(e);
            }
            e = new DirectedEdge(start, vertInicialTarefa, 0);
            g.addEdge(e);
            e = new DirectedEdge(vertFinalTarefa, end, 0);
            g.addEdge(e);
            tarefaAtual++;
        }
        System.out.println(g.toDot());

        AcyclicLP lp = new AcyclicLP(g, start);
        for (DirectedEdge e : lp.pathTo(end)) {
            if (e.weight() > 0)
                System.out.println(e);
        }
    }

    public static void main(String[] args) {
        CaminhoCritico c = new CaminhoCritico(args[0]);
    }
}