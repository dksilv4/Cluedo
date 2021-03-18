package CluedoTest;

import Cluedo.Tile;
import Cluedo.Room;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileTest {
    Tile tile = new Tile("room", 1, 2);
    Room room = new Room("ra");

    @Before
    public void setUp() {

    }

    @Test
    public void testIsSetOccupied() {
        this.tile.setOccupied(true);
        assertEquals(true, this.tile.isOccupied());
        this.tile.setOccupied(false);
        assertEquals(false, this.tile.isOccupied());
    }

    @Test
    public void testGetType() {
        assertEquals("room", this.tile.getType());
    }

    @Test
    public void testGetRow() {
        assertEquals(1, this.tile.getRow());
    }

    @Test
    public void testGetColumn() {
        assertEquals(2, this.tile.getColumn());
    }

    @Test
    public void testGetSetBelongsTo() {
        this.tile.setBelongsTo(room);
        assertEquals(room, this.tile.getBelongsTo());
    }

    @Test
    public void testIsAvailable() {
        this.tile.setOccupied(false);
        assertEquals(true, this.tile.isAvailable());
        this.tile.setType("wall");
        assertEquals(false, this.tile.isAvailable());
        this.tile.setOccupied(true);
        assertEquals(false, this.tile.isAvailable());

    }

    @Test
    public void testSetType() {
        this.tile.setType("door");
        assertEquals("door", this.tile.getType());
        this.tile.setType("room");
        assertEquals("room", this.tile.getType());
    }

    @Test
    public void testToString() {
        this.tile.setType("room");
        this.tile.setBelongsTo(room);
        assertEquals("\u001B[32m" + this.room.getName().charAt(0) + "\u001B[0m", this.tile.toString());
        this.tile.setType("");
        assertEquals("x", this.tile.toString());
    }
}
