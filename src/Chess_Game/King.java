package Chess_Game;

public class King  extends Figurine {
    public King(String color) {
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


    public static boolean hasMovedWhite=false;
    public static boolean hasMovedBlack=false;
    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        if (!board.isSquareEmpty(positionFirst,positionSecond)) {
            return false;
        }
        final int[][] KING_MOVES = {
                {-1, 0}, {-1, +1}, {0, +1}, {+1, +1},
                {+1, 0}, {+1, -1}, {0, -1}, {-1, -1}
        };

        String color = this.getColor();

        for(int i=0 ; i<8;i++){

            int checkX = positionFirst + KING_MOVES[i][0];
            int checkY = positionSecond + KING_MOVES[i][1];

            Figurine figurine= board.getSquare(checkX,checkY);

            if (figurine == null || !figurine.getColor().equals(color)) {
               continue;
            }
            if (figurine instanceof King && figurine.getColor().equals(color)) {
                move(board, checkX, checkY, positionFirst, positionSecond);
                if(this.getColor().equals("w")){
                    King.hasMovedWhite=true;
                }
                else if (this.getColor().equals("b")){
                    King.hasMovedBlack=true;
                }else return false;
                return true;
            }


        }

        return false;

    }

    @Override
    public boolean isLegalMove(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        return false;
    }


    @Override
    public boolean isLegalCapture(int startingX, int startingY, int positionFirst, int positionSecond, Board board) {
        return false;
    }
}