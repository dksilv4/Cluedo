package code;

/**
 * Exception thrown when the name of a room is invalid
 */
public class InvalidRoomNameException extends RuntimeException{

    /**
     * Constructs the exception
     * @param roomName a room name string
     * @param err an error message string
     */
    public InvalidRoomNameException(String roomName, String err){
        super("Invalid Room Name: "+roomName+", "+ err);
    }
}
