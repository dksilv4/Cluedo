package code;

/**
 * RoomCard extends Card that represents a Room
 */
public class RoomCard extends Card{
    public RoomCard(String name){
        super(name);
    }

    /**
     * ToString method for RoomCard
     * @return "Room Card: the name of the room"
     */
    public String toString(){
        return "Room Card: "+this.getName();
    }
}
