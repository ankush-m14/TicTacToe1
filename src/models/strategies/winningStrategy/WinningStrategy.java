package models.strategies.winningStrategy;

import models.boardDetails.Board;
import models.boardDetails.Cell;
import models.game.Move;
public interface WinningStrategy {
    boolean checkWinner(Move move, Board board);
    public void removeValuesFromMap( Cell cell);
}
