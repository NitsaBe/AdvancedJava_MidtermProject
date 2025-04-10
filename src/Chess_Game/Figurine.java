package Chess_Game;

public abstract class Figurine {

//    logic- in every specific figurine will be a checker() which will check if for a specific position
//    there are available pieces that could move there. if there are the Board object will be updated accordingly
//    ie the old position will be wiped and the new one will be updated
    public abstract boolean moveCorrectWhite(int positionFirst ,int PositionSecond);
}
