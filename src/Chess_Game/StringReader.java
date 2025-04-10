package Chess_Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringReader {

    String path = "src/Tbilisi2015.pgn";

    public static void main(String[] args) throws IOException {
        parser("src/Tbilisi2015.pgn");
    }

//    this will return  an ArrayList of moves that are in the game jpg
//
//        returned array   =   [    [ [metaData] , [gameData] ]    ,    [ [metaData] , [gameData] ]     ,   ... ]
//
//        metaData = [ [Event "Tbilisi FIDE GP 2015"] , [Site "Tbilisi GEO"] , [Date "2015.02.15"] ,  ...  ]
//
//        gameData = [   [ whitemove ,blackmove ]  ,  [ whitemove , blackmove ]  , ... ]
//


    public static List<List<List <String>>> parser(String full_game) throws IOException {

        return null;
    }

}
