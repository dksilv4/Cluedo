package code;

/**
 * Envelope represents the set of murder cards including a RoomCard, WeaponCard, and SuspectCard.
 */
public class Envelope {

    private final RoomCard roomCard;
    private final WeaponCard weaponCard;
    private final SuspectCard suspectCard;

    /**
     * Constructs and initializes Envelope with a RoomCard, WeaponCard, and SuspectCard.
     * @param roomCard card that was used for the murder
     * @param weaponCard card that was used for the murder
     * @param suspectCard card that was used for the murder
     */
    public Envelope(RoomCard roomCard, WeaponCard weaponCard, SuspectCard suspectCard) {
        this.roomCard = roomCard;
        this.weaponCard = weaponCard;
        this.suspectCard = suspectCard;
    }
    /**\
     * Return a RoomCard of an Envelope.
     * @return roomCard
     */
    public Card getRoom() {
        return this.roomCard;
    }

    /**
     * Return a WeaponCard of an Envelope.
     * @return weaponCard
     */
    public Card getWeapon() {
        return this.weaponCard;
    }

    /**
     * Return a SuspectCard of an Envelope.
     * @return suspect card
     */
    public Card getSuspect() {
        return this.suspectCard;
    }

    /**
     * @return string saying who committed the murder in which room with which weapon.
     */
    public String toString() {
        return String.format("The murder was committed by %s with %s in %s!!!",this.suspectCard.getName(), this.weaponCard.getName(), this.roomCard.getName());
    }

    /**
     * @param card card that is being checked
     * @return boolean representing whether card is in the Envelope
     */
    public boolean has(Card card){
        return this.roomCard.equals(card) || this.weaponCard.equals(card) || this.suspectCard.equals(card);
    }
}
