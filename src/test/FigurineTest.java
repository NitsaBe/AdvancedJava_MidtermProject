

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FigurineTest {

    // Since Figurine is abstract, we'll test through concrete subclasses

    @Test
    public void testMove() {
        Board board = new Board();

        // Clear some squares
        board.clearSquare(3, 3);
        board.clearSquare(4, 4);

        // Place a bishop at d5
        Bishop bishop = new Bishop("w");
        board.setSquare(3, 3, bishop);

        // Move the bishop to e4
        Figurine.move(board, 3, 3, 4, 4);

        // Verify source square is empty
        assertTrue(board.isSquareEmpty(3, 3));

        // Verify bishop is at target square
        Figurine movedBishop = board.getSquare(4, 4);
        assertNotNull(movedBishop);
        assertTrue(movedBishop instanceof Bishop);
        assertEquals("w", movedBishop.getColor());
    }

    @Test
    public void testGetColor() {
        Bishop whiteBishop = new Bishop("w");
        Bishop blackBishop = new Bishop("b");

        assertEquals("w", whiteBishop.getColor());
        assertEquals("b", blackBishop.getColor());
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

        // Place white bishop at e4
        Bishop whiteBishop = new Bishop("w");
        board.setSquare(4, 4, whiteBishop);

        // Place black pawn at g6 (diagonal to bishop)
        Pawn blackPawn = new Pawn("b");
        board.setSquare(2, 6, blackPawn);

        // Place white pawn at c6 (diagonal to bishop but same color)
        Pawn whitePawn = new Pawn("w");
        board.setSquare(2, 2, whitePawn);

        // Test valid capture
        assertTrue(whiteBishop.isLegalCapture(2, 6, board));

        // Test invalid capture (same color)
        assertFalse(whiteBishop.isLegalCapture(2, 2, board));

        // Test invalid capture (empty square)
        assertFalse(whiteBishop.isLegalCapture(6, 6, board));
    }
}