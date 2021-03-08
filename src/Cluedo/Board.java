package Cluedo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Board represents a game board
 */
public class Board {
    String board_data;
    Grid grid;
    List<List<Object>> board_data_list;

    /**
     * Constructs and initializes a Board
     */
    public Board() {
        this.board_data = new Data("board.txt").board;
        this.grid = new Grid(25, 25);
        this.grid.print();
        this.board_data_list = this.Lexer();
        this.getRoomDoors(board_data_list);
        this.cleanBoardData();
        this.placeRooms();
    }

    /**
     * main method
     *
     * @param args
     */
    public static void main(String[] args) {
        Board board = new Board();
    }

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
    public List<Space> getRowSpaces(List<Object> row) {
        List<Space> spaces = new ArrayList<Space>();
        for (int x = 0; x < row.size(); x++) {
            Object obj = row.get(x);
            System.out.println(obj);
            if (obj instanceof Space) {
                spaces.add((Space) obj);
            }
        }
        return spaces;
    }

    public void placeRooms() {
        System.out.println(this.board_data_list);
        int y_size = 0;
        for (int i = 1; i < this.board_data_list.size(); i += 3) {
            List<Object> row = this.board_data_list.get(i);
            System.out.println(row);
            List<Room> rooms = this.getRowRooms(row);
            System.out.println("ROOMS:" + rooms);
            List<Space> spaces = this.getRowSpaces(row);
            System.out.println("SPACES:"+spaces);
            int size_x = 1;
            int space =2;
            for (int x = 0; x < rooms.size(); x++) {
                Room room = rooms.get(x);
                System.out.println("Room: " + room);
                System.out.println("RoomX:"+room.getSizeX() +"\nRoomY:"+room.getSizeY());
                if(spaces.size() > x){
                    space = spaces.get(x).amount;
                    System.out.println("SPACE:"+space);
                }
                if(i ==1){
                    this.grid.addRoom(room, 1, size_x);
                    this.grid.print();
                }
                if(x == 0){
                    size_x += 2;
                }
                size_x += room.getSizeY()+space;
                
            }

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
                    JSONObject j = (JSONObject) ((JSONObject) ((JSONObject) data.jsonData.get("OriginalBoard")).get("Rooms")).get(obj.substring(1, obj.length()));
                    String name = (String) j.get("name");
                    List<String> size = Arrays.asList(((String) j.get("size")).split("x"));
                    Integer x = Integer.parseInt(size.get(0)) - 2;
                    Integer y = Integer.parseInt(size.get(1)) - 2;
                    Room newRoom = new Room(name);

                    newRoom.setSize(x, y);
                    col_list.add(newRoom);
                }
                if (String.valueOf(obj.charAt(0)).equals("d")) {
                    try {
                        if (String.valueOf(obj.charAt(1)).equals("d")) {
                            List<Door> doors = new ArrayList<Door>();
                            doors.add(new Door());
                            doors.add(new Door());
                            col_list.add(doors);
                        } else {
                            col_list.add(new Door());

                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        col_list.add(new Door());
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
     */
    public void cleanBoardData() {
        List<List<Object>> board_data = new ArrayList<>();
        for (List<Object> row : this.board_data_list) {
            List<Object> new_row = new ArrayList<Object>();
            for (Object obj : row) {
                if (!(obj instanceof Door)) {
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
//        for (int i = 1; i < board_data.size(); i += 3) {
//            System.out.println(board_data.get(i));
//        }
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
     * ???
     *
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
//                        System.out.println(above_obj);
                        if (above_obj instanceof Door) {
                            ((Room) obj).doors.add("T");
                        } else if (above_obj instanceof ArrayList) {
                            if (((ArrayList) above_obj).get(0) instanceof Door) {
                                ((Room) obj).doors.add("T");
                                ((Room) obj).doors.add("T");
                            }
                        }
                    }
                    if (i != board.size() - 1 && x != row.size() - 1) {
//                        System.out.println(i);
//                        System.out.println(x);
                        Object below_obj = board.get(i + 1).get(x);
                        if (below_obj instanceof Door) {
                            ((Room) obj).doors.add("B");
                        } else if (below_obj instanceof ArrayList) {
                            if (((ArrayList) below_obj).get(0) instanceof Door) {
                                ((Room) obj).doors.add("B");
                                ((Room) obj).doors.add("B");
                            }
                        }
                    }
                    if (x != 0) {
                        Object prev_obj = row.get(x - 1);
                        if (prev_obj instanceof Door) {
                            ((Room) obj).doors.add("L");
                        }
                    }
                    if (x != row.size() - 1) {
                        Object next_obj = row.get(x + 1);
                        if (next_obj instanceof Door) {
                            ((Room) obj).doors.add("R");
                        }
                    }
                }
            }
        }
    }


}
