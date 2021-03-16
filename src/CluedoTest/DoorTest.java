package CluedoTest;

import Cluedo.Door;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoorTest {
    Door door = new Door(2, 3);

    @Before
    public void setUp() {
    }

    @Test
    public void testToString() {
        assertEquals("\u001B[31md\u001B[0m", this.door.toString());
    }

    @Test
    public void testGetRow() {
        assertEquals(2, this.door.getRow());
    }

    @Test
    public void testGetColumn() {
        assertEquals(3, this.door.getColumn());
    }

    @Test
    public void testGetType() {
        assertEquals("door", this.door.getType());
    }
}
