//package java;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Board represents a game board
// */
//public class Board {
//    String board_data;
//    Grid grid;
//    List<List<Object>> board_data_list;
//
//    /**
//     * Constructs and initializes a Board
//     */
//    public Board() {
//        this.board_data = new Data("board.txt").board;
//        this.grid = new Grid(25, 25);
//        this.grid.print();
//        this.board_data_list = this.Lexer();
//        this.getRoomDoors(board_data_list);
////        this.printBoardDataList();
//        this.cleanBoardData();
////        this.print();
//        List<Object> r = board_data_list.get(1);
//        System.out.println();
//        System.out.println(r);
//        Room a = (Room) r.get(0);
//        this.grid.addRoom(a, 1, 1);
//        Room b = (Room) r.get(2);
//        this.grid.addRoom(b, 1, 3+3+a.size_x);
//        Room c = (Room) r.get(4);
//        this.grid.addRoom(c, 1, 25-1-c.size_y);
//        this.grid.print();
//
//        System.out.println();
//        r = board_data_list.get(4);
//        System.out.println(r);
//
//        Room d = (Room) r.get(0);
//        this.grid.addRoom(d, 3+d.size_y, 1);
//        Room e = (Room) r.get(2);
//        this.grid.addRoom(e, 7+d.size_y, 3+1+d.size_y);
//        Room f = (Room) r.get(4);
//        this.grid.addRoom(f, 3+d.size_y, 3+3+d.size_y+2+e.size_y);
//        this.grid.print();
//        System.out.println();
//        r = board_data_list.get(7);
//        System.out.println(r);
//
//        Room g = (Room) r.get(1);
//        this.grid.addRoom(g, 7+d.size_y+f.size_y, 25-1-g.size_y);
//        this.grid.print();
//    }
//
//    /**
//     * main method
//     * @param args
//     */
////    public static void main(String[] args) {
////        Board board = new Board();
////    }
//
//    /**
//     * Lexes a Board
//     * @return a lexed board
//     */
//    public List<List<Object>> Lexer() {
//        List<List<Object>> board = new ArrayList<List<Object>>();
//        Data data = new Data("data.json");
//        for (String row : this.board_data.split("\n")) {
//            String[] d = row.split(" ");
//            List<Object> col_list = new ArrayList<Object>();
//            for (String obj : d) {
//                if (String.valueOf(obj.charAt(0)).equals("R")) {
//                    JSONObject j = (JSONObject) ((JSONObject)((JSONObject) data.jsonData.get("OriginalBoard")).get("Rooms")).get(obj.substring(1, obj.length()));
//                    String name = (String) j.get("name");
//                    List<String> size = Arrays.asList(((String) j.get("size")).split("x"));
//                    Integer x = Integer.parseInt(size.get(0))-1;
//                    Integer y = Integer.parseInt(size.get(1))-1;
//                    Room newRoom = new Room(name);
//
//                    newRoom.setSize(x, y);
//                    col_list.add(newRoom);
//                }
//                if (String.valueOf(obj.charAt(0)).equals("d")) {
//                    try {
//                        if (String.valueOf(obj.charAt(1)).equals("d")) {
//                            List<Door> doors = new ArrayList<Door>();
//                            doors.add(new Door());
//                            doors.add(new Door());
//                            col_list.add(doors);
//                        } else {
//                            col_list.add(new Door());
//
//                        }
//                    } catch (StringIndexOutOfBoundsException e) {
//                        col_list.add(new Door());
//                    }
//                }
//                if (String.valueOf(obj.charAt(0)).equals("x")) {
//                    col_list.add(new Space(obj.length()));
//                }
//            }
//            board.add(col_list);
//        }
//        return board;
//
//    }
//
//    /**
//     * Cleans the Board data
//     */
//    public void cleanBoardData() {
//        List<List<Object>> board_data = new ArrayList<>();
//        for (List<Object> row : this.board_data_list) {
//            List<Object> new_row = new ArrayList<Object>();
//            for (Object obj : row) {
//                if (!(obj instanceof Door)) {
//                    if (obj instanceof Space) {
//                        if (new_row.size() > 0) {
//                            Object lastAdded = new_row.get(new_row.size() - 1);
//                            if (lastAdded instanceof Space) {
//                                ((Space) lastAdded).setAmount(((Space) lastAdded).amount + 1);
//                            } else {
//                                new_row.add(obj);
//                            }
//                        } else {
//                            new_row.add(obj);
//                        }
//
//                    } else {
//                        new_row.add(obj);
//                    }
//
//                }
//            }
//            board_data.add(new_row);
//        }
//        for (int i = 1; i < board_data.size(); i += 3) {
//            System.out.println(board_data.get(i));
//        }
//        this.board_data_list = board_data;
//
//    }
//
//    /**
//     * Prints the list of Board data
//     */
//    public void printBoardDataList() {
//        for (int i = 1; i < this.board_data_list.size(); i += 3) {
//            System.out.println(this.board_data_list.get(i));
//        }
//    }
//
////    public void print() {
////        System.out.println(this.board);
////        for(int i=1;i<this.board.size();i+=3){
//////            System.out.println(this.board.get(i));
//////        }
////    }
//
//    /**
//     * ???
//     * @param board
//     */
//    public void getRoomDoors(List<List<Object>> board) {
//        for (int i = 1; i < board.size(); i++) {
//            List<Object> row = board.get(i);
//            for (int x = 0; x < row.size(); x++) {
//                Object obj = row.get(x);
//                if (obj instanceof Room) {
//                    if (i != 0) {
//                        Object above_obj = board.get(i - 1).get(x);
////                        System.out.println(above_obj);
//                        if (above_obj instanceof Door) {
//                            ((Room) obj).doors.add("T");
//                        } else if (above_obj instanceof ArrayList) {
//                            if (((ArrayList) above_obj).get(0) instanceof Door) {
//                                ((Room) obj).doors.add("T");
//                                ((Room) obj).doors.add("T");
//                            }
//                        }
//                    }
//                    if (i != board.size() - 1 && x != row.size() - 1) {
////                        System.out.println(i);
////                        System.out.println(x);
//                        Object below_obj = board.get(i + 1).get(x);
//                        if (below_obj instanceof Door) {
//                            ((Room) obj).doors.add("B");
//                        } else if (below_obj instanceof ArrayList) {
//                            if (((ArrayList) below_obj).get(0) instanceof Door) {
//                                ((Room) obj).doors.add("B");
//                                ((Room) obj).doors.add("B");
//                            }
//                        }
//                    }
//                    if (x != 0) {
//                        Object prev_obj = row.get(x - 1);
//                        if (prev_obj instanceof Door) {
//                            ((Room) obj).doors.add("L");
//                        }
//                    }
//                    if (x != row.size() - 1) {
//                        Object next_obj = row.get(x + 1);
//                        if (next_obj instanceof Door) {
//                            ((Room) obj).doors.add("R");
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//
//}
