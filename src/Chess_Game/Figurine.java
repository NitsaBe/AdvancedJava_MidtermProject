package Chess_Game;

public abstract class Figurine {
    String color;

    public String getColor() {
        return color;
    }

    //    logic- in every specific figurine will be a checker() which will check if for a specific position
//    there are available pieces that could move there. if there are the Board object will be updated accordingly
//    ie the old position will be wiped and the new one will be updated

    public int calculatePreviousPosition(int currentPos, String color) {
        return color.equals("w") ? currentPos + 1 : currentPos - 1;
    }

    public void move(Board board, int fromFirst, int fromSecond, int toFirst, int toSecond) {
        board.clearSquare(fromFirst, fromSecond);
        board.setSquare(toFirst, toSecond, this);
    }

    public abstract boolean isLegalCapture(int positionFirst, int positionSecond, Board board) ;
    public abstract boolean isLegalMove( int positionFirst ,int positionSecond ,Board board);

    public abstract boolean isLegalCapture(int previousY, int positionFirst, int positionSecond, Board board);
}
