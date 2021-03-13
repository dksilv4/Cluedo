package Cluedo;

import java.util.ArrayList;
import java.util.List;

/**
 * Grid defines a grid for a Board
 */
public class Grid {
    private List<List<Tile>> grid = new ArrayList<List<Tile>>();
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
        for (int x = 0; x < rows; x++) {
            List<Tile> row = new ArrayList<>();
            for (int y = 0; y < columns; y++) {
                row.add(new Tile("space", x, y));
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
     */
    public void addWallTile(int row, int column) {
        this.grid.get(row).set(column, new Wall(row, column));
    }
    public void addDoorTile(int row, int column){
        this.grid.get(row).set(column, new Door(row, column));
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
        int total_x = start_row + room.getSizeY();
        int total_y = start_column + room.getSizeX();
        for (int x = start_row; x < total_x; x++) {
            for (int y = start_column; y < total_y; y++) {
                if ((x == start_row || y == start_column || x == total_x - 1 || y == total_y - 1) && !room.getName().equals("X")) {
                    this.addWallTile(x, y);
                }
                else{
                    Tile tile = this.getTile(x, y);
                    tile.setBelongsTo(room);
                    tile.setType("room");
                }
                room.addTile(this.getTile(x, y));
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
    private void addRoomDoors(Room room, int start_row, int start_column) {
        System.out.println(room.getTiles());
        List<Tile> topTiles = new ArrayList<Tile>();
        List<Tile> rightTiles = new ArrayList<Tile>();
        List<Tile> bottomTiles = new ArrayList<Tile>();
        List<Tile> leftTiles = new ArrayList<Tile>();
        for (Tile tile : room.getTiles()) {
            if (tile.getRow()== start_row) {
                topTiles.add(tile);
            }
            if (tile.getColumn() == start_column + room.getSizeX() - 1) {
                rightTiles.add(tile);
            }
            if (tile.getRow() == start_row + room.getSizeY() - 1) {
                bottomTiles.add(tile);
            }
            if (tile.getColumn() == start_column) {
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
        for (String door_loc : room.getDoors()) {
            System.out.println(door_loc);
            switch (door_loc) {
                case "T":
                    Tile tile = topTiles.get(Math.round(topTiles.size() >> 1));
                    this.addDoorTile(tile.getRow(),tile.getColumn());
                    break;
                case "R":
                    tile = rightTiles.get(Math.round(rightTiles.size() >> 1));
                    this.addDoorTile(tile.getRow(),tile.getColumn());
                    break;
                case "B":
                    tile = bottomTiles.get(Math.round(bottomTiles.size() >> 1));
                    this.addDoorTile(tile.getRow(),tile.getColumn());
                    break;
                case "L":
                    tile = leftTiles.get(Math.round(leftTiles.size() >> 1));
                    this.addDoorTile(tile.getRow(),tile.getColumn());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + door_loc);
            }
        }
    }
}
