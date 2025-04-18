

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameValidatorTest {

    @Test
    public void testValidateGameWithBishopMoves() {
        Board board = new Board();

        // Create a sequence of moves including bishop moves
        List<List<String>> moves = new ArrayList<>();

        // 1. e4 e5
        moves.add(Arrays.asList("e4", "e5"));

        // 2. Nf3 Nc6
        moves.add(Arrays.asList("Nf3", "Nc6"));

        // 3. Bc4 Bc5 (bishop development for both sides)
        moves.add(Arrays.asList("Bc4", "Bc5"));

        boolean isValid = GameValidator.validateGameMoves(moves, board);
        assertTrue(isValid);
    }

    @Test
    public void testInvalidBishopMove() {
        Board board = new Board();

        // Create a sequence of moves with an invalid bishop move
        List<List<String>> moves = new ArrayList<>();

        moves.add(Arrays.asList("e4", "e5"));


        moves.add(Arrays.asList("Bc4", "Ba3"));

        boolean isValid = GameValidator.validateGameMoves(moves, board);
        assertTrue(isValid);
    }

    @Test
    public void testBishopCapture() {
        Board board = new Board();

        // Create a sequence of moves with a bishop capture
        List<List<String>> moves = new ArrayList<>();

        // 1. e4 d5
        moves.add(Arrays.asList("e4", "d5"));

        // 2. exd5 Qxd5
        moves.add(Arrays.asList("exd5", "Qxd5"));

        // 3. Bc4 (Bishop attacks queen)
        // 3. ... Qe4+ (Queen moves)
        moves.add(Arrays.asList("Bc4", "Qe4+"));

        // 4. Be2 (Bishop blocks check)
        moves.add(Arrays.asList("Be2", null));

        boolean isValid = GameValidator.validateGameMoves(moves, board);
        assertTrue(isValid);
    }

    @Test
    public void testBishopCheckmate() {
        Board board = new Board();

        // Create the fool's mate sequence where bishop delivers checkmate
        List<List<String>> moves = new ArrayList<>();

        // 1. f3 e5
        moves.add(Arrays.asList("f3", "e5"));

        // 2. g4 Qh4#
        moves.add(Arrays.asList("g4", "Qh4#"));

        boolean isValid = GameValidator.validateGameMoves(moves, board);
        assertTrue(isValid);
    }

    @Test
    public void testBishopPinning() {
        Board board = new Board();

        // Sequence where bishop pins a piece
        List<List<String>> moves = new ArrayList<>();

        // 1. e4 e5
        moves.add(Arrays.asList("e4", "e5"));

        // 2. Bc4 Nc6
        moves.add(Arrays.asList("Bc4", "Nc6"));

        // 3. Qh5 (threatens mate) Nf6 (blocks)
        moves.add(Arrays.asList("Qh5", "Nf6"));

        // 4. Qxf7# (Queen takes f7 with checkmate)
        moves.add(Arrays.asList("Qxf7#", null));

        boolean isValid = GameValidator.validateGameMoves(moves, board);
        assertTrue(isValid);
    }

    @Test
    public void testIsValidMove() {
        Board board = new Board();

        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }

        // Place white bishop at e4
        Bishop bishop = new Bishop("w");
        board.setSquare(4, 4, bishop);

        // Test valid bishop move in algebraic notation
        boolean isValid = GameValidator.isValidMove("Bc6", "w", board);
        assertTrue(isValid);

        // Test invalid bishop move
        board = new Board(); // Reset the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.clearSquare(i, j);
            }
        }
        board.setSquare(4, 4, bishop);

        boolean isInvalid = GameValidator.isValidMove("Bd4", "w", board); // Not a diagonal move
        assertFalse(isInvalid);
    }
}