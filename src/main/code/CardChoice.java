package code;

/**
 * CardChoice represents the set of cards that a player choose on their turn.
 */
public class CardChoice {

    private RoomCard room;
    private WeaponCard weapon;
    private SuspectCard suspect;
    private PlayerPiece playerPiece;

    /**
     * Constructs and initializes a CardChoice taking a PlayerPiece, a RoomCard, a WeaponCard, and a SuspectCard.
     * @param playerPiece
     * @param room
     * @param weapon
     * @param suspect
     */
    public CardChoice(PlayerPiece playerPiece, RoomCard room, WeaponCard weapon, SuspectCard suspect) {
        this.room = room;
        this.weapon = weapon;
        this.suspect = suspect;
        this.playerPiece = playerPiece;
    }

    /**
     * Return a RoomCard of a CardChoice.
     * @return
     */
    public RoomCard getRoom() {
        return room;
    }

    /**
     * Set a RoomCard for a CardChoice.
     * @param room
     */
    public void setRoom(RoomCard room) {
        this.room = room;
    }

    /**
     * Return a WeaponCard of a CardChoice.
     * @return
     */
    public WeaponCard getWeapon() {
        return weapon;
    }

    /**
     * Set a WeaponCard for a CardChoice.
     * @param weapon
     */
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
