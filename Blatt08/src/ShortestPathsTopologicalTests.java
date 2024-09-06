import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathsTopologicalTests {
    private WeightedDigraph generateLargeGraph(int v) {
        WeightedDigraph wd = new WeightedDigraph(v);

        int edged = ThreadLocalRandom.current().nextInt(v * v, v * v * v);
        for (int i = 0; i < edged; i++) {
            wd.addEdge(ThreadLocalRandom.current().nextInt(0, v), ThreadLocalRandom.current().nextInt(0, v), ThreadLocalRandom.current().nextInt(0, 100));
        }

        return wd;
    }

    @Test
    @Timeout(1)
    void testTime() {
        WeightedDigraph wd = generateLargeGraph(320); // this takes no more than 300 ms
        ShortestPathsTopological spt = new ShortestPathsTopological(wd, 0);
    }

    @ParameterizedTest
    @MethodSource("shortestPathDataSource")
    public void testShortestPath(ShortestPathData data) {
        WeightedDigraph wd = data.getWeightedDigraph();
        ShortestPathsTopological spt = new ShortestPathsTopological(wd, data.start);

        data.assertCorrect(spt.pathTo(data.end));
    }

    public static Stream<ShortestPathData> shortestPathDataSource(){
        WeightedDigraphData d1 = new WeightedDigraphData(5, new int[]{0,1,1,1,2,2,2,3,3,3,4,4});
        WeightedDigraphData d2 = new WeightedDigraphData(6, new int[]{0,5,1,5,1,3,5,4,5,4,3,6,3,1,7,1,2,8});
        return Stream.of(new ShortestPathData[]{
                new ShortestPathData(d1, 0, 4, new int[]{0,1,2,3,4}),
                new ShortestPathData(d1, 1, 3, new int[]{1,2,3}),
                new ShortestPathData(d2, 0, 2, new int[]{0,5,1,2}),
                new ShortestPathData(d2, 0, 3, new int[]{0,5,4,3}),
        });
    }

    public static class WeightedDigraphData {
        private int v;
        private int[] edges;

        public WeightedDigraphData(int v, int[] edges) {

            this.v = v;
            this.edges = edges;
        }

        public WeightedDigraph getWeightedDigraph() {
            WeightedDigraph wd = new WeightedDigraph(v);
            for (int i = 0; i < edges.length; i++) {
                wd.addEdge(edges[i], edges[++i], edges[++i]);
            }

            return wd;
        }
    }

    public static class ShortestPathData extends WeightedDigraphData {

        public int start;
        public int end;
        public int[] path;

        public ShortestPathData(int v, int[] edges, int start, int end, int[] path) {
            super(v, edges);
            this.start = start;
            this.end = end;
            this.path = path;
        }

        public ShortestPathData(WeightedDigraphData wd, int start, int end, int[] path) {
            this(wd.v, wd.edges, start, end, path);
        }

        public void assertCorrect(Stack<Integer> in){
            assertEquals(path.length, in.size(), "path has wrong length");

            for(int i = 0; i < path.length; i++){
                assertEquals(path[i], in.pop());
            }
        }
    }
}
