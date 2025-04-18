package Chess_Game;

public class Rook extends Figurine {
    public Rook(String color) {
        this.color = color;
    }
    //        [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites
    public static boolean hasMovedWhiteY0=false;
    public static boolean hasMovedWhiteY7=false;

    public static boolean hasMovedBlackY0=false;
    public static boolean hasMovedBlackY7=false;
    private boolean isLegalMoveHelper(int positionFirst, int positionSecond, Board board, String color,
                                      int direction, int checkPosX, int checkPosY, int lastCheckX, int lastCheckY) {
        // Check if position is outside board bounds
        if (checkPosX > 7 || checkPosX < 0 || checkPosY > 7 || checkPosY < 0) {
//            System.out.println("Error: Rook move out of board bounds");
            return false;
        }

        Figurine figure = board.getSquare(checkPosX, checkPosY);

        if (!board.isSquareEmpty(checkPosX, checkPosY)) {
            if (figure instanceof Rook) {
                if (figure.color.equals(color)) {
                    if ((lastCheckX == checkPosX && lastCheckY == -1) ||
                            (lastCheckX == -1 && lastCheckY == checkPosY)) {
//                        System.out.println("Error: Rook cannot move through same color pieces");
                        return false;
                    }
                    move(board, checkPosX, checkPosY, positionFirst, positionSecond);
                   if( board.isSquareEmpty(0,0)){
                       hasMovedBlackY0=true;
                    }
                   else if( board.isSquareEmpty(0,7)){
                        hasMovedBlackY7=true;
                    }
                    else if( board.isSquareEmpty(7,0)){
                        hasMovedWhiteY0=true;
                    }
                    else if( board.isSquareEmpty(7,7)){
                       hasMovedWhiteY0=true;
                   }
                    return true;
                }
            }
//            System.out.println("Error: Rook path blocked by opponent piece");
            return false;
        }

        return switch (direction) {
            case 0 -> // Up
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX-1, checkPosY, lastCheckX, lastCheckY);
            case 1 -> // Right
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX, checkPosY+1, lastCheckX, lastCheckY);
            case 2 -> // Down
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX+1, checkPosY, lastCheckX, lastCheckY);
            case 3 -> // Left
                    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX, checkPosY-1, lastCheckX, lastCheckY);
            default -> {
                System.out.println("Error: Invalid rook move direction");
                yield false;
            }
        };
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        if (board.isSquareEmpty(positionFirst, positionSecond)) {
            return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst, positionSecond, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst, positionSecond, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst, positionSecond, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst, positionSecond, -1, -1);
        } else {
            System.out.println("Error: Target square is not empty");
            return false;
        }
    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalMove(positionFirst, positionSecond, board);
        }

        if (startingY == -1) {
            if (startingX > positionFirst) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst, positionSecond, startingX, startingY);
            } else if (startingX < positionFirst) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst, positionSecond, startingX, startingY);
            }
        } else {
            if (startingY > positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst, positionSecond, startingX, startingY);
            } else if (startingY < positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst, positionSecond, startingX, startingY);
            }
        }

        System.out.println("Error: Invalid rook move parameters");
        return false;
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalCapture(positionFirst, positionSecond, board);
        }
        if (board.isSquareEmpty(positionFirst, positionSecond) ||
                board.getSquare(positionFirst, positionSecond).color.equals(color)) {
            System.out.println("Error: Invalid rook capture - target empty or same color");
            return false;
        }
        board.setSquare(positionFirst, positionSecond, null);
        return isLegalMove(startingX, startingY, positionFirst, positionSecond, board);
    }
}