class Graph:

    def __init__(self, filename=None):
        self.verts = {}
        self.totalEdges = 0
        self.totalVerts = 0

        if filename:
            with open(filename) as f:
                totalVerts = int(f.readline())
                totalEdges = int(f.readline())
                for v in range(totalVerts):
                    self.addVert(v)
                for e in range(totalEdges):
                    line = f.readline().split(' ')
                    v,w = int(line[0]), int(line[1])
                    self.addEdge(v,w)

    def addVert(self, value):
        if value in self.verts:
            return
        self.verts[value] = []
        self.totalVerts += 1


    def addEdge(self, v1, v2):
        if v1 not in self.verts:
            self.addVert(v1)
        if v2 not in self.verts:
            self.addVert(v2)
        assert(v2 not in self.verts[v1])
        self.verts[v1].append(v2)
        self.verts[v2].append(v1)
        self.totalEdges += 1

    def adj(self, v):
        if v not in self.verts:
            return None
        return self.verts[v]

    def print(self):
        print("Vertices:",len(self.verts))
        print("Edges:",self.totalEdges)
        for v in self.verts:
            print(f"{v}: ", end="")
            for w in self.verts[v]:
                print(f"{w} ", end="")
            print()

    def toDot(self):
        print("graph {")
        print("  rankdir = LR")
        print("  node [shape=circle]")
        # Precisa listar todos os vértices, pois pode não haver arestas!
        for v in self.verts:
            print(f"  {v};")
        # Agora lista as arestas
        edges = set()
        for v in self.verts:
            for w in self.verts[v]:
                edge = f"{min(v,w)}-{max(v, w)}"
                # Verifica se esta aresta já foi listada
                # (como é não dirigido, só pode listar uma vez)
                if edge not in edges:
                    print(f"  {v} -- {w}")
                    edges.add(edge)
        print("}")


if __name__ == "__main__":

    g = Graph('tinyG.txt')
    #g = Graph()

    """
    g.addEdge(0, 2);
    g.addEdge(2, 1);
    g.addEdge(2, 4);
    g.addEdge(1, 3);
    g.addEdge(3, 4);
    """

    print(g.adj(2))

    g.print()
    print()

    g.toDot()

