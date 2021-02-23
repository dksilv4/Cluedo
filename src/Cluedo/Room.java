package Cluedo;

public class Room {
    String name;
    public Room(String name){
        this.name = name;
        this.checkName();

    }
    public String toString(){
        return "Room:"+this.name;
    }
    private void checkName(){
        if(this.name.length()>12){
            throw new InvalidRoomNameException(this.name, "length of name has to be 12 or lower.");
        }
    }
}
