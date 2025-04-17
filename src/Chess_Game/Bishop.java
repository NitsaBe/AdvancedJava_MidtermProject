package Chess_Game;

public class Bishop extends Figurine {

    //        [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites

    public Bishop(String color) {
        this.color=color;
    }




    private boolean isLegalMoveHelper(int positionFirst, int positionSecond, Board board , String color ,
                              int direction, int checkPosX, int checkPosY ){
        if(checkPosX>7 ||checkPosX<0 ||checkPosY>7 ||checkPosY<0){
            return false;
        }
       Figurine figure=  board.getSquare(checkPosX,checkPosY);
       if (!board.isSquareEmpty(checkPosX,checkPosY)){
           if (figure instanceof Bishop){
               if (figure.color.equals(color) ){
                   move(board,checkPosX,checkPosY, positionFirst,positionSecond );
                   return true ;
               }

           }
           return false;
       }
       if (direction==0) {
         return   isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX - 1, checkPosY + 1);
       }
       if (direction==1){
         return   isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX +1, checkPosY +1);
       }
        if (direction==2){
           return isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX +1, checkPosY -1);
        }
        if (direction==3){
         return    isLegalMoveHelper(positionFirst, positionSecond, board, color, direction, checkPosX -1, checkPosY -1);
        }
        return false;
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        if(board.isSquareEmpty(positionFirst,positionSecond)){
              return isLegalMoveHelper(positionFirst,positionSecond,board,this.color ,0 ,positionFirst,positionSecond)||
                    isLegalMoveHelper(positionFirst,positionSecond,board,this.color ,1 ,positionFirst,positionSecond)||
                    isLegalMoveHelper(positionFirst,positionSecond,board,this.color ,2 ,positionFirst,positionSecond)||
                    isLegalMoveHelper(positionFirst,positionSecond,board,this.color ,3 ,positionFirst,positionSecond);
        }
        else return false;

    }
    @Override
    public boolean isLegalMove(int startingX,int startingY, int positionFirst, int positionSecond,Board board){
        if (startingX!=-1 && startingY!=-1){
            isLegalMove(positionFirst,positionSecond,board);
        }
        else if (startingY==-1){
            if (startingX>positionFirst){
                return isLegalMoveHelper(positionFirst,positionSecond,board,this.color,1,positionFirst,positionSecond)
                        ||isLegalMoveHelper(positionFirst,positionSecond,board,this.color,2,positionFirst,positionSecond);
            }
            else if (startingX<positionFirst){
                return isLegalMoveHelper(positionFirst,positionSecond,board,this.color,0,positionFirst,positionSecond)
                        ||isLegalMoveHelper(positionFirst,positionSecond,board,this.color,3,positionFirst,positionSecond);
            }
            return false;
        } else {

            if (startingY>positionSecond){
                return isLegalMoveHelper(positionFirst,positionSecond,board,this.color,0,positionFirst,positionSecond)
                        ||isLegalMoveHelper(positionFirst,positionSecond,board,this.color,1,positionFirst,positionSecond);
            }
            else if (startingY<positionSecond){
                return isLegalMoveHelper(positionFirst,positionSecond,board,this.color,2,positionFirst,positionSecond)
                        ||isLegalMoveHelper(positionFirst,positionSecond,board,this.color,3,positionFirst,positionSecond);
            }
            return false;
        }
        return false;

    }

    @Override
    public boolean isLegalCapture(int positionFirst, int positionSecond, Board board) {

        if (board.isSquareEmpty(positionFirst,positionSecond)||
                board.getSquare(positionFirst,positionSecond).color.equals(color)){
            return false;
        }

        else {
            board.setSquare(positionFirst,positionSecond,null);
            return isLegalMove(positionFirst, positionSecond, board);
        }

    }
    @Override
    public boolean isLegalCapture(int startingX, int startingY,int positionFirst, int positionSecond, Board board) {
        if (startingX==-1&&startingY==-1){
            return isLegalCapture(positionFirst,positionSecond,board);
        }
        if (board.isSquareEmpty(positionFirst,positionSecond) ||
                board.getSquare(positionFirst,positionSecond).color.equals(color)){
            return false;
        }
        board.setSquare(positionFirst,positionSecond,null);
        return isLegalMove(startingX,startingY,positionFirst,positionSecond,board);

    }




}
