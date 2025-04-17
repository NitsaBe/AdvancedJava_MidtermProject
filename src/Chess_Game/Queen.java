package Chess_Game;

public class Queen extends Figurine {
    public Queen(String color) {
        this.color=color;
    }
    //        [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites


    private boolean isLegalMoveHelper(int positionFirst, int positionSecond, Board board, String color,
                                      int checkPosX, int checkPosY, int direction) {

        if (checkPosX > 7 || checkPosX < 0 || checkPosY > 7 || checkPosY < 0) {
            return false;
        }

        Figurine figure = board.getSquare(checkPosX, checkPosY);


        if (!board.isSquareEmpty(checkPosX, checkPosY)) {
            if (figure instanceof Queen) {
                if (figure.color.equals(color)) {
                    move(board, checkPosX, checkPosY, positionFirst, positionSecond);
                    return true;
                }
            }
            return false;
        }

        return switch (direction) {
            case 0 -> // Up
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX, checkPosY - 1, direction);
            case 1 -> // Up-Right
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX + 1, checkPosY - 1, direction);
            case 2 -> // right
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX + 1, checkPosY, direction);
            case 3 -> // right-Down
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX + 1, checkPosY + 1, direction);
            case 4 -> // Down
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX, checkPosY + 1, direction);
            case 5 -> // Down-Left
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX - 1, checkPosY + 1, direction);
            case 6 -> // Left
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX - 1, checkPosY, direction);
            case 7 -> // Left-up
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, checkPosX - 1, checkPosY - 1, direction);
            default -> false;
        };
    }


    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        if (board.isSquareEmpty(positionFirst, positionSecond)) {
            for (int direction = 0; direction < 8; direction++) {
                if (isLegalMoveHelper(positionFirst, positionSecond, board, this.color, direction,
                        positionFirst, positionSecond)) {
                    return true;
                }
            }
        }

        return false;

    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        return false;
    }

    @Override
    public boolean isLegalCapture(int positionFirst, int positionSecond, Board board) {
        if (board.isSquareEmpty(positionFirst,positionSecond)||
                board.getSquare(positionFirst,positionSecond).color.equals(color)) {
            return false;
        }

        board.setSquare(positionFirst,positionSecond,null);
        return isLegalMove(positionFirst, positionSecond, board);
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        return false;
    }
}
