import sys
from graph import Graph

class DFS:

    def __init__(self, g, s):
        self.g = g
        self.start = s

        self.marked = [False] * g.totalVerts
        self.edgeTo = [0] * g.totalVerts  

        self.dfs(s)

    def dfs(self, v):
        self.marked[v] = True
        for w in g.adj(v):
            if not self.marked[w]:
                self.edgeTo[w] = v
                self.dfs(w)

    def hasPathTo(self, v):
        return self.marked[v]
    
    def pathTo(self, v):
        path = []
        if not self.hasPathTo(v):
            return path
        while v != self.start:
            path.insert(0,v)
            v = self.edgeTo[v]
        path.insert(0,self.start)
        return path

if __name__ == "__main__":

    g = Graph(sys.argv[1])
    cp = DFS(g, 0)

    for v in range(g.totalVerts):
        print(f"{v}: ", end="")
        if cp.hasPathTo(v):
            print(cp.pathTo(v))
        else:
            print("SEM CAMINHO")