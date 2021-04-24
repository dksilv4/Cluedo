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
     * @param roomCard
     * @param weaponCard
     * @param suspectCard
     */
    public Envelope(RoomCard roomCard, WeaponCard weaponCard, SuspectCard suspectCard) {
        this.roomCard = roomCard;
        this.weaponCard = weaponCard;
        this.suspectCard = suspectCard;
    }

    /**\
     * Return a RoomCard of an Envelope.
     * @return
     */
    public Card getRoom() {
        return this.roomCard;
    }

    /**
     * Return a WeaponCard of an Envelope.
     * @return
     */
    public Card getWeapon() {
        return this.weaponCard;
    }

    /**
     * Return a SuspectCard of an Envelope.
     * @return
     */
    public Card getSuspect() {
        return this.suspectCard;
    }

    /**
     * Return a string saying who committed the murder in which room with which weapon.
     * @return
     */
    public String toString() {
        return String.format("The murder was committed by %s with %s in %s!!!",this.suspectCard.getName(), this.weaponCard.getName(), this.roomCard.getName());
    }

    /**
     * A boolean checking if the Envelope has an argument card.
     * @param card
     * @return
     */
    public boolean has(Card card){
        return this.roomCard.equals(card) || this.weaponCard.equals(card) || this.suspectCard.equals(card);
    }
}
