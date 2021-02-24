package Cluedo;

import java.util.ArrayList;
import java.util.List;

public class Board {
    String board_data;
    List<List<Object>> grid;

    public Board() {
        this.board_data = new Data("board.txt").board;
        this.grid = this.getGrid(20,20);
        this.printGrid();
        this.changeGridTile(1, 1, "X");
    }

    public static void main(String[] args) {
        Board board = new Board();
    }

    public void Lexer() {
        List<List<Object>> board = new ArrayList<List<Object>>();
        for (String row : this.board_data.split("\n")) {
            String[] d = row.split(" ");
            List<Object> col_list = new ArrayList<Object>();
            for (String obj : d) {
                if (String.valueOf(obj.charAt(0)).equals("R")) {
                    col_list.add(new Room(obj.substring(1,obj.length())));
                }
                if (String.valueOf(obj.charAt(0)).equals("d")) {
                    col_list.add(new Door());
                }
                if (String.valueOf(obj.charAt(0)).equals("x")) {
                    col_list.add(new Space(obj.length()));
                }
            }
            board.add(col_list);
        }
        System.out.println(board);
        for(List<Object> row: board){
            System.out.println(row);
        }
    }

    public List<List<Object>> getGrid(int rows, int columns){
        List<List<Object>> grid = new ArrayList<List<Object>>();
        for(int x=0;x<rows;x++){
            List<Object> row = new ArrayList<>();
            for(int y=0;y<columns;y++){
                row.add(new Tile());
            }
            grid.add(row);
        }
        return grid;
    }
    public void printGrid(){
        for(List<Object> row: this.grid){
            System.out.println(row);
        }
    }
    public void changeGridTile(int row, int column, Object value){

    }
}
