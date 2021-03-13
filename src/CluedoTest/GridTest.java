package CluedoTest;

import Cluedo.*;
import com.sun.org.apache.xpath.internal.axes.AxesWalker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GridTest {
    Grid grid;
    @Before
    public void setUp(){
        this.grid = new Grid();
        this.grid.makeGrid(10,10);
    }
    @Test
    public void testMakeGrid(){
        System.out.println(this.grid.getGrid());
        assertEquals(10, this.grid.getGrid().size());
        for(int i=0;i<10;i++){
            assertEquals(10, this.grid.getGrid().get(i).size());
        }
    }
    @Test
    public void testDeleteGrid(){
        this.grid.deleteGrid();
        assertEquals(new ArrayList<List<Tile>>(), this.grid.getGrid());
    }

    @Test
    public void testAddWallTile(){
        assertEquals(true, this.grid.getTile(1, 1) instanceof Tile);
        assertEquals(false, this.grid.getTile(1, 1) instanceof Wall);
        this.grid.addWallTile(1,1);
        assertEquals(true, this.grid.getTile(1, 1) instanceof Tile);
        assertEquals(true, this.grid.getTile(1, 1) instanceof Wall);
    }

    @Test
    public void testAddDoorTile(){
        assertEquals(true, this.grid.getTile(1, 1) instanceof Tile);
        assertEquals(false, this.grid.getTile(1, 1) instanceof Door);
        this.grid.addDoorTile(1,1);
        assertEquals(true, this.grid.getTile(1, 1) instanceof Tile);
        assertEquals(true, this.grid.getTile(1, 1) instanceof Door);
    }
    @Test
    public void testGetTile(){
        this.grid.addDoorTile(1, 1);
        assertEquals("door", this.grid.getTile(1, 1).getType());
        this.grid.addWallTile(1, 1);
        assertEquals("wall", this.grid.getTile(1, 1).getType());
    }
    @Test
    public void testGetGrid(){
        assertEquals(10, this.grid.getGrid().size());
        for(int i=0;i<10;i++){
            assertEquals(10, this.grid.getGrid().get(i).size());
        }
        assertEquals(this.grid.getGrid(), this.grid.getGrid());
    }
//    @Test
//    public void testChangeAndGetTile(){
//        Tile tile = new Tile("space", 1, 1);
//        this.grid.changeTile(1, 1, tile);
//        assertEquals(this.grid.getTile(1, 1), tile);
//    }

    @Test
    public void testAddRoom(){
        Room room = new Room("A");
        room.setSize(7, 7);
        this.grid.addRoom(room, 1, 1);
        this.grid.print();
        for(int x=2;x<7;x++){
            for(int y=2;y<7;y++){
                Tile tile = this.grid.getTile(x, y);
                assertEquals("room", tile.getType());
            }
        }
    }
    @Test
    public void testAddRoomDoors(){
        Room room = new Room("A");
        room.setSize(7, 7);
        room.addDoor("L");
        room.addDoor("T");
        room.addDoor("R");
        room.addDoor("B");
        this.grid.addRoom(room, 1, 1);
        this.grid.print();
        assertEquals("door", this.grid.getTile(1, 4).getType());//TOP
        assertEquals("door", this.grid.getTile(4, 7).getType());//RIGHT
        assertEquals("door", this.grid.getTile(7, 4).getType());//BOTTOM
        assertEquals("door", this.grid.getTile(4, 1).getType());//LEFT

    }
    @Test
    public void test1(){
        this.grid.deleteGrid();
        this.grid.makeGrid(25, 25);
        Room room = new Room("A");
        room.setSize(7, 5);
        room.addDoor("B");
        this.grid.addRoom(room, 0, 0);
        Room room1 = new Room("B");
        room1.setSize(3, 3);
        room1.addDoor("B");
        room1.addDoor("R");
        room1.addDoor("L");
        this.grid.addRoom(room1, 1, 12);
        this.grid.print();
        Room room2 = new Room("B");
        room2.setSize(6, 3);
        room2.addDoor("B");
        this.grid.addRoom(room2, 0, 17);
        this.grid.print();

    }
}
