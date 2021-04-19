package code;

public class PlayerPiece extends Piece{

    /**
     *
     */
    private Tile location;
    private DetectiveSlip slip;
    private Player belongsTo = null;
    private boolean playing = false;
    private boolean kicked = false;
    public PlayerPiece(String name){
        super(name);
    }

    public DetectiveSlip getSlip() {
        return this.slip;
    };

    public void setSlip(DetectiveSlip slip){
        this.slip = slip;

    }

    public Tile getLocation() {
        return location;
    }

    public void setLocation(Tile location) {
        this.location = location;
        location.setOccupier(this);
    }
    public String toString(){
        if(this.isPlaying()){
            return "\u001b[35m"+this.getName().charAt(0)+"\u001B[0m";
        }
        return "\u001b[33m"+this.getName().charAt(0)+"\u001B[0m";
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Player getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Player belongsTo) {
        this.belongsTo = belongsTo;
    }

    public boolean isKicked() {
        return kicked;
    }

    public void setKicked(boolean kicked) {
        this.kicked = kicked;
    }
}
