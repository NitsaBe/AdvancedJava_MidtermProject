package Chess_Game;

public class Rook extends Figurine {
    public Rook(String color) {
        this.color=color;
    }

    @Override
    public boolean moveCorrectWhite(int positionFirst, int PositionSecond) {
        return false;
    }
}
