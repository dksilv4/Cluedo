import code.Envelope;
import code.RoomCard;
import code.SuspectCard;
import code.WeaponCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EnvelopeTest {

    Envelope envelope = new Envelope(new RoomCard("c"), new WeaponCard("b"), new SuspectCard("a"));

    @Before
    public void setUp(){

    }

    @Test
    public void testGetRoom(){
        assertEquals("c", this.envelope.getRoom().getName());
    }

    @Test
    public void testGetWeapon(){
        assertEquals("b", this.envelope.getWeapon().getName());
    }

    @Test
    public void testGetSuspect(){
        assertEquals("a", this.envelope.getSuspect().getName());
    }

    @Test
    public void testToString(){
        assertEquals("The murder was committed by a with b in c!!!", this.envelope.toString());
    }
}
