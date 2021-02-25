package Cluedo;

public class Player {

    private Boolean inGame = true;
    private String realName = "";
    private int movesRemaining = 0;
    private String pieceName = "";
    private int playerType = -1;

    void setupPlayer() {
        realName = "";
        pieceName = "";
        movesRemaining = 0;
    }

    public Player(String realName, String character, int playerType) {
        setupPlayer();
        pieceName = character;
        this.realName = realName;
        this.playerType = playerType;
    }
    //public void getPosition() {
      //  return position;
    //}

}
