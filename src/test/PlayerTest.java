

import code.Player;
import code.PlayerPiece;
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
    public void testGetName(){
        assertEquals("Diogo", this.player.getName());
    }

    @Test
    public void testGetPlayerPiece(){
        assertEquals(this.playerPiece, this.player.getPlayerPiece());
    }
}
