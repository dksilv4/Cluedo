package CluedoTest;

import Cluedo.*;
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
            if (x == 0 ||x == 3 || x == 7) {
                Door door = (Door) row.get(x);
                assertEquals(true, door instanceof Door);
            }  else {
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
    public void testPlaceRoomKitchen() {
        List<Room> rooms = this.board.getRooms();
        Room kitchen = rooms.get(0);
        int tile=0;
        for(int x=0;x<kitchen.getSizeX();x++){
            for(int y=0;y<kitchen.getSizeY();y++){
                Tile testTile = kitchen.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomBallRoom() {
        List<Room> rooms = this.board.getRooms();
        Room ballRoom = rooms.get(1);
        Room kitchen = rooms.get(0);
        int newY = kitchen.getSizeX()+2;
        int tile=0;
        for(int x=0;x<ballRoom.getSizeY();x++){
            for(int y=newY;y<ballRoom.getSizeX()+newY;y++){
                Tile testTile = ballRoom.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomConservatory() {
        List<Room> rooms = this.board.getRooms();
        Room conservatory = rooms.get(2);
        Room kitchen = rooms.get(0);
        Room ballRoom = rooms.get(1);
        int newY = kitchen.getSizeX()+2 + ballRoom.getSizeX()+2;
        int newX = 0;
        int tile=0;
        for(int x=newX;x<conservatory.getSizeY()+newX;x++){
            for(int y=newY;y<conservatory.getSizeX()+newY;y++){
                Tile testTile = conservatory.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomDiningRoom() {
        List<Room> rooms = this.board.getRooms();
        Room diningRoom = rooms.get(3);
        Room conservatory = rooms.get(2);
        Room kitchen = rooms.get(0);
        Room ballRoom = rooms.get(1);
        int newX = kitchen.getSizeY()+1;
        int newY = 0;
        int tile=0;
        for(int x=newX;x<diningRoom.getSizeY()+newX;x++){
            for(int y=newY;y<diningRoom.getSizeX()+newY;y++){
                Tile testTile = diningRoom.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomBilliard() {
        List<Room> rooms = this.board.getRooms();
        Room billiard = rooms.get(5);
        Room kitchen = rooms.get(0);
        int newX = kitchen.getSizeY() + 1;
        int newY = this.board.getGrid().getColumns() - billiard.getSizeX();
        int tile=0;
        for(int x=newX;x<billiard.getSizeY()+newX;x++){
            for(int y=newY;y<billiard.getSizeX()+newY;y++){
                Tile testTile = billiard.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomX() {
        List<Room> rooms = this.board.getRooms();
        Room X = rooms.get(4);
        int newX = this.board.getGrid().getRows()/2 - X.getSizeY()/2;
        int newY = this.board.getGrid().getColumns()/2 - X.getSizeX()/2;
        int tile=0;
        for(int x=newX;x<X.getSizeY()+newX;x++){
            for(int y=newY;y<X.getSizeX()+newY;y++){
                Tile testTile = X.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomLibrary() {
        List<Room> rooms = this.board.getRooms();
        Room library = rooms.get(6);
        Room kitchen = rooms.get(0);
        Room diningRoom = rooms.get(3);
        int newX = kitchen.getSizeY() + 1 + diningRoom.getSizeY()+1;
        int newY = this.board.getGrid().getColumns() - library.getSizeX();
        int tile=0;
        for(int x=newX;x<library.getSizeY()+newX;x++){
            for(int y=newY;y<library.getSizeX()+newY;y++){
                Tile testTile = library.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomLounge() {
        List<Room> rooms = this.board.getRooms();
        Room lounge = rooms.get(7);
        Room library = rooms.get(6);
        Room kitchen = rooms.get(0);
        Room diningRoom = rooms.get(3);
        int newX = kitchen.getSizeY() + 1 + diningRoom.getSizeY()+1 +library.getSizeY()+1;
        int newY = 0;
        int tile=0;
        for(int x=newX;x<lounge.getSizeY()+newX;x++){
            for(int y=newY;y<lounge.getSizeX()+newY;y++){
                Tile testTile = lounge.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomHall() {
        List<Room> rooms = this.board.getRooms();
        Room hall = rooms.get(8);
        Room library = rooms.get(4);
        Room kitchen = rooms.get(0);
        Room diningRoom = rooms.get(3);
        Room lounge = rooms.get(7);
        int newX = kitchen.getSizeY() + diningRoom.getSizeY() +library.getSizeY()+1;
        int newY = lounge.getSizeX() +3;
        int tile=0;
        for(int x=newX;x<hall.getSizeY()+newX;x++){
            for(int y=newY;y<hall.getSizeX()+newY;y++){
                Tile testTile = hall.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testPlaceRoomStudy() {
        List<Room> rooms = this.board.getRooms();
        Room study = rooms.get(8);
        Room hall = rooms.get(8);
        Room library = rooms.get(4);
        Room kitchen = rooms.get(0);
        Room diningRoom = rooms.get(3);
        Room lounge = rooms.get(7);
        int newX = kitchen.getSizeY() + diningRoom.getSizeY() +library.getSizeY()+1;
        int newY = this.board.getGrid().getColumns()/2 - study.getSizeX()/2;
        int tile=0;
        for(int x=newX;x<study.getSizeY()+newX;x++){
            for(int y=newY;y<study.getSizeX()+newY;y++){
                Tile testTile = study.getTiles().get(tile);
                System.out.println(testTile);
                System.out.println(testTile.getRow()+" "+testTile.getColumn());
                System.out.println(x+" "+y);
                tile++;
                assertEquals(x, testTile.getRow());
                assertEquals(y, testTile.getColumn());
            }
        }
    }

    @Test
    public void testCleanBoardData() {
        this.board.getRooms();
        // 1, 3,4,6,7, 9,12
        for (int x = 0; x < this.board.getBoardDataList().size(); x++) {
            List<Object> row = this.board.getBoardDataList().get(x);
            if (x == 0) {
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(8, space.getAmount());
                assertEquals(true, space instanceof Space);
            } else if (x == 1) {
                assertEquals(5, row.size());
                Room kitchen = (Room) row.get(0);
                assertEquals(true, kitchen instanceof Room);
                assertEquals("Kitchen", kitchen.getName());
                assertEquals(6, kitchen.getSizeX());
                assertEquals(6, kitchen.getSizeY());

            } else if (x == 2) {
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(6, space.getAmount());
                assertEquals(true, space instanceof Space);

            } else if (x == 3) {
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(8, space.getAmount());
                assertEquals(true, space instanceof Space);
            } else if (x == 4) {
                assertEquals(5, row.size());

                Room d = (Room) row.get(0);
                assertEquals(true, d instanceof Room);
                assertEquals("Dining Room", d.getName());
                assertEquals(8, d.getSizeX());
                assertEquals(7, d.getSizeY());

                Space space = (Space) row.get(1);
                assertEquals(1, space.getAmount());
                assertEquals(true, space instanceof Space);

                Room X = (Room) row.get(2);
                assertEquals(true, X instanceof Room);
                assertEquals("X", X.getName());
                assertEquals(5, X.getSizeX());
                assertEquals(7, X.getSizeY());

                Space spaceB = (Space) row.get(3);
                assertEquals(2, spaceB.getAmount());
                assertEquals(true, space instanceof Space);

                Room billiard = (Room) row.get(4);
                assertEquals(true, billiard instanceof Room);
                assertEquals("Billiard", billiard.getName());
                assertEquals(6, billiard.getSizeX());
                assertEquals(5, billiard.getSizeY());
            } else if (x == 5) {
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(6, space.getAmount());
                assertEquals(true, space instanceof Space);
            } else if (x == 6) {
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(7, space.getAmount());
                assertEquals(true, space instanceof Space);
            } else if (x == 7) {
                assertEquals(2, row.size());
                Space space = (Space) row.get(0);
                assertEquals(6, space.getAmount());
                assertEquals(true, space instanceof Space);

                Room Library = (Room) row.get(1);
                assertEquals(true, Library instanceof Room);
                assertEquals("Library", Library.getName());
                assertEquals(6, Library.getSizeX());
                assertEquals(5, Library.getSizeY());

            } else if (x == 8) {
                assertEquals(1, row.size());
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(8, space.getAmount());
                assertEquals(true, space instanceof Space);
            } else if (x == 9) {
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(5, space.getAmount());
                assertEquals(true, space instanceof Space);
            } else if (x == 10) {
                assertEquals(5, row.size());

                Room lounge = (Room) row.get(0);
                assertEquals(true, lounge instanceof Room);
                assertEquals("Lounge", lounge.getName());
                assertEquals(7, lounge.getSizeX());
                assertEquals(6, lounge.getSizeY());

                Space space = (Space) row.get(1);
                assertEquals(2, space.getAmount());
                assertEquals(true, space instanceof Space);

                Room hall = (Room) row.get(2);
                assertEquals(true, hall instanceof Room);
                assertEquals("Hall", hall.getName());
                assertEquals(5, hall.getSizeX());
                assertEquals(7, hall.getSizeY());

                space = (Space) row.get(3);
                assertEquals(2, space.getAmount());
                assertEquals(true, space instanceof Space);

                Room study = (Room) row.get(4);
                assertEquals(true, study instanceof Room);
                assertEquals("Study", study.getName());
                assertEquals(7, study.getSizeX());
                assertEquals(4, study.getSizeY());
            } else if (x == 11) {
                assertEquals(1, row.size());
                assertEquals(1, row.size());
                Space space = (Space) row.get(0);
                assertEquals(8, space.getAmount());
                assertEquals(true, space instanceof Space);
            }
        }
    }

    @Test
    public void testGetRoomsAndGetRoomDoors() {
        List<Room> room = this.board.getRooms();
        for (int i = 0; i < room.size(); i++) {
            if (i == 0) {
                Room kitchen = (Room) room.get(0);
                assertEquals(true, kitchen instanceof Room);
                assertEquals("Kitchen", kitchen.getName());
                assertEquals(6, kitchen.getSizeX());
                assertEquals(6, kitchen.getSizeY());
                List<String> doors = kitchen.getDoors();
                assertEquals(1, doors.size());
                assertEquals("B", doors.get(0));
            }
            if (i == 1) {
                Room ballroom = (Room) room.get(1);
                assertEquals(true, ballroom instanceof Room);
                assertEquals("Ball Room", ballroom.getName());
                assertEquals(8, ballroom.getSizeX());
                assertEquals(6, ballroom.getSizeY());
                List<String> doors = ballroom.getDoors();
                assertEquals(3, doors.size());
                assertEquals("B", doors.get(0));
                assertEquals("L", doors.get(1));
                assertEquals("R", doors.get(2));


            }
            if (i == 2) {
                Room conservatory = (Room) room.get(2);
                assertEquals(true, conservatory instanceof Room);
                assertEquals("Conservatory", conservatory.getName());
                assertEquals(6, conservatory.getSizeX());
                assertEquals(5, conservatory.getSizeY());
                List<String> doors = conservatory.getDoors();
                assertEquals(1, doors.size());
                assertEquals("L", doors.get(0));
            }
            if (i == 3) {
                Room diningRoom = (Room) room.get(3);
                assertEquals(true, diningRoom instanceof Room);
                assertEquals("Dining Room", diningRoom.getName());
                assertEquals(8, diningRoom.getSizeX());
                assertEquals(7, diningRoom.getSizeY());

                List<String> doors = diningRoom.getDoors();
                assertEquals(2, doors.size());
                assertEquals("B", doors.get(0));
                assertEquals("R", doors.get(1));
            }
            if (i == 4) {
                Room X = (Room) room.get(4);
                assertEquals(true, X instanceof Room);
                assertEquals("X", X.getName());
                assertEquals(5, X.getSizeX());
                assertEquals(7, X.getSizeY());

                List<String> doors = X.getDoors();
                assertEquals(0, doors.size());
            }
            if (i == 5) {
                Room billiard = (Room) room.get(5);
                assertEquals(true, billiard instanceof Room);
                assertEquals("Billiard", billiard.getName());
                assertEquals(6, billiard.getSizeX());
                assertEquals(5, billiard.getSizeY());

                List<String> doors = billiard.getDoors();
                assertEquals(2, doors.size());
                assertEquals("B", doors.get(0));
                assertEquals("L", doors.get(1));

            }
            if (i == 6) {
                Room library = (Room) room.get(6);
                assertEquals(true, library instanceof Room);
                assertEquals("Library", library.getName());
                assertEquals(6, library.getSizeX());
                assertEquals(5, library.getSizeY());

                List<String> doors = library.getDoors();
                assertEquals(2, doors.size());
                assertEquals("T", doors.get(0));
                assertEquals("L", doors.get(1));
            }
            if (i == 7) {
                Room lounge = (Room) room.get(7);
                assertEquals(true, lounge instanceof Room);
                assertEquals("Lounge", lounge.getName());
                assertEquals(7, lounge.getSizeX());
                assertEquals(6, lounge.getSizeY());

                List<String> doors = lounge.getDoors();
                assertEquals(1, doors.size());
                assertEquals("T", doors.get(0));
            }

            if (i == 8) {
                Room hall = (Room) room.get(8);
                assertEquals(true, hall instanceof Room);
                assertEquals("Hall", hall.getName());
                assertEquals(5, hall.getSizeX());
                assertEquals(7, hall.getSizeY());

                List<String> doors = hall.getDoors();
                assertEquals(2, doors.size());
                assertEquals("T", doors.get(0));
                assertEquals("R", doors.get(1));
            }
            if (i == 9) {
                Room study = (Room) room.get(9);
                assertEquals(true, study instanceof Room);
                assertEquals("Study", study.getName());
                assertEquals(7, study.getSizeX());
                assertEquals(4, study.getSizeY());

                List<String> doors = study.getDoors();
                assertEquals(1, doors.size());
                assertEquals("T", doors.get(0));
            }
        }
    }
}
