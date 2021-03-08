package CluedoTest;

import Cluedo.Player;
import Cluedo.PlayerPiece;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player;
    PlayerPiece playerPiece;
    @Before
    public void setUp(){
        this.playerPiece = new PlayerPiece("PlayerPieceTest");
        this.player = new Player("Diogo", this.playerPiece);
    }
    @Test
    public void testCreation(){
        assertEquals("Diogo", this.player.name);
        assertEquals("PlayerPieceTest", this.player.playerPiece.name);
    }
}
