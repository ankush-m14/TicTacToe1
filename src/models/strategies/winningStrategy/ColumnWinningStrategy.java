package models.strategies.winningStrategy;
import models.boardDetails.Board;
import models.boardDetails.Cell;
import models.game.Move;

import java.util.HashMap;
import java.util.Map;
public class ColumnWinningStrategy implements WinningStrategy{
    private Map<Integer, Map<Character, Integer>> colMaps = new HashMap<>();

    public boolean checkWinner(Move move, Board board ){
        int col = move.getCell().getCol();
        char symbol = move.getPlayer().getSymbol();

        colMaps.putIfAbsent( col, new HashMap<>() ); // Creating new map, if not present :

        Map<Character, Integer> currMap = colMaps.get(col); //Getting the current columns map :

        int frequency = currMap.getOrDefault(symbol, 0) + 1; // updating the frequency :
        currMap.put( symbol, frequency );

        return currMap.get(symbol) == board.getDimensions();
    }
    public void removeValuesFromMap( Cell cell){
        int col = cell.getCol();
        char symbol = cell.getPlayer().getSymbol();
        Map<Character, Integer> currMap = colMaps.get(col);
        int freq = currMap.get(symbol);
        currMap.put(symbol, freq -1);
    }
}
