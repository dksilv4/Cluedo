package Cluedo;

import java.util.ArrayList;
import java.util.List;

/**
 * Grid defines a grid for a Board
 */
public class Grid {
    List<List<Tile>> grid = new ArrayList<List<Tile>>();
    int rows;
    int columns;

    /**
     * Contructs and initializes a Grid
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.makeGrid();

    }

    /**
     * Build a grid
     */
    private void makeGrid() {
        for (int x = 0; x < this.rows; x++) {
            List<Tile> row = new ArrayList<>();
            for (int y = 0; y < this.columns; y++) {
                row.add(new Tile("space", x, y));
            }
            this.grid.add(row);
        }
    }

    /**
     * Returns a Grid object
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
    }

    /**
     * Changes a Tile
     * @param row the row number of a tile
     * @param column the column number of a tile
     * @param tile a Tile
     */
    public void changeTile(int row, int column, Tile tile) {
        this.grid.get(row).set(column, tile);
    }

    /**
     *
     * @param row row number
     * @param column column number
     * @return a Tile on a Grid
     */
    public Tile getTile(int row, int column) {
        return this.grid.get(row).get(column);
    }

    /**
     * Add a Room to a Grid
     * @param room a Room object
     * @param start_row starting row number
     * @param start_column starting column number
     */
    public void addRoom(Room room, int start_row, int start_column) {
        for (int x = start_row; x < start_row + room.size_x; x++) {
            for (int y = start_column; y < start_column + room.size_y; y++) {

                Tile newTile = new Tile("room", x, y);
                newTile.setBelongsTo(room);
                this.changeTile(x, y, newTile);
                room.changeTile(x - start_row, y - start_column, newTile);

                try {
                    if (y == start_column) {
                        this.changeTile(x, y - 1, new Tile("wall", x, y));
                    } else if (y == start_column + room.size_y - 1) {
                        this.changeTile(x, y + 1, new Tile("wall", x, y));
                    }
                    if (x == start_row + room.size_x - 1) {
                        if (y == start_column) {
                            this.changeTile(x + 1, y - 1, new Tile("wall", x, y));
                        } else if (y == start_column + room.size_y - 1) {
                            this.changeTile(x + 1, y + 1, new Tile("wall", x, y));
                        }
                        this.changeTile(x + 1, y, new Tile("wall", x, y));

                    } else if (x == start_row) {
                        if (y == start_column) {
                            this.changeTile(x - 1, y - 1, new Tile("wall", x, y));
                        } else if (y == start_column + room.size_y - 1) {
                            this.changeTile(x - 1, y + 1, new Tile("wall", x, y));
                        }
                        this.changeTile(x - 1, y, new Tile("wall", x, y));
                    }
                } catch (Exception e) {
                }
            }
        }
        this.addRoomDoors(room);
    }

    /**
     * Add doors to a Room
     * @param room a Room
     */
    public void addRoomDoors(Room room) {
        for (String door_loc : room.doors) {
            switch (door_loc) {
                case "T":
                    List<Tile> tiles = room.tiles.get(0);
                    int row = tiles.get(0).row - 1;
                    List<Integer> columns = new ArrayList<>();
                    for (Tile tile : tiles) {
                        columns.add(tile.column);
                    }
                    int column = columns.get((int) Math.round(Math.random() * (columns.size() - 1)));
                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeTile(row, column, new Tile("door", row, column));
                    }
                    break;
                case "R":
                    List<Integer> rows = new ArrayList<>();
                    column = 0;
                    for (List<Tile> tileList : room.tiles) {
                        for (Tile tile : tileList) {
                            if (tile.column > column) column = tile.column;
                            if (!rows.contains(tile.row)) rows.add(tile.row);
                        }
                    }
                    row = rows.get((int) Math.round(Math.random() * (rows.size() - 1)));
                    column++;
                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeTile(row, column, new Tile("door", row, column));
                    }
                    break;
                case "B":
                    columns = new ArrayList<>();
                    tiles = room.tiles.get(0);
                    row = room.tiles.get(room.size_x - 1).get(0).row;
                    for (Tile tile : tiles) {
                        columns.add(tile.column);
                    }
                    column = columns.get((int) Math.round(Math.random() * (columns.size() - 1)));
                    row++;

                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeTile(row, column, new Tile("door", row, column));
                    }
                    break;
                case "L":
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

                    if (this.getTile(row, column).type.equals("wall")) {
                        this.changeTile(row, column, new Tile("door", row, column));
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + door_loc);
            }
        }
    }
}
