package code;

public class Border extends Tile{
    /**
     * Constructs a Tile that represents a border, players cannot interact with this tile.
     *
     * @param row    the number of row
     * @param column the number of column
     */
    public Border( int row, int column) {
        super("border", row, column);
    }

    /**
     * @return string that represents the class in the terminal
     */
    public String toString(){
        return "\u001B[33mb\u001B[0m";
    }
}
