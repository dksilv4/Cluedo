package code;

/**
 * PlayerPiece represents the pieces player use on the board.
 */
public class PlayerPiece extends Piece{
    private Tile location = null;
    private DetectiveSlip slip;
    private Player belongsTo = null;
    private boolean playing = false;
    private boolean kicked = false;

    /**
     * @param name name of the instance
     */
    public PlayerPiece(String name){
        super(name);
    }

    /**
     * @return The detective slip of the piece
     */
    public DetectiveSlip getSlip() {
        return this.slip;
    };

    /**
     * Set the detective slip of the piece
     * @param slip DetectiveSlip instance
     */
    public void setSlip(DetectiveSlip slip){
        this.slip = slip;
    }

    /**
     * @return The tile location of the piece
     */
    public Tile getLocation() {
        return location;
    }

    /**
     * Set the location of the piece
     * @param location instance of Tile
     */
    public void setLocation(Tile location) {
        if(this.location!=null){
            this.location.removeOccupier();

        }
        this.location = location;
        location.setOccupier(this);
    }

    /**
     * ToString method for PlayerPiece
     * @return Name of the piece with color
     */
    public String toString(){
        if(this.isPlaying()){
            return "\u001b[35m"+this.getName().charAt(0)+"\u001B[0m";
        }
        return "\u001b[33m"+this.getName().charAt(0)+"\u001B[0m";
    }

    /**
     * @return Boolean if the piece is playing
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Set the variable playing to the param playing
     * @param playing boolean variable representing whether the player piece is currently playing
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * @return Player which player it belongs to
     */
    public Player getBelongsTo() {
        return belongsTo;
    }

    /**
     * @return boolean of whether the player piece can play
     */
    public boolean ableToPlay(){
        return !this.isKicked() && this.belongsTo != null;
    }

    /**
     * Set the variable BelongsTo
     * @param belongsTo
     */
    public void setBelongsTo(Player belongsTo) {
        this.belongsTo = belongsTo;
    }

    /**
     * @return variable kicked
     */
    public boolean isKicked() {
        return kicked;
    }

    /**
     * @param kicked boolean variable representing if the player piece has been removed from being able to play the game
     */
    public void setKicked(boolean kicked) {
        this.kicked = kicked;
    }
}
