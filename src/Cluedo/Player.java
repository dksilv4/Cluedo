package Cluedo;

public class Player {
    private String name;
    private PlayerPiece playerPiece;
    public Player(String name, PlayerPiece playerPiece) {
        this.name = name;
        this.playerPiece = playerPiece;
    }

    public String getName(){
        return this.name;
    }

    public PlayerPiece getPlayerPiece(){
        return this.playerPiece;
    }
}
