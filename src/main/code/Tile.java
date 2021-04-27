package code;

/**
 * Tile represents the tiles on a grid
 */
public class Tile {

    private String type; // type of tile
    private Room belongsTo; // parent room if tile is part of a room
    private final int row; // row location of tile
    private final int column; // column location of tile
    private PlayerPiece occupier = null; // if a player is in the tile, the player piece will be set as the occupier

    /**
     * Constructs a Tile
     *
     * @param type   a type string of a Tile
     * @param row    the number of row
     * @param column the number of column
     */
    public Tile(String type, int row, int column) {
        this.type = type;
        this.row = row;
        this.column = column;
    }

    /**
     * @param occupier playerPiece instance
     */
    public void setOccupier(PlayerPiece occupier) {
        this.occupier = occupier;
    }

    /**
     * sets the occupier to null showing that a player isn't on the tile
     */
    public void removeOccupier() {
        this.occupier = null;
    }

    /**
     * @return occupier of tile
     */
    public PlayerPiece getOccupier() {
        return this.occupier;
    }

    /**
     * Checks if a Tile is occupied
     *
     * @return boolean variable representing whether the tile is occupied or not
     */
    public boolean isOccupied() {
        if (this.occupier != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the type of a Tile
     *
     * @return a type string
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the row of a Tile
     *
     * @return a type string
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the column of a Tile
     *
     * @return a type string
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Sets which Room a Tile belongs to
     *
     * @param room a Room object
     */
    public void setBelongsTo(Room room) {
        this.belongsTo = room;
        room.addTile(this);
    }

    /**
     * Checks if a Player can move to a Tile
     *
     * @return boolean
     */
    public Room getBelongsTo() {
        return this.belongsTo;
    }

    /**
     * @return whether a tile is available for a player to move to
     */
    public boolean isAvailable() {
        return !this.isOccupied() && !this.getType().equals("wall");
    }

    /**
     * Sets the type of tile to the param type
     * @param type string representing the type of tile
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return string representation of the instance
     */
    public String toString() {
        if (this.isOccupied()) {
            return this.occupier.toString();
        } else {
            switch (type) {
                case "passage":
                    return "\u001b[36mP\u001B[0m";
                case "room":
                    return belongsTo.toString();
                default:
                    return "\u001b[30mx\u001B[0m";
            }
        }
    }
}
