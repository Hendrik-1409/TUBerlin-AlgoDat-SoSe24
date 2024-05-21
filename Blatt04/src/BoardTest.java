import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BoardTest {
	Board board;
	int n;

	void init() {
		n = 5;
		board = new Board(this.n);
	}

	@Test
	void testBoard() {
		init();
	}

	@Test
	void testGetN() {
		fail("Not yet implemented");
	}

	@Test
	void testNFreeFields() {
		fail("Not yet implemented");
	}

	@Test
	void testGetField() {
		fail("Not yet implemented");
	}

	@Test
	void testSetField() {
		fail("Not yet implemented");
	}

	@Test
	void testDoMove() {
		fail("Not yet implemented");
	}

	@Test
	void testUndoMove() {
		fail("Not yet implemented");
	}

	@Test
	void testIsGameWon() {
		fail("Not yet implemented");
	}

	@Test
	void testValidMoves() {
		fail("Not yet implemented");
	}

	@Test
	void testPrint() {
		init();
		board.print();
	}

}
