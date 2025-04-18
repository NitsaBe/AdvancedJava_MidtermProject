
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    @Test
    public void testBishopConstruction() {
        Bishop whiteBishop = new Bishop("w");
        Bishop blackBishop = new Bishop("b");

        assertEquals("w", whiteBishop.getColor());
        assertEquals("b", blackBishop.getColor());
    }

    @Test
    public void testIsLegalMoveEmptyBoard() {
        // Create a custom board with only bishops
        Board board = new Board();

        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }

        // Place white bishop at e4 (position 4,4)
        Bishop whiteBishop = new Bishop("w");
        board.setSquare(4, 4, whiteBishop);

        // Test diagonal moves in all directions
        // Up-right diagonal
        assertTrue(whiteBishop.isLegalMove(3, 5, board));
        assertTrue(whiteBishop.isLegalMove(2, 6, board));
        assertTrue(whiteBishop.isLegalMove(1, 7, board));

        // Up-left diagonal
        board.setSquare(4, 4, whiteBishop);
        assertTrue(whiteBishop.isLegalMove(3, 3, board));
        assertTrue(whiteBishop.isLegalMove(2, 2, board));
        assertTrue(whiteBishop.isLegalMove(1, 1, board));
        assertTrue(whiteBishop.isLegalMove(0, 0, board));

        // Down-right diagonal
        assertTrue(whiteBishop.isLegalMove(5, 5, board));
        assertTrue(whiteBishop.isLegalMove(6, 6, board));
        assertTrue(whiteBishop.isLegalMove(7, 7, board));

        // Down-left diagonal
        assertTrue(whiteBishop.isLegalMove(5, 3, board));
        assertTrue(whiteBishop.isLegalMove(6, 2, board));
        assertTrue(whiteBishop.isLegalMove(7, 1, board));

        // Test non-diagonal move (should fail)
        assertFalse(whiteBishop.isLegalMove(4, 5, board));
        assertFalse(whiteBishop.isLegalMove(3, 4, board));
    }

    @Test
    public void testIsLegalMoveWithPieceBlocking() {
        Board board = new Board();

        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }

        // Place white bishop at e4 (position 4,4)
        Bishop whiteBishop = new Bishop("w");
        board.setSquare(4, 4, whiteBishop);

        // Place blocking pieces
        Pawn blockingPawn1 = new Pawn("w");
        board.setSquare(2, 6, blockingPawn1); // Block up-right path

        Pawn blockingPawn2 = new Pawn("b");
        board.setSquare(6, 2, blockingPawn2); // Block down-left path

        // Test moves that should be blocked
        assertFalse(whiteBishop.isLegalMove(1, 7, board)); // Blocked by own pawn
        assertFalse(whiteBishop.isLegalMove(7, 1, board)); // Blocked by opponent's pawn

        // Moves that should still be valid
        assertTrue(whiteBishop.isLegalMove(3, 5, board)); // One square before blockage
        assertTrue(whiteBishop.isLegalMove(5, 3, board)); // One square before blockage
    }

    @Test
    public void testIsLegalCapture() {
        Board board = new Board();

        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }

        // Place white bishop at e4 (position 4,4)
        Bishop whiteBishop = new Bishop("w");
        board.setSquare(4, 4, whiteBishop);

        // Place opponent pieces for capture
        Pawn blackPawn1 = new Pawn("b");
        board.setSquare(2, 6, blackPawn1); // Up-right diagonal

        Pawn blackPawn2 = new Pawn("b");
        board.setSquare(6, 2, blackPawn2); // Down-left diagonal

        // Place own piece (cannot capture)
        Pawn whitePawn = new Pawn("w");
        board.setSquare(2, 2, whitePawn); // Up-left diagonal

        // Test captures
        assertTrue(whiteBishop.isLegalCapture(-1, -1, 2, 6, board)); // Should capture opponent's pawn
        assertTrue(whiteBishop.isLegalCapture(-1, -1, 6, 2, board)); // Should capture opponent's pawn

        // Test invalid captures
        assertFalse(whiteBishop.isLegalCapture(-1, -1, 2, 2, board)); // Cannot capture own piece
        assertFalse(whiteBishop.isLegalCapture(-1, -1, 5, 5, board)); // No piece to capture
    }

    @Test
    public void testDisambiguatedMoves() {
        Board board = new Board();

        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }

        // Place two white bishops that could potentially move to the same square
        Bishop bishop1 = new Bishop("w");
        board.setSquare(4, 4, bishop1); // c6

        Bishop bishop2 = new Bishop("w");
        board.setSquare(0, 6, bishop2); // e8

        // Both bishops can move to d5 (3,3)
        board.clearSquare(3, 3);

        // Test with file disambiguation
