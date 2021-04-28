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

//    public static void main(String[] args) {
//        Board board = new Board();
//    }

    /**
     * Constructs and initializes a Board
     */
    public Board() {
        this.board_data = new Data("board.txt").getBoard();
        this.grid = new Grid();
        this.grid.makeGrid(28, 24);
        this.board_data_list = this.Lexer();
        this.getRoomDoors(board_data_list);
        this.cleanBoardData();
        this.placeRooms();
        this.rooms = this.getRooms();
        this.renderSecretPassages();
    }

    public void renderSecretPassages() {
        // kitchen, conservatory, lounge, study
        Room kitchen = this.getRoom("Kitchen");
        kitchen.setSecretPassage(kitchen.getTiles().get((kitchen.getSizeX() * 2 - 2)));
        Room conservatory = this.getRoom("Conservatory");
        conservatory.setSecretPassage(conservatory.getTiles().get((conservatory.getSizeX() * conservatory.getSizeY()) - (conservatory.getSizeX() + 2)));
        Room lounge = this.getRoom("Lounge");
        lounge.setSecretPassage(lounge.getTiles().get(lounge.getSizeX() + 1));
        Room study = this.getRoom("Study");
        study.setSecretPassage(study.getTiles().get((study.getSizeX() * study.getSizeY()) - (study.getSizeX() + 2)));
    }

    /**
     * Functions that gets all the times from a specific room from the board grid to allow the selection of the playerPiece Spawns.
     * PLayerPieces will spawn in a random Tile inside the room.
     *
     * @return List of tiles for room X of the board grid. The room will always be in the centre of the map.
     */
    public List<Tile> getSpawns() {
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
     * Returns grid representing the board.
     *
     * @return Grid object
     */
    public Grid getGrid() {
        return this.grid;
    }

    /**
     * Returns a list of Room from a specific row given as a parameter.
     *
     * @param row List of object representing a row in the grid.
     * @return List<Room> (List of rooms)
     */
    public List<Room> getRowRooms(List<Object> row) {
        List<Room> rooms = new ArrayList<Room>(); //Making an empty list of rooms.
        for (int x = 0; x < row.size(); x++) { // go through each item in the list.
            Object obj = row.get(x);
            if (obj instanceof Room) { // If the item in list is an instance of Room
                rooms.add((Room) obj); // add room to list
            }
        }
        return rooms;
    }

    /**
     * Returns a list of all the rooms in the grid.,
     *
     * @return a list of all rooms in the board.
     */
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<Room>(); //New empty list of Rooms
        for (List<Object> row : this.board_data_list) { // go through all list of all items that are used to make the grid
            for (Object obj : row) { // for each object in the row
                if (obj instanceof Room) { // if object is a room
                    rooms.add((Room) obj); // add room to the empty room list
                }
            }
        }
        return rooms; //returns the list of rooms
    }

    /**
     * Set function for the variable rooms
     *
     * @param rooms
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Returns all the Space instances found within a row given as a parameter.
     *
     * @param row
     * @return List<Space> (List of Spaces)
     */
    public List<Space> getRowSpaces(List<Object> row) {
        List<Space> spaces = new ArrayList<Space>();
        for (int x = 0; x < row.size(); x++) {
            Object obj = row.get(x);
            if (obj instanceof Space) {
                spaces.add((Space) obj);
            }
        }
        return spaces;
    }

    /**
     * Places rooms in the grid depending on their location on the list, the sizes of each rooms and how many sets of rows and rooms the board data contains.
     * Calculates the place where each room should be added into the board. This is all done automatically and dynamically.
     * Changing the board.txt file will change how the board looks.
     * Current limitation of this method is that it's limited to 4 rows of rooms ( this can be unlimited just need to add more if statements checking the variable i).
     */
    public void placeRooms() {
        int y_size = 0; //Used to allow the function to know if the room should be being in a different row depending if it needs to be below another previously added room.
        for (int i = 1; i < this.board_data_list.size(); i += 3) { // for each item inside the board data list
            List<Object> row = this.board_data_list.get(i); //Get a row
            List<Room> rooms = this.getRowRooms(row); //Gets all the rooms within the row
            List<Space> spaces = this.getRowSpaces(row); // Gets all the spaces within the row
            int size_x = 0; // variable representing what columns of the grid the rooms needs to start at.
            int space = 0; // variable representing the spaces required between rooms.
            int size_y = 0; // variable representing the size of the tallest room in the row.
            for (int x = 0; x < rooms.size(); x++) {// for each item inside room list
                Room room = rooms.get(x); // Represents a single room
                if (room.getName().equals("X")) { // If the room name is X, this room will always be in the middle of the map therefore its placed completely different compared to all the other rooms.
                    this.grid.addRoom(room, this.grid.getRows() / 2 - room.getSizeY() / 2, this.grid.getColumns() / 2 - room.getSizeX() / 2); // Add room at the centre of the grid.
                    size_x += room.getSizeX() + 3;// adds a certain amount into the variable size_x so the next rooms doesn't overlap room X.
                } else {
                    if (spaces.size() > x) { // if the length of the list of spaces is bigger than the variable x
                        space += spaces.get(x).amount; // Get the space within the list of spaces in location x.
                    }
                    if (size_y < room.getSizeY()) { // if size_y is smaller than the height of the room
                        size_y = room.getSizeY() + 1; // Make size_y the height of the room +1
                    }
                    //the limit of the function at the moment is that I have to specify the variable i to be able to add the rooms into the grid without them overlapping each others.
                    if (i == 1) { // if i == 1 means that its the first row of items within the board data list meaning that all the rooms will start at row 0
                        if (x == rooms.size() - 1) { // if x is the last item of rooms, the room will be added to the right side of the grid.
                            this.grid.addRoom(room, 0, this.grid.getColumns() - room.getSizeX());
                        } else {// if not the last rooms in the row, size_x is used to make sure that the rooms don't overlap each other.
                            this.grid.addRoom(room, 0, size_x);
                        }
                        size_x += (space + room.getSizeX()); //add the width of the room into this variable.
                    }
                    if (i == 4) { // if i==4 represents the second row of rooms. They don't start at row 0 anymore. Therefore we use y_size to represent the row that the rooms needs to be placed at.
                        if (x == rooms.size() - 1) { // if its the last room in the row.
                            this.grid.addRoom(room, y_size, this.grid.getColumns() - room.getSizeX()); // last the room in the right side.
                        } else {
                            this.grid.addRoom(room, y_size, size_x);
                        }
                        size_x += (space + room.getSizeX() + 1); //add the width of the room into this variable.

                    }
                    if (i == 7) { // same as if(i==4)
                        if (x == rooms.size() - 1) {
                            this.grid.addRoom(room, y_size, this.grid.getColumns() - room.getSizeX());
                        } else {
                            this.grid.addRoom(room, y_size, size_x);
                        }
                        size_x += (space + room.getSizeX() + 1);
                    }
                    if (i == 10) {// same as if(i==4)
                        if (x == rooms.size() - 1) {
                            this.grid.addRoom(room, y_size, this.grid.getColumns() - room.getSizeX());
                        } else {
                            this.grid.addRoom(room, y_size, size_x);
                        }
                        size_x += (space + room.getSizeX() + 1);
                    }
                }
            }
            y_size += size_y; // size_y (representing the size of the tallest room in the row) is added to y_size (representing the row at which the rooms she be placed at).
        }
    }

    /**
     * Reads the raw data from the json file, and the text file to compile a list of rows for the grid. The rows are Lists of objects.
     * Data.json is used to read the name and the size of all the rooms.
     * Coded in a way that a player could change the data.json file to change the name of the rooms within the game and be able to also change the size of any room.
     * This will all be dynamically shown in the grid.
     *
     * @return a lexed board
     */
    public List<List<Object>> Lexer() {
        List<List<Object>> board = new ArrayList<List<Object>>(); // Empty List of Object Lists
        Data data = new Data("data.json"); //Reading data from the data.json
        for (String row : this.board_data.split("\n")) {//Spliting the data from the text file using the new line chr. (Each item split represents a row in the grid)
            String[] d = row.split(" "); // Split the row by spaces. This will represent a list of all the different objects found within the row.
            List<Object> col_list = new ArrayList<Object>(); // Represents a column list which is a list of all items that will be placed in different columns.
            for (String obj : d) { // for each object found in row
                if (String.valueOf(obj.charAt(0)).equals("R")) {// if the string object begins with R, this means that it's a room and that the name of the room is the rest of the string excluding the initial R
                    JSONObject j = (JSONObject) ((JSONObject) ((JSONObject) data.getJsonData().get("OriginalBoard")).get("Rooms")).get(obj.substring(1)); // Gets the rooms from the json data.
                    String name = (String) j.get("name");// gets the name of the room.
                    List<String> size = Arrays.asList(((String) j.get("size")).split("x")); //List of the number of rows and columns the room has
                    int x = Integer.parseInt(size.get(1));// get the number of columns
                    int y = Integer.parseInt(size.get(0));// get the number of rows
                    Room newRoom = new Room(name);//make a new room
                    newRoom.setSize(x, y); // give it the right size.
                    col_list.add(newRoom);//add the room to the column list
                }
                if (String.valueOf(obj.charAt(0)).equals("d")) {//If string object is a "d" it represents a door.
                    try {
                        if (String.valueOf(obj.charAt(1)).equals("d")) {//Checking for double doors
                            List<Door> doors = new ArrayList<Door>();//if it has double doors had both to this empty list and then add the list into the column list
                            doors.add(new Door(0, 0));
                            doors.add(new Door(0, 0));
                            col_list.add(doors);
                        } else {
                            col_list.add(new Door(0, 0)); // if not double door just had a door to the column list
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        col_list.add(new Door(0, 0));// if not double door just had a door to the column list
                    }
                }
                if (String.valueOf(obj.charAt(0)).equals("x")) { // if the String is "x" it represents a space in the list
                    col_list.add(new Space(obj.length()));
                }
            }
            board.add(col_list); //add the column list to the board data. The column list represents a row within the board now.
        }
        return board;//return the board once all the items have been scanned through.

    }

    /**
     * Cleans the Board data
     * Removes all the unnecessary data that is found in the board data that isn't needed.
     * Merge spaces and remove doors.
     */
    public void cleanBoardData() {
        List<List<Object>> board_data = new ArrayList<>(); // new List to represent the new board data.
        for (List<Object> row : this.board_data_list) { // For each item in the old board data
            List<Object> new_row = new ArrayList<Object>(); // Empty list representing the new row
            for (Object obj : row) { // go through each item in each row from the old data
                if (!(obj instanceof Door) && !(obj instanceof List)) {// if object is  not a door or a list
                    if (obj instanceof Space) { // if object is a space
                        if (new_row.size() > 0) {//if the new row already has an item inside the list
                            Object lastAdded = new_row.get(new_row.size() - 1);// check if it a space has been added previously
                            if (lastAdded instanceof Space) { //if a space has been added previously
                                ((Space) lastAdded).setAmount(((Space) lastAdded).amount + 1);//increase the amount of the space from the prev added space
                            } else {
                                new_row.add(obj);// if a space wasn't previously added add the space to the new row
                            }
                        } else {
                            new_row.add(obj); // if a space wasn't previously added add the space to the new row
                        }

                    } else {
                        new_row.add(obj); // if not instance of space
                    }
                }
            }
            board_data.add(new_row); // add the new row to the empty list representing the new board data
        }

        this.board_data_list = board_data;// assign thew new board data to the old board data

    }

    /**
     * Prints the list of Board data
     */
    public void printBoardDataList() {
        for (int i = 1; i < this.board_data_list.size(); i += 3) {
            System.out.println(this.board_data_list.get(i));
        }
    }

    /**
     * Goes through the board data and check the positioning of all the rooms and door and assigns doors to each room if they have one in any direction.
     *
     * @param board
     */
    public void getRoomDoors(List<List<Object>> board) {
        for (int i = 1; i < board.size(); i++) { // for each item in board
            List<Object> row = board.get(i);// assign a row variable to represent each row in board
            for (int x = 0; x < row.size(); x++) {// for each item in row
                Object obj = row.get(x);// assign this variable to
                if (obj instanceof Room) { // if object is a room
                    if (i != 0) { // if not the first row
                        Object above_obj = board.get(i - 1).get(x); // get the object in the same column in the previous row
                        if (above_obj instanceof Door) { // if the object is an instance of door
                            ((Room) obj).addDoor("T"); // add T to a list within door to represent the fact that the room has a door on the top
                        } else if (above_obj instanceof ArrayList) {// If the above object is an instance of a door and the first item of the list if an instance of a door
                            if (((ArrayList) above_obj).get(0) instanceof Door) {
                                ((Room) obj).addDoor("T");// add two doors into the room.
                                ((Room) obj).addDoor("T");
                            }
                        }
                    }
                    if (i != board.size() - 1 && x != row.size()) {//if not the last row
                        Object below_obj = board.get(i + 1).get(x); // get the object below the room
                        if (below_obj instanceof Door) { // if object is an isntance of Door
                            ((Room) obj).addDoor("B"); //Add B into the rooms doors list
                        } else if (below_obj instanceof ArrayList) { // if the object is a list
                            if (((ArrayList) below_obj).get(0) instanceof Door) { // and contains only instances of Door add two Bs into the rooms door list
                                ((Room) obj).addDoor("B");
                                ((Room) obj).addDoor("B");
                            }
                        }
                    }
                    if (x != 0) { // if x isn't 0 (if object isn't the first object in the row)
                        Object prev_obj = row.get(x - 1); // get the previous object
                        if (prev_obj instanceof Door) { // if object is an instance of door
                            ((Room) obj).addDoor("L"); // add L to the room's door list
                        }
                    }
                    if (x != row.size() - 1) { // if the object isn't the last item in the list
                        Object next_obj = row.get(x + 1); // get the next item in the row
                        if (next_obj instanceof Door) { // if the item is an instance of Door
                            ((Room) obj).addDoor("R");// add R to the room's door list
                        }
                    }
                }
            }
        }
    }
}