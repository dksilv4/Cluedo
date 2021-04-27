package code;

/**
 * SuspectCard extends Card that represents a suspect.
 */
public class SuspectCard extends Card {

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
