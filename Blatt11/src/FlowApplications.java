import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;


public class FlowApplications {

    /**
     * cloneFlowNetwork() makes a deep copy of a FlowNetwork
     * (FlowNetwork has unfortunately no copy constructor)
     *
     * @param flowNetwork the flow network that should be cloned
     * @return cloned flow network (deep copy) with same order of edges
     */
    private static FlowNetwork cloneFlowNetwork(FlowNetwork flowNetwork) {
        int V = flowNetwork.V();
        FlowNetwork clone = new FlowNetwork(V);

//        Simple version (but reverses order of edges)
//        for (FlowEdge e : flowNetwork.edges()) {
//            FlowEdge eclone = new FlowEdge(e.from(), e.to(), e.capacity());
//            clone.addEdge(eclone);
//        }

        for (int v = 0; v < flowNetwork.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<FlowEdge> reverse = new Stack<>();
            for (FlowEdge e : flowNetwork.adj(v)) {
                if (e.to() != v) {
                    FlowEdge eclone = new FlowEdge(e.from(), e.to(), e.capacity());
                    reverse.push(eclone);
                }
            }
            while (!reverse.isEmpty()) {
                clone.addEdge(reverse.pop());
            }
        }
        return clone;
    }




    /**
     * numberOfEdgeDisjointPaths() returns the (maximum) number of edge-disjoint paths that exist in
     * an undirected graph between two nodes s and t using Edmonds-Karp.
     *
     * @param graph the graph that is to be investigated
     * @param s     node on one end of the path
     * @param t     node on the other end of the path
     * @return number of edge-disjoint paths in graph between s and t
     */

    public static int numberOfEdgeDisjointPaths(Graph graph, int s, int t) {
        // TODO
    }

    /**
     * edgeDisjointPaths() returns a maximal set of edge-disjoint paths that exist in
     * an undirected graph between two nodes s and t using Edmonds-Karp.
     *
     * @param graph the graph that is to be investigated
     * @param s     node on one end of the path
     * @param t     node on the other end of the path
     * @return a {@code Bag} of edge-disjoint paths in graph between s and t
     * Each path is stored in a {@code LinkedList<Integer>}.
     */

    public static Bag<LinkedList<Integer>> edgeDisjointPaths(Graph graph, int s, int t) {
        // TODO
    }


    /**
     * isUnique determines for a given Flow Network that has a guaranteed minCut,
     * if that one is unique, meaning it's the only minCut in that network
     *
     * @param flowNetworkIn the graph that is to be investigated
     * @param s             source node s
     * @param t             sink node t
     * @return true if the minCut is unique, otherwise false
     */

    public static boolean isUnique(FlowNetwork flowNetworkIn, int s, int t) {
        // TODO
    }


    /**
     * findBottlenecks finds all bottleneck nodes in the given flow network
     * and returns the indices in a Linked List
     *
     * @param flowNetwork the graph that is to be investigated
     * @param s           index of the source node of the flow
     * @param t           index of the target node of the flow
     * @return {@code LinkedList<Integer>} containing all bottleneck vertices
     * @throws IllegalArgumentException is flowNetwork does not have a unique cut
     */

    public static LinkedList<Integer> findBottlenecks(FlowNetwork flowNetwork, int s, int t) {
        // TODO
    }

    public static void main(String[] args) {
/*
        // Test for Task 2.1 and 2.2 (useful for debugging!)
        Graph graph = new Graph(new In("Graph1.txt"));
        int s = 0;
        int t = graph.V() - 1;
        int n = numberOfEdgeDisjointPaths(graph, s, t);
        System.out.println("#numberOfEdgeDisjointPaths: " + n);
        Bag<LinkedList<Integer>> paths = edgeDisjointPaths(graph, s, t);
        for (LinkedList<Integer> path : paths) {
            System.out.println(path);
        }
*/

/*
        // Example for Task 3.1 and 3.2 (useful for debugging!)
        FlowNetwork flowNetwork = new FlowNetwork(new In("Flussgraph1.txt"));
        int s = 0;
        int t = flowNetwork.V() - 1;
        boolean unique = isUnique(flowNetwork, s, t);
        System.out.println("Is mincut unique? " + unique);
        // Flussgraph1 is non-unique, so findBottlenecks() should be tested with Flussgraph2
        flowNetwork = new FlowNetwork(new In("Flussgraph2.txt"));
        LinkedList<Integer> bottlenecks = findBottlenecks(flowNetwork, s, t);
        System.out.println("Bottlenecks: " + bottlenecks);
*/
    }

}

