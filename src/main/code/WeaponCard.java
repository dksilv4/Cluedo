package code;

public class WeaponCard extends Card {
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
