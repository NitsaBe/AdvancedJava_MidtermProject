package Chess_Game;

import java.util.List;
import java.util.Map;
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
            Pattern.compile("^([a-h][1-8])$");
    private static final Pattern PAWN_CAPTURE_PATTERN =
            Pattern.compile("^([a-h])(x)([a-h][1-8])$");
    private static final Pattern PIECE_MOVE_PATTERN =
            Pattern.compile("^([KQRBN])([a-h]?[1-8]?)([a-h][1-8])$");
    private static final Pattern PIECE_CAPTURE_PATTERN =
            Pattern.compile("^([KQRBN])([a-h]?[1-8]?)x([a-h][1-8])$");
    private static final Pattern CASTLE_PATTERN =
            Pattern.compile("^(O-O|O-O-O)$");


    public static boolean validateGameMoves(List<List<String>> gameData,Board board) {
        for (List<String> movePair : gameData) {
            String whiteMove = movePair.get(0);
            if (!isValidMove(whiteMove,"w",board)) {
                System.err.println("Invalid white move: " + whiteMove);
                return false;
            }

            if (movePair.size() > 1) {
                String blackMove = movePair.get(1);
                if (!isValidMove(blackMove,"b",board)) {
                    System.err.println("Invalid black move: " + blackMove);
                    return false;
                }
            }
        }
        return true;
    }


    private static final Map<Character, Integer> FILE_TO_Y = Map.of(
            'a', 0, 'b', 1, 'c', 2, 'd', 3,
            'e', 4, 'f', 5, 'g', 6, 'h', 7
    );

    private static final Map<Character, Integer> RANK_TO_X = Map.of(
            '1', 7, '2', 6, '3', 5, '4', 4,
            '5', 3, '6', 2, '7', 1, '8', 0
    );

    private static int letterToY(char file) {
        return FILE_TO_Y.get(Character.toLowerCase(file));
    }
    private static int numberToX(char rank) {
        return RANK_TO_X.get(rank);
    }


    ///todo what about pawn becoming queen

    public static boolean isValidMove(String move, String color , Board board) {

        if (move == null || move.isEmpty()) return false;
        char[] movesInCharArray = move.toCharArray();

        if (CASTLE_PATTERN.matcher(move).matches()) {

            //todo

            return true;
        }

        if (PAWN_MOVE_PATTERN.matcher(move).matches()) {
            int checkY = letterToY(movesInCharArray[0]);
            int checkX = numberToX(movesInCharArray[1]);

            Pawn pawn = new Pawn(color);
            return pawn.isLegalMove(checkX, checkY, board);
        }

        if (PAWN_CAPTURE_PATTERN.matcher(move).matches()) {
            int startingY = letterToY(movesInCharArray[0]);
            int checkY = letterToY(movesInCharArray[2]);
            int checkX = numberToX(movesInCharArray[3]);

            Pawn pawn = new Pawn(color);
            return pawn.isLegalCapture(-1, startingY, checkX, checkY, board);
        }


        if (PIECE_MOVE_PATTERN.matcher(move).matches()) {
            char movePiece = movesInCharArray[0];
            int fromPosX = -1;
            int fromPosY = -1;
            int targetX = -1;
            int targetY = -1;

            // Handle different move lengths
            switch (movesInCharArray.length) {
                case 3: // Basic move (e.g., "Nf3")
                    targetX = numberToX(movesInCharArray[2]);
                    targetY = letterToY(movesInCharArray[1]);
                    break;

                case 4: // Disambiguated move (e.g., "Nbd2" or "N1a3")
                    if (movesInCharArray[1] >= 'a' && movesInCharArray[1] <= 'h') { // File disambiguation
                        fromPosY = letterToY(movesInCharArray[1]);
                        targetX = numberToX(movesInCharArray[3]);
                        targetY = letterToY(movesInCharArray[2]);
                    }
                    else if (movesInCharArray[1] >= '1' && movesInCharArray[1] <= '8') { // Rank disambiguation
                        fromPosX = numberToX(movesInCharArray[1]);
                        targetX = numberToX(movesInCharArray[3]);
                        targetY = letterToY(movesInCharArray[2]);
                    }
                    break;

                case 5: // Full specification (e.g., "Qa1a8")
                    fromPosY = letterToY(movesInCharArray[1]);
                    fromPosX = numberToX(movesInCharArray[2]);
                    targetX = numberToX(movesInCharArray[4]);
                    targetY = letterToY(movesInCharArray[3]);
                    break;

                default:
                    return false;
            }

            // Create piece based on move character
            Figurine figurine = switch (movePiece) {
                case 'K' -> new King(color);
                case 'Q' -> new Queen(color);
                case 'R' -> new Rook(color);
                case 'B' -> new Bishop(color);
                case 'N' -> new Knight(color);
                default -> null;
            };

            if (figurine == null) return false;

            return figurine.isLegalMove(fromPosX, fromPosY, targetX, targetY, board);
        }

        if (PIECE_CAPTURE_PATTERN.matcher(move).matches()) {
            char movePiece = movesInCharArray[0];
            int fromPosX = -1;
            int fromPosY = -1;
            int targetX = -1;
            int targetY = -1;

            // Extract target position (always the last 2 chars before potential check symbols)
            targetY = letterToY(movesInCharArray[movesInCharArray.length - 2]);
            targetX = numberToX(movesInCharArray[movesInCharArray.length - 1]);

            // Handle different capture formats
            switch (movesInCharArray.length) {
                case 4: // Basic capture (e.g. "Nxf3")
                    // No disambiguation information
                    break;

                case 5: // Disambiguated capture (e.g. "Ndxf3" or "N1xf3")
                    char disambigChar = movesInCharArray[1];
                    if (disambigChar >= 'a' && disambigChar <= 'h') {
                        fromPosY = letterToY(disambigChar); // File disambiguation
                    } else if (disambigChar >= '1' && disambigChar <= '8') {
                        fromPosX = numberToX(disambigChar); // Rank disambiguation
                    }
                    break;

                case 6: // Fully specified capture (e.g. "Qa1xb2")
                    fromPosY = letterToY(movesInCharArray[1]);
                    fromPosX = numberToX(movesInCharArray[2]);
                    break;

                default:
                    return false;
            }

            // Create the appropriate piece
            Figurine figurine = switch (movePiece) {
                case 'K' -> new King(color);
                case 'Q' -> new Queen(color);
                case 'R' -> new Rook(color);
                case 'B' -> new Bishop(color);
                case 'N' -> new Knight(color);
                default -> null;
            };

            if (figurine == null) return false;

            // Call isLegalCapture instead of isLegalMove
            return figurine.isLegalCapture(fromPosX, fromPosY, targetX, targetY, board);
        }
        return false;
    }



    }







