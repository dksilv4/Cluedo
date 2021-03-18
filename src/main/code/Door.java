package code;

public class Door extends Tile{
    /**
     * Constructs a Tile
     *
     * @param row    the number of row
     * @param column the number of column
     */
    public Door(int row, int column) {
        super("door", row, column);
    }

    public String toString(){
        return "\u001B[31md\u001B[0m";
    }
}
