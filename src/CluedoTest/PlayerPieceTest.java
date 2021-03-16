package CluedoTest;

import Cluedo.PlayerPiece;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerPieceTest {
    private PlayerPiece playerPiece = new PlayerPiece("ii");

    @Before
    public void setUp(){

    }

    @Test
    public void testGetName(){
        assertEquals("ii", this.playerPiece.getName());
    }

    @Test
    public void testToString(){
        assertEquals("PlayerPiece: ii", this.playerPiece.toString());
    }
}
