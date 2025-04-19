import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
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
        // Reset king movement tracking
        King.hasMovedWhite = false;
        King.hasMovedBlack = false;
    }

    @Test
    public void testKingConstruction() {
        King whiteKing = new King("w");
        King blackKing = new King("b");

        assertEquals("w", whiteKing.getColor());
        assertEquals("b", blackKing.getColor());
    }

    @Test
    public void testKingMovement() {
        // Place a black king at e7 (1,4)
        King blackKing = new King("b");
        board.setSquare(1, 4, blackKing);

        // Test valid king move: e7 to e6 (2,4)
        assertTrue(blackKing.isLegalMove(2, 4, board));

        // Verify king moved and flagged as moved
        assertNull(board.getSquare(1, 4));
        assertTrue(board.getSquare(2, 4) instanceof King);
        assertTrue(King.hasMovedBlack);
    }

    @Test
    public void testInvalidKingMove() {
        // Place a black king at e7 (1,4)
        King blackKing = new King("b");
        board.setSquare(1, 4, blackKing);

        // Test invalid move: too far away
        assertFalse(blackKing.isLegalMove(3, 4, board));

        // Test invalid move: target square is occupied
        board.setSquare(2, 4, new Pawn("b"));
        assertFalse(blackKing.isLegalMove(2, 4, board));
    }

    @Test
    public void testKingMoveWithStartingPosition() {
        // Place a black king at e7 (1,4)
        King blackKing = new King("b");
        board.setSquare(1, 4, blackKing);

        // Test move with starting position specified (-1, -1 means unspecified)
        assertTrue(blackKing.isLegalMove(-1, -1, 2, 4, board));

        // Test move with invalid starting position
        assertFalse(blackKing.isLegalMove(2, 2, 3, 3, board));
    }

    @Test
    public void testKingCapture() {
        // Place a black king at e7 (1,4)
        King blackKing = new King("b");
        board.setSquare(1, 4, blackKing);

        // Place a white pawn at e6 (2,4)
        board.setSquare(2, 4, new Pawn("w"));

        // Test capture
        assertTrue(blackKing.isLegalCapture(2, 4, board));

        // Verify king moved and capture occurred
        assertNull(board.getSquare(1, 4));
        assertTrue(board.getSquare(2, 4) instanceof King);
    }

    @Test
    public void testInvalidKingCapture() {
        // Place a black king at e7 (1,4)
        King blackKing = new King("b");
        board.setSquare(1, 4, blackKing);

        // Place a black pawn at e6 (2,4) - same color, can't capture
        board.setSquare(2, 4, new Pawn("b"));

        // Test invalid capture - same color
        assertFalse(blackKing.isLegalCapture(2, 4, board));
    }

    @Test
    public void testKingCaptureWithStartingPosition() {
        // Place a black king at e7 (1,4)
        King blackKing = new King("b");
        board.setSquare(1, 4, blackKing);

        // Place a white pawn at e6 (2,4)
        board.setSquare(2, 4, new Pawn("w"));

        // Test capture with starting position
        assertTrue(blackKing.isLegalCapture(-1, -1, 2, 4, board));

        // Test invalid capture with specified starting position
        assertFalse(blackKing.isLegalCapture(0, 0, 2, 4, board));
    }

    @Test
    public void testKingMovedTracking() {
        // Test white king tracking
        King whiteKing = new King("w");
        board.setSquare(7, 4, whiteKing);

        assertFalse(King.hasKingMoved("w"));
        assertTrue(whiteKing.isLegalMove(6, 4, board));
        assertTrue(King.hasKingMoved("w"));

        // Reset and test black king tracking
        King.hasMovedWhite = false;
        King.hasMovedBlack = false;

        King blackKing = new King("b");
        board.setSquare(0, 4, blackKing);

        assertFalse(King.hasKingMoved("b"));
        assertTrue(blackKing.isLegalMove(1, 4, board));
        assertTrue(King.hasKingMoved("b"));
    }
}