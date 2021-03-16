package Cluedo;

public class Border extends Tile{
    /**
     * Constructs a Tile
     *
     * @param row    the number of row
     * @param column the number of column
     */
    public Border( int row, int column) {
        super("border", row, column);
    }

    public String toString(){
        return "\u001B[33mb\u001B[0m";
    }
}
