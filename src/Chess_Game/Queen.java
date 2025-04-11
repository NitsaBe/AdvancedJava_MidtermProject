package Chess_Game;

public class Queen extends Figurine {
    public Queen(String color) {
        this.color=color;
    }


    @Override
    public boolean moveCorrectWhite(int positionFirst, int PositionSecond, Board board) {
        return false;
    }
}