//        assertTrue(bishop1.isLegalMove(2, -1, 3, 3, board)); // Bishop on file c to d5
//        assertTrue(bishop2.isLegalMove(0, -1, 3, 3, board)); // Bishop on rank 8 to d5

        // Test with rank disambiguation
//        assertTrue(bishop1.isLegalMove(-1, 2, 3, 3, board)); // Bishop on rank 6 to d5
        assertTrue(bishop2.isLegalMove(-1, 4, 3, 3, board)); // Bishop on file e to d5
    }

    @Test
    public void testBishopMoveThroughPieces() {
        Board board = new Board();

        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }

        // Place white bishop at a1 (position 7,0)
        Bishop whiteBishop = new Bishop("w");
        board.setSquare(7, 0, whiteBishop);

        // Place blocking piece at c3
        Pawn blockingPawn = new Pawn("w");
        board.setSquare(5, 2, blockingPawn);

        // Test move to h8 (which should be blocked)
        assertFalse(whiteBishop.isLegalMove(0, 7, board));

        // Test move to b2 (which should be valid)
        assertTrue(whiteBishop.isLegalMove(6, 1, board));
    }

    @Test
    public void testInitialBoardPositions() {
        // Test with a fresh board
        Board board = new Board();

        // Verify bishops are in correct starting positions
        Figurine whiteBishopC1 = board.getSquare(7, 2);
        Figurine whiteBishopF1 = board.getSquare(7, 5);
        Figurine blackBishopC8 = board.getSquare(0, 2);
        Figurine blackBishopF8 = board.getSquare(0, 5);

        assertTrue(whiteBishopC1 instanceof Bishop);
        assertTrue(whiteBishopF1 instanceof Bishop);
        assertTrue(blackBishopC8 instanceof Bishop);
        assertTrue(blackBishopF8 instanceof Bishop);

        assertEquals("w", whiteBishopC1.getColor());
        assertEquals("w", whiteBishopF1.getColor());
        assertEquals("b", blackBishopC8.getColor());
        assertEquals("b", blackBishopF8.getColor());

        // Test initial legal moves for the white bishop on c1
        assertFalse(((Bishop)whiteBishopC1).isLegalMove(6, 1, board)); // Blocked by pawn
        assertFalse(((Bishop)whiteBishopC1).isLegalMove(5, 0, board)); // Blocked by pawn

        // Test initial legal moves for the white bishop on f1
        assertFalse(((Bishop)whiteBishopF1).isLegalMove(6, 4, board)); // Blocked by pawn
        assertFalse(((Bishop)whiteBishopF1).isLegalMove(6, 6, board)); // Blocked by pawn
    }

    @Test
    public void testBishopOutOfBounds() {
        Board board = new Board();

        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }

        // Place white bishop at h1 (position 7,7)
        Bishop whiteBishop = new Bishop("w");
        board.setSquare(7, 7, whiteBishop);

        // Test moves outside the board (should fail silently)
        assertFalse(whiteBishop.isLegalMove(8, 8, board)); // Out of bounds
        assertFalse(whiteBishop.isLegalMove(-1, 5, board)); // Out of bounds
    }
}