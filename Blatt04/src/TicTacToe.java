import static java.lang.Math.max;
import static java.lang.Math.scalb;

import java.util.LinkedList;

/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     * @return          rating of game situation from player's point of view
     * 
     * https://stackoverflow.com/questions/6416706/easy-way-to-convert-iterable-to-collection
    **/
    public static int alphaBeta(Board board, int player)
    {
        return alphaBeta(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, board.nFreeFields());
    }

    public static int alphaBeta(Board board, int player, int alpha, int beta, int depth) {
        if (board.isGameWon()) {
            return -(board.nFreeFields() + 1);
        }
        else if (depth == 0) {
            return 0;
        }
        int value = Integer.MIN_VALUE;
        for (Position pos : board.validMoves()) {
            if (depth < alpha) {
                return alpha;
            }
            board.doMove(pos, player);
            value = Math.max(value, -alphaBeta(board, -player, -beta, -alpha, depth - 1));
            board.undoMove(pos);
            alpha = Math.max(alpha, value);         
        }
        return value;
    }

    
    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
    **/
    public static void evaluatePossibleMoves(Board board, int player)
    {
        if (board.isGameWon()) {
            System.out.println("Game is over");
            return;
        }
        if (player == 1) {
            System.out.println("Evaluation for player 'x':");
        } else {
            System.out.println("Evaluation for player 'o':");
        }
        for (int y = 0; y < board.getN(); y++) {
            for (int x = 0; x < board.getN(); x++) {
                Position pos = new Position(x, y);
                if (board.getField(pos) == 1) {
                    System.out.print("x ");
                } else if (board.getField(pos) == -1) {
                    System.out.print("o ");
                } else {
                    board.doMove(pos, player);
                    System.out.print(-alphaBeta(board, -player) + " ");
                    board.undoMove(pos);
                }
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args)
    {
        Board board = new Board(4);
        board.doMove(new Position(0, 0), 1);
        board.doMove(new Position(0, 1), 1);
        board.doMove(new Position(1, 0), 1);
        board.print();
        System.out.println(TicTacToe.alphaBeta(board, 1));
    }
}

