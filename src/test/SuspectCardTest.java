

import code.SuspectCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SuspectCardTest {
    private SuspectCard suspectCard = new SuspectCard("ii");

    @Before
    public void setUp(){

    }

    @Test
    public void testGetName(){
        assertEquals("ii", this.suspectCard.getName());
    }

    @Test
    public void testToString(){
        assertEquals("PlayerPiece: ii", this.suspectCard.toString());
    }
}
