import controller.GameController;
import models.actors.Bot;
import models.actors.BotDifficultyLevel;
import models.actors.Player;
import models.actors.PlayerType;
import models.game.Game;
import models.game.GameStatus;
import models.strategies.playingStrategies.EasyBotPlayingStrategy;
import models.strategies.winningStrategy.ColumnWinningStrategy;
import models.strategies.winningStrategy.DiagWinningStrategy;
import models.strategies.winningStrategy.RowWinningStrategy;
import models.strategies.winningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args)  throws Exception {
        GameController gameController = new GameController();
        int dimension = 3;
        List<Player> players = new ArrayList<>();


        players.add( new Bot("RoBot", '0', PlayerType.BOT,
                2, BotDifficultyLevel.EASY, new EasyBotPlayingStrategy()) );
        players.add( new Player("Ankush", 'x', PlayerType.HUMAN, 1));




        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColumnWinningStrategy());
        winningStrategies.add(new DiagWinningStrategy());

        Game game = gameController.startGame(dimension, players, winningStrategies);

        while (gameController.getGameState(game).equals(GameStatus.IN_PROGRESS)) {
            //Display the board.
            System.out.println("This is the current state of Board");
            gameController.displayBoard(game);

            int player_idx = game.getNextMovePlayerIndex();
            Player currPlayer = game.getPlayers().get( player_idx );

            if( currPlayer.getPlayerType().equals(PlayerType.HUMAN) ){
                System.out.println("Do you want to undo the game? Y/N");
                Scanner sc = new Scanner( System.in );
                String response = sc.next();
                System.out.println();
                if( response.equals( "Y" ) || response.equals( "y" )  ){
                    gameController.undo(game);
                }
            }
            gameController.makeMove(game);
        }

        if (gameController.getGameState(game).equals(GameStatus.GAME_OVER)) {
            //Somebody has won the game.
            gameController.displayBoard(game);
            System.out.println("Winner is " + gameController.getWinner(game).getName());
        } else {
            System.out.println("Game has DRAWN");
        }
    }
}