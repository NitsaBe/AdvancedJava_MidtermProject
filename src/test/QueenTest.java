import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {
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
    public void testQueenConstruction() {
        Queen whiteQueen = new Queen("w");
        Queen blackQueen = new Queen("b");

        assertEquals("w", whiteQueen.getColor());
        assertEquals("b", blackQueen.getColor());
    }

    @Test
    public void testQueenHorizontalMove() {
        // Place a white queen at d1 (7,3)
        Queen whiteQueen = new Queen("w");
        board.setSquare(7, 3, whiteQueen);

        // Test valid horizontal move: d1 to h1 (7,7)
        assertTrue(whiteQueen.isLegalMove(7, 7, board));

        // Verify queen moved
        assertNull(board.getSquare(7, 3));
        assertTrue(board.getSquare(7, 7) instanceof Queen);
    }

    @Test
    public void testQueenVerticalMove() {
        // Place a white queen at d1 (7,3)
        Queen whiteQueen = new Queen("w");
        board.setSquare(7, 3, whiteQueen);

        // Test valid vertical move: d1 to d8 (0,3)
        assertTrue(whiteQueen.isLegalMove(0, 3, board));

        // Verify queen moved
        assertNull(board.getSquare(7, 3));
        assertTrue(board.getSquare(0, 3) instanceof Queen);
    }

    @Test
    public void testQueenDiagonalMove() {
        // Place a white queen at d1 (7,3)
        Queen whiteQueen = new Queen("w");
        board.setSquare(7, 3, whiteQueen);

        // Test valid diagonal move: d1 to h5 (3,7)
        assertTrue(whiteQueen.isLegalMove(3, 7, board));

        // Reset queen position
        board.clearSquare(3, 7);
        board.setSquare(7, 3, whiteQueen);

        // Test valid diagonal move: d1 to a4 (4,0)
        assertTrue(whiteQueen.isLegalMove(4, 0, board));
    }

    @Test
    public void testInvalidQueenMove() {
        // Place a white queen at d1 (7,3)
        Queen whiteQueen = new Queen("w");
        board.setSquare(7, 3, whiteQueen);

        // Test invalid knight-like move
        assertFalse(whiteQueen.isLegalMove(5, 4, board));

        // Test move to occupied square with same color
        board.setSquare(7, 5, new Pawn("w"));
        assertFalse(whiteQueen.isLegalMove(7, 5, board));
    }

    @Test
    public void testQueenBlockedMove() {
        // Place a white queen at d1 (7,3)
        Queen whiteQueen = new Queen("w");
        board.setSquare(7, 3, whiteQueen);

        // Place a blocking piece at d4 (4,3)
        board.setSquare(4, 3, new Pawn("w"));

        // Test blocked vertical move: d1 to d8
        assertFalse(whiteQueen.isLegalMove(0, 3, board));

        // Test unblocked horizontal move: d1 to h1
        assertTrue(whiteQueen.isLegalMove(7, 7, board));
    }

    @Test
    public void testQueenCapture() {
        // Place a white queen at d1 (7,3)
        Queen whiteQueen = new Queen("w");
        board.setSquare(7, 3, whiteQueen);

        // Place a black pawn at d5 (3,3) for capture
        board.setSquare(3, 3, new Pawn("b"));

        // Test capture
        assertTrue(whiteQueen.isLegalCapture(-1, -1, 3, 3, board));

        // Verify queen moved and capture occurred
        assertNull(board.getSquare(7, 3));
        assertTrue(board.getSquare(3, 3) instanceof Queen);
    }

    @Test
    public void testInvalidQueenCapture() {
        // Place a white queen at d1 (7,3)
        Queen whiteQueen = new Queen("w");
        board.setSquare(7, 3, whiteQueen);

        // Place a white pawn at d5 (3,3) - same color, can't capture
        board.setSquare(3, 3, new Pawn("w"));

        // Test invalid capture - same color
        assertFalse(whiteQueen.isLegalCapture(-1, -1, 3, 3, board));

        // Test invalid capture - blocked path
        board.clearSquare(3, 3);
        board.setSquare(5, 3, new Pawn("w"));
        board.setSquare(3, 3, new Pawn("b"));
        assertFalse(whiteQueen.isLegalCapture(-1, -1, 3, 3, board));
    }
}