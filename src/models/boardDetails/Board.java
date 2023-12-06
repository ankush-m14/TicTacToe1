package models.boardDetails;
import java.util.ArrayList;
import java.util.List;
public class Board {
    private int dimensions;
    private List<List<Cell>> board;

    public Board(int dimensions) {
        this.dimensions = dimensions;
        this.board = new ArrayList<>();
        createBoard();
    }

    public void createBoard( ){
        for( int row = 0; row < dimensions; row++ ){
            //Creating rows :
            board.add( new ArrayList<>() ); // Board is a 2D list, This code is responsible for crating columns :

            // Filling each row with columns :
            for( int col = 0; col < dimensions; col++ ){
                board.get(row).add( new Cell(row, col) );
            }
        }
    }

    public void displayBoard(){
        for( int row = 0; row < dimensions; row++ ){
            for( int col = 0; col < dimensions; col++ ){
                Cell cell = board.get(row).get(col);
                if( cell.getCellStatus().equals( CellStatus.EMPTY) ){
                    System.out.print(" | ");
                }else{
                    System.out.print(" " + cell.getPlayer().getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    // <-------------------- GETTERS & SETTERS ---------------------------------------->
    public int getDimensions() {
        return dimensions;
    }
    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }
    public List<List<Cell>> getBoard() {
        return board;
    }
    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
}
