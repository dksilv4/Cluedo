package code;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import code.PlayerPiece;

/**
 * Board represents a game board
 */
public class Board {
    private final String board_data;
    private final Grid grid;
    private List<List<Object>> board_data_list;
    private List<Room> rooms;

    /**
     * Constructs and initializes a Board
     */
    public Board() {
        this.board_data = new Data("board.txt").getBoard();
        this.grid = new Grid();
        this.grid.makeGrid(28, 24);
        this.grid.print();
        this.board_data_list = this.Lexer();
        System.out.println(board_data_list);
        this.getRoomDoors(board_data_list);
        this.cleanBoardData();
        this.placeRooms();
        this.rooms = this.getRooms();



    }

    public List<Tile> getSpawns(){
        Room room = this.getRoom("X");
        return room.getTiles();
    }

    public Room getRoom(String roomName) {
        for (Room room : this.rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        throw new RoomNotFoundException(roomName);
    }


    /**
     * Returns board_data_list of the object
     *
     * @return List<List < Object>> board_data_list
     */
    public List<List<Object>> getBoardDataList() {
        return this.board_data_list;
    }

    /**
     * main method
     *
     * @param args
     */
    public static void main(String[] args) {
        Board board = new Board();
    }

    /**
     * Returns grid of the object
     *
     * @return Grid object
     */
    public Grid getGrid() {
        return this.grid;
    }

    /**
     * Returns rooms in a row
     *
     * @param row
     * @return List<Room>
     */
    public List<Room> getRowRooms(List<Object> row) {
        List<Room> rooms = new ArrayList<Room>();
        for (int x = 0; x < row.size(); x++) {
            Object obj = row.get(x);
            System.out.println(obj);
            if (obj instanceof Room) {
                rooms.add((Room) obj);
            }
        }
        return rooms;
    }

    /**
     * @return a list of all rooms in the board.
     */
    public List<Room> getRooms() {
        System.out.println("GET ROOMS");
        List<Room> rooms = new ArrayList<Room>();
        for (List<Object> row : this.board_data_list) {
            System.out.println(row);
            for (Object obj : row) {
                if (obj instanceof Room) {
                    rooms.add((Room) obj);
                }
            }
        }
        System.out.println(rooms);
        return rooms;
    }

    public void setRooms(List<Room> rooms){
        this.rooms = rooms;
    }

    /**
     * Returns spaces in a row
     *
     * @param row
     * @return List<Space>
     */
    public List<Space> getRowSpaces(List<Object> row) {
        List<Space> spaces = new ArrayList<Space>();
        for (int x = 0; x < row.size(); x++) {
            Object obj = row.get(x);
//            System.out.println(obj);
            if (obj instanceof Space) {
                spaces.add((Space) obj);
            }
        }
        return spaces;
    }

    /**
     * Place rooms on a board
     */
    public void placeRooms() {
        System.out.println(this.board_data_list);
        int y_size = 0;
        for (int i = 1; i < this.board_data_list.size(); i += 3) {
            List<Object> row = this.board_data_list.get(i);
//            System.out.println(row);
            List<Room> rooms = this.getRowRooms(row);
//            System.out.println("ROOMS:" + rooms);
            List<Space> spaces = this.getRowSpaces(row);
//            System.out.println("SPACES:" + spaces);
            int size_x = 0;
            int space = 0;
            int size_y = 0;
            for (int x = 0; x < rooms.size(); x++) {
                Room room = rooms.get(x);
//                System.out.println("Room: " + room);
//                System.out.println("RoomX:" + room.getSizeX() + "\nRoomY:" + room.getSizeY());
//                System.out.println(x);
//                System.out.println(rooms.size());
                if (room.getName().equals("X")) {
                    System.out.println((this.grid.getRows() / 2 - room.getSizeY() / 2) + " " + (this.grid.getColumns() / 2 - room.getSizeX() / 2));
                    this.grid.addRoom(room, this.grid.getRows() / 2 - room.getSizeY() / 2, this.grid.getColumns() / 2 - room.getSizeX() / 2);
//                    this.grid.print();
                    size_x += room.getSizeX() + 3;
                } else {
                    if (spaces.size() > x) {
                        space += spaces.get(x).amount;
//                        System.out.println("SPACE:" + space);
                    }
                    if (size_y < room.getSizeY()) {
                        size_y = room.getSizeY() + 1;
                    }
                    if (i == 1) {

                        if (x == rooms.size() - 1) {
//                            System.out.println(x);
                            this.grid.addRoom(room, 0, this.grid.getColumns() - room.getSizeX());
//                            this.grid.print();
                        } else {
                            this.grid.addRoom(room, 0, size_x);
//                            this.grid.print();
                        }
                        size_x += (space + room.getSizeX());

                    }
                    if (i == 4) {

                        if (x == rooms.size() - 1) {
//                            System.out.println(x);
                            this.grid.addRoom(room, y_size, this.grid.getColumns() - room.getSizeX());
//                            this.grid.print();
                        } else {
                            this.grid.addRoom(room, y_size, size_x);
//                            this.grid.print();
                        }
                        size_x += (space + room.getSizeX() + 1);

                    }
                    if (i == 7) {
                        if (x == rooms.size() - 1) {
//                            System.out.println(x);
                            this.grid.addRoom(room, y_size, this.grid.getColumns() - room.getSizeX());
//                            this.grid.print();
                        } else {
                            this.grid.addRoom(room, y_size, size_x);
//                            this.grid.print();
                        }
                        size_x += (space + room.getSizeX() + 1);
                    }
                    if (i == 10) {
                        if (x == rooms.size() - 1) {
//                            System.out.println(x);
                            this.grid.addRoom(room, y_size, this.grid.getColumns() - room.getSizeX());
//                            this.grid.print();
                        } else {
                            this.grid.addRoom(room, y_size, size_x);
//                            this.grid.print();
                        }
                        size_x += (space + room.getSizeX() + 1);
                    }
                }
            }
            y_size += size_y;
        }
        this.grid.print();
    }

    public void placePlayerPieces(List<PlayerPiece> playerPieces) {
        for (PlayerPiece playerPiece : playerPieces) {

        }
    }

    /**
     * Lexes a Board
     *
     * @return a lexed board
     */
    public List<List<Object>> Lexer() {
        List<List<Object>> board = new ArrayList<List<Object>>();
        Data data = new Data("data.json");
        for (String row : this.board_data.split("\n")) {
            String[] d = row.split(" ");
            List<Object> col_list = new ArrayList<Object>();
            for (String obj : d) {
                if (String.valueOf(obj.charAt(0)).equals("R")) {
                    JSONObject j = (JSONObject) ((JSONObject) ((JSONObject) data.getJsonData().get("OriginalBoard")).get("Rooms")).get(obj.substring(1));
                    String name = (String) j.get("name");
                    List<String> size = Arrays.asList(((String) j.get("size")).split("x"));
                    int x = Integer.parseInt(size.get(1));
                    int y = Integer.parseInt(size.get(0));
                    Room newRoom = new Room(name);

                    newRoom.setSize(x, y);
                    col_list.add(newRoom);
                }
                if (String.valueOf(obj.charAt(0)).equals("d")) {
                    try {
                        if (String.valueOf(obj.charAt(1)).equals("d")) {
                            List<Door> doors = new ArrayList<Door>();
                            doors.add(new Door(0, 0));
                            doors.add(new Door(0, 0));
                            col_list.add(doors);
                        } else {
                            col_list.add(new Door(0, 0));

                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        col_list.add(new Door(0, 0));
                    }
                }
                if (String.valueOf(obj.charAt(0)).equals("x")) {
                    col_list.add(new Space(obj.length()));
                }
            }
            board.add(col_list);
        }
        return board;

    }

    /**
     * Cleans the Board data
     * <p>
     * Merge spaces and remove doors.
     */
    public void cleanBoardData() {
        List<List<Object>> board_data = new ArrayList<>();
        for (List<Object> row : this.board_data_list) {
            List<Object> new_row = new ArrayList<Object>();
            for (Object obj : row) {
                if (!(obj instanceof Door) && !(obj instanceof List)) {
                    if (obj instanceof Space) {
                        if (new_row.size() > 0) {
                            Object lastAdded = new_row.get(new_row.size() - 1);
                            if (lastAdded instanceof Space) {
                                ((Space) lastAdded).setAmount(((Space) lastAdded).amount + 1);
                            } else {
                                new_row.add(obj);
                            }
                        } else {
                            new_row.add(obj);
                        }

                    } else {
                        new_row.add(obj);
                    }
                }
            }
            board_data.add(new_row);
        }
        System.out.println("CLEAN BOARD DATA");
        for (int i = 1; i < board_data.size(); i += 3) {
            System.out.println(board_data.get(i));
        }
        System.out.println("CLEAN BOARD DATA END");

        this.board_data_list = board_data;

    }

    /**
     * Prints the list of Board data
     */
    public void printBoardDataList() {
        for (int i = 1; i < this.board_data_list.size(); i += 3) {
            System.out.println(this.board_data_list.get(i));
        }
    }

//    public void print() {
//        System.out.println(this.board);
//        for(int i=1;i<this.board.size();i+=3){
////            System.out.println(this.board.get(i));
////        }
//    }

    /**
     * @param board
     */
    public void getRoomDoors(List<List<Object>> board) {
        for (int i = 1; i < board.size(); i++) {
            List<Object> row = board.get(i);
            for (int x = 0; x < row.size(); x++) {
                Object obj = row.get(x);
                if (obj instanceof Room) {
                    if (i != 0) {
                        Object above_obj = board.get(i - 1).get(x);
                        if (above_obj instanceof Door) {
                            ((Room) obj).addDoor("T");
                        } else if (above_obj instanceof ArrayList) {
                            if (((ArrayList) above_obj).get(0) instanceof Door) {
                                ((Room) obj).addDoor("T");
                                ((Room) obj).addDoor("T");
                            }
                        }
                    }
                    if (i != board.size() - 1 && x != row.size()) {
                        Object below_obj = board.get(i + 1).get(x);
                        if (below_obj instanceof Door) {
                            ((Room) obj).addDoor("B");
                        } else if (below_obj instanceof ArrayList) {
                            if (((ArrayList) below_obj).get(0) instanceof Door) {
                                ((Room) obj).addDoor("B");
                                ((Room) obj).addDoor("B");
                            }
                        }
                    }
                    if (x != 0) {
                        Object prev_obj = row.get(x - 1);
                        if (prev_obj instanceof Door) {
                            ((Room) obj).addDoor("L");
                        }
                    }
                    if (x != row.size() - 1) {
                        Object next_obj = row.get(x + 1);
                        if (next_obj instanceof Door) {
                            ((Room) obj).addDoor("R");
                        }
                    }
                }
            }
        }
    }
}
