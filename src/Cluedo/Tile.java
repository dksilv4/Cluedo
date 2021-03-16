package Cluedo;

/**
 * Tile represents the tiles on a grid
 */
public class Tile {

    private String type;
    private Room belongsTo;
    private int row;
    private int column;
    private boolean isOccupied;

    /**
     * Constructs a Tile
     * @param type a type string of a Tile
     * @param row the number of row
     * @param column the number of column
     */
    public Tile(String type, int row, int column){
        this.type = type;
        this.row = row;
        this.column = column;
    }

    /**
     * Checks if a Tile is occupied
     * @return boolean
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Sets whether a Tile is occupied or not
     * @param isOccupied boolean
     */
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    /**
     * Returns the type of a Tile
     * @return a type string
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the row of a Tile
     * @return a type string
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Returns the column of a Tile
     * @return a type string
     */
    public int getColumn(){
        return this.column;
    }
    /**
     * Sets which Room a Tile belongs to
     * @param room a Room object
     */
    public void setBelongsTo(Room room){
        this.belongsTo = room;
    }

    /**
     * Checks if a Player can move to a Tile
     * @return boolean
     */

    public Room getBelongsTo() { return this.belongsTo; }

    public boolean isAvailable(){
        return !this.isOccupied() && !this.getType().equals("wall");
    }

    /**
     * Returns the information of a Tile as a string
     * @return a string
     */
    public void setType(String type){
        this.type = type;
    }
    public String toString(){
        switch (type){
            case "room":
                return belongsTo.toString();
            default:
                return "x";
        }
    }
//    public String toString(){
//        switch (type){
//            case "door":
//                return "\u001B[31md\u001B[0m"+this.row+" "+this.column;
//            case "room":
//                return belongsTo.toString()+this.row+" "+this.column;
//            case "wall":
//                return "\u001B[34mw\u001B[0m"+this.row+" "+this.column;
//            default:
//                return "x";
//        }
//    }
}
