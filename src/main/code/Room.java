package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Room represents a room on a board
 */
public class Room {
    private final String name;
    private final List<String> doors = new ArrayList<String>(); // string list representing all the doors
    private final List<Tile> tiles = new ArrayList<Tile>(); // Tile list of all the tiles within Grid that represent the Room
    private WeaponPiece weaponPiece = null; // Weapon piece that is located in the room
    private boolean hasSecretPassage = false; // Boolean variable showing whether a secret passage can be found in the room
    private Tile secretPassage; // Tile of the room where the secret passage is located
    int size_x; // amount of columns the room has
    int size_y; // amount of rows the room has

    /**
     * Constructs and initializes a Room with a name
     *
     * @param name a name string
     */
    public Room(String name) {
        this.name = name;
        this.checkName();
    }

    /**
     * Set a WeaponPiece in the room
     * @param weaponPiece WeaponPiece in a room
     */
    public void setWeaponPiece(WeaponPiece weaponPiece) {
        this.weaponPiece = weaponPiece;
    }

    /**
     * @return The WeaponPiece in the Room
     */
    public WeaponPiece getWeaponPiece() {
        return this.weaponPiece;
    }

    /**
     * @return The Tiles that construct the Room
     */
    public List<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * @return The doors in the Room
     */
    public List<String> getDoors() {
        return this.doors;
    }

    /**
     * @return The name of the Room
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the amount of columns the room has
     */
    public int getSizeX() {
        return this.size_x;
    }

    /**
     * @param passage Tile instance where the secret passage should be
     */
    public void setSecretPassage(Tile passage) {
        this.secretPassage = passage;
        passage.setType("passage");
        this.setHasSecretPassage(true);
    }

    /**
     * @return The secretPassage of the Room
     */
    public Tile getSecretPassage() {
        return this.secretPassage;
    }

    /**
     * @return The size of rows
     */
    public int getSizeY() {
        return this.size_y;
    }

    /**
     * ToString method for Room
     * @return a string "Room:" + its name
     */
    public String toString() {
//        return "Room:"+this.name;
        return "\u001B[32m" + this.name.charAt(0) + "\u001B[0m";
    }

    /**
     * Adds a door to a Room
     *
     * @param door a door string
     */
    public void addDoor(String door) {
        this.doors.add(door);
    }

    /**
     * Add doors to a Room
     *
     * @param doors doors list
     */
    public void addDoors(List<String> doors) {
        this.doors.addAll(doors);
    }

    /**
     * Set size of a Room
     *
     * @param size_x size of x
     * @param size_y size of y
     */
    public void setSize(int size_y, int size_x) {
        this.size_x = size_x;
        this.size_y = size_y;
    }

    /**
     * Add a Tile to the Room
     * @param tile instance of Tile
     */
    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    /**
     * Check if the name of a Room is valid
     */
    private void checkName() {
        if (this.name.length() > 12) {
            throw new InvalidRoomNameException(this.name, "length of name has to be 12 or lower.");
        }
    }

    /**
     * @return Boolean if the room has the secretPassage
     */
    public boolean isHasSecretPassage() {
        return hasSecretPassage;
    }

    /**
     * @param hasSecretPassage Set the variable hasSecretPassage
     */
    public void setHasSecretPassage(boolean hasSecretPassage) {
        this.hasSecretPassage = hasSecretPassage;
    }
}
