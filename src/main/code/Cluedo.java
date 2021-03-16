package code;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cluedo {
    List<PlayerPiece> playerPieces;
    public Cluedo(){
        List<PlayerPiece> playerPieces = this.getPlayerPieces();
        System.out.println(playerPieces);
    }
    public List<PlayerPiece> getPlayerPieces(){
        List<PlayerPiece> playerPieces = new ArrayList<PlayerPiece>();
        Data data = new Data("data.json");
        JSONObject playerPiecesData = (JSONObject) data.jsonData.get("PlayerPieces");
        System.out.println(playerPieces);
        for(int i=0;i<6;i++){
            String playerPieceName = String.valueOf(playerPiecesData.get(""+i+""));
            this.playerPieces.add(new PlayerPiece(playerPieceName));
        }
        return playerPieces;
    }
//    public static void main(String[] args) {
//        Cluedo cluedo = new Cluedo();
//
//    }

}
