package code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectiveSlip {
    private final Map<Card, Boolean> cards = new HashMap<Card, Boolean>();

    public DetectiveSlip(List<SuspectCard> suspectCards, List<WeaponCard> weaponCards, List<RoomCard> roomCards) {
        this.addWeaponCards(weaponCards);
        this.addSuspectCards(suspectCards);
        this.addRoomCards(roomCards);
    }

    public void addWeaponCards(List<WeaponCard> cards) {
        for (WeaponCard card : cards) {
            this.cards.put(card, false);
        }
    }

    public void addSuspectCards(List<SuspectCard> cards) {
        for (SuspectCard card : cards) {
            this.cards.put(card, false);
        }
    }

    public void addRoomCards(List<RoomCard> cards) {
        for (RoomCard card : cards) {
            this.cards.put(card, false);
        }
    }

    public Boolean check(Card card) {
        try {
            return cards.get(card);
        } catch (Exception e) {
            System.out.println("No card matched to the name.");
        }
        return null;
    }

    public void addCard(Card card, boolean checked) {
        cards.put(card, checked);
    }

    public void markSlip(Card card, Boolean value) {
        cards.computeIfPresent(card, (k, v) -> value);
    }

    public Map<Card, Boolean> getCards() {
        return this.cards;
    }

}
