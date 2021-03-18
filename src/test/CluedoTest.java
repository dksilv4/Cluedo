package code;

import code.Cluedo;
import code.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CluedoTest {
    private Cluedo cluedo;

    @Before
    public void setUp() {
        this.cluedo = new Cluedo();
    }

    @Test
    public void testGetPlayerPieces() {
        assertEquals(6, this.cluedo.getSuspectCards().size());

//        System.out.println(this.cluedo.getPlayerPieces());
    }

    @Test
    public void testShuffleSuspectCards() {

    }

    @Test
    public void testShuffleWeaponCards() {

    }

    @Test
    public void testShuffleRoomCards() {

    }

    @Test
    public void testSetEnvelope() {
        List<Player> testPlayers = new ArrayList<>();
        testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Hayden"));
        testPlayers.add(new Player("Diogo"));
        this.cluedo.setPlayers(testPlayers);
        this.cluedo.setSuspectCards();
        this.cluedo.setWeaponCards();
        this.cluedo.setRoomCards();
        this.cluedo.setEnvelope();
        this.cluedo.gatherCards();
        this.cluedo.giveOutCards();

        System.out.println(this.cluedo.getRoomCards());
        assertEquals(9, testPlayers.get(0).getCards().size());
        assertEquals(9, testPlayers.get(1).getCards().size());
    }

    @Test
    public void testGatherCards() {

    }

    @Test
    public void testShareCards() {
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(new Player("Hayden"));
        testPlayers.add(new Player("Diogo"));
        this.cluedo.setPlayers(testPlayers);
        this.cluedo.setSuspectCards();
        this.cluedo.setWeaponCards();
        this.cluedo.setRoomCards();
        this.cluedo.gatherCards();
        this.cluedo.giveOutCards();
        System.out.println(this.cluedo.getRoomCards());
        assertEquals(11, testPlayers.get(0).getCards().size());
        assertEquals(10, testPlayers.get(1).getCards().size());
    }
}
