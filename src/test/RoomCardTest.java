import code.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RoomCardTest {

    RoomCard roomCard = new RoomCard("a");

    @Before
    public void setUp(){

    }

    @Test
    public void testToString(){
        assertEquals("Room Card: a", this.roomCard.toString());
    }
}
