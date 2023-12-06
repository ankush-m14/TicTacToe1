package models.strategies.playingStrategies;
import models.boardDetails.Board;
import models.game.Move;
public interface BotPlayingStrategy {
    Move makeMove(Board board );
}
