package CluedoTest;

import Cluedo.Door;
import Cluedo.InvalidRoomNameException;
import Cluedo.Room;
import Cluedo.Tile;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoomTest {
    Room room = new Room("Room");
    @Before
    public void setUp() {
        this.room.setSize(100, 200);
    }

    @Test
    public void testGetTiles() {
        Tile tile1 = new Tile("wall", 2, 4);
        Tile tile2 = new Tile("room", 3, 5);
        this.room.addTile(tile1);
        this.room.addTile(tile2);
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile1);
        tiles.add(tile2);
        for(int i = 0; i < 2; i++){
            assertEquals(tiles.get(i), this.room.getTiles().get(i));
        }
    }

    @Test
    public void testGetDoors(){
        ArrayList<String> doors = new ArrayList<String>();
        doors.add("T");
        doors.add("R");
        doors.add("L");
        doors.add("B");
        this.room.addDoors(doors);
        for(int i = 0; i < 4; i++){
            assertEquals(doors.get(i), this.room.getDoors().get(i));
        }
    }

    @Test
    public void testToString() {
    assertEquals("\u001B[32m" + this.room.getName().charAt(0) + "\u001B[0m", this.room.toString());
    }

    @Test
    public void testGetSizeX() {
        assertEquals(200, this.room.getSizeX());
    }

    @Test
    public void testGetSizeY() {
        assertEquals(100, this.room.getSizeY());
    }

    @Test
    public void testAddDoor() {
        assertEquals(0, this.room.getDoors().size());
        this.room.addDoor("T");
        assertEquals("T", this.room.getDoors().get(0));
        assertEquals(1, this.room.getDoors().size());
        this.room.addDoor("L");
        assertEquals("L", this.room.getDoors().get(1));
        assertEquals(2, this.room.getDoors().size());
        this.room.addDoor("R");
        assertEquals("R", this.room.getDoors().get(2));
        assertEquals(3, this.room.getDoors().size());
        this.room.addDoor("B");
        assertEquals("B", this.room.getDoors().get(3));
        assertEquals(4, this.room.getDoors().size());
    }

    @Test
    public void addDoors() {
        List<String> doors = new ArrayList<String>();
        doors.add("L");
        doors.add("R");
        doors.add("B");
        this.room.addDoors(doors);

        assertEquals(3, this.room.getDoors().size());
        assertEquals("L", this.room.getDoors().get(0));
        assertEquals("R", this.room.getDoors().get(1));
        assertEquals("B", this.room.getDoors().get(2));

    }

    @Test
    public void testSetSize() {
        this.room.setSize(400, 500);
        assertEquals(400, this.room.getSizeY());
        assertEquals(500, this.room.getSizeX());
    }

    @Test
    public void testAddTile() {
        this.room.addTile(new Tile("wall", 3, 5));
        assertEquals(1, this.room.getTiles().size());
    }

    @Test(expected = InvalidRoomNameException.class)
    public void testCheckNameError() {
        Room testRoom = new Room("roergergreghaergaergaergae");
    }

    @Test
    public void testCheckNameValid() {
        Room testRoom = new Room("gae");

    }

}
