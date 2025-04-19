import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
        // Clear the board for clean testing
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.setSquare(i, j, null);
            }
        }
    }

    @Test
    public void testKnightConstruction() {
        Knight whiteKnight = new Knight("w");
        Knight blackKnight = new Knight("b");

        assertEquals("w", whiteKnight.getColor());
        assertEquals("b", blackKnight.getColor());
    }

    @Test
    public void testKnightMovement() {
        // Place a white knight at b1 (7,1)
        Knight whiteKnight = new Knight("w");
        board.setSquare(7, 1, whiteKnight);

        // Test valid knight moves
        assertTrue(whiteKnight.isLegalMove(5, 2, board)); // b1 to c3

        // Reset knight position
        board.clearSquare(5, 2);
        board.setSquare(7, 1, whiteKnight);

        assertTrue(whiteKnight.isLegalMove(5, 0, board)); // b1 to a3
    }

    @Test
    public void testInvalidKnightMove() {
        // Place a white knight at b1 (7,1)
        Knight whiteKnight = new Knight("w");
        board.setSquare(7, 1, whiteKnight);

        // Test invalid moves (non-knight moves)
        assertFalse(whiteKnight.isLegalMove(6, 1, board)); // straight line
        assertFalse(whiteKnight.isLegalMove(6, 2, board)); // diagonal
        assertFalse(whiteKnight.isLegalMove(4, 1, board)); // too far straight

        // Test target square occupied by same color
        board.setSquare(5, 2, new Pawn("w"));
        assertFalse(whiteKnight.isLegalMove(5, 2, board));
    }

    @Test
    public void testKnightMoveWithFileDisambiguation() {
        // Place two white knights that could both move to the same square
        Knight knight1 = new Knight("w");
        Knight knight2 = new Knight("w");
        board.setSquare(5, 1, knight1); // b3
        board.setSquare(5, 5, knight2); // f3

        // Test move with file disambiguation (Nbd5)
        assertTrue(knight1.isLegalMove(-1, 1, 4, 3, board)); // knight at b-file to d5

        // Verify knight moved
        assertNull(board.getSquare(5, 1));
        assertTrue(board.getSquare(4, 3) instanceof Knight);
    }

    @Test
    public void testKnightMoveWithRankDisambiguation() {
        // Place two white knights that could both move to the same square
        Knight knight1 = new Knight("w");
        Knight knight2 = new Knight("w");
        board.setSquare(3, 1, knight1); // b5
        board.setSquare(7, 1, knight2); // b1

        // Test move with rank disambiguation (N1c3)
        assertTrue(knight2.isLegalMove(7, -1, 5, 2, board)); // knight at rank 1 to c3

        // Verify knight moved
        assertNull(board.getSquare(7, 1));
        assertTrue(board.getSquare(5, 2) instanceof Knight);
    }

    @Test
    public void testKnightCapture() {
        // Place a white knight at b1 (7,1)
        Knight whiteKnight = new Knight("w");
        board.setSquare(7, 1, whiteKnight);

        // Place a black pawn at c3 (5,2)
        board.setSquare(5, 2, new Pawn("b"));

        // Test capture
        assertTrue(whiteKnight.isLegalCapture(5, 2, board));

        // Verify knight moved and capture occurred
        assertNull(board.getSquare(7, 1));
        assertTrue(board.getSquare(5, 2) instanceof Knight);
    }

    @Test
    public void testInvalidKnightCapture() {
        // Place a white knight at b1 (7,1)
        Knight whiteKnight = new Knight("w");
        board.setSquare(7, 1, whiteKnight);

        // Place a white pawn at c3 (5,2) - same color, can't capture
        board.setSquare(5, 2, new Pawn("w"));

        // Test invalid capture - same color
        assertFalse(whiteKnight.isLegalCapture(5, 2, board));

        // Test invalid capture - empty square
        board.clearSquare(5, 2);
        assertFalse(whiteKnight.isLegalCapture(5, 2, board));
    }

    @Test
    public void testKnightCaptureWithDisambiguation() {
        // Place two white knights that could both capture the same piece
        Knight knight1 = new Knight("w");
        Knight knight2 = new Knight("w");
        board.setSquare(5, 1, knight1); // b3
        board.setSquare(5, 5, knight2); // f3

        // Place a black pawn at d4 (4,3)
        board.setSquare(4, 3, new Pawn("b"));

        // Test capture with file disambiguation (Nbxd4)
        assertTrue(knight1.isLegalCapture(-1, 1, 4, 3, board));

        // Verify knight moved and capture occurred
        assertNull(board.getSquare(5, 1));
        assertTrue(board.getSquare(4, 3) instanceof Knight);
    }
}