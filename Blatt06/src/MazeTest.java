import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class MazeTest {
    static class P {
        public int v;
        public int w;

        public P(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    @ParameterizedTest
    @MethodSource("invalidPosPair")
    void testInvalidAddEdge(P p) { // this test mostly makes sure that Grap.addEdge was used. It allso checks that (x,x) is not allowed to be added.
        Maze m = new Maze(4, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            m.addEdge(p.v, p.w);
        }, "(" + p.v + ", " + p.w + ") should not be valid.");
    }

    static Stream<P> invalidPosPair() {
        return Stream.of(new P[]{
                new P(-1, 0), new P(0, -1), new P(-1, -1)
        });
    }

    @ParameterizedTest
    @MethodSource("validPosPair")
    void testValidAddEdge(P p) {
        Maze m = new Maze(4, 0);
        m.addEdge(p.v, p.w);
    }

    static Stream<P> validPosPair() {
        return Stream.of(new P[]{
                new P(1, 2), new P(2, 1), new P(3, 2)
        });
    }


    @Test
    void testHasEdgeRefl() {
        Maze m = new Maze(4, 0);
        assertTrue(m.hasEdge(1, 1));
        assertTrue(m.hasEdge(3, 3));
    }


    @Test
    void testHasEdgeOverrun() {
        Maze m = new Maze(1, 0);
        assertFalse(m.hasEdge(10, 4));
    }

    @Test
    void testHashEdgeWithAdd() {
        Maze m = new Maze(3, 0);
        m.addEdge(1, 2);
        assertTrue(m.hasEdge(2, 1));
        assertFalse(m.hasEdge(2, 3));
    }

    @Test
    void testMazegrid() throws IOException, InterruptedException {
        int N = 4;
        Maze m = new Maze(N, 0);
        Graph g = m.mazegrid();
        assertNotEquals(g, m.M(), "mazegrid should create a new Graph!");
        try {
            for (int y = 0; y < N; y++) {
                // verbindungen nach oben
                if (y != 0) {
                    for (int x = 0; x < N; x++) {
                        assertTrue(g.adj(x + y * N).contains(x + N * (y - 1)));
                    }
                }

                // verbindungen nach oben
                if (y != N - 1) {
                    for (int x = 0; x < N; x++) {
                        assertTrue(g.adj(x + y * N).contains((x + N * (y + 1))));
                    }
                }

                // verbindungen rechts nach links
                for (int x = 0; x < N - 1; x++) {
                    assertTrue(g.adj(x + y * N).contains(x + 1 + y * N));
                }
            }
        } catch (AssertionFailedError ex) {
            GridGraph gg = new GridGraph(g);
            gg.plot();
            Thread.sleep(2000);
            fail("You missed a vertex....");
        }
    }

    @Test
    void StudentHatAddEdgeSoVerstandenWieGedachtUndNichtWieGeschrieben(){
        Maze m = new Maze(10,0);
        m.addEdge(1,1); // soll gehen
        m.addEdge(1,1); // auch wiederholtes hinzufügen soll gehen.
    }

    @Test
    void testMazegridCount() {
        for (int n = 2; n < 10; n++) {
            Maze m = new Maze(n, 0);
            Graph g = m.mazegrid();

            // check, that a node at a corner has 2 neighbours
            assertEquals(2, g.adj(0).size()); // top left
            assertEquals(2, g.adj(n - 1).size()); // top right
            assertEquals(2, g.adj(n * n - n).size()); // bottom left
            assertEquals(2, g.adj(n * n - 1).size()); //bottom right
            // check, that a node at on an edge has 3 neighbours
            for (int i = 1; i < n - 1; i++) {
                assertEquals(3, g.adj(i).size()); // top
                assertEquals(3, g.adj(n * i).size()); // left
                assertEquals(3, g.adj(n * i + n - 1).size()); // right
                assertEquals(3, g.adj(n * n - n + i).size()); // bottom
            }
            // check, that a node in the middle has 4 neighbours
            for (int x = 1; x < n - 1; x++) {
                for (int y = 1; y < n - 1; y++) {
                    assertEquals(4, g.adj(x + y * n).size());
                }
            }
        }
    }

    @Test
    void testBuildMaze() throws InterruptedException {
        int size = 8;
        int startnode = 4;
        // A maze is "ok", if every node is reachable from the startnode.
        Maze m = new Maze(size, startnode);
        Graph g = m.M();

        // check, that each node is reachable
        DepthFirstPaths dfp = new DepthFirstPaths(g, startnode);
        dfp.dfs(g);
        try {
            for (int i = 0; i < size * size; i++) {
                if (i != startnode) {
                    assertTrue(dfp.hasPathTo(i));
                }
            }
        } catch (AssertionFailedError e) {
            GridGraph gg = new GridGraph(g);
            Thread.sleep(5000);
            fail("Not every node is reachable.");
        }
    }

    class MazeExt extends Maze {

        public MazeExt(int N, int startnode) {
            super(N, startnode);
        }

        public Graph getGraphPublic() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method innerMethod = Maze.class.getDeclaredMethod("M");
            innerMethod.setAccessible(true);
            return (Graph) innerMethod.invoke(this);
        }
    }

    @Test
    void testBuildMazeRightEdgeCount() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InterruptedException {
        for (int i = 0; i < 10; i++) { // since the maze is random better check it more than once
            for (int size = 2; size < 10; size++) {
                int startnode = ThreadLocalRandom.current().nextInt(0, size * size - 1);
                MazeExt m = new MazeExt(size, startnode);
                int edgesInMaze = m.getGraphPublic().E();


                assertEquals(size * size - 1, edgesInMaze);
            }
        }
    }

    @Test
    void testFindWay() {
        // as long as DepthFirstPaths or RandomDepthFirstPaths was used this should be correct
        Maze m = new Maze(4, 0);
        // but lets perform some sanity checks...
        assertTrue(m.findWay(0, 1).size() > 0);
        assertNotNull(m.findWay(0, 0));
        assertEquals(4, m.findWay(4, 2).get(0));
    }
}
