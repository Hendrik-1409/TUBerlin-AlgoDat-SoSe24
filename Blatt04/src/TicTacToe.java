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
        for (Position pos : board.validMoves()) {
            board.doMove(pos, player);
            int value = -alphaBeta(board, -player, -beta, -alpha, depth - 1);
            board.undoMove(pos);
            if (value >= beta) {
                return value;  // Beta cut-off.
            }
            if (value > alpha) {
                alpha = value;  // Update alpha.
            }
        }
        return alpha;
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
        // TODO
    }

    public static void main(String[] args)
    {
    }
}

