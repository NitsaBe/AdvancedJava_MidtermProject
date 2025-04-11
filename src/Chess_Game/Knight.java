package Chess_Game;

public class Knight extends Figurine {
    public Knight(String color) {
        this.color=color;
    }


    @Override
    public boolean moveCorrectWhite(int positionFirst, int PositionSecond, Board board) {
        return false;
    }
}
