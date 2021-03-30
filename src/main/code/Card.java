package code;

/**
 * Represent a Card in the game.
 * Used to place all the functions that will be used in all types of cards.
 */
public class Card {
    private final String name;
    public Card(String name){
        this.name = name;
    }

    /**
     * @return the variable name
     */
    public String getName() {
        return name;
    }
}
