package Chess_Game;

public class King  extends Figurine {
    public King(String color) {
        this.color=color;
    }

    @Override
    public boolean moveCorrectWhite(int positionFirst, int PositionSecond, Board board) {
        return false;
    }
}
