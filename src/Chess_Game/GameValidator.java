package Chess_Game;

import java.util.List;
import java.util.regex.Pattern;

public class GameValidator {
    //        [ [0][0], [0][1], [0][2], [0][3], [0][4], [0][5], [0][6], [0][7] ], // Row 0   blacks   [  [8][a]  ,[8][b] ...
//            [ [1][0], [1][1], [1][2], [1][3], [1][4], [1][5], [1][6], [1][7] ], // Row 1
//            [ [2][0], [2][1], [2][2], [2][3], [2][4], [2][5], [2][6], [2][7] ], // Row 2
//            [ [3][0], [3][1], [3][2], [3][3], [3][4], [3][5], [3][6], [3][7] ], // Row 3
//            [ [4][0], [4][1], [4][2], [4][3], [4][4], [4][5], [4][6], [4][7] ], // Row 4
//            [ [5][0], [5][1], [5][2], [5][3], [5][4], [5][5], [5][6], [5][7] ], // Row 5
//            [ [6][0], [6][1], [6][2], [6][3], [6][4], [6][5], [6][6], [6][7] ], // Row 6
//            [ [7][0], [7][1], [7][2], [7][3], [7][4], [7][5], [7][6], [7][7] ]  // Row 7   whites

    //    this will return  an ArrayList of moves that are in the game jpg
//
//        returned array   =   [    [ [metaData] , [gameData] ]    ,    [ [metaData] , [gameData] ]     ,   ... ]
//
//        metaData = [ [Event "Tbilisi FIDE GP 2015"] , [Site "Tbilisi GEO"] , [Date "2015.02.15"] ,  ...  ]
//
//        gameData = [   [ whitemove ,blackmove ]  ,  [ whitemove , blackmove ]  , ... ]
    // Patterns for move validation
    private static final Pattern PAWN_MOVE_PATTERN =
            Pattern.compile("^([a-h][1-8])[+#]?$");
    private static final Pattern PAWN_CAPTURE_PATTERN =
            Pattern.compile("^([a-h])x([a-h][1-8])(?:=([QRBN]))?[+#]?$");
    private static final Pattern PIECE_MOVE_PATTERN =
            Pattern.compile("^([KQRBN])([a-h]?[1-8]?)([a-h][1-8])[+#]?$");
    private static final Pattern PIECE_CAPTURE_PATTERN =
            Pattern.compile("^([KQRBN])([a-h]?[1-8]?)x([a-h][1-8])[+#]?$");
    private static final Pattern CASTLE_PATTERN =
            Pattern.compile("^(O-O|O-O-O)[+#]?$");

    /**
     * Validates a list of move pairs (white and black moves)
     * @param gameData List of move pairs in format [[whiteMove, blackMove], ...]
     * @return true if all moves are valid, false otherwise
     */
    public static boolean validateGameMoves(List<List<String>> gameData) {
        for (List<String> movePair : gameData) {
            String whiteMove = movePair.get(0);
            if (!isValidMove(whiteMove)) {
                System.err.println("Invalid white move: " + whiteMove);
                return false;
            }

            if (movePair.size() > 1) {
                String blackMove = movePair.get(1);
                if (!isValidMove(blackMove)) {
                    System.err.println("Invalid black move: " + blackMove);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a move string is valid according to chess notation rules
     * @param move The move string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidMove(String move) {


        if (move == null || move.isEmpty()) return false;
        char [] movesInCharArray= move.toCharArray();


        if (CASTLE_PATTERN.matcher(move).matches()) {

            //todo

            return true;
        }

        if (PAWN_MOVE_PATTERN.matcher(move).matches()) {



            return true;
        }

        if (PIECE_MOVE_PATTERN.matcher(move).matches()) {
            return true;
        }

        return false;
    }

    /**
     * Parses a move into its components
     * @param move The move string to parse
     * @return MoveInfo object containing parsed components
     */





}
