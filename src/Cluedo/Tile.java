package Cluedo;

/**
 * Tile represents the tiles on a grid
 */
public class Tile {

    String type;
    Room belongsTo;
    int row;
    int column;
    boolean isOccupied;

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
    public boolean available(){
        return !this.isOccupied() && !this.getType().equals("wall");
    }

    /**
     * Returns the information of a Tile as a string
     * @return a string
     */
    public void newType(String type){
        this.type = type;
    }
    public String toString(){
        switch (type){
            case "door":
                return "\u001B[31md\u001B[0m";
            case "room":
                return belongsTo.toString();
            case "wall":
                return "\u001B[34mw\u001B[0m";
            case "border":
                return "\u001B[33mb\u001B[0m";
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
