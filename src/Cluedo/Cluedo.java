package Cluedo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cluedo {
    public List<PlayerPiece> playerPieces;
    public Cluedo(){
        this.setPlayerPieces();
        System.out.println(playerPieces);
    }
    private void setPlayerPieces(){
        List<PlayerPiece> playerPieces = new ArrayList<PlayerPiece>();
        Data data = new Data("data.json");
        JSONObject playerPiecesData = (JSONObject) data.jsonData.get("PlayerPieces");
//        System.out.println(playerPieces);
//        System.out.println(playerPiecesData);
        for(int i=0;i<6;i++){
            String playerPieceName = String.valueOf(playerPiecesData.get(""+i+""));
//            System.out.println(playerPieceName);
            playerPieces.add(new PlayerPiece(playerPieceName));
        }
        this.playerPieces = playerPieces;
    }
    public List<PlayerPiece> getPlayerPieces() {
        return this.playerPieces;
    }

    public static void main(String[] args) {
        Cluedo cluedo = new Cluedo();
//        System.out.println(cluedo.playerPieces);

    }

}
