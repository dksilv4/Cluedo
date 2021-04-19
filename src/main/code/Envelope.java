package code;

public class Envelope {

    private final RoomCard roomCard;
    private final WeaponCard weaponCard;
    private final SuspectCard suspectCard;

    public Envelope(RoomCard roomCard, WeaponCard weaponCard, SuspectCard suspectCard) {
        this.roomCard = roomCard;
        this.weaponCard = weaponCard;
        this.suspectCard = suspectCard;
    }

    public Card getRoom() {
        return this.roomCard;
    }

    public Card getWeapon() {
        return this.weaponCard;
    }

    public Card getSuspect() {
        return this.suspectCard;
    }

    public String toString() {
        return String.format("The murder was committed by %s with %s in %s!!!",this.suspectCard.getName(), this.weaponCard.getName(), this.roomCard.getName());
    }

    public boolean has(Card card){
        return this.roomCard.equals(card) || this.weaponCard.equals(card) || this.suspectCard.equals(card);
    }
}
