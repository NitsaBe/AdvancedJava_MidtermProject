package Chess_Game;

public class Knight extends Figurine {
    public Knight(String color) {
        this.color=color;
    }
//        [
//            [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites
//            ]

    private boolean isLegalMoveHelper(int positionFirst , int positionSecond , Board board ,int direction){
        final int[][] KNIGHT_MOVES = {
                {-2, +1}, {-1, +2}, {+1, +2}, {+2, +1},
                {+2, -1}, {+1, -2}, {-1, -2}, {-2, -1}
        };

        if (direction < 0 || direction > 7) {
            return false;
        }

        int checkX = positionFirst + KNIGHT_MOVES[direction][0];
        int checkY = positionSecond + KNIGHT_MOVES[direction][1];

        if(checkX>7 ||checkX<0 ||checkY>7 ||checkY<0){
            return false;
        }
        String color= this.getColor();

        Figurine figurine=board.getSquare(checkX,checkY);

        if(figurine==null || !figurine.getColor().equals(color)){
            return false;
        }
        if (figurine instanceof Knight && figurine.getColor().equals(color)){
            move(board,checkX,checkY,positionFirst,positionSecond);
            return true;
        }
        return false;


    }
    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        boolean answer=false;
        for (int i=0;i<8;i++){
            answer=isLegalMoveHelper(positionFirst,positionSecond,board,i);
            if(answer){
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        if(startingX==-1 && startingY==-1){return false;}
        if (startingY==-1){
            if(startingX>positionFirst){
                return isLegalMoveHelper(positionFirst,positionSecond,board,2)||
                        isLegalMoveHelper(positionFirst,positionSecond,board,3)||
                        isLegalMoveHelper(positionFirst,positionSecond,board,4)||
                        isLegalMoveHelper(positionFirst,positionSecond,board,5);

            }
            else if (startingX<positionFirst){
                return isLegalMoveHelper(positionFirst,positionSecond,board,0)||
                        isLegalMoveHelper(positionFirst,positionSecond,board,1)||
                        isLegalMoveHelper(positionFirst,positionSecond,board,6)||
                        isLegalMoveHelper(positionFirst,positionSecond,board,7);
            }
            return false;
        }
        else if (startingX==-1) {
            if (startingY > positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, 0) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, 1) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, 2) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, 3);

            } else if (startingY < positionSecond) {
                return isLegalMoveHelper(positionFirst, positionSecond, board, 4) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, 5) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, 6) ||
                        isLegalMoveHelper(positionFirst, positionSecond, board, 7);
            }
            return false;
        }

        return false;
    }

    @Override
    public boolean isLegalCapture(int positionFirst, int positionSecond, Board board) {
        return false;
    }

    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        return false;
    }
}
