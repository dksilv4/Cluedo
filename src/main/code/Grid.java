package code;

import java.util.ArrayList;
import java.util.List;

/**
 * Grid defines a grid for a Board
 */
public class Grid {
    private List<List<Tile>> grid = new ArrayList<List<Tile>>();
    private int rows; // how many rows the grid has
    private int columns; // how many columns the grid has

    /**
     * Constructs and initializes a Grid
     */
    public Grid() {
    }

    /**
     * @return rows
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * @return columns
     */
    public int getColumns(){
        return this.columns;
    }

    /**
     * Build a grid with size rows X columns
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

    /**
     * Deletes the grid that was previously generated
     */
    public void deleteGrid() {
        this.grid = new ArrayList<List<Tile>>();
    }

    /**
     * Returns the list of lists of tiles that represents the overall map/board
     *
     * @return a grid object
     */
    public List<List<Tile>> getGrid() {
        return this.grid;
    }

    /**
     * Prints each row in grid separately to show the overall size and items within the variable grid
     */
    public void print() {
        for (List<Tile> row : this.grid) {
            System.out.println(row);
        }
        System.out.println("\n");

    }

    /**
     * Changes a Tile to be a Wall Tile
     *
     * @param row    the row number of a tile
     * @param column the column number of a tile
     */
    public void addWallTile(int row, int column) {
        this.grid.get(row).set(column, new Wall(row, column));
    }

    /**Changes a Tile to be a Door Tile
     *
     * @param row the row number of Door to be added to
     * @param column the column to add the Door to
     */
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
                    room.addTile(this.getTile(x,y));
                }
                else{
                    Tile tile = this.getTile(x, y);
                    tile.setBelongsTo(room);
                    tile.setType("room");
                }
            }
        }
        this.addRoomDoors(room, start_row, start_column);
    }


    /**Adds the doors to all the rooms to the side the doors are meant to be
     *
     * @param room Room instance to be added
     * @param start_row row where the first tile of the room begins
     * @param start_column column where the first tile of the room begins
     */
    private void addRoomDoors(Room room, int start_row, int start_column) {
        List<Tile> topTiles = new ArrayList<Tile>();
        List<Tile> rightTiles = new ArrayList<Tile>();
        List<Tile> bottomTiles = new ArrayList<Tile>();
        List<Tile> leftTiles = new ArrayList<Tile>();

        //Gets a separate list of all the tiles that are in one of the four side of room
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
        // Goes through how many doors the room depending on the string value representing the door it adds a
        // door in the middle of the corresponding side of the room
        for (String door_loc : room.getDoors()) {
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
