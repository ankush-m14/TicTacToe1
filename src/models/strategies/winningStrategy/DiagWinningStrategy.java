package models.strategies.winningStrategy;
import models.boardDetails.Board;
import models.boardDetails.Cell;
import models.game.Move;

import java.util.HashMap;
import java.util.Map;
public class DiagWinningStrategy implements  WinningStrategy{
    private Map<Character, Integer> leftDiagonalMap = new HashMap<>();
    private Map<Character, Integer> rightDiagonalMap = new HashMap<>();
    private int dimension;
    @Override
    public boolean checkWinner(Move move, Board board) {
        dimension = board.getDimensions();
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Character symbol = move.getPlayer().getSymbol();

        if( row == col ){
            int freq = leftDiagonalMap.getOrDefault( symbol, 0)+1;
            leftDiagonalMap.put( symbol, freq );
        }
        if( row + col == board.getDimensions()-1 ){
            int freq = rightDiagonalMap.getOrDefault( symbol, 0)+1;
            rightDiagonalMap.put( symbol, freq );
        }

        if( row == col && leftDiagonalMap.get(symbol) == board.getDimensions() ) return true;
        return( row + col == board.getDimensions()-1
                && rightDiagonalMap.get(symbol) == board.getDimensions());

    }
    public void removeValuesFromMap( Cell cell){

        int row = cell.getRow();
        int col = cell.getCol();
        char symbol = cell.getPlayer().getSymbol();

        if( row == col ){
            int freq = leftDiagonalMap.get(symbol);
            leftDiagonalMap.put( symbol, freq-1 );
        }

        if( row + col == dimension-1 ){
            int freq = rightDiagonalMap.get(symbol);
            rightDiagonalMap.put( symbol, freq -1 );
        }
    }
}
