import Chess_Game.Board;
import Chess_Game.StringReader;

import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board b=new Board();
        for (int i=0 ; i<8 ; i++){
            System.out.println(b.getBoard()[i][i]);
        }

        try {
            String pgnContent = StringReader.pgnFileToString("src/Tbilisi2015.pgn");



            List<String> separatedGames = StringReader.separatedFullGames(pgnContent);
            System.out.println("\nFound " + separatedGames.size() + " games:");
            for (int i = 0; i < separatedGames.size(); i++) {
                System.out.println("Game " + (i+1) + " length: " + separatedGames.get(i).length() + separatedGames.get(i));
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    }
