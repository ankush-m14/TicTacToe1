package controller;
import models.actors.Player;
import models.game.Game;
import models.game.GameStatus;
import models.strategies.winningStrategy.WinningStrategy;
import java.util.List;
public class GameController {
    public Game startGame(int dimension, List<Player> player,
                          List<WinningStrategy> winningStrategyList)throws Exception{
        return Game.getBuilder()
                .setDimension( dimension )
                .setPlayers( player )
                .setWinningStrategies( winningStrategyList )
                .build();
    }
    public void makeMove(Game game) {
        game.makeMove();
    }

    public void displayBoard(Game game) {
        game.printBoard();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void undo(Game game) {
        game.undo();
    }

    public GameStatus getGameState(Game game) {
        return game.getGameStatus();
    }
}
