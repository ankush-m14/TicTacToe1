package models.actors;
import models.boardDetails.Board;
import models.boardDetails.Cell;
import models.game.Move;

import java.util.Scanner;

public class Player {
    private String name;
    private char symbol;
    private PlayerType playerType;
    private int id;

    public Player(String name, char symbol, PlayerType playerType, int id) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.id = id;
    }

    public Move makeMove(Board board){
        Scanner sc = new Scanner(System.in);

        // Getting row value form the player :
        System.out.println("Please tell the row index of your next move");
        int row = sc.nextInt();

        // Getting column Value from the player:
        System.out.println("Please tell the col index of your next move");
        int col = sc.nextInt();
        System.out.println("------------------------------------------------------");

        return new Move(new Cell(row, col),this);
    }


    // <-------------------------- GETTERS & SETTERS -------------------------------------------->
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
