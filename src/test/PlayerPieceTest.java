import code.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlayerPieceTest{
    PlayerPiece playerPiece = new PlayerPiece("Hayden");
    DetectiveSlip slip;
    List<WeaponCard> weaponCards = new ArrayList<>();
    List<SuspectCard> suspectCards = new ArrayList<>();
    List<RoomCard> roomCards = new ArrayList<>();
    List<String> weaponCardStrings;
    Tile location = new Tile("x", 3, 5);
    @Before
    public void setUp(){
        this.weaponCardStrings = new ArrayList<String>();
        weaponCardStrings.add("Pikachu");
        for(String s: this.weaponCardStrings){
            weaponCards.add(new WeaponCard(s));
        }
        suspectCards.add(new SuspectCard("Dramon"));
        roomCards.add(new RoomCard("Gotham"));
        this.slip = new DetectiveSlip(suspectCards, weaponCards, roomCards);

    }

    @Test
    public void testSetSlip(){
        this.playerPiece.setSlip(this.slip);
        assertEquals(false, this.playerPiece.getSlip().getCards().get(this.suspectCards.get(0)));
        assertEquals(false, this.playerPiece.getSlip().getCards().get(this.roomCards.get(0)));
        assertEquals(false, this.playerPiece.getSlip().getCards().get(this.weaponCards.get(0)));
    }
    @Test
    public void testSetLocation(){
        this.playerPiece.setLocation(this.location);
        assertEquals(3, this.playerPiece.getLocation().getRow());
        assertEquals(5, this.playerPiece.getLocation().getColumn());

    }

    @Test
    public void testToString(){
        assertEquals("\u001b[33mH\u001B[0m", this.playerPiece.toString());
    }
}
