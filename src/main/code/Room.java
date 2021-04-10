package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Room represents a room on a board
 */
public class Room {
    private final String name;
    private final List<String> doors = new ArrayList<String>();
    private final List<Tile> tiles  = new ArrayList<Tile>();
    private WeaponPiece weaponPiece = null;
    private boolean hasSecretPassage = false;
    private Tile secretPassage;
    int size_x;
    int size_y;

    /**
     * Constructs and initializes a Room with a name
     * @param name a name string
     */
    public Room(String name){
        this.name = name;
        this.checkName();
    }

    public void setWeaponPiece(WeaponPiece weaponPiece){
        this.weaponPiece = weaponPiece;
    }
    public WeaponPiece getWeaponPiece(){
        return this.weaponPiece;
    }
    public List<Tile> getTiles(){
        return this.tiles;
    }
    public List<String> getDoors(){
        return this.doors;
    }
    public String getName(){
        return this.name;
    }
    public int getSizeX(){
        return this.size_x;
    }
    public void setSecretPassage(Tile passage){
        this.secretPassage = passage;
        passage.setType("passage");
        this.setHasSecretPassage(true);
    }
    public Tile getSecretPassage() { return this.secretPassage; }

    public int getSizeY(){
        return this.size_y;
    }
    /**
     * ToString method for Room
     * @return a string "Room:" + its name
     */
    public String toString(){
//        return "Room:"+this.name;
        return "\u001B[32m"+this.name.charAt(0)+"\u001B[0m";
    }

    /**
     * Adds a door to a Room
     * @param door a door string
     */
    public void addDoor(String door){
        this.doors.add(door);
    }

    /**
     * Add doors to a Room
     * @param doors doors list
     */
    public void addDoors(List<String> doors){
        this.doors.addAll(doors);
    }

    /**
     * Set size of a Room
     * @param size_x size of x
     * @param size_y size of y
     */
    public void setSize(int size_y, int size_x){
        this.size_x = size_x;
        this.size_y = size_y;
    }
    public void addTile(Tile tile){
        this.tiles.add(tile);
    }

    /**
     * Check if the name of a Room is valid
     */
    private void checkName(){
        if(this.name.length()>12){
            throw new InvalidRoomNameException(this.name, "length of name has to be 12 or lower.");
        }
    }

    public boolean isHasSecretPassage() {
        return hasSecretPassage;
    }

    public void setHasSecretPassage(boolean hasSecretPassage) {
        this.hasSecretPassage = hasSecretPassage;
    }
}
