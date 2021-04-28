package code;

/**
 * Extension of Tile to represent a Door within the map
 */
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

    /**
     * @return string representing the class
     */
    public String toString(){
        if(this.getOccupier()!=null){
            return this.getOccupier().toString();
        }
        return "\u001B[31md\u001B[0m";
    }
}
