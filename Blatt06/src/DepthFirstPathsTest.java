import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

class DepthFirstPathsTest {

    @ParameterizedTest
    @MethodSource("orderTestDataStream")
    void testDfs(OrderTestData data) {
        Graph g = data.getGraph();
        DepthFirstPaths p = new DepthFirstPaths(g, data.start);
        p.dfs(g);
        data.assertCorrect(p);
    }

    @ParameterizedTest
    @MethodSource("orderTestDataStream")
    void testNonrecursiveDFS(OrderTestData data) {
        Graph g = data.getGraph();
        DepthFirstPaths p = new DepthFirstPaths(g, data.start);
        p.nonrecursiveDFS(g);
        data.assertCorrect(p);
    }

    public static class GraphTestData {
        private int size;
        private int[] vertices;

        public GraphTestData(int size, int[] vertices) {
            this.size = size;
            this.vertices = vertices;
            assertTrue(vertices.length % 2 == 0, "Vertices are specified in pairs (e.g. {1,2,3,4} would mean E={(1,2),(2,1),(3,4),(4,3)}) but vertices.length is odd.");
        }

        public Graph getGraph() {
            Graph g = new Graph(this.size);
            for (int i = 0; i < vertices.length; i++) {
                g.addEdge(vertices[i], vertices[++i]);
            }

            return g;
        }

    }
    public static class GraphStartTestData extends GraphTestData{
        public int start;

        public GraphStartTestData(int size, int[] vertices, int start) {
            super(size, vertices);
            this.start = start;
        }
    }
    static class OrderTestData extends GraphStartTestData {
        public int[] preorder;
        public int[] postorder;
        public int[] egdeTo;
        public int[] distTo;

        public OrderTestData(int size, int[] vertices, int start, int[] preorder, int[] postorder, int[] edgeTo, int[] distTo) {
            super(size, vertices, start);
            this.preorder = preorder;
            this.postorder = postorder;
            this.distTo = distTo;
            this.egdeTo = edgeTo;
        }

        public void assertCorrect(DepthFirstPaths p) {
            // check, that the preorder matches the expected result.
            Queue<Integer> pre = p.pre();
            String preErr = "Expected preorder " + Arrays.toString(this.preorder) + " dose not match the output " + pre;
            int i = 0;
            while (!pre.isEmpty()) {
                assertEquals(this.preorder[i++], pre.poll(), preErr);
            }
            assertEquals(this.preorder.length, i, preErr);

            // check, that the postorder matches the expected result.
            Queue<Integer> post = p.post();
            String postErr = "Expected postorder " + Arrays.toString(this.postorder) + " dose not match the output " + post;
            int j = 0;
            while (!post.isEmpty()) {
                assertEquals(this.postorder[j++], post.poll(), postErr);
            }
            assertEquals(this.postorder.length, j, postErr);

            // check, that edgeTo matches the expected result.
            assertArrayEquals(this.egdeTo, p.edge(), "Expected edgeTo " + Arrays.toString(this.egdeTo) + " dose not match the output " + Arrays.toString(p.edge()));

            // check, that distTo matches the expected result.
            assertArrayEquals(this.distTo, p.dist(), "Expected distTo " + Arrays.toString(this.distTo) + " dose not match the output " + Arrays.toString(p.dist()));
        }
    }

    static Stream<OrderTestData> orderTestDataStream() {
        return Stream.of(new OrderTestData[]{
                new OrderTestData(2, new int[]{0, 1}, 0, new int[]{0, 1}, new int[]{1, 0}, new int[]{0, 0}, new int[]{0, 1}),
                new OrderTestData(2, new int[]{0, 0, 0, 1}, 0, new int[]{0, 1}, new int[]{1, 0}, new int[]{0, 0}, new int[]{0, 1}),
                new OrderTestData(2, new int[]{1, 1, 0, 0, 0, 1}, 0, new int[]{0, 1}, new int[]{1, 0}, new int[]{0, 0}, new int[]{0, 1}),
                new OrderTestData(7, new int[]{0, 1, 1, 3, 1, 4, 0, 2, 2, 5, 2, 6}, 0,
                        new int[]{0, 1, 3, 4, 2, 5, 6},
                        new int[]{3, 4, 1, 5, 6, 2, 0},
                        new int[]{0, 0, 0, 1, 1, 2, 2},
                        new int[]{0, 1, 1, 2, 2, 2, 2}),
                new OrderTestData(7, new int[]{0, 1, 1, 3, 1, 4, 0, 2, 2, 5, 2, 6}, 4,
                        new int[]{4, 1, 0, 2, 5, 6, 3},
                        new int[]{5, 6, 2, 0, 3, 1, 4},
                        new int[]{1, 4, 0, 1, 0, 2, 2},
                        new int[]{2, 1, 3, 2, 0, 4, 4}),
        });
    }

    @ParameterizedTest
    @MethodSource("pathTestDataStream")
    void testPathTo(PathTestData data) { // this test assumes that you did not change the oder in which nodes are checked.
        Graph g = data.getGraph();

        DepthFirstPaths p = new DepthFirstPaths(g, data.goal);
        p.dfs(g);
        data.assertCorrect(p.pathTo(data.start));
    }

    public static class GoalTestData extends GraphStartTestData{
        public int goal;

        public GoalTestData(int size, int[] vertices, int start, int goal) {
            super(size, vertices, start);
            this.goal = goal;
        }
    }
    public static class PathTestData extends GoalTestData{

        public int[] path;

        public PathTestData(int size, int[] vertices, int start, int goal, int[] path) {
            super(size, vertices, start, goal);
            this.path = path;
        }

        public void assertCorrect(List<Integer> actual){
            if(actual == null){
                assertTrue(this.path == null);
                return; // test is ok
            }

            String err = "Expected path " + Arrays.toString(this.path) + " dose not match " + actual;

            assertEquals(this.path.length, actual.size(), err);
            for(int i = 0; i < this.path.length; i++){
                assertEquals(this.path[i], actual.get(i), err);
            }
        }
    }

    static Stream<PathTestData> pathTestDataStream(){
        return Stream.of(new PathTestData[]{
                new PathTestData(2, new int[]{}, 0,1, null),
                new PathTestData(1, new int[]{}, 0,0, new int[]{0}),
                new PathTestData(5, new int[]{0,1,1,2,2,3,3,4}, 0,4, new int[]{0,1,2,3,4}),
        });
    }

}

