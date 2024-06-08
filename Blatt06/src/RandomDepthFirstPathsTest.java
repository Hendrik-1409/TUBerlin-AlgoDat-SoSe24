import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class RandomDepthFirstPathsTest {

    @ParameterizedTest
    @MethodSource("pathTestDataStream")
    void testRandomDFS(DepthFirstPathsTest.GoalTestData data) {
        for (int i = 0; i < tries; i++) {
            Graph g = data.getGraph();
            RandomDepthFirstPaths p1 = new RandomDepthFirstPaths(g, data.goal);
            p1.randomDFS(g);
            List<Integer> a1 = p1.pathTo(data.start);

            RandomDepthFirstPaths p2 = new RandomDepthFirstPaths(g, data.goal);
            p2.randomDFS(g);
            List<Integer> a2 = p2.pathTo(data.start);


            if (assertListNotEquals(a1, a2)) {
                return;
            }
        }

        fail("The paths were the same for " + tries + " tries.");
    }

    private static final int tries = 10;

    @ParameterizedTest
    @MethodSource("pathTestDataStream")
    void testRandomNonrecursiveDFS(DepthFirstPathsTest.GoalTestData data) {
        for (int i = 0; i < tries; i++) {
            Graph g = data.getGraph();
            RandomDepthFirstPaths p1 = new RandomDepthFirstPaths(g, data.goal);
            p1.randomNonrecursiveDFS(g);
            List<Integer> a1 = p1.pathTo(data.start);

            RandomDepthFirstPaths p2 = new RandomDepthFirstPaths(g, data.goal);
            p2.randomNonrecursiveDFS(g);
            List<Integer> a2 = p2.pathTo(data.start);

            if (assertListNotEquals(a1, a2)) {
                return;
            }
            ;
        }

        fail("The paths were the same for " + tries + " tries.");
    }

    boolean assertListNotEquals(List a1, List a2) {
        if (a1.size() != a2.size()) {
            return true;
        }

        for (int i = 0; i < a1.size(); i++) {
            if (a1.get(i) != a2.get(i)) {
                return true;
            }
        }

        return false;
    }

    public static Stream<DepthFirstPathsTest.GoalTestData> pathTestDataStream() {
        return Stream.of(new DepthFirstPathsTest.GoalTestData[]{
                new DepthFirstPathsTest.GoalTestData(8, new int[]{0, 1, 0, 2, 0, 5, 3, 7, 4, 3, 5, 6, 4, 1, 1, 7}, 4, 7)
        });
    }
}

