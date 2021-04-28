package code;

/**
 * Class that extends Tile representing Tile type Wall
 */
public class Wall extends Tile{
    /**
     * Constructs a Tile
     *
     * @param row    the number of row
     * @param column the number of column
     */
    public Wall( int row, int column) {
        super("wall" , row, column);
    }

    /**
     * @return a string representation of the instance
     */
    public String toString(){
        return "\u001B[34mw\u001B[0m";
    }
}
