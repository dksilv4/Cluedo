import code.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DetectiveSlipTest {

    DetectiveSlip slip;
    List<WeaponCard> weaponCards = new ArrayList<>();
    List<SuspectCard> suspectCards = new ArrayList<>();
    List<RoomCard> roomCards = new ArrayList<>();
    List<String> weaponCardStrings;
    List<String> suspectCardStrings;
    List<String> roomCardStrings;

    @Before
    public void setUp(){
        this.weaponCardStrings = new ArrayList<String>();
        weaponCardStrings.add("Pikachu");
        weaponCardStrings.add("Tortoise");
        weaponCardStrings.add("Arceus");
        for(String s: this.weaponCardStrings){
            weaponCards.add(new WeaponCard(s));
        }
        this.suspectCardStrings = new ArrayList<String>();
        suspectCards.add(new SuspectCard("Dramon"));
        suspectCards.add(new SuspectCard("Batmon"));
        suspectCards.add(new SuspectCard("Cumon"));

        roomCards.add(new RoomCard("Gotham"));
        roomCards.add(new RoomCard("Staples"));
        roomCards.add(new RoomCard("McDonalds"));

        this.slip = new DetectiveSlip(suspectCards, weaponCards, roomCards);

    }

    @Test
    public void testAddWeaponCards(){
        List<Card> cards = new ArrayList<>(this.slip.getCards().keySet());
        for(WeaponCard card: this.weaponCards){
            assertTrue(cards.contains(card));
            assertEquals(false, this.slip.getCards().get(card));
        }
    }

    @Test
    public void testAddSuspectCards(){
        List<Card> cards = new ArrayList<>(this.slip.getCards().keySet());
        for(SuspectCard card: this.suspectCards){
            assertTrue(cards.contains(card));
            assertEquals(false, this.slip.getCards().get(card));
        }
    }

    @Test
    public void testAddRoomCards(){
        List<Card> cards = new ArrayList<>(this.slip.getCards().keySet());
        for(RoomCard card: this.roomCards){
            assertTrue(cards.contains(card));
            assertEquals(false, this.slip.getCards().get(card));
        }
    }

    @Test
    public void testCheckAndMarkSlip(){
        for(Card card: this.slip.getCards().keySet()){
            this.slip.markSlip(card, false);
            assertEquals(false, this.slip.getCards().get(card));
            this.slip.markSlip(card, true);
            assertEquals(true, this.slip.getCards().get(card));
        }
    }

    @Test
    public void testAddCard(){
        int n = this.slip.getCards().size();
        WeaponCard newWeapon = new WeaponCard("asd");
        this.slip.addCard(newWeapon, true);
        List<Card> cards = new ArrayList<>(this.slip.getCards().keySet());
        System.out.println(cards);
        assertEquals(n + 1, this.slip.getCards().size());
        assertEquals(true, this.slip.getCards().get(newWeapon));
    }

}
