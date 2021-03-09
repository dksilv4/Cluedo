package Cluedo;

import java.util.ArrayList;
import java.util.List;

/**
 * Grid defines a grid for a Board
 */
public class Grid {
    public List<List<Tile>> grid = new ArrayList<List<Tile>>();
    int rows;
    int columns;

    /**
     * Constructs and initializes a Grid
     */
    public Grid() {

    }

    /**
     * Build a grid
     *
     * @param rows    the number of rows
     * @param columns the number of columns
     */
    public void makeGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        for (int x = 0; x < rows+1; x++) {
            List<Tile> row = new ArrayList<>();
            for (int y = 0; y < columns+1; y++) {
                if(x==0){
                    row.add(new Tile("border", x, y));
                }else{
                    row.add(new Tile("space", x, y));

                }
            }
            this.grid.add(row);
        }
    }

    public void deleteGrid() {
        this.grid = new ArrayList<List<Tile>>();
    }

    /**
     * Returns a Grid object
     *
     * @return a grid object
     */
    public List<List<Tile>> getGrid() {
        return this.grid;
    }

    /**
     * Prints a Grid object
     */
    public void print() {
        for (List<Tile> row : this.grid) {
            System.out.println(row);
        }
        System.out.println("\n");
    }

    /**
     * Changes a Tile
     *
     * @param row    the row number of a tile
     * @param column the column number of a tile
     * @param tile   a Tile
     */
    public void changeTile(int row, int column, Tile tile) {
        this.grid.get(row).set(column, tile);
    }

    /**
     * @param row    row number
     * @param column column number
     * @return a Tile on a Grid
     */
    public Tile getTile(int row, int column) {
        return this.grid.get(row).get(column);
    }

    /**
     * Add a Room to a Grid
     *
     * @param room         a Room object
     * @param start_row    starting row number
     * @param start_column starting column number
     */
    public void addRoom(Room room, int start_row, int start_column) {
        start_row += 1;
        start_column += 1;
        int total_x = start_row + room.getSizeY();
        int total_y = start_column + room.getSizeX();
        for (int x = start_row; x < total_x; x++) {
            for (int y = start_column; y < total_y; y++) {
                Tile tile = this.getTile(x, y);
                tile.setBelongsTo(room);
                tile.newType("room");
                if ((x == start_row || y == start_column || x == total_x - 1 || y == total_y - 1) && !room.name.equals("X")) {
                    tile.newType("wall");
                }
                room.addTile(tile);
            }
        }
//        this.print();
        this.addRoomDoors(room, start_row, start_column);
    }


    /**
     * Add doors to a Room
     *
     * @param room a Room
     */
    public void addRoomDoors(Room room, int start_row, int start_column) {
        System.out.println(room.tiles);
        List<Tile> topTiles = new ArrayList<Tile>();
        List<Tile> rightTiles = new ArrayList<Tile>();
        List<Tile> bottomTiles = new ArrayList<Tile>();
        List<Tile> leftTiles = new ArrayList<Tile>();
        for (Tile tile : room.tiles) {
            if (tile.row == start_row) {
                topTiles.add(tile);
            }
            if (tile.column == start_column + room.getSizeX() - 1) {
                rightTiles.add(tile);
            }
            if (tile.row == start_row + room.getSizeY() - 1) {
                bottomTiles.add(tile);
            }
            if (tile.column == start_column) {
                leftTiles.add(tile);
            }
        }
//        System.out.println("TOP");
//        System.out.println(topTiles);
//        System.out.println("RIGHT");
//        System.out.println(rightTiles);
//        System.out.println("BOTTOM");
//        System.out.println(bottomTiles);
//        System.out.println("LEFT");
//        System.out.println(leftTiles);


        for (String door_loc : room.doors) {
            System.out.println(door_loc);
            switch (door_loc) {
                case "T":
                    int loc = Math.round(topTiles.size() >> 1);
//                    System.out.println(loc);
                    topTiles.get(loc).newType("door");
                    break;
                case "R":
                    loc = Math.round(rightTiles.size() >> 1);
                    rightTiles.get(loc).newType("door");
                    break;
                case "B":
                    loc = Math.round(bottomTiles.size() >> 1);
//                    System.out.println(loc);
                    bottomTiles.get(loc).newType("door");
                    break;
                case "L":
                    loc = Math.round(leftTiles.size() >> 1);
//                    System.out.println(loc);
                    leftTiles.get(loc).newType("door");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + door_loc);
            }
        }
    }
}
