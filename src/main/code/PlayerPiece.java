package code;

public class PlayerPiece extends Piece{

    /**
     *
     */
    private Tile location;
    private DetectiveSlip slip;
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
        return "\u001b[33m"+String.valueOf(this.getName())+"\u001B[0m";
    }
}
