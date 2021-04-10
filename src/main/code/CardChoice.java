package code;

public class CardChoice {

    private RoomCard room;
    private WeaponCard weapon;
    private SuspectCard suspect;
    private PlayerPiece playerPiece;

    public CardChoice(PlayerPiece playerPiece, RoomCard room, WeaponCard weapon, SuspectCard suspect) {
        this.room = room;
        this.weapon = weapon;
        this.suspect = suspect;
        this.playerPiece = playerPiece;
    }

    public RoomCard getRoom() {
        return room;
    }

    public void setRoom(RoomCard room) {
        this.room = room;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponCard weapon) {
        this.weapon = weapon;
    }

    public SuspectCard getSuspect() {
        return suspect;
    }

    public void setSuspect(SuspectCard suspect) {
        this.suspect = suspect;
    }

    public PlayerPiece getPlayerPiece() {
        return playerPiece;
    }

    public void setPlayerPiece(PlayerPiece playerPiece) {
        this.playerPiece = playerPiece;
    }
}
