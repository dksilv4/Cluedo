package CluedoTest;

import Cluedo.PlayerPiece;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerPieceTest {
    private PlayerPiece playerPiece;
    @Before
    public void setUp(){
        this.playerPiece = new PlayerPiece("PPT");
    }
    @Test
    public void testCreation(){
        assertEquals("PPT", this.playerPiece.name);
    }
    @Test
    public void testToString(){
        assertEquals("PlayerPiece: PPT", this.playerPiece.toString());
    }
}
