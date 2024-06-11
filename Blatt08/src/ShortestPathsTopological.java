import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;

    public ShortestPathsTopological(WeightedDigraph G, int s) {
        // TODO
    }

    public void relax(DirectedEdge e) {
        // TODO
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

