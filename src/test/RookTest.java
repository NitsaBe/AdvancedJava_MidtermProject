import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RookTest {
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
        // Clear the board for most tests
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }
        // Reset static flags
        Rook.hasMovedBlackY0 = false;
        Rook.hasMovedBlackY7 = false;
        Rook.hasMovedWhiteY0 = false;
        Rook.hasMovedWhiteY7 = false;
    }

    @Test
    public void testRookMovementVertical() {
        // Place a white rook at a1 (7,0)
        board.clearBoard();
        Rook whiteRook = new Rook("w");
        board.setSquare(7, 0, whiteRook);

        // Test vertical movement up
        assertTrue(whiteRook.isLegalMove(3, 0, board));
//        Figurine.move(board, 7, 0, 3, 0);

        // Verify rook moved
        assertNull(board.getSquare(7, 0));
        assertEquals(whiteRook, board.getSquare(3, 0));

        // Test vertical movement down
        assertTrue(whiteRook.isLegalMove(6, 0, board));


        // Verify rook moved
        assertNull(board.getSquare(3, 0));
        assertEquals(whiteRook, board.getSquare(6, 0));
    }

    @Test
    public void testRookMovementHorizontal() {
        // Place a black rook at e8 (0,4)
        Rook blackRook = new Rook("b");
        board.setSquare(0, 4, blackRook);

        // Test horizontal movement right
        assertTrue(blackRook.isLegalMove(0, 7, board));


        // Verify rook moved
        assertNull(board.getSquare(0, 4));
        assertEquals(blackRook, board.getSquare(0, 7));

        // Test horizontal movement left
        assertTrue(blackRook.isLegalMove(0, 2, board));


        // Verify rook moved
        assertNull(board.getSquare(0, 7));
        assertEquals(blackRook, board.getSquare(0, 2));
    }

    @Test
    public void testRookIllegalMove() {
        // Place a white rook at d4 (4,3)
        Rook whiteRook = new Rook("w");
        board.setSquare(4, 3, whiteRook);

        // Test diagonal move (illegal for rook)
        assertFalse(whiteRook.isLegalMove(2, 5, board));

        // Test L-shaped move (illegal for rook)
        assertFalse(whiteRook.isLegalMove(2, 4, board));
    }

    @Test
    public void testRookBlockedPath() {
        // Place a white rook at a1 (7,0)
        Rook whiteRook = new Rook("w");
        board.setSquare(7, 0, whiteRook);

        // Place a blocking piece at a4 (4,0)
        Pawn blockingPawn = new Pawn("w");
        board.setSquare(4, 0, blockingPawn);

        // Test moving past the blocking piece
        assertFalse(whiteRook.isLegalMove(2, 0, board));

        // Test moving to the square right before the blocking piece
        assertTrue(whiteRook.isLegalMove(5, 0, board));
    }

    @Test
    public void testRookCapture() {
        // Place a white rook at e1 (7,4)
        Rook whiteRook = new Rook("w");
        board.setSquare(7, 4, whiteRook);

        // Place a black pawn at e5 (3,4)
        Pawn blackPawn = new Pawn("b");
        board.setSquare(3, 4, blackPawn);

        // Test capture
        assertTrue(whiteRook.isLegalCapture(3, 4, board));

        // Execute capture
        Figurine.move(board, 7, 4, 3, 4);

        // Verify capture succeeded
        assertNull(board.getSquare(7, 4));
        assertEquals(whiteRook, board.getSquare(3, 4));
    }

    @Test
    public void testRookIllegalCapture() {
        // Place a white rook at e1 (7,4)
        Rook whiteRook = new Rook("w");
        board.setSquare(7, 4, whiteRook);

        // Place a white pawn at e5 (3,4) - same color
        Pawn whitePawn = new Pawn("w");
        board.setSquare(3, 4, whitePawn);

        // Test capture - should fail because same color
        assertFalse(whiteRook.isLegalCapture(3, 4, board));

        // Place a black pawn at g3 (5,6) - diagonal position
        Pawn blackPawn = new Pawn("b");
        board.setSquare(5, 6, blackPawn);

        // Test capture - should fail because not in rook's movement path
        assertFalse(whiteRook.isLegalCapture(5, 6, board));
    }

    @Test
    public void testRookDisambiguatedMoveRank() {
        // Place two white rooks on same file: a1 (7,0) and a5 (3,0)
        Rook whiteRook1 = new Rook("w");
        board.setSquare(7, 0, whiteRook1);

        Rook whiteRook2 = new Rook("w");
        board.setSquare(3, 0, whiteRook2);

        // Test disambiguated move (specifying rank 1)
        assertTrue(whiteRook1.isLegalMove(7, -1, 7, 4, board));

        // Test disambiguated move (specifying rank 5)
        assertTrue(whiteRook2.isLegalMove(3, -1, 3, 4, board));
    }

    @Test
    public void testRookDisambiguatedMoveFile() {
        // Place two white rooks on same rank: a1 (7,0) and h1 (7,7)
        Rook whiteRook1 = new Rook("w");
        board.setSquare(7, 0, whiteRook1);

        Rook whiteRook2 = new Rook("w");
        board.setSquare(7, 7, whiteRook2);

        // Test disambiguated move (specifying file a)
        assertTrue(whiteRook1.isLegalMove(-1, 0, 5, 0, board));

        // Test disambiguated move (specifying file h)
        assertTrue(whiteRook2.isLegalMove(-1, 7, 5, 7, board));
    }

    @Test
    public void testRookMovedStatus() {
        // White kingside rook
        board.clearBoard();
        Rook whiteRook = new Rook("w");
        board.setSquare(7, 7, whiteRook);

        // Move rook
        Figurine.move(board, 7, 7, 6, 7);

        // Check if rook moved status is updated
        assertTrue(Rook.hasMovedWhiteY7);
        assertFalse(Rook.hasMovedWhiteY0);
        assertFalse(Rook.hasMovedBlackY0);
        assertFalse(Rook.hasMovedBlackY7);

        // Test hasRookMoved utility method
        assertTrue(Rook.hasRookMoved("w", 7));
        assertFalse(Rook.hasRookMoved("w", 0));
    }

    @Test
    public void testOutOfBoundsMoves() {
        Rook whiteRook = new Rook("w");
        board.setSquare(7, 7, whiteRook);

        // Test moves outside board bounds
        assertFalse(whiteRook.isLegalMove(7, 8, board));
        assertFalse(whiteRook.isLegalMove(8, 7, board));
        assertFalse(whiteRook.isLegalMove(-1, 7, board));
    }
}