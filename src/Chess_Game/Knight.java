package Chess_Game;

public class Knight extends Figurine {

    /**
     * Possible knight moves as coordinate offsets (row, column)
     */
    private static final int[][] KNIGHT_MOVES = {
            {-2, +1}, {-1, +2}, {+1, +2}, {+2, +1},
            {+2, -1}, {+1, -2}, {-1, -2}, {-2, -1}
    };

    /**
     * Constructor
     * @param color Color of the knight ("w" for white, "b" for black)
     */
    public Knight(String color) {
        this.color = color;
    }

    /**
     * Helper method to check if a knight can move in a specific direction
     * @param targetX Target X position
     * @param targetY Target Y position
     * @param board Current board state
     * @param direction Direction index (0-7) in KNIGHT_MOVES array
     * @return true if move is legal, false otherwise
     */
    private boolean isLegalMoveHelper(int targetX, int targetY, Board board, int direction) {
        if (direction < 0 || direction > 7) {
            return false;
        }

        int checkX = targetX + KNIGHT_MOVES[direction][0];
        int checkY = targetY + KNIGHT_MOVES[direction][1];

        if (checkX > 7 || checkX < 0 || checkY > 7 || checkY < 0) {
            return false;
        }

        // Check if there's a knight of our color at the position
        Figurine figurine = board.getSquare(checkX, checkY);
        if (figurine == null || !figurine.getColor().equals(color)) {
            return false;
        }

        if (figurine instanceof Knight && figurine.getColor().equals(color)) {
            move(board, checkX, checkY, targetX, targetY);
            return true;
        }

        return false;
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        boolean answer = false;
        for (int i = 0; i < 8; i++) {
            answer = isLegalMoveHelper(positionFirst, positionSecond, board, i);
            if (answer) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int targetX, int targetY, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalMove(targetX, targetY, board);
        }

        int[] directionsToCheck;

        if (startingY == -1) {
            // Rank disambiguation
            directionsToCheck = (startingX > targetX)
                    ? new int[]{2, 3, 4, 5}  // Knight moving down
                    : new int[]{0, 1, 6, 7}; // Knight moving up
        } else if (startingX == -1) {
            // File disambiguation
            directionsToCheck = (startingY > targetY)
                    ? new int[]{0, 1, 2, 3}  // Knight moving right
                    : new int[]{4, 5, 6, 7}; // Knight moving left
        } else {
            return false; // Both rank and file specified is not handled
        }

        // Check specified directions
        for (int direction : directionsToCheck) {
            if (isLegalMoveHelper(targetX, targetY, board, direction)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalCapture(positionFirst, positionSecond, board);
        }
        if (board.isSquareEmpty(positionFirst, positionSecond) ||
                board.getSquare(positionFirst, positionSecond).color.equals(color)) {
            return false;
        }
        board.setSquare(positionFirst, positionSecond, null);
        return isLegalMove(startingX, startingY, positionFirst, positionSecond, board);
    }

}
