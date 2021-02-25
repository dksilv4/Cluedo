package Cluedo;

import org.json.JSONObject;

public class Cluedo {
    public static void main(String[] args) {
        System.out.println("test");
        Data data = new Data("data.json");
        JSONObject playerPieces = (JSONObject) data.jsonData.get("PlayerPieces");
        System.out.println(playerPieces.get("0"));
        
    }
}
