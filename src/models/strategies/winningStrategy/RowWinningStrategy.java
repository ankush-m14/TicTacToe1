package models.strategies.winningStrategy;
import models.boardDetails.Board;
import models.boardDetails.Cell;
import models.game.Move;

import java.util.HashMap;
import java.util.Map;
public class RowWinningStrategy implements WinningStrategy{
    private Map<Integer, Map<Character, Integer>> rowMaps = new HashMap<>();
    @Override
    public boolean checkWinner(Move move, Board board ){
        int row = move.getCell().getRow();
        char symbol = move.getPlayer().getSymbol();

        rowMaps.putIfAbsent( row, new HashMap<>() ); // Creating new map, if not present :

        Map<Character, Integer> currMap = rowMaps.get(row); //Getting the current columns map :

        int frequency = currMap.getOrDefault(symbol, 0) + 1; // updating the frequency :
        currMap.put( symbol, frequency );

        return currMap.get(symbol) == board.getDimensions();
    }
    public void removeValuesFromMap( Cell cell){
        int row = cell.getRow();
        char symbol = cell.getPlayer().getSymbol();
        Map<Character, Integer> currMap = rowMaps.get(row);
        int freq = currMap.get(symbol);
        currMap.put(symbol, freq -1);
    }
}
