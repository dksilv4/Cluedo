package code;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> cards = new ArrayList<Card>();
    private PlayerPiece piece;

    public Player(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public void addCard(Card card){
        this.cards.add(card);
    }
    public List<Card> getCards() {
        return this.cards;
    }
    public void setPiece(PlayerPiece piece){
        this.piece = piece;
        piece.setBelongsTo(this);
    }
    public PlayerPiece getPiece(){
        return this.piece;
    }
    public String toString(){
        return this.getName();
    }
    public void fillSlip(){
        for(Card card: this.cards){
            piece.getSlip().markSlip(card, true);
        }
    }
}
