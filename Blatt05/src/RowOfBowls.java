import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    private int[][] solutionMatrix;
    //private int[] position;
    private int[] values;

    public RowOfBowls() {
    }

    /**
     * Implements an optimal game using dynamic programming.
     * 
     * The algorithm stores the maximum gain for each pair of starting and ending
     * positions in the <code>solutionMatrix</code>.
     * 
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both
     *         parties play optimally
     */
    public int maxGain(int[] values) {
        this.values = values;
        int n = values.length;
        solutionMatrix = new int[n][n];
        // Base cases
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return values[0];
        }
        // Store the result of the base cases in the solution matrix
        for (int i = 0; i < n; i++) {
            solutionMatrix[i][i] = values[i];
        }
        // Calculate the maximum gain for each pair of starting and ending positions
        // and store it in the solution matrix
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                int k = j + i - 1;
                // Calculate the maximum gain for picking the leftmost and rightmost bowls
                int pickLeft = values[j] - solutionMatrix[j + 1][k];
                int pickRight = values[k] - solutionMatrix[j][k - 1];
                // Store the maximum gain in the solution matrix
                solutionMatrix[j][k] = Math.max(pickLeft, pickRight);
            }
        }
        // Return the maximum gain for the first player
        return solutionMatrix[0][n - 1];
        /*solutionMatrix = new int[values.length + 1][values.length + 1][2];
-        position = new int[] {0, values.length - 1};
-        int result[] = maxGain(values, 0, 0);
-        System.out.println(Arrays.toString(values) + Arrays.toString(result));
-        return result[0] - result[1];*/
    }

    /*
    this function was reformated and rewritten by CoediumAI and ChatGPT. Ultimatly Abandoned 
    private int[] maxGain(int[] bowels, int player1, int player2) {
        int[] result, result2;
        System.out.println("maxGain called with position " + position[0] + ", " + position[1]);
        if (solutionMatrix[position[0]][position[1]][0] == 0 &&
            solutionMatrix[position[0]][position[1]][1] == 0) {

            System.out.println("Calculating for position " + position[0] + ", " + position[1]);

            if (Math.abs(position[0] - position[1]) < 1) {
                int[] i = {player1 + bowels[position[0]], player2};
                solutionMatrix[position[0]][position[1]] = i;
                System.out.println("Solution: " + Arrays.toString(i));
                return i;
            }

            position[0]++;
            result = maxGain(bowels, player2, player1);
            position[0]--;
            result[1] += bowels[position[0]];

            position[1]--;
            result2 = maxGain(bowels, player2, player1);
            position[1]++;
            result2[1] += bowels[position[1]];

            if (result[1] < result2[1]) {
                int output[] = {result2[1], result2[0]};
                solutionMatrix[position[0]][position[1]] = output;
                System.out.println("Solution: " + Arrays.toString(output));
                return output;
            } else {
                int output[] = {result[1], result[0]};
                solutionMatrix[position[0]][position[1]] = output;
                System.out.println("Solution: " + Arrays.toString(output));
                return output;
            }
        } else {
            System.out.println("Returning cached solution for position " + position[0] + ", " + position[1]);
            return solutionMatrix[position[0]][position[1]];
        }
    }*/


    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both
     *         parties play optimally
     */
    public int maxGainRecursive(int[] values) {
        if (values.length == 0) {
            return 0;
        }
        if (values.length == 1) {
            return values[0];
        }
        LinkedList<Integer> bowels = new LinkedList<Integer>();
        for (int i : values) {
            bowels.add(i);
        }
        int result[] = maxGainRecursive(bowels, 0, 0);
        System.out.println(bowels.toString() + Arrays.toString(result));
        return result[0] - result[1];
    }

    private int[] maxGainRecursive(LinkedList<Integer> bowels, int player1, int player2) {
        if (bowels.size() == 1) {
            int[] i = { bowels.peekFirst() + player1, player2 };
            return i;
        }
        int tmp = bowels.removeFirst();
        player1 += tmp;
        int[] result = maxGainRecursive(bowels, player2, player1);
        player1 -= tmp;
        bowels.addFirst(tmp);
        tmp = bowels.removeLast();
        player1 += tmp;
        int[] result2 = maxGainRecursive(bowels, player2, player1);
        bowels.addLast(tmp);
        if (result2[1] > result[1]) {
            int output[] = { result2[1], result2[0] };
            return output;
        } else {
            int output[] = { result[1], result[0] };
            return output;
        }
    }

    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in
     * maxGain(int values) and stored in solutionMatrix.
     * 
     * @return optimal sequence of chosen bowls (represented by the index in the
     *         values array)
     */
    public Iterable<Integer> optimalSequence() {
        List<Integer> sequence = new ArrayList<>();
        int i = 0; // Index of current leftmost bowl
        int j = values.length - 1; // Index of current rightmost bowl

        while (i <= j) {
            int remainingLeft;
            if (i + 1 <= j) {
                remainingLeft = solutionMatrix[i + 1][j];
            } else {
                remainingLeft = 0;
            }

            int remainingRight;
            if (i <= j - 1) {
                remainingRight = solutionMatrix[i][j - 1];
            } else {
                remainingRight = 0;
            }

            int pickLeft = values[i] - remainingLeft;
            int pickRight = values[j] - remainingRight;

            // Here we are at a point where we have two possible ways to continue:
            // We can either pick the leftmost bowl or the rightmost one.
            // We want to pick the one that will leave the other player with the least amount of points.
            if (pickLeft >= pickRight) {
                // If picking the leftmost bowl leaves the other player with the least amount of points,
                // then we pick the leftmost one.
                sequence.add(i);
                i++;
            } else {
                // If picking the rightmost one leaves the other player with the least amount of points,
                // then we pick the rightmost one.
                sequence.add(j);
                j--;
            }
        }

        return sequence;
    }

    public static void main(String[] args) {
        // For Testing

    }
}

//Some comments generated by CodeiumAI
