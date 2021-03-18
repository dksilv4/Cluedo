package code;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> cards = new ArrayList<Card>();
    public Player(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public void addCard(Card card){
        this.cards.add(card);
    }
    public List<Card> getCards(){
        return this.cards;
    }
}
