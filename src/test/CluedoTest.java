
import code.Cluedo;
import code.Data;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CluedoTest {
    private Cluedo cluedo;

    @Before
    public void setUp() {
        this.cluedo = new Cluedo();
    }
    @Test
    public void testGetPlayerPieces(){
        assertEquals(6, this.cluedo.getPlayerPieces().size());

//        System.out.println(this.cluedo.getPlayerPieces());
    }



}
