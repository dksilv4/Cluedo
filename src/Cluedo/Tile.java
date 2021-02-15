package Cluedo;

public class Tile {
    boolean isOccupied = false;
    boolean isDoor;
    boolean isRoom;
    public Tile(boolean isDoor, boolean isRoom){
        this.isDoor = isDoor;
        this.isRoom = isRoom;
    }
    public boolean getIsDoor(){
        return this.isDoor;
    }
    public boolean getIsRoom(){
        return this.isRoom;
    }
    public boolean getIsOccupied(){
        return this.isOccupied;
    }
    public void setIsOccupied(boolean isOccupied){
        this.isOccupied = isOccupied;
    }
}
