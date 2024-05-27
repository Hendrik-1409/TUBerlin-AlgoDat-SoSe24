import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;

class BoardTest {
    @Test
    void testBoardSimple() {
        //check that the constructor fails for invalid sizes i.e n \notin [1,10]
        assertThrows(InputMismatchException.class, () -> new Board(0), "Board with size 0 should not be allowed!");
        assertThrows(InputMismatchException.class, () -> new Board(11), "Board with size > 10 should not be allowed!");
    }

    @Test
    void testBoardComplete() {
        for (int i = 1; i <= 10; i++) {
            Board board = new Board(i);
            //test if size has been set correctly
            assertEquals(i, board.getN());

            //ensure board is empty
            assertEquals(i * i, board.nFreeFields());

            //test if the bottom right corner can be accessed -> was the actual size initialized in all directions?
            board.getField(new Position(i - 1, i - 1));
        }

        assertThrows(InputMismatchException.class, () -> new Board(11));
        assertThrows(InputMismatchException.class, () -> new Board(0));
        assertThrows(InputMismatchException.class, () -> new Board(-1));
    }

    @Test
    void testGetNSimple() {
        Board b = new Board(4);
        assertEquals(4, b.getN(), "Board dose not set N.");
    }

    @Test
    void testGetNComplete() {
        //tests if for every board size, the correct number is returned
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, (new Board(i)).getN());
        }
    }

    @Test
    void testNFreeFields() {
        for (int n = 1; n <= 10; n++) {
            Board b = new Board(n);
            assertEquals(n * n, b.nFreeFields(), "Board did not correctly count free spaces!");
        }
    }

    @Test
    void testNFreeFieldsSetMultipleTimes() {
        Board b = new Board(3);
        Position p = new Position(1, 1);
        b.setField(p, 1);
        assertEquals(3 * 3 - 1, b.nFreeFields());
        b.setField(p, 1);
        assertEquals(3*3-1, b.nFreeFields());
        b.setField(p, 0);
        assertEquals(3*3, b.nFreeFields());
        b.setField(p, 0);
        assertEquals(3*3, b.nFreeFields());
    }

    @Test
    void testNFreeFieldsComplex() {
        for (int i = 1; i <= 10; i++) {
            Board board = new Board(i);
            //test if nFreeFields is correct for initial board
            assertEquals(i * i, board.nFreeFields());

            //test if sample fields can be set, with correct changes to nFreeFields

            //set token at position
            Position position = new Position(i - 1, i - 1);
            board.setField(position, 1);
            assertEquals(i * i - 1, board.nFreeFields());

            //set different token at same position
            board.setField(position, -1);
            assertEquals(i * i - 1, board.nFreeFields());

            //set token at different position
            board.setField(new Position(0, 0), -1);
            if (i == 1) {
                //for i = 1, all tokens have been placed at the same position
                assertEquals(0, board.nFreeFields());
            } else {
                assertEquals(i * i - 2, board.nFreeFields());
            }

        }

    }

    @Test
    void testGetFieldSimple() {
        // his test will fail if getField dose not make sure that the position is actually on the field.
        Board b = new Board(4);
        assertThrows(InputMismatchException.class, () -> b.getField(new Position(-1, 3)));
        assertThrows(InputMismatchException.class, () -> b.getField(new Position(4, 3)));
        assertThrows(InputMismatchException.class, () -> b.getField(new Position(3, -1)));
        assertThrows(InputMismatchException.class, () -> b.getField(new Position(3, 4)));

        b.getField(new Position(3, 3));
    }

    @Test
    void testSetField() {
        // This test will fail if setField dose not make sure, that pos is actually on the board
        Board b = new Board(4);

        assertThrows(InputMismatchException.class, () -> b.setField(new Position(-1, 3), 1));
        assertThrows(InputMismatchException.class, () -> b.setField(new Position(4, 3), 1));
        assertThrows(InputMismatchException.class, () -> b.setField(new Position(3, -1), 1));
        assertThrows(InputMismatchException.class, () -> b.setField(new Position(3, 4), 1));

        assertThrows(InputMismatchException.class, () -> b.setField(new Position(2, 2), 5));

        b.setField(new Position(2, 2), 1);
    }


    private static class BoardDoMoveWrapper extends Board {

        public BoardDoMoveWrapper(int n) {
            super(n);
        }

        @Override
        public void setField(Position pos, int token) throws InputMismatchException {
            throw new InputMismatchException("Set field used");
        }
    }

    @Test
    void testDoMove() {
        // Test, that doMove uses setField, because if it dose the impl is also correct.
        Exception e = assertThrows(InputMismatchException.class, () -> {
            Board b = new BoardDoMoveWrapper(4);
            b.doMove(new Position(2, 2), 1);
        });
        assertEquals("Set field used", e.getMessage(), "Use setFiled in doMove !");

        assertThrows(InputMismatchException.class, ()->{
            Position p = new Position(0,0);
            Board b = new Board(3);
            try {
                b.setField(p, 1);
            }catch (Exception f){throw new RuntimeException();}

            b.doMove(p, 1);
        }, "Do move should not be allowed on non empty position!");
    }

    private static class BoardUndoMoveWrapper extends Board {

        public BoardUndoMoveWrapper(int n) {
            super(n);
        }

        @Override
        public void setField(Position pos, int token) throws InputMismatchException {
            throw new InputMismatchException("Set field used");
        }
    }

    @Test
    void testUndoMove() {
        // Test, that undoMove uses setField, because if it dose the impl is also correct.
        Exception e = assertThrows(InputMismatchException.class, () -> {
            Board b = new BoardUndoMoveWrapper(4);
            b.setField(new Position(2, 2), 1);
            b.undoMove(new Position(2, 2));
        }, "undo Move dose not use setField");
        assertEquals("Set field used", e.getMessage(), "Use setField in undoMove !");

        // check that only a pos that is non empty can be "undone"
        assertThrows(InputMismatchException.class, () -> {
            Board b = new Board(3);
            b.undoMove(new Position(0, 0));
        }, "undoMove dose not check if there is a move to be undone");

        Board b = new Board(3);
        b.setField(new Position(0, 0), 1);
        b.undoMove(new Position(0, 0));
    }

    @Test
    public void setAndGet() {
        Board b = new Board(3);
        b.setField(new Position(2, 2), 1);
        assertEquals(1, b.getField(new Position(2, 2)));
    }


    void testWon(int n, String state, boolean expected) {
        Board b = new Board(n);
        BoardLoader.load(b, state);

        assertEquals(expected, b.isGameWon(), "Game \n" + state + "\n Should be " + (expected ? "won" : "lost") + ", but is " + (b.isGameWon() ? "won" : "lost"));
    }

    @Test
    void testIsGameWon() {
        // test some winning configs..
        testWon(3, """
                XXX
                OOO
                   
                """, true);


        testWon(3, """
                XXX
                                
                   
                """, true);

        testWon(3, """
                X
                 X 
                  X
                """, true);

        testWon(3, """
                   
                OOO
                   
                """, true);

        testWon(3, """
                  X
                 X 
                X   
                """, true);

        testWon(3, """
                X
                 O
                  X  
                """, false);

        testWon(3, """
                XOX
                OXO
                OXO   
                """, false);

        testWon(4, """
                XXXO
                """, false);

        testWon(4, "XXXX", true);

        testWon(10, "", false);
    }

    @Test
    void testValidMoves() {
        Board b = new Board(4);
        b.setField(new Position(2, 2), 1);
        Iterable<Position> moves = b.validMoves();

        int count = 0;

        for (Position ignored : moves) {
            count++;
        }

        assertEquals(4 * 4 - 1, count);
    }

    @Test
    void testValidMovesComplex() {
        for (int i = 1; i <= 10; i++) {
            Board board = new Board(i);
            assertNotNull(board.validMoves());
            int counter = 0;
            for (Position pos : board.validMoves()) {
                assertEquals(0, board.getField(pos));
                counter++;
            }
            assertEquals(i * i, counter);

            board.setField(new Position(0, 0), 1);
            if (i > 1) {
                board.setField(new Position(1, 0), 1);
                if (i > 2) {
                    board.setField(new Position(2, 2), 1);
                    board.setField(new Position(0, 2), 1);
                    board.setField(new Position(1, 0), -1);
                }
            }

            counter = 0;
            for (Position pos : board.validMoves()) {
                assertEquals(0, board.getField(pos));
                counter++;
            }

            if (i == 1) {
                assertEquals(0, counter);
            } else if (i == 2) {
                assertEquals(2, counter);
            } else {
                assertEquals(i * i - 4, counter);
            }

        }
    }

    @Test
    void testPrint(){
        //intercept system.out
        ByteArrayOutputStream sysOutStream = new ByteArrayOutputStream();
        PrintStream sout = System.out;
        System.setOut(new PrintStream(sysOutStream));

        //stream for comparison
        ByteArrayOutputStream compOutput = new ByteArrayOutputStream();
        PrintStream compPrint = new PrintStream(compOutput);

        try {

            Board board = new Board(3);
            board.setField(new Position(0, 0), -1);
            board.setField(new Position(0, 1), 1);
            board.setField(new Position(2, 0), -1);
            board.setField(new Position(1, 2), 1);
            board.print();

            compPrint.println("o_o");
            compPrint.println("x__");
            compPrint.println("_x_");
            compPrint.println();

            assertEquals(compOutput.toString(), sysOutStream.toString());

        }finally {
            //restore system.out
            System.setOut(sout);
        }
    }

}


