import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Stack;

import static java.lang.Math.abs;

/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    private int[][] board;
    private int freeFields;

    /**
     * Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n) throws InputMismatchException {
        this.n = n;
        if (1 > this.n || this.n > 10) {
            throw new InputMismatchException();
        }
        this.freeFields = n * n;
        this.board = new int[n][n];
    }

    /**
     * @return length/width of the Board object
     */
    public int getN() {
        return n;
    }

    /**
     * @return number of currently free fields
     */
    public int nFreeFields() {
        return this.freeFields;
    }

    /**
     * @return token at position pos
     */
    public int getField(Position pos) throws InputMismatchException {
        if (pos.x < 0 || pos.y < 0 || pos.x >= this.n || pos.y >= this.n) {
            throw new InputMismatchException();
        }
        return this.board[pos.x][pos.y];
    }

    /**
     * Sets the specified token at Position pos.
     */
    public void setField(Position pos, int token) throws InputMismatchException {
        if (pos.x < 0 || pos.y < 0 || pos.x >= this.n || pos.y >= this.n || (token != -1 && token != 1 && token != 0)) {
            throw new InputMismatchException();
        }
        if (token != this.board[pos.x][pos.y] && token != 0 && this.board[pos.x][pos.y] == 0) {
            this.freeFields--;
        }
        else if (token != this.board[pos.x][pos.y] && token == 0) {
            this.freeFields++;
        }
        this.board[pos.x][pos.y] = token;
    }

    /**
     * Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player) throws InputMismatchException {
        /*if (this.board[pos.x][pos.y] != 0) {
            throw new InputMismatchException();
        }*/
        this.setField(pos, player);
    }

    /**
     * Clears board at Position pos.
     */
    public void undoMove(Position pos) {
        this.setField(pos, 0);
    }

    /**
     * @return true if game is won, false if not
     */
    public boolean isGameWon() {
        boolean x1 = true;
        boolean x2 = true;
        int comparex1 = this.board[0][0];
        int comparex2 = this.board[this.n - 1][0];
        if (this.board[0][0] == 0) {
            x1 = false;
        }
        if (this.board[this.n - 1][0] == 0) {
            x2 = false;
        }
        for (int i = 0; i < this.n; i++) {
            boolean thisy = true;
            int camparey = this.board[i][0];
            if (camparey == 0) {
                thisy = false;
            }
            boolean thisx = true;
            int camparex = this.board[0][i];
            if (camparex == 0) {
                thisx = false;
            }
            for (int j = 0; j < this.n; j++) {
                if (camparey != this.board[i][j]) {
                    thisy = false;
                }
                if (camparex != this.board[j][i]) {
                    thisx = false;
                }
                if (j == i && this.board[i][j] != comparex1) {
                    x1 = false;
                }
                if (this.n - 1 - j == i && this.board[i][j] != comparex2) {
                    x2 = false;
                }
            }
            if (thisy || thisx) {
                return true;
            }
        }
        if (x1 || x2) {
            return true;
        }
        return false;
    }

    /**
     * @return set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves() {
        LinkedList<Position> positions = new LinkedList<Position>();
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.board[i][j] == 0) {
                    positions.addLast(new Position(i, j));
                }
            }
        }
        return positions;
    }

    /**
     * Outputs current state representation of the Board object.
     * Practical for debugging.
     */
    public void print() {
        System.out.println("This Board:");
        System.out.print("y  ");
        for (int i = 0; i < this.n; i++) {
            System.out.print((i + 1) + "  ");
        }
        System.out.print("\n");
        for (int y = 0; y < n; y++) {
            System.out.print((y + 1) + ": ");
            for (int x = 0; x < n; x++) {
                System.out.print(this.board[x][y] + "  ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

}
