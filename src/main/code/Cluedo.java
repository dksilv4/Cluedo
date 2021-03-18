package code;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cluedo {
    private List<SuspectCard> suspectCards;
    private List<WeaponCard> weaponCards;
    private List<PlayerPiece> playerPieces;
    private List<WeaponPiece> weaponPieces;
    private List<RoomCard> roomCards;
    private final List<Card> cards = new ArrayList<Card>();
    private Envelope envelope;
    private List<Player> players = new ArrayList<Player>();
    private final Board board = new Board();

    /**
     * Initializes Cluedo and set suspects, weapons and rooms
     */
    public Cluedo() {



    }

    public void setSpawns(){
        System.out.println(this.board.getSpawns());
    }
    public void setUpPlayers() {
        this.players.add(new Player("Diogo"));
        this.players.add(new Player("Hayden"));
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    private void setUpCards() {
        this.setSuspectCards();
        this.setWeaponCards();
        this.setRoomCards();
        System.out.println(suspectCards);
        System.out.println(weaponCards);
        System.out.println(roomCards);
        this.shuffleCards();
        this.setEnvelope();
        System.out.println(envelope);
        this.shuffleCards();
        this.gatherCards();
        this.giveOutCards();
        for (Player player : this.players) {
            System.out.println(player.getCards().size());
        }
    }

    public void setEnvelope() {
        this.envelope = new Envelope(this.roomCards.get(0), this.weaponCards.get(0), this.suspectCards.get(0));
        this.roomCards.remove(0);
        this.weaponCards.remove(0);
        this.suspectCards.remove(0);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void shuffleCards() {
        Collections.shuffle(suspectCards);
        Collections.shuffle(weaponCards);
        Collections.shuffle(roomCards);

    }
    public void setPlayers(List<Player> players){
        this.players = players;
    }
    public void gatherCards() {
        this.cards.addAll(this.roomCards);
        this.cards.addAll(this.weaponCards);
        this.cards.addAll(this.suspectCards);
    }


    public void giveOutCards() {
        boolean shared = false;
        while (!shared) {
            System.out.println(cards.size());
            for (Player player : this.players) {
                if(this.cards.size()== 0){
                    shared = true;
                }
                else{
                    player.addCard(this.cards.get(0));
                    cards.remove(0);
                }
            }
        }
    }

    public List<SuspectCard> getSuspectCards() {
        return this.suspectCards;
    }

    public List<WeaponCard> getWeaponCards() {
        return this.weaponCards;
    }

    public List<RoomCard> getRoomCards() {
        return this.roomCards;
    }

    /**
     * It makes a list of all the suspect cards from the file: data.json
     */
    public void setSuspectCards() {
        List<SuspectCard> suspectCards = new ArrayList<SuspectCard>();
        List<PlayerPiece> playerPiece = new ArrayList<PlayerPiece>();
        Data data = new Data("data.json");
        JSONObject suspectData = (JSONObject) data.getJsonData().get("Suspects");
        for (int i = 0; i < 6; i++) {
            String suspectName = String.valueOf(suspectData.get("" + i + ""));
            suspectCards.add(new SuspectCard(suspectName));
            playerPieces.add(new PlayerPiece(suspectName));
        }
        this.suspectCards = suspectCards;
        this.playerPieces = playerPieces;
    }

    /**
     * Makes a list of weapon cards from the file data.json
     */
    public void setWeaponCards() {
        List<WeaponCard> weaponCards = new ArrayList<>();
        List<WeaponPiece> weaponPieces = new ArrayList<>();

        Data data = new Data("data.json");
        JSONObject weaponData = (JSONObject) data.getJsonData().get("Weapons");
        for (int i = 0; i < 6; i++) {
            String weaponName = String.valueOf(weaponData.get(String.valueOf(i)));
            weaponCards.add(new WeaponCard(weaponName));
            weaponPieces.add(new WeaponPiece(weaponName));
        }
        this.weaponCards = weaponCards;
        this.weaponPieces = weaponPieces;
    }

    /**
     * Make a list of all room cards from data.json
     */
    public void setRoomCards() {
        List<RoomCard> roomCards = new ArrayList<RoomCard>();
        Data data = new Data("data.json");
        JSONObject roomData = (JSONObject) ((JSONObject) data.getJsonData().get("OriginalBoard")).get("Rooms");
        for (String keyStr : roomData.keySet()) {
            String roomName = String.valueOf(((JSONObject) roomData.get(keyStr)).get("name"));
//            System.out.println(roomName);
            if (!roomName.equals("X")) {
                System.out.println(roomName);
                roomCards.add(new RoomCard(roomName));
            }
        }
        this.roomCards = roomCards;
    }

    public static void main(String[] args) {
        Cluedo cluedo = new Cluedo();
        cluedo.setUpPlayers();
        cluedo.setUpCards();
        cluedo.setSpawns();
//        System.out.println(cluedo.playerPieces);
    }

    public List<WeaponPiece> getWeaponPieces() {
        return weaponPieces;
    }
}
