import sys
from queue import Queue
from graph import Graph

class BFS:

    def __init__(self, g, s):
        self.g = g
        self.start = s

        self.marked = [False] * g.totalVerts
        self.edgeTo = [0] * g.totalVerts  
        self.distTo = [0] * g.totalVerts  

        self.bfs(s)

    def bfs(self, v):
        fila = Queue()
        fila.put(v)
        self.marked[v] = True
        while fila.qsize() > 0:
            v = fila.get()
            self.marked[v] = True
            for w in g.adj(v):
                if not self.marked[w]:
                    self.edgeTo[w] = v
                    self.marked[w] = True
                    self.distTo[w] = self.distTo[v] + 1
                    fila.put(w)

    def hasPathTo(self, v):
        return self.marked[v]
    
    def getDist(self, v):
        return self.distTo[v]
    
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
    cp = BFS(g, 0)

    for v in range(g.totalVerts):
        print(f"{v}: ", end="")
        if cp.hasPathTo(v):
            print(cp.pathTo(v))
        else:
            print("SEM CAMINHO")