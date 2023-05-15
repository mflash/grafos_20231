import sys
from digraph import Digraph

class OrdTopo:

    def __init__(self, g):
        self.g = g
        self.marked = [False] * g.totalVerts
        self.reversePost = []

        for v in range(g.totalVerts):
            if not self.marked[v]:
                self.dfs(v)

    def dfs(self, v):
        self.marked[v] = True
        for w in g.adj(v):
            if not self.marked[w]:
                self.dfs(w)
        self.reversePost.insert(0,v)

    def ordemTopologica(self):
        return self.reversePost

if __name__ == "__main__":

    g = Digraph(sys.argv[1])
    topo = OrdTopo(g)
    for v in topo.ordemTopologica():
        print(v, end=" ")
    print()