package Cluedo;

import java.util.ArrayList;
import java.util.List;

public class Room {
    String name;
    List<String> doors = new ArrayList<String>();
    List<List<Tile>> tiles  = new ArrayList<List<Tile>>();
    int size_x;
    int size_y;
    public Room(String name){
        this.name = name;
        this.checkName();
    }
    public String toString(){
//        return "Room:"+this.name;
        return "\u001B[32m"+this.name+"\u001B[0m"+this.doors;
    }
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
        System.out.println(this.tiles);
    }
    public void changeTile(int row, int column, Tile tile){
        this.tiles.get(row).set(column, tile);
    }
    private void checkName(){
        if(this.name.length()>12){
            throw new InvalidRoomNameException(this.name, "length of name has to be 12 or lower.");
        }
    }
}
