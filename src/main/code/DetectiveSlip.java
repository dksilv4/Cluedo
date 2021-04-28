package code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that each of the players detective slips where they can mark cards during their game.
 */
public class DetectiveSlip {
    private final Map<Card, Boolean> cards = new HashMap<Card, Boolean>(); //Map representing where all the cards in the game are initially set to false

    /**
     * @param suspectCards list of suspect cards
     * @param weaponCards list of weapon cards
     * @param roomCards list of room cards
     */
    public DetectiveSlip(List<SuspectCard> suspectCards, List<WeaponCard> weaponCards, List<RoomCard> roomCards) {
        this.addWeaponCards(weaponCards);
        this.addSuspectCards(suspectCards);
        this.addRoomCards(roomCards);
    }

    /** Adds all the cards from the parameter to cards
     *
     * @param cards list of cards
     */
    public void addWeaponCards(List<WeaponCard> cards) {
        for (WeaponCard card : cards) {
            this.cards.put(card, false);
        }
    }

    /** Adds all the cards from the parameter to cards
     *
     * @param cards list of cards
     */
    public void addSuspectCards(List<SuspectCard> cards) {
        for (SuspectCard card : cards) {
            this.cards.put(card, false);
        }
    }

    /** Adds all the cards from the parameter to cards
     *
     * @param cards list of cards
     */
    public void addRoomCards(List<RoomCard> cards) {
        for (RoomCard card : cards) {
            this.cards.put(card, false);
        }
    }

    /** Checks if the card given is in the slip
     *
     * @param card represent a card from the game
     * @return Card or null depending if the card given as a parameter is found inside
     */
    public Boolean check(Card card) {
        try {
            return cards.get(card);
        } catch (Exception e) {
            System.out.println("No card matched to the name.");
        }
        return null;
    }

    /** Adds a card to the Map with checked representing whether the player has found or discovered the card.
     *
     * @param card Adds a card to the slip
     * @param checked Represent a boolean value that is required for the Map
     */
    public void addCard(Card card, boolean checked) {
        cards.put(card, checked);
    }

    /** Marks the card given to the value that is given in
     *
     * @param card card from cards
     * @param value value to set the card in the map to
     */
    public void markSlip(Card card, Boolean value) {
        cards.computeIfPresent(card, (k, v) -> value);
    }

    /**
     * @return the variable cards
     */
    public Map<Card, Boolean> getCards() {
        return this.cards;
    }

}
