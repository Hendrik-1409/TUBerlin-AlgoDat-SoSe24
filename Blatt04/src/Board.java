import java.util.InputMismatchException;
import java.util.Stack;

import static java.lang.Math.abs;
/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    //TODO
    
    /**
     *  Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n)
    {
        // TODO
    }
    
    /**
     *  @return     length/width of the Board object
     */
    public int getN() { return n; }
    
    /**
     *  @return     number of currently free fields
     */
    public int nFreeFields() {
        // TODO
    }
    
    /**
     *  @return     token at position pos
     */
    public int getField(Position pos) throws InputMismatchException
    {
        // TODO
    }

    /**
     *  Sets the specified token at Position pos.
     */    
    public void setField(Position pos, int token) throws InputMismatchException
    {
        // TODO
    }
    
    /**
     *  Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player)
    {
        // TODO
    }

    /**
     *  Clears board at Position pos.
     */
    public void undoMove(Position pos)
    {
        // TODO
    }
    
    /**
     *  @return     true if game is won, false if not
     */
    public boolean isGameWon() {
        // TODO
    }

    /**
     *  @return     set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves()
    {
        // TODO
    }

    /**
     *  Outputs current state representation of the Board object.
     *  Practical for debugging.
     */
    public void print()
    {
        // TODO
    }

}

