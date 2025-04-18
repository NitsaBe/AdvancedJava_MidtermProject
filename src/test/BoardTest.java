import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testBoardInitialization() {
        Board board = new Board();

        // Test that all pieces are placed correctly
        // Check bishops specifically
        Figurine whiteC1Bishop = board.getSquare(7, 2);
        Figurine whiteF1Bishop = board.getSquare(7, 5);
        Figurine blackC8Bishop = board.getSquare(0, 2);
        Figurine blackF8Bishop = board.getSquare(0, 5);

        assertTrue(whiteC1Bishop instanceof Bishop);
        assertEquals("w", whiteC1Bishop.getColor());

        assertTrue(whiteF1Bishop instanceof Bishop);
        assertEquals("w", whiteF1Bishop.getColor());

        assertTrue(blackC8Bishop instanceof Bishop);
        assertEquals("b", blackC8Bishop.getColor());

        assertTrue(blackF8Bishop instanceof Bishop);
        assertEquals("b", blackF8Bishop.getColor());
    }

    @Test
    public void testGetSquare() {
        Board board = new Board();

        // Test getSquare with coordinates
        Figurine whiteKing = board.getSquare(7, 4);
        assertTrue(whiteKing instanceof King);
        assertEquals("w", whiteKing.getColor());

        // Test getSquare with algebraic notation
        Figurine blackQueen = board.getSquare("d", 8);
        assertTrue(blackQueen instanceof Queen);
        assertEquals("b", blackQueen.getColor());
    }

    @Test
    public void testSetSquare() {
        Board board = new Board();

        // Create a new bishop
        Bishop newBishop = new Bishop("w");

        // Set bishop to e4
        board.setSquare(4, 4, newBishop);

        // Verify bishop is placed correctly
        Figurine placedBishop = board.getSquare(4, 4);
        assertTrue(placedBishop instanceof Bishop);
        assertEquals("w", placedBishop.getColor());
    }

    @Test
    public void testClearSquare() {
        Board board = new Board();

        // Verify king exists at e1
        assertNotNull(board.getSquare(7, 4));

        // Clear square
        board.clearSquare(7, 4);

        // Verify square is empty
        assertTrue(board.isSquareEmpty(7, 4));
    }

    @Test
    public void testIsSquareEmpty() {
        Board board = new Board();

        // Test occupied square
        assertFalse(board.isSquareEmpty(0, 0));

        // Test empty square
        assertTrue(board.isSquareEmpty(3, 3));

        // Clear a square and verify it's empty
        board.clearSquare(0, 0);
        assertTrue(board.isSquareEmpty(0, 0));
    }

    @Test
    public void testAlgebraicNotationConversion() {
        Board board = new Board();

        // Check e1 (white king)
        Figurine whiteKing = board.getSquare("e", 1);
        assertTrue(whiteKing instanceof King);
        assertEquals("w", whiteKing.getColor());

        // Check a8 (black rook)
        Figurine blackRook = board.getSquare("a", 8);
        assertTrue(blackRook instanceof Rook);
        assertEquals("b", blackRook.getColor());

        // Check c8 (black bishop)
        Figurine blackBishop = board.getSquare("c", 8);
        assertTrue(blackBishop instanceof Bishop);
        assertEquals("b", blackBishop.getColor());
    }
}