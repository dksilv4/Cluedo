package Cluedo;

public class InvalidRoomNameException extends RuntimeException{
    public InvalidRoomNameException(String roomName, String err){
        super("Invalid Room Name: "+roomName+", "+ err);
    }
}
