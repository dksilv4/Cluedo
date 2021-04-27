package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Player that plays the game
 * All the necessary data for the player to be able to play the game will be within this class
 */
public class Player {
    private final String name;
    private final List<Card> cards = new ArrayList<Card>(); // list of cards the player has
    private PlayerPiece piece; // playing piece that the player is using

    /**
     * @param name name of player
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * @return name of player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a Card instance to the players list of cards
     *
     * @param card representing any the available cards within the game
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * @return player's cards
     */
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Assigns the piece as the players piece and also sets the belongs to variable within the
     * playerPiece to the instance of this class
     *
     * @param piece represents a playing player piece within the game
     */
    public void setPiece(PlayerPiece piece) {
        this.piece = piece;
        piece.setBelongsTo(this);
    }

    /**
     * @return player's piece
     */
    public PlayerPiece getPiece() {
        return this.piece;
    }

    /**
     * @return representation of this class into string (the name of the player is used for this)
     */
    public String toString() {
        return this.getName();
    }

    /**
     * Used when giving the player the cards in the beginning of the game to make sure that all the cards
     * they received are marked true on their detective slip
     */
    public void fillSlip() {
        for (Card card : this.cards) {
            piece.getSlip().markSlip(card, true);
        }
    }

    /**
     * @return boolean variable to show whether the player was an AI or Human player
     */
    public boolean isAI() {
        return this instanceof AI;
    }

}
