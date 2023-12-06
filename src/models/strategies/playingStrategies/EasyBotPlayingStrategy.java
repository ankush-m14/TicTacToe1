package models.strategies.playingStrategies;

import models.boardDetails.Board;
import models.boardDetails.Cell;
import models.boardDetails.CellStatus;
import models.game.Move;
import models.strategies.playingStrategies.BotPlayingStrategy;

import java.util.HashSet;
import java.util.Set;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    private  static Set<Cell> set = new HashSet<>();
    @Override
    public Move makeMove(Board board) {
        for( int row = 0; row < board.getDimensions(); row++ ){
            for( int col = 0; col < board.getDimensions(); col++ ){
                Cell cell = board.getBoard().get(row).get(col);

                if( cell.getCellStatus().equals(CellStatus.EMPTY) ){
                    if( row == board.getDimensions() -1 && col == board.getDimensions() -1 ){
                        return new Move(cell, null);
                    }
                    else if ( set.contains(cell) ){
                        // If we have the cull in set, that means, we have undone a move and we don't want
                        // to make the same move again, so we skip them and chose the next available cell
                        set.remove(cell);
                    }else return new Move(cell, null);
                }
            }
        }
        return null;
    }

    public static class MediumBotPlayingStrategy implements BotPlayingStrategy {
        @Override
        public Move makeMove(Board board) {
            return null;
        }
    }

    public static Set<Cell> getSet() {
        return set;
    }
}
