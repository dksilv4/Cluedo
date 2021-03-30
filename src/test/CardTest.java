import code.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardTest {

    Card card = new Card("q");

    @Before
    public void setUp(){

    }

    @Test
    public void testGetName(){
        assertEquals("q", this.card.getName());
    }
}
