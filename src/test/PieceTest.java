import code.Piece;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PieceTest {

    Piece piece = new Piece("byPiece");

    @Before
    public void setUp(){
    }

    @Test
    public void testGetName(){
        assertEquals("byPiece", this.piece.getName());
    }

    @Test
    public void testSetName(){
        assertEquals("byPiece", this.piece.getName());
        this.piece.setName("a");
        assertEquals("a", this.piece.getName());
    }
}
