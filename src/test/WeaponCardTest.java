import code.WeaponCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WeaponCardTest {

    WeaponCard weaponCard = new WeaponCard("a");

    @Before
    public void setUp(){

    }

    @Test
    public void testToString(){
        assertEquals("Weapon: a", this.weaponCard.toString());
    }
}
