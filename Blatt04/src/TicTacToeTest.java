import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class TicTacToeTest {

    @Test
    void test1x1() {
        // Handsimulierte bsp
        Board b = new Board(1);
        assertEquals(1, TicTacToe.alphaBeta(b, 1));
        assertEquals(1, TicTacToe.alphaBeta(b, -1));
    }

    @Test
    void test2x2() {
        Board b = new Board(2);
        assertEquals(2, TicTacToe.alphaBeta(b, 1));
    }

    @Test
    void testTutBsp() {
        Board b = new Board(3);
        //tut bsp
        BoardLoader.load(b, "XOO\n X \nX O");
        assertEquals(3, TicTacToe.alphaBeta(b, 1));
    }

    @Test
    void bspBlatt(){
        Board b = new Board(3);
        BoardLoader.load(b, " O \n   \n  X");
        assertEquals(3, TicTacToe.alphaBeta(b, 1));
    }

    // Rrandomly generated games checked against my output
    @Test
    void testRandom0() {
        Board b = new Board(3);
        BoardLoader.load(b, "   \n   \nOO ");
        assertEquals(-4, TicTacToe.alphaBeta(b, 1));
        assertEquals(7, TicTacToe.alphaBeta(b, -1));
    }

    @Test
    void testRandom1() {
        Board b = new Board(3);
        BoardLoader.load(b, "XO\n   \n   ");
        assertEquals(3, TicTacToe.alphaBeta(b, 1));
        assertEquals(0, TicTacToe.alphaBeta(b, -1));
    }

    @Test
    void testRandom2() {
        Board b = new Board(3);
        BoardLoader.load(b, "X  \n  O\n OX");
        assertEquals(5, TicTacToe.alphaBeta(b, 1));
        assertEquals(3, TicTacToe.alphaBeta(b, -1));
    }

    @Test
    void testRandom3() {
        Board b = new Board(3);
        BoardLoader.load(b, " O \n OX\n  X");
        assertEquals(5, TicTacToe.alphaBeta(b, 1));
        assertEquals(5, TicTacToe.alphaBeta(b, -1));
    }

    @Test
    void testRandom4() {
        Board b = new Board(3);
        BoardLoader.load(b, " XX\n   \n  X");
        assertEquals(6, TicTacToe.alphaBeta(b, 1));
        assertEquals(-5, TicTacToe.alphaBeta(b, -1));
    }

    @Test
    void testRandom5(){
        Board b = new Board(3);
        BoardLoader.load(b, "XO \nO  \n   ");
        assertEquals(0, TicTacToe.alphaBeta(b, 1));
        assertEquals(4, TicTacToe.alphaBeta(b, -1));
    }

    // This test will take forever, if pruning is not used
    @Test
    void testRandom6(){
        Board b = new Board(4);
        BoardLoader.load(b, "XXX ");
        assertEquals(13, TicTacToe.alphaBeta(b, 1));
        assertEquals(0, TicTacToe.alphaBeta(b, -1));
    }

    @Test
    void testEvaluatePossibleMoves() {
        // if the above test is ok then we only need to check one output, if user did not reimplement the evaluation.
        ByteArrayOutputStream sysOutStream = new ByteArrayOutputStream();
        ByteArrayOutputStream compOutput = new ByteArrayOutputStream();
        PrintStream compPrint = new PrintStream(compOutput);

        System.setOut(new PrintStream(sysOutStream));

        Board b = new Board(3);
        BoardLoader.load(b, " O \n   \n  X");

        // copy board in tmp board
        Board b_tmp = new Board(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                b_tmp.setField(new Position(j, i), b.getField(new Position(j, i)));
            }
        }

        TicTacToe.evaluatePossibleMoves(b, 1);

        compPrint.println("Evaluation for player 'x':");
        compPrint.println("0 o 3");
        compPrint.println("0 3 -2");
        compPrint.println("3 0 x");

        assertEquals(compOutput.toString(), sysOutStream.toString());

        // make sure that board class-variable doesn't get changed,
        // because else your doMove() (if it can) throws Exception
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(b_tmp.getField(new Position(j, i)), b.getField(new Position(j, i)),
                        "Don't change your class-variable of board, " +
                                "because AlgoDat-Team uses it multiple times");
            }
        }
    }

    @Test
    void testEvaluatePossibleMoves2() {
        // if the above test is ok then we only need to check one output, if user did not reimplement the evaluation.
        ByteArrayOutputStream sysOutStream = new ByteArrayOutputStream();
        ByteArrayOutputStream compOutput = new ByteArrayOutputStream();
        PrintStream compPrint = new PrintStream(compOutput);

        System.setOut(new PrintStream(sysOutStream));

        Board b = new Board(3);
        BoardLoader.load(b, "   \n   \nOO ");
        TicTacToe.evaluatePossibleMoves(b, 1);

        compPrint.println("Evaluation for player 'x':");
        compPrint.println("-6 -6 -6");
        compPrint.println("-6 -6 -6");
        compPrint.println("o o -4");

        assertEquals(compOutput.toString(), sysOutStream.toString());
    }

}


