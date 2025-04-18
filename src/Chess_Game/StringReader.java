package Chess_Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringReader {

    String path = "src/Tbilisi2015.pgn";


//    this will return  an ArrayList of moves that are in the game jpg
//
//        returned array   =   [    [ [metaData] , [gameData] ]    ,    [ [metaData] , [gameData] ]     ,   ... ]
//
//        metaData = [ [Event "Tbilisi FIDE GP 2015"] , [Site "Tbilisi GEO"] , [Date "2015.02.15"] ,  ...  ]
//
//        gameData = [   [ whitemove ,blackmove ]  ,  [ whitemove , blackmove ]  , ... ]
//

    public static String pgnFileToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private static final Pattern TAG_PATTERN = Pattern.compile("\\[(\\w+)\\s+\"([^\"]*)\"\\]");
    private static final Pattern MOVE_TEXT_PATTERN = Pattern.compile(
            "([KQRBN]?[a-h]?[1-8]?x?[a-h][1-8](?:=[QRBN])?|O-O|O-O-O)(?:[+#])?");
    private static final Pattern MOVE_NUMBER_PATTERN = Pattern.compile("(\\d+)\\.+\\s*");
    private static final Pattern RESULT_PATTERN = Pattern.compile("(1-0|0-1|1/2-1/2|\\*)");
    public static List<String> separatedFullGamesIntoFullGamesList(String fileContent) {
        List<String> games = new ArrayList<>();

        String normalized = fileContent.replace("\r\n", "\n").trim();
        String[] rawGames = normalized.split("(?=\\n\\[Event )");


        for (String game : rawGames) {
            String cleaned = game.trim();
            if (!cleaned.isEmpty()) {
                games.add(cleaned);
            }
        }

        return games;
    }

    private static  List<List<String>> parseSingleGameIntoMetaPlusGamePlusResultList(String gameText) {
        List<List<String>> gameData = new ArrayList<>();

        List<String> metadata = new ArrayList<>();
        Matcher metaMatcher = TAG_PATTERN.matcher(gameText);
        while (metaMatcher.find()) {
            metadata.add(String.format("[%s \"%s\"]", metaMatcher.group(1), metaMatcher.group(2)));
        }

        String movesAndResult = gameText.replaceAll("\\[.*?\\]", "")  // Remove metadata tags
                .replaceAll("\\{.*?\\}", "")   // Remove comments
                .trim();

        Matcher resultMatcher = RESULT_PATTERN.matcher(movesAndResult);
        String result = "";
        if (resultMatcher.find()) {
            result = resultMatcher.group();
            movesAndResult = movesAndResult.substring(0, resultMatcher.start()).trim();
        }

        gameData.add(metadata);  // Metadata at index 0
        gameData.add(Collections.singletonList(movesAndResult));  // Move text at index 1
        gameData.add(Collections.singletonList(result));  // Result at index 2

        return gameData;
    }


    private static List<List<String>> parseGameIntoMovesList(String movesData){
        List<List<String>> movesList = new ArrayList<>();

        // Remove move numbers (e.g., "1.", "2...") and split into individual tokens
        String[] tokens = movesData.split(MOVE_NUMBER_PATTERN.pattern());

        // Now process the tokens to extract moves
        List<String> allMoves = new ArrayList<>();

        for (String token : tokens) {
            if (token.trim().isEmpty()) continue;
            Matcher moveMatcher = MOVE_TEXT_PATTERN.matcher(token);
            while (moveMatcher.find()) {
                allMoves.add(moveMatcher.group());
            }
        }

        // Pair white and black moves together
        for (int i = 0; i < allMoves.size(); i += 2) {
            String whiteMove = allMoves.get(i);
            String blackMove = (i + 1 < allMoves.size()) ? allMoves.get(i + 1) : null;

            movesList.add(Arrays.asList(whiteMove, blackMove));
        }

        return movesList;

    }

    private static List<List<String>> parseMetadataIntoList(String metaData){
        List<List<String>> metadataList = new ArrayList<>();
        List<String> mandatoryTags = Arrays.asList("Event", "Site", "Date", "Round", "White", "Black", "Result");
        List<String> foundTags = new ArrayList<>();
        List<String> missingTags = new ArrayList<>();

        Matcher tagMatcher = TAG_PATTERN.matcher(metaData);
        while (tagMatcher.find()) {
            String tagName = tagMatcher.group(1);
            String tagValue = tagMatcher.group(2);
            foundTags.add(tagName);
            metadataList.add(Arrays.asList(tagName, tagValue));
        }

        for (String mandatoryTag : mandatoryTags) {
            if (!foundTags.contains(mandatoryTag)) {
                missingTags.add(mandatoryTag);
            }
        }

        if (!missingTags.isEmpty()) {
            throw new ArithmeticException("ayoo");
        }

        return metadataList;
    }


    public static List<List<List <String>>> parseGameNotation(String fullGame) {
        List<List<List<String>>> parsedGame = new ArrayList<>();

        // First parse metadata and moves+result
        List<List<String>> metaGameResult = parseSingleGameIntoMetaPlusGamePlusResultList(fullGame);

        // Then parse the metadata into structured list
        List<List<String>> structuredMetadata = parseMetadataIntoList(fullGame);

        // Then parse the moves into pairs
        String movesText = metaGameResult.get(1).get(0);
        List<List<String>> movePairs = parseGameIntoMovesList(movesText);

        // Get the result
        String result = metaGameResult.get(2).get(0);

        // Structure the output
        parsedGame.add(structuredMetadata);  // Index 0: Structured metadata
        parsedGame.add(movePairs);           // Index 1: Move pairs
        parsedGame.add(Collections.singletonList(Collections.singletonList(result))); // Index 2: Result

        return parsedGame;
    }


}
