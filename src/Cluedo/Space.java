package Cluedo;

/**
 *
 */
public class Space {
    int amount;
    public Space(int amount){
        this.amount = amount;
    }
    public String toString(){
        return this.amount+ " spaces";
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
