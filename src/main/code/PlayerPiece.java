package code;

public class PlayerPiece extends Piece{

    /**
     *
     */
    private Tile location;
    public PlayerPiece(String name){
        super(name);
    }

    public Tile getLocation() {
        return location;
    }

    public void setLocation(Tile location) {
        this.location = location;
    }
}
