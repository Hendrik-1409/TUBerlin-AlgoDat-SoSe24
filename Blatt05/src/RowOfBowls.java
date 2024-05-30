import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    public RowOfBowls() {
    }
    
    /**
     * Implements an optimal game using dynamic programming
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values)
    {
        // TODO
    }

    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
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
        int result = maxGainRecursive(bowels, 0, 0)[0];
        System.err.println(bowels.toString() + result);
        return result;
    }

    private int[] maxGainRecursive(LinkedList<Integer> bowels, int player1, int player2) {
        if (bowels.size() == 1) {
            int[] i = {bowels.peekFirst() + player1, player2};
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
            int output[] = {result2[1], result2[0]};
            return output;
        } else {
            int output[] = {result[1], result[0]};
            return output;
        }
    }
    
    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in maxGain(int values)
     * @return optimal sequence of chosen bowls (represented by the index in the values array)
     */
    public Iterable<Integer> optimalSequence()
    {
        // TODO
    }


    public static void main(String[] args)
    {
        // For Testing
        
        }
}

