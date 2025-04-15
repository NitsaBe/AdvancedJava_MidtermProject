package Chess_Game;

public class Rook extends Figurine {
    public Rook(String color) {
        this.color=color;
    }


    @Override
    public boolean isLegalCapture(int positionFirst, int positionSecond, Board board) {
        return false;
    }

    @Override
    public boolean isLegalMove(int positionFirst, int positionSecond, Board board) {
        return false;
    }

    @Override
    public boolean isLegalCapture(int previousY, int positionFirst, int positionSecond, Board board) {
        return false;
    }
}
