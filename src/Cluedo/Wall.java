package Cluedo;

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

    public String toString(){
        return "\u001B[34mw\u001B[0m";
    }
}
