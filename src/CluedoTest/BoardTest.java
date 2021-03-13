package CluedoTest;

import Cluedo.Board;
import Cluedo.Door;
import Cluedo.Room;
import Cluedo.Space;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    List<Object> row = new ArrayList<Object>();
    Board board = new Board();
    List<List<Object>> lexedBoard = this.board.Lexer();


    @Before
    public void setUp() {
        Room room = new Room("Hayden");
        room.setSize(10, 10);
        room.addDoor("L");
        Room roomB = new Room("Diogo");
        roomB.setSize(20, 5);
        roomB.addDoor("B");
        Space spaceA = new Space(10);
        Space spaceB = new Space(1);

        row.add(room);
        row.add(roomB);
        row.add(spaceA);
        row.add(spaceB);

    }

    @Test
    public void testGetRowRooms() {
        List<Room> rooms = this.board.getRowRooms(this.row);
        assertEquals(2, rooms.size());
        assertEquals(true, rooms.get(0) instanceof Room);
        assertEquals(true, rooms.get(1) instanceof Room);
        assertEquals("Hayden", rooms.get(0).getName());
        assertEquals("Diogo", rooms.get(1).getName());
    }

    @Test
    public void testGetSpaces() {

        List<Space> spaces = this.board.getRowSpaces(this.row);
        assertEquals(2, spaces.size());
        assertEquals(true, spaces.get(0) instanceof Space);
        assertEquals(true, spaces.get(1) instanceof Space);
        assertEquals(10, spaces.get(0).getAmount());
        assertEquals(1, spaces.get(1).getAmount());
    }

    @Test
    public void testLexerRow0() {
        //[1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces]
        List<Object> row = this.lexedBoard.get(0);

        for (int x = 0; x < row.size(); x++) {
            Space space = (Space) row.get(x);
            assertEquals(true, space instanceof Space);
            assertEquals(1, space.getAmount());
        }
    }

    @Test
    public void testLexerRow1() {
        //[K, 2 spaces, d, B, d, 2 spaces, d, C]

        List<Object> row = this.lexedBoard.get(1);
        Room roomA = (Room) row.get(0);
        assertEquals(true, roomA instanceof Room);
        assertEquals("Kitchen", roomA.getName());
        assertEquals(6, roomA.getSizeX());
        assertEquals(6, roomA.getSizeY());

        Space spaceA = (Space) row.get(1);
        assertEquals(true, spaceA instanceof Space);
        assertEquals(2, spaceA.getAmount());

        Door doorA = (Door) row.get(2);
        assertEquals(true, doorA instanceof Door);

        Room roomB = (Room) row.get(3);
        assertEquals(true, roomB instanceof Room);
        assertEquals("Ball Room", roomB.getName());
        assertEquals(8, roomB.getSizeX());
        assertEquals(6, roomB.getSizeY());

        Door doorB = (Door) row.get(4);
        assertEquals(true, doorB instanceof Door);

        Space spaceB = (Space) row.get(5);
        assertEquals(true, spaceB instanceof Space);
        assertEquals(2, spaceB.getAmount());

        Door doorC = (Door) row.get(6);
        assertEquals(true, doorC instanceof Door);

        Room roomC = (Room) row.get(7);
        assertEquals(true, roomC instanceof Room);
        assertEquals("Conservatory", roomC.getName());
        assertEquals(6, roomC.getSizeX());
        assertEquals(5, roomC.getSizeY());
    }

    @Test
    public void testLexerRow2() {
        //[d, 1 spaces, 1 spaces, d, 1 spaces, 1 spaces, 1 spaces, 1 spaces]

        List<Object> row = this.lexedBoard.get(2);
        for (int x = 0; x < row.size(); x++) {
            if (x == 0 || x == 3) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            } else {
                Space space = (Space) row.get(x);
                assertEquals(true, space instanceof Space);
                assertEquals(1, space.getAmount());
            }
        }

    }

    @Test
    public void testLexerRow3() {
        //[1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces]

        List<Object> row = this.lexedBoard.get(3);
        for (int x = 0; x < row.size(); x++) {
            Space space = (Space) row.get(x);
            assertEquals(true, space instanceof Space);
        }
    }

    @Test
    public void testLexerRow4() {
        //[D, d, 1 spaces, X, 1 spaces, 1 spaces, d, B]

        List<Object> row = this.lexedBoard.get(4);
        for (int x = 0; x < row.size(); x++) {
            if (x == 0) {
                Room room = (Room) row.get(x);
                assertEquals(true, room instanceof Room);
                assertEquals("Dining Room", room.getName());
                assertEquals(8, room.getSizeX());
                assertEquals(7, room.getSizeY());
            } else if (x == 1 || x == 6) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            }
            if (x == 2 || x == 4 || x == 5) {
                Space space = (Space) row.get(x);
                assertEquals(true, space instanceof Space);
            }
            if (x == 3) {
                Room room = (Room) row.get(x);
                assertEquals(true, room instanceof Room);
                assertEquals("X", room.getName());
                assertEquals(5, room.getSizeX());
                assertEquals(7, room.getSizeY());
            }
            if (x == 7) {
                Room room = (Room) row.get(x);
                assertEquals(true, room instanceof Room);
                assertEquals("Billiard", room.getName());
                assertEquals(6, room.getSizeX());
                assertEquals(5, room.getSizeY());
            }
        }
    }

    @Test
    public void testLexerRow5() {
        //[d, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, d]
        List<Object> row = this.lexedBoard.get(5);
        for (int x = 0; x < row.size(); x++) {
            if (x == 0 || x == 7) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            } else {
                Space space = (Space) row.get(x);
                assertEquals(true, space instanceof Space);
                assertEquals(1, space.getAmount());
            }
        }
    }

    @Test
    public void testLexerRow6() {
        //[1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, d]
        List<Object> row = this.lexedBoard.get(6);
        for (int x = 0; x < row.size(); x++) {
            if (x == 7) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            } else {
                Space space = (Space) row.get(x);
                assertEquals(1, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
        }
    }

    @Test
    public void testLexerRow7() {
        //[1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, d, L]
        List<Object> row = this.lexedBoard.get(7);
        for (int x = 0; x < row.size(); x++) {
            if (x == 7) {
                Room room = (Room) row.get(x);
                assertEquals(true, room instanceof Room);
                assertEquals("Library", room.getName());
            } else if (x == 6) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            } else {
                Space space = (Space) row.get(x);
                assertEquals(1, space.getAmount());
                assertEquals(true, space instanceof Space);
            }

        }
    }

    @Test
    public void testLexerRow8() {
        //[1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces]
        List<Object> row = this.lexedBoard.get(8);
        for (int x = 0; x < row.size(); x++) {
            Space space = (Space) row.get(x);
            assertEquals(1, space.getAmount());
            assertEquals(true, space instanceof Space);
        }
    }

    @Test
    public void testLexerRow9() {
        //[d, 1 spaces, 1 spaces, [d, d], 1 spaces, 1 spaces, 1 spaces, d]
        List<Object> row = this.lexedBoard.get(9);
        for (int x = 0; x < row.size(); x++) {
            if (x == 0 || x == 7) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            } else if (x == 3) {
                List<Door> doors = (List<Door>) row.get(x);
                for (Door door : doors) {
                    assertEquals(true, door instanceof Door);
                }
            } else {
                Space space = (Space) row.get(x);
                assertEquals(1, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
        }
    }

    @Test
    public void testLexerRow10() {
        //[L, 1 spaces, 1 spaces, H, d, 1 spaces, 1 spaces, S]
        List<Object> row = this.lexedBoard.get(10);
        for (int x = 0; x < row.size(); x++) {
            if (x == 0) {
                Room room = (Room) row.get(x);
                assertEquals(true, room instanceof Room);
                assertEquals("Lounge", room.getName());
                assertEquals(7, room.getSizeX());
                assertEquals(6, room.getSizeY());
            } else if (x == 3) {
                Room room = (Room) row.get(x);
                assertEquals(true, room instanceof Room);
                assertEquals("Hall", room.getName());
                assertEquals(5, room.getSizeX());
                assertEquals(7, room.getSizeY());
            } else if (x == 7) {
                Room room = (Room) row.get(x);
                assertEquals(true, room instanceof Room);
                assertEquals("Study", room.getName());
                assertEquals(7, room.getSizeX());
                assertEquals(4, room.getSizeY());
            } else if (x == 4) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            } else {
                Space space = (Space) row.get(x);
                assertEquals(1, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
        }
    }

    @Test
    public void testLexerRow11() {
        //[1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces, 1 spaces]
        List<Object> row = this.lexedBoard.get(11);
        for (int x = 0; x < row.size(); x++) {
            Space space = (Space) row.get(x);
            assertEquals(1, space.getAmount());
            assertEquals(true, space instanceof Space);
        }
    }

    @Test
    public void testPlaceRooms() {

    }

    @Test
    public void testGetRoomDoors() {

    }

    @Test
    public void testCleanBoardData() {
        this.board.getRooms();
        // 1, 3,4,6,7, 9,12
        for (int x = 0; x < this.board.getBoardDataList().size(); x++) {
            List<Object> row = this.board.getBoardDataList().get(x);
            if(x==0){
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(8, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
            else if(x==1){
                assertEquals(5, row.size());
                Room kitchen = (Room) row.get(0);
                assertEquals(true, kitchen instanceof Room);
                assertEquals("Kitchen", kitchen.getName());
                assertEquals(6, kitchen.getSizeX());
                assertEquals(6, kitchen.getSizeY());

                Space space = (Space) row.get(1);
                assertEquals(2, space.getAmount());
                assertEquals(true, space instanceof Space);

                Room ballroom = (Room) row.get(2);
                assertEquals(true, kitchen instanceof Room);
                assertEquals("BallRoom", kitchen.getName());
                assertEquals(8, kitchen.getSizeX());
                assertEquals(6, kitchen.getSizeY());

                Space spaceB = (Space) row.get(3);
                assertEquals(2, spaceB.getAmount());
                assertEquals(true, spaceB instanceof Space);

            }
            else if(x==2){
                assertEquals(1, row.size());
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(6, space.getAmount());
                assertEquals(true, space instanceof Space);

            }
            else if(x==3){
                assertEquals(1, row.size());
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(8, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
            else if(x==4){
                assertEquals(5, row.size());

            }
            else if(x==5){
                assertEquals(1, row.size());
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(6, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
            else if(x==6){
                assertEquals(1, row.size());
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(7, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
            else if(x==7){
                assertEquals(2, row.size());
            }
            else if(x==8){
                assertEquals(1, row.size());
            }
            else if(x==9){
                assertEquals(3, row.size());
            }
            else if(x==10){
                assertEquals(5, row.size());
            }
            else if(x==11){
                assertEquals(1, row.size());
            }


        }

    }

    @Test
    public void testGetRooms() {
        this.board.getRooms();
    }
}
