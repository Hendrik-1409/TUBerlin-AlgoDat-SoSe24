import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ClusterTests {

    @ParameterizedTest
    @MethodSource("findClustersSource")
    public void testFindClustersNum(GraphClusterTestData data) throws InterruptedException {
        Clustering c = new Clustering(data.getGraph());
        c.findClusters(data.getNumberOfClusters());
        data.assertCorrect(c);
    }

    @ParameterizedTest
    @MethodSource("findClustersSource")
    public void testFindClustersThresh(GraphClusterTestData data) throws InterruptedException {
        assumeTrue(data.threshold > 0, "This test case is not applicable to clustering by threshold.");
        Clustering c = new Clustering(data.getGraph());
        c.findClusters(data.threshold);
        data.assertCorrect(c);

    }

    public static Stream<GraphClusterTestData> findClustersSource() {
        return Stream.of(new GraphClusterTestData[]{
                new GraphClusterTestData("graph_small.txt", -1, new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29}}), // klar
                new GraphClusterTestData("graph_small.txt", -1, new int[][]{
                        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
                        {14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29}
                }),// sieht bei .plot richtig aus
                new GraphClusterTestData("graph_small.txt", 0.565, new int[][]{
                        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
                        {14, 15, 16, 17, 18, 19, 20, 21},
                        {22, 23, 24, 25, 26, 27, 28, 29}
                }),
                new GraphClusterTestData("graph_bigger.txt", -1, new int[][]{
                        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37},
                        {38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77}
                }),
                new GraphClusterTestData("graph_bigger.txt", 0.565, new int[][]{
                        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20},
                        {21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37},
                        {38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59},
                        {60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77}
                }),// sieht bei .plot richtig aus
        });
    }

    private static class GraphTestData {
        private String path;

        public GraphTestData(String path) {
            this.path = path;
        }

        public EdgeWeightedGraph getGraph() {
            return new EdgeWeightedGraph(new In(getFile()));
        }

        private File getFile() {
            return new File(path);
        }
    }

    private static class GraphClusterTestData extends GraphTestData {

        public double threshold;
        private int[][] expected;

        public GraphClusterTestData(String path, double threshold, int[][] expected) {
            super(path);
            this.threshold = threshold;
            this.expected = expected;
        }

        public int getNumberOfClusters() {
            return expected.length;
        }

        public void assertCorrect(Clustering c) throws InterruptedException {
            try {
                int clusterNum = 0;
                for (Iterable<Integer> cluster : c.clusters) {
                    int elmNum = 0;
                    for (int elm : cluster) {
                        assertEquals(expected[clusterNum][elmNum], elm, "Cluster number " + clusterNum + " element " + elmNum + " dose not match the expected element. Is your cluster sorted?");
                        elmNum++;
                    }
                    assertEquals(expected[clusterNum].length, elmNum, "Cluster number " + clusterNum + " dose not have the expected length.");
                    clusterNum++;
                }

                assertEquals(this.expected.length, clusterNum, "The number of clusters dose not match.");
            } catch (ArrayIndexOutOfBoundsException ex) {
                this.failCheck("Your cluster dose not match the expected size.", c);
            } catch (AssertionFailedError ex) {
                this.failCheck(ex, c);
            }
        }

        private void failCheck(String s, Clustering c) throws InterruptedException {
            c.plotClusters();
            Thread.sleep(5000);
            fail(s);
        }

        private void failCheck(AssertionError ex, Clustering c) throws InterruptedException {
            c.plotClusters();
            Thread.sleep(5000);
            throw ex;
        }
    }

    @ParameterizedTest
    @MethodSource("coefficientOfVariationTestDataSource")
    public void testCoefficientOfVariation(CoefficientOfVariationTestData data) {
        List<Edge> part = Arrays.asList(data.edges);

        assertEquals(data.expected, (new Clustering(new EdgeWeightedGraph(0))).coefficientOfVariation(part));
    }

    public static Stream<CoefficientOfVariationTestData> coefficientOfVariationTestDataSource(){
        return Stream.of(new CoefficientOfVariationTestData[]{
                new CoefficientOfVariationTestData(new double[]{42,187,1337,Math.E}, 1.4018914120879833), // zufallswerte
                new CoefficientOfVariationTestData(new double[]{}, 0), // wollen die tests so
                new CoefficientOfVariationTestData(new double[]{1,1,1,1}, 0),
                new CoefficientOfVariationTestData(new double[]{1,2,3,4,5,6,7,9,10}, 0.5548257366129916)
        });
    }

    public static class CoefficientOfVariationTestData{
        public Edge[] edges;
        public double expected;

        public CoefficientOfVariationTestData(Edge[] edges, double expected){
            this.edges = edges;
            this.expected = expected;
        }

        public CoefficientOfVariationTestData(double[] weights,double expected ){
            edges = new Edge[weights.length];
            int i = 0;
            for (double w : weights){
                edges[i++] = new Edge(0,0, w);
            }
            this.expected = expected;
        }
    }

    @ParameterizedTest
    @MethodSource("validateTestDataSource")
    public void testValidate(ValidateTestData data) {
        Clustering c = new Clustering(new In(data.path));
        c.findClusters(data.expected.length);
        assertArrayEquals(data.expected, c.validation());
    }

    public static Stream<ValidateTestData> validateTestDataSource() {
        return Stream.of(new ValidateTestData[]{
                new ValidateTestData("iris_small.txt", new int[]{23, 18, 0}),
                new ValidateTestData("iris.txt", new int[]{50, 50, 2}),
        });
    }

    private static class ValidateTestData {
        public String path;
        public int[] expected;

        public ValidateTestData(String path, int[] expected) {
            this.path = path;
            this.expected = expected;
        }
    }
}
