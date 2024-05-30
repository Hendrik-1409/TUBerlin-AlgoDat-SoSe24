import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RowOfBowlsTest {
    static class BowlTestData{
        public int expected;
        public int[] data;
        public BowlTestData(int expected, int[] data){
            this.expected = expected;
            this.data = data;
        }
    }

    static Stream<BowlTestData> bowltestSource(){
        return Stream.of(new BowlTestData[]{
                new BowlTestData(0, new int[]{}), // offensichtlich
                new BowlTestData(1, new int[]{1}), // offensichtlich
                new BowlTestData(1, new int[]{1, 2}), // offensichtlich
                new BowlTestData(2, new int[]{1, 2, 3}),  // handsimulation
                new BowlTestData(4, new int[]{4, 7, 2, 3}), // Tut test
                new BowlTestData(1, new int[]{3, 4, 1, 2, 8, 5}), // Tut test
        });
    }

    @ParameterizedTest
    @MethodSource("bowltestSource")
    void testMaxGain(BowlTestData data) {
        RowOfBowls r = new RowOfBowls();
        assertEquals(data.expected, r.maxGain(data.data));
    }

    @ParameterizedTest
    @MethodSource("bowltestSource")
    void testMaxGainRecursiveIntArray(BowlTestData data) {
        RowOfBowls r = new RowOfBowls();
        assertEquals(data.expected, r.maxGainRecursive(data.data));
    }

    boolean matchAny(Iterable<Integer> needle, int[][] haystacks) {
        List<Integer> l = new ArrayList<Integer>();
        needle.forEach(l::add);

        int[] needleAr = new int[l.size()];
        int i = 0;
        for (int n : l) {
            needleAr[i++] = n;
        }

        outer:
        for (int[] haystack : haystacks) {
            if (needleAr.length == haystack.length) {
                for (int j = 0; j < haystack.length; j++) {
                    if (needleAr[j] != haystack[j]) {
                        continue outer;
                    }
                }
                return true;
            }
        }

        return false;
    }

    @Test
    void testOptimalSequence() {
        RowOfBowls r = new RowOfBowls();
        r.maxGain(new int[]{4, 7, 2, 3});
        assertTrue(matchAny(r.optimalSequence(), new int[][]{{3, 0, 1, 2}, {3, 2, 1, 0}}), "The sequence did not match any expected result.");
    }

    int[] randomBowls(int len) {
        int[] bowls = new int[len];
        for (int i = 0; i < len; i++) {
            bowls[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        return bowls;
    }

    @Test
    void testConsistency() {
        long executionTime = 5000;
        long startTime = System.currentTimeMillis();

        int len = 1;
        Random r = new Random();
        RowOfBowls rb = new RowOfBowls();

        while (startTime + executionTime > System.currentTimeMillis()) {
            // generate random "bowls"
            int[] bowls = randomBowls(len);
            // calc output for the sequences
            int rec = rb.maxGainRecursive(bowls);
            int dp = rb.maxGain(bowls);
            // check, that they match
            assertEquals(rec, dp, "Output for random bowls did not match!");
            // check that the output for sequence is ok
            int score = 0;
            boolean sign = true;
            for (int indx : rb.optimalSequence()) {
                if (sign) {
                    score += bowls[indx];
                } else {
                    score -= bowls[indx];
                }

                sign = !sign;
            }

            assertEquals(rec, score, "Sequence resulted in a different score than calcualted.");

            len++;
        }
    }


    long time(Executable calc) throws Throwable {
        long startTime = System.currentTimeMillis();
        calc.execute();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    @Test
    void testSpeedIsNotTooSlow() throws  Throwable {
        int[] bowlN = randomBowls(5000);
        RowOfBowls rb = new RowOfBowls();
        long time = time(() -> {
            rb.maxGain(bowlN);
        });

        assertTrue(time < 5000, "This should not take this long, it took 300ms on my system (:");
    }

    @Test
    void testSpeedIsInOnSquared() throws Throwable {
        int n = 5000;

        int[] bowlN = randomBowls(n);
        RowOfBowls rb = new RowOfBowls();
        long timeForN = time(() -> {
            rb.maxGain(bowlN);
        });

        int[] bowl2N = randomBowls(2*n);
        long timeFor2N = time(() -> {
            rb.maxGain(bowl2N);
        });

        assertTrue(timeForN * timeForN > timeFor2N, "Your algo should approximately run less than n^2 time.");
    }
}

