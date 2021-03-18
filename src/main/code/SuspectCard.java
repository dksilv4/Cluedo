package code;

public class SuspectCard extends Card {
    /**
     * PlayerPiece represents a player's piece
     */

    /**
     * Constructs a PlayerPiece with a name
     * @param name a name string
     */
    public SuspectCard(String name){
        super(name);
    }

    /**
     * Returns a name of a PlayerPiece as a string
     * @return "PlayerPiece: " + name
     */
    public String toString(){
        return "PlayerPiece: "+ this.getName();
    }

}
