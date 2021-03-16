package code;

public class PlayerPiece {
    /**
     * PlayerPiece represents a player's piece
     */
    private String name;

    /**
     * Constructs a PlayerPiece with a name
     * @param name a name string
     */
    public PlayerPiece(String name){
        this.name = name;
    }

    /**
     * Returns a name of a PlayerPiece as a string
     * @return "PlayerPiece: " + name
     */
    public String toString(){
        return "PlayerPiece: "+this.name;
    }

    public String getName(){
        return this.name;
    }

}
