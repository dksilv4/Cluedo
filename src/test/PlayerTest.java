
import code.Player;
import code.SuspectCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player;
    SuspectCard suspectCard;

    @Before
    public void setUp(){
        this.suspectCard = new SuspectCard("PlayerPieceTest");
        this.player = new Player("Diogo");
    }

    @Test
    public void testGetName(){
        assertEquals("Diogo", this.player.getName());
    }
}
