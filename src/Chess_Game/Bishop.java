package Chess_Game;

public class Bishop extends Figurine {

    public Bishop(String color) {
        this.color = color;
    }

    private boolean isLegalMoveHelper(int positionFirst, int positionSecond, Board board, String color,
                                      int direction, int checkPosX, int checkPosY, int lastCheckX, int lastCheckY) {
        if (checkPosX > 7 || checkPosX < 0 || checkPosY > 7 || checkPosY < 0) {
//            System.out.println("Error: Bishop move out of board bounds");
            return false;
        }
        Figurine figure = board.getSquare(checkPosX, checkPosY);
        if (!board.isSquareEmpty(checkPosX, checkPosY)) {
            if (figure instanceof Bishop) {
                if (figure.color.equals(color)) {
                    if ((lastCheckX == checkPosX && lastCheckY == -1) ||
                            (lastCheckX == -1 && lastCheckY == checkPosY)) {
//                        System.out.println("Error: Bishop cannot move through same color pieces");
                        return false;
                    }
                    move(board, checkPosX, checkPosY, positionFirst, positionSecond);
                    return true;
                }
                System.out.println("Error: Bishop cannot capture same color piece");
                return false;
            }
        }
        if (direction == 0) {
            return isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX - 1, checkPosY + 1, lastCheckX, lastCheckY);
        }
        if (direction == 1) {
            return isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX + 1, checkPosY + 1, lastCheckX, lastCheckY);
        }
        if (direction == 2) {
            return isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX + 1, checkPosY - 1, lastCheckX, lastCheckY);
        }
        if (direction == 3) {
            return isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX - 1, checkPosY - 1, lastCheckX, lastCheckY);
        }
        System.out.println("Error: Invalid bishop move direction");
        return false;
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        if (board.isSquareEmpty(positionFirst, positionSecond)) {
            return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst - 1, positionSecond + 1, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst + 1, positionSecond + 1, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst + 1, positionSecond - 1, -1, -1) ||
                    isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst - 1, positionSecond - 1, -1, -1);
        } else {
            System.out.println("Error: Target square is not empty");
            return false;
        }
    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalMove(positionFirst, positionSecond, board);
        } else if (startingY == -1) {
            if (startingX > positionFirst) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst, positionSecond, startingX, startingY) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst, positionSecond, startingX, startingY);
            } else if (startingX < positionFirst) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst, positionSecond, startingX, startingY) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst, positionSecond, startingX, startingY);
            }
        } else {
            if (startingY > positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 0, positionFirst, positionSecond, startingX, startingY) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 1, positionFirst, positionSecond, startingX, startingY);
            } else if (startingY < positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 2, positionFirst, positionSecond, startingX, startingY) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, this.color, 3, positionFirst, positionSecond, startingX, startingY);
            }
        }
        System.out.println("Error: Invalid bishop move parameters");
        return false;
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if (startingX == -1 && startingY == -1) {
            return isLegalCapture(positionFirst, positionSecond, board);
        }
        if (board.isSquareEmpty(positionFirst, positionSecond) ||
                board.getSquare(positionFirst, positionSecond).color.equals(color)) {
            System.out.println("Error: Invalid bishop capture - target empty or same color");
            return false;
        }
        board.setSquare(positionFirst, positionSecond, null);
        return isLegalMove(startingX, startingY, positionFirst, positionSecond, board);
    }
}