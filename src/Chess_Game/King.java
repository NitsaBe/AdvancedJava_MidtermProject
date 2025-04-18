package Chess_Game;

public class King  extends Figurine {
    public King(String color) {
        this.color = color;
    }
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