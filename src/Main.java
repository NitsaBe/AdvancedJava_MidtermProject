import Chess_Game.*;

import java.io.IOException;
import java.util.List;


//            [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board b=new Board();

//        Pawn pawn=      new Pawn("w");
//        Pawn pp=      new Pawn("b");
//        Bishop bishop=new Bishop("w");
//        Knight knight=new Knight("b");
//        Knight wn=new Knight("w");
//
//
//        Queen q=new Queen("b");
//        pawn.isLegalMove(4,7,b);
//     pawn.isLegalMove(3,7,b);
//        pawn.isLegalMove(4,0,b);
//        pawn.isLegalMove(3,0,b);
//
//
//        pp.isLegalMove(3,3,b);
//        pp.isLegalMove(3,4,b);
//        System.out.println((  q.isLegalCapture(4,7,b)));
//        System.out.println( q.isLegalCapture(4,0,b));
//        System.out.println( q.isLegalCapture(2,1,b));
//        System.out.println( q.isLegalCapture(3,0,b));
//
//
//
//        Pawn s=      new Pawn("b");
//        System.out.println(w.isLegalMove(4,1,b));
//
//        System.out.println( w.isLegalMove(3,1,b));
//        System.out.println(     w.isLegalMove(2,1,b));
//        System.out.println(  w.isLegalMove(1,1,b));
//
//        System.out.println(    w.isLegalCapture(1,0,1,b));
//        System.out.println(    w.isLegalCapture(1,1,1,b));
//        System.out.println(   w.isLegalCapture(1,1,3,b));
//        System.out.println(w.isLegalCapture(1,1,0,b));
//        System.out.println(s.isLegalMove(3,0,b));

        try {
            String pgnContent = StringReader.pgnFileToString("src/Tbilisi2015.pgn");
            List<String> l=StringReader.separatedAllGamesIntoFullGamesList(pgnContent);

            for (int i =0 ; i<l.size() ; i++) {

                System.out.println( StringReader.parseGameNotation(l.get(i)).get(0));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    }
