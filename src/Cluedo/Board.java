package Cluedo;

import java.util.ArrayList;
import java.util.List;

public class Board {
    String board_data;
    List<List<Tile>> grid;


    public Board() {
        this.board_data = new Data("board.txt").board;
        this.grid = this.getGrid(25, 25);
        this.printGrid();
        List<List<Object>> board = this.Lexer();
        this.getRoomDoors(board);
//        Room room = new Room("Test");
//        room.doors.add("T");
//        room.doors.add("R");
//        room.doors.add("B");
//        room.doors.add("L");
//        room.setSize(5, 5);
//        this.addRoom(room, 3, 3);
//        this.printGrid();
//        Room room1 = new Room("ATEST");
//        room1.doors.add("B");
//        room1.setSize(3, 4);
//        this.addRoom(room1 , 10, 10);
//        Room room2 = new Room("BTEST");
//        room2.doors.add("R");
//        room2.setSize(2, 4);
//        this.addRoom(room2 , 17, 1);
    }

    public static void main(String[] args) {
        Board board = new Board();
    }

    public List<List<Object>> Lexer() {
        List<List<Object>> board = new ArrayList<List<Object>>();
        for (String row : this.board_data.split("\n")) {
            String[] d = row.split(" ");
            List<Object> col_list = new ArrayList<Object>();
            for (String obj : d) {
                if (String.valueOf(obj.charAt(0)).equals("R")) {
                    col_list.add(new Room(obj.substring(1, obj.length())));
                }
                if (String.valueOf(obj.charAt(0)).equals("d")) {
                    try{
                        if(String.valueOf(obj.charAt(1)).equals("d")){
                            List<Door> doors = new ArrayList<Door>();
                            doors.add(new Door());
                            doors.add(new Door());
                            col_list.add(doors);
                        }
                        else{
                            col_list.add(new Door());

                        }
                    }catch (StringIndexOutOfBoundsException e){
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

    public void printBoard(List<List<Object>> board) {
        System.out.println(board);
        for(int i=1;i<board.size();i+=3){
            System.out.println(board.get(i));
        }
    }

    public void getRoomDoors(List<List<Object>> board) {
        for (int i = 1; i < board.size(); i++) {
            List<Object> row = board.get(i);
            for (int x = 0; x < row.size(); x++) {
                Object obj = row.get(x);
                if (obj instanceof Room) {
                    if(i!=0){
                        Object above_obj = board.get(i-1).get(x);
//                        System.out.println(above_obj);
                        if(above_obj instanceof Door){
                            ((Room) obj).doors.add("T");
                        }
                        else if(above_obj instanceof ArrayList){
                            if(((ArrayList) above_obj).get(0) instanceof Door){
                                ((Room) obj).doors.add("T");
                                ((Room) obj).doors.add("T");
                            }
                        }
                    }
                    if(i!=board.size()-1 && x != row.size()-1){
//                        System.out.println(i);
//                        System.out.println(x);
                        Object below_obj = board.get(i+1).get(x);
                        if(below_obj instanceof Door){
                            ((Room) obj).doors.add("B");
                        }
                        else if(below_obj instanceof ArrayList){
                            if(((ArrayList) below_obj).get(0) instanceof Door){
                                ((Room) obj).doors.add("B");
                                ((Room) obj).doors.add("B");
                            }
                        }
                    }
                    if (x != 0) {
                        Object prev_obj = row.get(x - 1);
                        if (prev_obj instanceof Door) {
                            ((Room) obj).doors.add("R");
                        }
                    }
                    if (x != row.size()-1) {
                        Object next_obj = row.get(x + 1);
                        if (next_obj instanceof Door) {
                            ((Room) obj).doors.add("L");
                        }
                    }
                }
            }
        }
        this.printBoard(board);

    }
    public List<List<Tile>> getGrid(int rows, int columns) {
        List<List<Tile>> grid = new ArrayList<List<Tile>>();
        for (int x = 0; x < rows; x++) {
            List<Tile> row = new ArrayList<>();
            for (int y = 0; y < columns; y++) {
                row.add(new Tile("space", x, y));
            }
            grid.add(row);
        }
        return grid;
    }

    public void printGrid() {
        for (List<Tile> row : this.grid) {
            System.out.println(row);
        }
    }

    public void changeGridTile(int row, int column, Tile tile) {
        this.grid.get(row).set(column, tile);
//        this.printGrid();
    }

    public Tile getTile(int row, int column) {
        return this.grid.get(row).get(column);
    }

    public void addRoom(Room room, int start_row, int start_column) {
        for (int x = start_row; x < start_row + room.size_x; x++) {
            for (int y = start_column; y < start_column + room.size_y; y++) {
                System.out.println(x + " " + y);

                Tile newTile = new Tile("room", x, y);
                newTile.setBelongsTo(room);
                this.changeGridTile(x, y, newTile);
                room.changeTile(x - start_row, y - start_column, newTile);

                try {
                    if (y == start_column) {
                        this.changeGridTile(x, y - 1, new Tile("wall", x, y));
                    } else if (y == start_column + room.size_y - 1) {
                        this.changeGridTile(x, y + 1, new Tile("wall", x, y));
                    }
                    if (x == start_row + room.size_x - 1) {
                        if (y == start_column) {
                            this.changeGridTile(x + 1, y - 1, new Tile("wall", x, y));
                        } else if (y == start_column + room.size_y - 1) {
                            this.changeGridTile(x + 1, y + 1, new Tile("wall", x, y));
                        }
                        this.changeGridTile(x + 1, y, new Tile("wall", x, y));

                    } else if (x == start_row) {
                        if (y == start_column) {
                            this.changeGridTile(x - 1, y - 1, new Tile("wall", x, y));
                        } else if (y == start_column + room.size_y - 1) {
                            this.changeGridTile(x - 1, y + 1, new Tile("wall", x, y));
                        }
                        this.changeGridTile(x - 1, y, new Tile("wall", x, y));
                    }
                } catch (Exception e) {

                }

            }
        }
        this.addRoomDoors(room);
    }

    public void addRoomDoors(Room room) {
        for (String door_loc : room.doors) {
            System.out.println(door_loc);
            switch (door_loc) {
                case "T":
                    List<Tile> tiles = room.tiles.get(0);
                    int row = tiles.get(0).row - 1;
                    List<Integer> columns = new ArrayList<>();
                    System.out.println(tiles);
                    for (Tile tile : tiles) {
                        columns.add(tile.column);
                    }
                    System.out.println(columns);
                    int column = columns.get((int) Math.round(Math.random() * (columns.size() - 1)));
                    System.out.println(row);
                    System.out.println(column);
                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeGridTile(row, column, new Tile("door", row, column));
                    }
                    this.printGrid();
                    break;
                case "R":
                    List<Integer> rows = new ArrayList<>();
                    column = 0;
                    System.out.println(room.tiles);
                    for (List<Tile> tileList : room.tiles) {
                        for (Tile tile : tileList) {
                            if (tile.column > column) column = tile.column;
                            if (!rows.contains(tile.row)) rows.add(tile.row);
                        }
                    }
                    row = rows.get((int) Math.round(Math.random() * (rows.size() - 1)));
                    column++;
                    System.out.println(rows);
                    System.out.println(row);
                    System.out.println(column);
                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeGridTile(row, column, new Tile("door", row, column));
                    }
                    this.printGrid();
                    break;
                case "B":
                    System.out.println(room.tiles);
                    columns = new ArrayList<>();
                    tiles = room.tiles.get(0);
                    row = room.tiles.get(room.size_x - 1).get(0).row;
                    for (Tile tile : tiles) {
                        columns.add(tile.column);
                    }
                    column = columns.get((int) Math.round(Math.random() * (columns.size() - 1)));
                    row++;
                    System.out.println(row);
                    System.out.println(column);
                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeGridTile(row, column, new Tile("door", row, column));
                    }
                    this.printGrid();
                    break;
                case "L":
                    System.out.println(room.tiles);
                    rows = new ArrayList<>();
                    column = 10000;
                    for (List<Tile> tileList : room.tiles) {
                        for (Tile tile : tileList) {
                            if (tile.column < column) column = tile.column;
                            if (!rows.contains(tile.row)) rows.add(tile.row);
                        }
                    }
                    row = rows.get((int) Math.round(Math.random() * (rows.size() - 1)));
                    column--;
                    System.out.println(rows);
                    System.out.println(row);
                    System.out.println(column);
                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeGridTile(row, column, new Tile("door", row, column));
                    }
                    this.printGrid();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + door_loc);
            }


        }

    }
}
