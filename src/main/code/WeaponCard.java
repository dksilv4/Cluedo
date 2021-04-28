package code;

/**
 * Class that represents another type of card. Weapon Cards.
 */
public class WeaponCard extends Card {
    /**
     * @param name name of card
     */
    public WeaponCard(String name) {
        super(name);
    }

    /**
     * Returns a name of a PlayerPiece as a string
     *
     * @return "PlayerPiece: " + name
     */
    public String toString() {
        return "Weapon: " + this.getName();
    }
}
