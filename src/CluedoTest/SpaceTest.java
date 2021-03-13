package CluedoTest;

import Cluedo.Space;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpaceTest {
    Space space = new Space(2);

    @Test
    public void testToString() {
        assertEquals("2 spaces", this.space.toString());

    }

    @Test
    public void testSetAmount() {
        assertEquals(2, this.space.getAmount());
        this.space.setAmount(4);
        assertEquals(4, this.space.getAmount());

    }

    @Test
    public void testGetAmount() {
        assertEquals(2, this.space.getAmount());
    }
}
