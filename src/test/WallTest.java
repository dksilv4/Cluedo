package CluedoTest;
import Cluedo.Tile;
import Cluedo.Wall;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WallTest {
    Wall wall = new Wall(1, 6);
    @Before
    public void setUp(){}
    @Test
    public void testToString(){
        assertEquals("\u001B[34mw\u001B[0m", this.wall.toString());
    }
    @Test
    public void testGetRow(){
        assertEquals(1, this.wall.getRow());
    }
    @Test
    public void testGetColumn(){
        assertEquals(6, this.wall.getColumn());
    }
    @Test
    public void testGetType(){
        assertEquals("wall", this.wall.getType());
    }
    @Test
    public void testType(){
        assertEquals(true, (this.wall instanceof Wall));
        assertEquals(true, (this.wall instanceof Tile));

    }
}