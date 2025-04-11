package Chess_Game;

public class Pawn extends Figurine {


    public Pawn (String color){
        this.color=color;
    }


    @Override
    public boolean moveCorrectWhite(int positionFirst, int PositionSecond, Board board) {
        return false;
    }
}
