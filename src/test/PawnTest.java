import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {
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
    public void testPawnConstruction() {
        Pawn whitePawn = new Pawn("w");
        Pawn blackPawn = new Pawn("b");

        assertEquals("w", whitePawn.getColor());
        assertEquals("b", blackPawn.getColor());
    }

    @Test
    public void testWhitePawnSingleSquareMove() {
        // Place a white pawn at e2 (6,4)
        Pawn whitePawn = new Pawn("w");
        board.setSquare(6, 4, whitePawn);

        // Test valid move: e2 to e3 (5,4)
        assertTrue(whitePawn.isLegalMove(5, 4, board));

        // Verify pawn moved
        assertNull(board.getSquare(6, 4));
        assertTrue(board.getSquare(5, 4) instanceof Pawn);
    }

    @Test
    public void testBlackPawnSingleSquareMove() {
        // Place a black pawn at e7 (1,4)
        Pawn blackPawn = new Pawn("b");
        board.setSquare(1, 4, blackPawn);

        // Test valid move: e7 to e6 (2,4)
        assertTrue(blackPawn.isLegalMove(2, 4, board));

        // Verify pawn moved
        assertNull(board.getSquare(1, 4));
        assertTrue(board.getSquare(2, 4) instanceof Pawn);
    }

    @Test
    public void testWhitePawnTwoSquareInitialMove() {
        // Place a white pawn at e2 (6,4)
        Pawn whitePawn = new Pawn("w");
        board.setSquare(6, 4, whitePawn);

        // Test valid initial two-square move: e2 to e4 (4,4)
        assertTrue(whitePawn.isLegalMove(4, 4, board));

        // Verify pawn moved
        assertNull(board.getSquare(6, 4));
        assertTrue(board.getSquare(4, 4) instanceof Pawn);
    }

    @Test
    public void testBlackPawnTwoSquareInitialMove() {
        // Place a black pawn at e7 (1,4)
        Pawn blackPawn = new Pawn("b");
        board.setSquare(1, 4, blackPawn);

        // Test valid initial two-square move: e7 to e5 (3,4)
        assertTrue(blackPawn.isLegalMove(3, 4, board));

        // Verify pawn moved
        assertNull(board.getSquare(1, 4));
        assertTrue(board.getSquare(3, 4) instanceof Pawn);
    }

    @Test
    public void testInvalidPawnMoves() {
        // Place a white pawn at e3 (5,4) - not in initial position
        Pawn whitePawn = new Pawn("w");
        board.setSquare(5, 4, whitePawn);

        // Test invalid two-square move from non-initial position: e3 to e5
        assertFalse(whitePawn.isLegalMove(3, 4, board));

        // Test invalid move to occupied square
        board.setSquare(4, 4, new Pawn("b"));
        assertFalse(whitePawn.isLegalMove(4, 4, board));

        // Test invalid diagonal move without capture
        assertFalse(whitePawn.isLegalMove(4, 5, board));
    }

    @Test
    public void testPawnBlockedMove() {
        // Place a white pawn at e2 (6,4)
        Pawn whitePawn = new Pawn("w");
        board.setSquare(6, 4, whitePawn);

        // Place a blocking piece at e3 (5,4)
        board.setSquare(5, 4, new Pawn("b"));

        // Test blocked single move
        assertFalse(whitePawn.isLegalMove(5, 4, board));

        // Test blocked two-square move
        assertFalse(whitePawn.isLegalMove(4, 4, board));
    }

    @Test
    public void testPawnCapture() {
        // Place a white pawn at e4 (4,4)
        Pawn whitePawn = new Pawn("w");
        board.setSquare(4, 4, whitePawn);

        // Place a black pawn at d5 (3,3) for capture
        board.setSquare(3, 3, new Pawn("b"));

        // Test diagonal capture with file disambiguation
        assertTrue(whitePawn.isLegalCapture(-1, 4, 3, 3, board));

        // Verify pawn moved and capture occurred
        assertNull(board.getSquare(4, 4));
        assertTrue(board.getSquare(3, 3) instanceof Pawn);
        assertEquals("w", board.getSquare(3, 3).getColor());
    }

    @Test
    public void testInvalidPawnCapture() {
        // Place a white pawn at e4 (4,4)
        Pawn whitePawn = new Pawn("w");
        board.setSquare(4, 4, whitePawn);

        // Place a white pawn at d5 (3,3) - same color, can't capture
        board.setSquare(3, 3, new Pawn("w"));

        // Test invalid capture - same color
        assertFalse(whitePawn.isLegalCapture(-1, 4, 3, 3, board));

        // Test invalid capture - too far away
        board.clearSquare(3, 3);
        board.setSquare(2, 3, new Pawn("b"));
        assertFalse(whitePawn.isLegalCapture(-1, 4, 2, 3, board));

        // Test invalid capture - wrong file
        board.setSquare(3, 2, new Pawn("b"));
        assertFalse(whitePawn.isLegalCapture(-1, 4, 3, 2, board));
    }
}