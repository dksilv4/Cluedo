package code;

public class RoomCard extends Card{
    public RoomCard(String name){
        super(name);
    }
    public String toString(){
        return "Room Card: "+this.getName();
    }
}
