package code;

/**
 * Exception thrown when the name of a room is invalid
 */
public class RoomNotFoundException extends RuntimeException{

    /**
     * Constructs the exception
     * @param roomName a room name string
     */
    public RoomNotFoundException(String roomName){
        super(roomName+": Room not found!");
    }
}