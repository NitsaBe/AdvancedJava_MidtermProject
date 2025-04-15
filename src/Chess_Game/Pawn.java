package Chess_Game;

import java.util.List;

public class Pawn extends Figurine {

    //        [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites

    public Pawn (String color){
        this.color=color;
    }



    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {

        String color=this.getColor();

        if (board.getSquare(positionFirst, positionSecond) != null) {
            return false;
        }

        int prevPosFirst = calculatePreviousPosition(positionFirst, color);

        if (prevPosFirst < 0 || prevPosFirst >= board.getBoard().length) {
            System.out.println("Invalid previous position for pawn");
            return false;
        }

        Figurine fig = board.getSquare(prevPosFirst, positionSecond);
        if (fig !=null) {

            if (isValidPawn(fig, color)) {
                move(board, prevPosFirst, positionSecond, positionFirst, positionSecond);
                return true;
            }

        }

        else if (isInitPosTarget(positionFirst,positionSecond,color)){
            prevPosFirst = calculatePreviousPosition(prevPosFirst, color);
            fig = board.getSquare(prevPosFirst, positionSecond);
            if (fig !=null) {
                if (isValidPawn(fig, color)) {
                    move(board, prevPosFirst, positionSecond, positionFirst, positionSecond);
                    return true;
                }
            }

        }
        return false;
    }



    public boolean isLegalCapture(int previousY , int positionFirst ,int positionSecond ,Board board){

        if (board.getSquare(positionFirst , positionSecond) == null ){
            return false;
        }
        String color= this.color;

        if (Math.abs(positionSecond-previousY)!=1){
            return false;
        }

        int positionFirstPrev=0;

        if (color.equals("w")){
            positionFirstPrev =positionFirst+1;
        } else if (color.equals("b")) {
            positionFirstPrev =positionFirst-1;
        } else {
            System.out.println("Invalid color");
            return false;
        }

        Figurine figurine= board.getSquare(positionFirstPrev, previousY);
        if (isValidPawn(figurine,color)){
            move(board,positionFirstPrev,previousY,positionFirst,positionSecond);
            return true;
        }
        return false;

    }


    private boolean isValidPawn(Figurine fig, String color) {
        return fig instanceof Pawn && fig.getColor().equals(color);
    }

    private boolean isInitPosTarget(int x, int y ,String c){
        if(c.equals("w")){
            return (x == 4) && (y >= 0 && y < 8);
        }
        else if(c.equals("b")){
            return (x==3)&&(y>=0 && y<8);
        }
        return false;
    }


    @Override
    public boolean moveCorrectWhite(int positionFirst, int PositionSecond, Board board) {
        return false;
    }
}
