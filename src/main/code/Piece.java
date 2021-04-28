package code;

/**
 * Representing an object within the board
 */
public class Piece {
    private String name;

    /**
     * @param name name of the piece
     */
    public Piece(String name){
        this.name = name;
    }

    /**
     * @return name of the piece
     */
    public String getName() {
        return name;
    }

    /**Changes the name of the instance
     * @param name string variable to become the name of the instance
     */
    public void setName(String name){
        this.name = name;
    }
}
