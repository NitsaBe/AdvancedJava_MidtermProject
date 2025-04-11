import Chess_Game.Board;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board b=new Board();
        for (int i=0 ; i<8 ; i++){
            System.out.println(b.getBoard()[i][i]);
        }
    }
}