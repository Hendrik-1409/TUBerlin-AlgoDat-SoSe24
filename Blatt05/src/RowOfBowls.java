import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    private int[][] solutionMatrix;
    private int[] position;

    public RowOfBowls() {
    }

    /**
     * Implements an optimal game using dynamic programming
     * 
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both
     *         parties play optimally
     */
    public int maxGain(int[] values) {
        if (values.length == 0) {
            return 0;
        }
        if (values.length == 1) {
            return values[0];
        }
        solutionMatrix = new int[values.length][values.length];
        position = new int[] {-1, values.length};
        int result[] = maxGain(values, 0, 0);
        System.out.println(Arrays.toString(values) + Arrays.toString(result));
        return result[0] - result[1];
    }

    private int[] maxGain(int[] bowels, int player1, int player2) {
        if (Math.abs(position[0] - position[1]) <= 1) {
            int[] i = {player1, player2};
            return i;
        }
        position[0]++;
        player1 += bowels[position[0]];
        int[] result = maxGain(bowels, player2, player1);
        player1 -= bowels[position[0]];
        position[0]--;
        position[1]--;
        player1 += bowels[position[1]];
        int[] result2 = maxGain(bowels, player2, player1);
        player1 -= bowels[position[1]];
        position[1]++;
        if (result[1] < result2[1]) {
            int output[] = { result2[1], result2[0] };
            return output;
        } else {
            int output[] = { result[1], result[0] };
            return output;
        }
    }

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
     * maxGain(int values)
     * 
     * @return optimal sequence of chosen bowls (represented by the index in the
     *         values array)
     */
    public Iterable<Integer> optimalSequence() {
        // TODO
    }

    public static void main(String[] args) {
        // For Testing

    }
}
