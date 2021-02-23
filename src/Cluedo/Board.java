package Cluedo;

import java.util.ArrayList;
import java.util.List;

public class Board {
    String board_data;

    public Board() {
        this.board_data = new Data("board.txt").board;
        System.out.println(board_data);
        this.Lexer();
    }

    public static void main(String[] args) {
        Board board = new Board();
    }

    public void Lexer() {
        List<List<Object>> board = new ArrayList<List<Object>>();
        for (String row : this.board_data.split("\n")) {
            String[] d = row.split(" ");
//            System.out.println(Arrays.toString(d));
            List<Object> col_list = new ArrayList<Object>();
            for (String obj : d) {
//                System.out.println(obj + obj.length());
                if (String.valueOf(obj.charAt(0)).equals("R")) {
                    Room room = new Room(obj.substring(1,obj.length()));
//                    System.out.println(room.toString());
                    col_list.add(room);
                }
                if (String.valueOf(obj.charAt(0)).equals("d")) {
                    Door door = new Door();
//                    System.out.println(door.toString());
                    col_list.add(door);
                }
                if (String.valueOf(obj.charAt(0)).equals("x")) {
                    Space space = new Space(obj.length());
//                    System.out.println(space.toString());
                    col_list.add(space);
                }
            }
//            System.out.println(col_list);
            board.add(col_list);
        }
        System.out.println(board);
    }
}
