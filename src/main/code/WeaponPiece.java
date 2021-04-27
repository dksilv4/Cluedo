package code;

/**
 * WeaponPiece extends Piece that represents a Weapon.
 */
public class WeaponPiece extends Piece {
    /**
     * @param name name of piece
     */
    public WeaponPiece(String name){
        super(name);
    }

    /**
     *
     * @return a string representation of the instance
     */
    public String toString(){
        return this.getName();
    }
}
