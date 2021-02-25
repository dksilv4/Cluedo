package Cluedo;

public class Tile {

    String type;
    Room belongsTo;
    int row;
    int column;
    boolean isOccupied;

    public Tile(String type, int row, int column){
        this.type = type;
        this.row = row;
        this.column = column;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getType() {
        return type;
    }

    public void setBelongsTo(Room room){
        this.belongsTo = room;

    }

    public boolean available(PlayerPiece piece){
        if(this.isOccupied() == false && piece.movingState() > 0 && this.getType() != "wall"){
            return true;
        }
        return false;
    }

    public String toString(){
        switch (type){
            case "door":
                return "\u001B[31md\u001B[0m";
            case "room":
                return belongsTo.toString();
            case "wall":
                return "\u001B[34mw\u001B[0m";
            default:
                return "x";

        }
    }
}
