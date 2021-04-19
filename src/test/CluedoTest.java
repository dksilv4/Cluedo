import java.util.*;

import org.junit.Before;
import org.junit.Test;
import code.*;

import static org.junit.Assert.*;

public class CluedoTest {
    private Cluedo cluedo;

    @Before
    public void setUp() {
        this.cluedo = new Cluedo();
        Player playerA = new Player("a");
        Player playerB = new Player("b");
        List<Player> p = new ArrayList<>();
        p.add(playerA);
        p.add(playerB);
        this.cluedo.setPlayers(p);
        this.cluedo.setSuspectCardsAndPlayerPieces();
    }

    @Test
    public void testAssignPlayerPiecesAndSetPlayerOrder(){
        Map<Player, Integer> playerHash = new HashMap<>();
        int a = 11;
        int b = 10;

        playerHash.put(this.cluedo.getPlayers().get(0), a);
        playerHash.put(this.cluedo.getPlayers().get(1), b);
        this.cluedo.assignPlayerPieces(playerHash);
        this.cluedo.setPlayerOrder(playerHash);
        assertEquals("Miss Scarlett", this.cluedo.getPlayers().get(0).getPiece().getName());
        assertEquals("Prof Plum", this.cluedo.getPlayers().get(1).getPiece().getName());

    }

    @Test
    public void testSetUpDetectiveSlips(){
        this.cluedo.setWeaponCardsAndPieces();
        this.cluedo.setRoomCards();
        this.cluedo.setSuspectCardsAndPlayerPieces();
        this.cluedo.setUpDetectiveSlips();

        Map<Card, Boolean> cards = new HashMap<>();
        for(Card card : this.cluedo.getWeaponCards()){
            cards.put(card, false);
        }
        for(Card card : this.cluedo.getSuspectCards()){
            cards.put(card, false);
        }
        for(Card card : this.cluedo.getRoomCards()){
            cards.put(card, false);
        }
        for(PlayerPiece piece : this.cluedo.getPlayerPieces()){
            assertEquals(cards, piece.getSlip().getCards());
        }
    }

    @Test
    public void testSetSpawns(){
        this.cluedo.setSpawns();
        for(PlayerPiece piece : this.cluedo.getPlayerPieces()){
            assertTrue(piece.getLocation() != null);
        }
    }

    @Test
    public void testSetPlayers(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("f"));
        players.add(new Player("o"));
        this.cluedo.setPlayers(players);
        assertEquals(players, this.cluedo.getPlayers());
    }

    @Test
    public void testShuffleCards() {
        this.cluedo.setUpCards();
        List<Card> weapons = new ArrayList<>(this.cluedo.getWeaponCards());
        List<Card> rooms = new ArrayList<>(this.cluedo.getRoomCards());
        List<Card> suspects = new ArrayList<>(this.cluedo.getSuspectCards());

        this.cluedo.shuffleCards();
        assertNotEquals(weapons, this.cluedo.getWeaponCards());
        assertNotEquals(rooms, this.cluedo.getRoomCards());
        assertNotEquals(suspects, (this.cluedo.getSuspectCards()));
    }

    @Test
    public void testSetEnvelope() {
        List<Player> testPlayers = new ArrayList<>();
        testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Hayden"));
        testPlayers.add(new Player("Diogo"));
        this.cluedo.setPlayers(testPlayers);
        this.cluedo.setSuspectCardsAndPlayerPieces();
        this.cluedo.setWeaponCardsAndPieces();
        this.cluedo.setRoomCards();
        this.cluedo.setEnvelope();
        this.cluedo.gatherCards();
        this.cluedo.giveOutCards();

        System.out.println(this.cluedo.getRoomCards());
        assertEquals(9, testPlayers.get(0).getCards().size());
        assertEquals(9, testPlayers.get(1).getCards().size());
    }

    @Test
    public void testGiveOutCards() {
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Hayden"));
        testPlayers.add(new Player("Diogo"));
        this.cluedo.setPlayers(testPlayers);
        this.cluedo.setSuspectCardsAndPlayerPieces();
        this.cluedo.setWeaponCardsAndPieces();
        this.cluedo.setRoomCards();
        this.cluedo.gatherCards();
        this.cluedo.giveOutCards();
        System.out.println(this.cluedo.getRoomCards());
        assertEquals(11, testPlayers.get(0).getCards().size());
        assertEquals(10, testPlayers.get(1).getCards().size());
    }
}
