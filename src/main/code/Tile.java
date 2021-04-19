package code;

/**
 * Tile represents the tiles on a grid
 */
public class Tile {

    private String type;
    private Room belongsTo;
    private int row;
    private int column;
    private PlayerPiece occupier= null;

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

    public void setOccupier(PlayerPiece occupier){
        this.occupier = occupier;
    }

    public void removeOccupier(){
        this.occupier = null;
    }

    public PlayerPiece getOccupier(){
        return this.occupier;
    }
    /**
     * Checks if a Tile is occupied
     * @return boolean
     */
    public boolean isOccupied() {
        if(this.occupier != null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean available() {
        if(!this.getType().equals("wall") && !this.isOccupied()){
            return true;
        }
        return false;
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
        room.addTile(this);
        System.out.println(room.getTiles());
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
        if(this.isOccupied()){
            return this.occupier.toString();
        }
        else{
            switch (type){
                case "passage":
                    return "\u001b[36mP\u001B[0m";
                case "room":
                    return belongsTo.toString();
                default:
                    return "\u001b[30mx\u001B[0m";
            }
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
