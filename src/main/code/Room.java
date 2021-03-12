package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Room represents a room on a board
 */
public class Room {
    String name;
    List<String> doors = new ArrayList<String>();
    List<List<Tile>> tiles  = new ArrayList<List<Tile>>();
    int size_x;
    int size_y;

    /**
     * Constructs and initializes a Room with a name
     * @param name a name string
     */
    public Room(String name){
        this.name = name;
        this.checkName();

    /**
     * ToString method for Room
     * @return a string "Room:" + its name
     */
    }
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
    public void setSize(int size_x, int size_y){
        this.size_x = size_x;
        this.size_y = size_y;
        for(int x=0;x<size_x;x++){
            List<Tile> row = new ArrayList<>();
            for(int y=0;y<size_y;y++){
                row.add(new Tile("space", x, y));
            }
            this.tiles.add(row);
        }
    }

    /**
     * Changes a Tile
     * @param row row number of a tile
     * @param column column number of a tile
     * @param tile a Tile
     */
    public void changeTile(int row, int column, Tile tile){
        this.tiles.get(row).set(column, tile);
    }

    /**
     * Check if the name of a Room is valid
     */
    private void checkName(){
        if(this.name.length()>12){
            throw new InvalidRoomNameException(this.name, "length of name has to be 12 or lower.");
        }
    }
}
