package Chess_Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String pgnFileToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
    public static List<String> separatedFullGames(String fileContent) {
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

    private static  List<List<String>> parseSingleGame(String gameText) {
        List<List<String>> gameData = new ArrayList<>();

        List<String> metadata = new ArrayList<>();

        Pattern metaPattern = Pattern.compile("\\[(\\w+)\\s+\"([^\"]*)\"\\]");
        Matcher metaMatcher = metaPattern.matcher(gameText);

        while (metaMatcher.find()) {
            metadata.add(String.format("[%s \"%s\"]", metaMatcher.group(1), metaMatcher.group(2)));
        }



        String moves = gameText.replaceAll("\\[.*?\\]", "")  // Remove metadata tags
                .replaceAll("\\{.*?\\}", "")   // Remove comments
                .trim();

        gameData.add(metadata);
        gameData.add(Collections.singletonList(moves));

        return gameData;
    }




    public static List<List<List <String>>> parser(String full_game) throws IOException {

        return null;
    }


}
