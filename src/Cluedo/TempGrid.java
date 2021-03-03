package Cluedo;

/**
 * Represents the information of a Grid
 */
public class TempGrid {
    Grid grid;

    /**
     * Constructs a TempGrid
     */
    public TempGrid(){
        Room room01 = new Room("A Room");
        room01.setSize(5, 5);
        room01.addDoor("R");
        room01.addDoor("B");

        Room room02 =  new Room("B Room");
        room02.setSize(6, 3);
        room02.addDoor("L");
        room02.addDoor("B");
        room02.addDoor("R");
        Room room03 =  new Room("C Room");
        room03.setSize( 7, 8);
        room03.addDoor("L");
        room03.addDoor("B");
        Room room04 =  new Room("D Room");
        room04.setSize(5, 4);
        room04.addDoor("B");
        room04.addDoor("R");
        Room room05 = new Room("X Room");
        room05.setSize(5, 7);
        room05.addDoor("L");
        room05.addDoor("B");
        room05.addDoor("R");
        Room room06 = new Room("E Room");
        room06.setSize(6, 5);
        room06.addDoor("L");
        room06.addDoor("B");
        Room room07 = new Room("F Room");
        room07.setSize(6, 5);
        room07.addDoor("R");
        Room room08 = new Room("G Room");
        room08.setSize(4, 5);
        room08.addDoor("L");
        room08.addDoor("R");
        Room room09 = new Room("H Room");
        room09.setSize(4, 5);
        room09.addDoor("L");



        this.grid = new Grid(25, 25);
        this.grid.addRoom(room01, 1, 1);
        this.grid.addRoom(room02, 1, 9);
        this.grid.addRoom(room03, 1, 16);
        this.grid.addRoom(room04, 9, 1);
        this.grid.addRoom(room05, 11, 9);
        this.grid.addRoom(room06, 11, 19);
        this.grid.addRoom(room07, 18, 1);
        this.grid.addRoom(room08, 20, 10);
        this.grid.addRoom(room09, 20, 19);
        this.grid.print();

    }

    /**
     * Main method of TempGrid class
     * @param args
     */
    public static void main(String[] args) {
        TempGrid g = new TempGrid();
    }
}
