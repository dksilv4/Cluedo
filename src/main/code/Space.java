package code;

/**
 * Class that represents the size of empty/ walking space
 * used to render the grid and rooms
 */
public class Space {
    int amount;

    /**
     * @param amount // amount of space
     */
    public Space(int amount) {
        this.amount = amount;
    }

    /**
     * @return string representing the instance
     */
    public String toString() {
        return this.amount + " spaces";
    }

    /**
     * @param amount amount of spaces
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return amount of space
     */
    public int getAmount() {
        return this.amount;
    }
}
