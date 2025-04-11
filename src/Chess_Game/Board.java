package Chess_Game;

public class Board {

//        [
//            [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites
//            ]
    private  Object [][] board  ;

    public Board() {

        board= new Object[7][7];
        for (int x = 0; x < 8; x++) {
            board[1][x]=new Pawn("w");
            board[6][x]=new Pawn("b");
        }
        board[7][3]= new Queen("w");
        board[0][3]= new Queen("b");

        board[7][4]=new King("w");
        board[0][4]= new King("b");


        board[7][0]= new Rook("w");
        board[7][7]= new Rook("w");
        board[0][7]= new Rook("b");
        board[0][0]= new Rook("b");



        board[7][1]=new Knight("w");
        board[7][6]=new Knight("w");
        board[0][1]= new Knight("b");
        board[0][6]= new Knight("b");



        board[0][2] = new Bishop("b");
        board[0][5] = new Bishop("b");
        board[7][2] = new Bishop("w");
        board[7][5]= new Bishop("w");




    }

}
