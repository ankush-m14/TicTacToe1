package models.game;
import models.actors.Player;
import models.actors.PlayerType;
import models.boardDetails.Board;
import models.boardDetails.Cell;
import models.boardDetails.CellStatus;
import models.exceptions.GameInvalidationException;
import models.strategies.playingStrategies.EasyBotPlayingStrategy;
import models.strategies.winningStrategy.WinningStrategy;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Game {
    private Board board;
    private List<Player> players;
    private GameStatus gameStatus;
    private Player winner;
    private int nextMovePlayerIndex;
    private List<Move> moves;
    private List<WinningStrategy> winningStrategy;

    private Game( int dimension, List<Player> players, List<WinningStrategy> winningStrategies ){
        this.board = new Board( dimension );
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.nextMovePlayerIndex = 0;
        this.moves = new ArrayList<>();
        this.winningStrategy = winningStrategies;
        this.players = players;
    }

    // <--------------------------- OWN CLASS METHODS ----------------------------------------------->
    public static Builder getBuilder(){
        return new Builder();
    }
    public void printBoard() {
        board.displayBoard();
    }
    public void undo() {
        if(moves.isEmpty()){ // Edge case :
            System.out.println("Sorry, Not enough moves to undo !!");
            return;
        }
        //----------------- clearing the cell ------------------------
        Move recentMove = moves.get( moves.size()-1 );
        EasyBotPlayingStrategy.getSet().add( recentMove.getCell() ); // to make sure the bot doesnt make the same move again
        Cell cell = recentMove.getCell();
        cell.setCellStatus(CellStatus.EMPTY);
        moves.remove( moves.size() -1 );

        // <------------- Removing records from the map ---------------->
        for( WinningStrategy ws : winningStrategy ){
            ws.removeValuesFromMap( cell );
        }
        // <-----------------Announcing and updating the use about the change------------------>
        System.out.println("move at row : " + cell.getRow() +", col : " +
                cell.getCol() + " made by Player : " + cell.getPlayer().getName() + " is undone or removed !!" );

        System.out.println("-----------------------------------------------------------------------------");
        System.out.println();

        // <------------- Resetting the player --------------------->
        nextMovePlayerIndex -= 1;
        nextMovePlayerIndex %= players.size();
        nextMovePlayerIndex = ( nextMovePlayerIndex < 0 )? 0 : nextMovePlayerIndex;   //Edge case :
    }
    private boolean validateMove( Move move ){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        return row < board.getDimensions() && row >= 0 &&
                col < board.getDimensions() && col >=0 &&
                board.getBoard().get(row).get(col).getCellStatus().equals(CellStatus.EMPTY)
                ;
    }
    private boolean checkWinner(Board board, Move move) {
        for (WinningStrategy strategy : winningStrategy) {
            if (strategy.checkWinner(move, board)) {
                return true;
            }
        }
        return false;
    }
    public void makeMove(){
        Player currPlayer = players.get(nextMovePlayerIndex);
        System.out.println("It is " + currPlayer.getName() + "'s move.");
        Move move = currPlayer.makeMove(this.board);

        System.out.println(currPlayer.getName() + " has made a move at Row: " + move.getCell().getRow() +
                ", col: " + move.getCell().getCol() + ".");

        //Validate the move before we apply the move on Board.
        if (!validateMove(move)) {
            System.out.println("Invalid move by player: " + currPlayer.getName());
            return;
        }
        storeMoveAndSetPlayersTurn(move, currPlayer);
    }
    public void storeMoveAndSetPlayersTurn(Move move, Player currPlayer){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Cell finalCellToMakeMove = board.getBoard().get(row).get(col);
        finalCellToMakeMove.setCellStatus(CellStatus.FILLED);
        finalCellToMakeMove.setPlayer(currPlayer);

        Move finalMove = new Move(finalCellToMakeMove, currPlayer);
        moves.add(finalMove);

        nextMovePlayerIndex += 1;
        nextMovePlayerIndex %= players.size();

        checkForWinners( finalMove, currPlayer); // For every move we check for winners :
    }

    public void checkForWinners( Move finalMove, Player currPlayer ){
        if (checkWinner(board, finalMove)) {
            gameStatus = GameStatus.GAME_OVER;
            winner = currPlayer;

        } else if (moves.size() == board.getDimensions() * board.getDimensions()) {
            //Game has DRAWN
            gameStatus = GameStatus.DRAW;
        }
    }

    // <-------------------------- SETTERS & GETTERS ----------------------------->
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    //<------------------------------INNER BUILDER CLASS --------------------------------------->
    public static class Builder{
        private int dimension;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        private boolean validate(){
            Set<Character> allSymbols = new HashSet<>();
            List<Player> bots = new ArrayList<>();
            for( Player p : this.players ){
                boolean isDuplicateSymbol = allSymbols.contains(p.getSymbol());

                if( p.getPlayerType().equals(PlayerType.BOT) ) {
                    bots.add( p );
                }
                if( isDuplicateSymbol ){ // No two players can have the same symbol :
                    return false;
                }else {
                    allSymbols.add( p.getSymbol() );
                }
            }
            return bots.size() <= 1; // 0 or 1 bot is allowed :
        }
        public Game build() throws Exception{
            if( !validate() ){
                throw new GameInvalidationException("Invalid Game" );
            }
            return new Game( dimension, players, winningStrategies );
        }

        // <----------------------------- BUILDER CLASS SETTERS & GETTERS ------------------------------------->
        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }
    }
}
