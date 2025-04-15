package Chess_Game;

public class Queen extends Figurine {
    public Queen(String color) {
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
