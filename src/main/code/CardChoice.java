package code;

/**
 * CardChoice represents the set of cards that a player chooses to make their suggestion or accusation.
 */
public class CardChoice {

    private RoomCard room;
    private WeaponCard weapon;
    private SuspectCard suspect;
    private PlayerPiece playerPiece;

    /**
     * Constructs and initializes a CardChoice taking a PlayerPiece, a RoomCard, a WeaponCard, and a SuspectCard.
     * @param playerPiece player that made the choice
     * @param room room where the player thinks the murder happened
     * @param weapon weapon used for the murder
     * @param suspect player that committed the murder
     */
    public CardChoice(PlayerPiece playerPiece, RoomCard room, WeaponCard weapon, SuspectCard suspect) {
        this.room = room;
        this.weapon = weapon;
        this.suspect = suspect;
        this.playerPiece = playerPiece;
    }
    public CardChoice(){

    }

    /**
     * Return a RoomCard of a CardChoice.
     * @return variable room
     */
    public RoomCard getRoom() {
        return room;
    }

    /**
     * Set a RoomCard for a CardChoice.
     * @param room room card
     */
    public void setRoom(RoomCard room) {
        this.room = room;
    }

    /**
     * Return a WeaponCard of a CardChoice.
     * @return weapon
     */
    public WeaponCard getWeapon() {
        return weapon;
    }

    /**
     * Set a WeaponCard for a CardChoice.
     * @param weapon weapon card
     */
    public void setWeapon(WeaponCard weapon) {
        this.weapon = weapon;
    }

    /**
     * Return a SuspectCard of a CardChoice.
     * @return suspect card
     */
    public SuspectCard getSuspect() {
        return suspect;
    }

    /**
     * Set a SuspectCard for a CardChoice.
     * @param suspect suspect card
     */
    public void setSuspect(SuspectCard suspect) {
        this.suspect = suspect;
    }

    /**
     * Return a PlayerPiece of a CardChoice.
     * @return player that made the suggestion or accusation
     */
    public PlayerPiece getPlayerPiece() {
        return playerPiece;
    }

}
