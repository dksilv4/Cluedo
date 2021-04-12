package code;

import org.json.JSONObject;

import java.util.*;

public class Cluedo {
    private List<SuspectCard> suspectCards;
    private List<WeaponCard> weaponCards;
    private List<RoomCard> roomCards;
    private List<PlayerPiece> playerPieces;
    private List<WeaponPiece> weaponPieces;
    private final List<Card> cards = new ArrayList<Card>();
    private List<Player> players = new ArrayList<Player>();
    private final Board board = new Board();
    private Scanner sc = new Scanner(System.in);
    private Envelope envelope;

    /**
     * Initializes Cluedo and loads all the necessary data to run the game
     */
    public Cluedo() {
        if (!isJUnitTest()) { // if not running from test
            this.setUpPlayers();
            this.setUpCards();
            this.setSpawns();
            this.getPlayerTurnOrder();
            this.fillDetectiveSlips();
            this.allocateWeapons();

            //this.turn();
//            for(Room room: this.board.getRooms()){
//                System.out.println(room.getWeaponPiece());
//            }
        }
    }

    /**
     * Represents a turn within the game.
     */
    public void turn() {
        boolean running = true;
        while (running) {
            for (PlayerPiece playerPiece : this.playerPieces) {
                if(!playerPiece.isKicked()){
                    playerPiece.setPlaying(true);
                    this.board.getGrid().print();

                    int steps = rollDice();
                    System.out.println(playerPiece + " rolled " + steps);
//                if (playerPiece.getName().equals("Prof Plum")) {
//                    running = false;
//                    break;
//                }
//                    Boolean stop = false;
                    while (steps > 0) {
                        System.out.println("Steps left:" + steps);
                        String direction = this.getUserDirection();
                        if (direction.equals("u") || direction.equals("d") || direction.equals("l") || direction.equals("r")) {
                            Tile oldLocation = playerPiece.getLocation();
                            this.playerMove(playerPiece, direction);
                            if (oldLocation.equals(playerPiece.getLocation())) {
                                System.out.println("Player could not move in that direction.");
                            } else if (playerPiece.getLocation().getType().equals("room") && oldLocation.getType().equals("door")) {
                                steps = 0;
//                                verifySuggestion(this.getCardChoices(playerPiece));
//                                System.out.println("The suggested cards have been marked.");

                            } else if (!(playerPiece.getLocation().getType().equals("room") && oldLocation.getType().equals("room"))) {
                                steps--;
                            } else if (playerPiece.getLocation().getType().equals("passage") && oldLocation.getType().equals("room")) {
                                useSecretPassage(playerPiece.getLocation().getBelongsTo(), playerPiece);
                                steps--;
                            }
                        }
                        else if (direction.equals("s")) {
                            steps = 0;
                            playerPiece.setPlaying(false);
                        }
                    }
//                    System.out.println("Do you want to make an accusation? Y/N");
//                    if(sc.nextLine().toUpperCase().equals("Y")){
//                        if(this.makeAccusation(getCardChoices(playerPiece))){
//                            //PLAYER WINS
//                            running = false;
//                        }
//                        else{
//                            playerPiece.setKicked(true);
//                        }
//
//                    }

                }

            }
            running = false;
        }
    }
    private RoomCard findRoomCard(String search){
        for (RoomCard card : this.getRoomCards()) {
            if (card.getName().toLowerCase().equals(search.toLowerCase())) {
                return card;
            }
        }
        return null;
    }
    public WeaponCard findWeaponCard(String search){
        for (WeaponCard card : this.getWeaponCards()){
            if (card.getName().toLowerCase().equals(search.toLowerCase())){
                return card;
            }
        }
        return null;
    }
    public SuspectCard findSuspectCard(String search){
        for (SuspectCard card : this.getSuspectCards()){
            if (card.getName().toLowerCase().equals(search.toLowerCase())){
                return card;
            }
        }
        return null;
    }
    public CardChoice getCardChoices(PlayerPiece playerPiece){
        System.out.println("Please select one of the following Rooms (Type in room name):");
        System.out.println(this.getRoomCards());
        boolean validCard = false;
        RoomCard roomCard = this.findRoomCard(sc.nextLine());
        if(roomCard == null){
            while(!validCard){
                System.out.println("Invalid input! Please select one of the following Rooms (Type in room name):");
                System.out.println(this.getRoomCards());
                roomCard = this.findRoomCard(sc.nextLine());
                if(roomCard!=null){
                    validCard = true;
                }
            }
        }
        validCard = false;
        WeaponCard weaponCard = this.findWeaponCard(sc.nextLine());
        if(weaponCard == null){
            while(!validCard){
                System.out.println("Invalid input! Please select one of the following weapons (Type in room name):");
                System.out.println(this.getRoomCards());
                weaponCard = this.findWeaponCard(sc.nextLine());
                if(weaponCard!=null){
                    validCard = true;
                }
            }
        }
        validCard = false;
        SuspectCard suspectCard = this.findSuspectCard(sc.nextLine());
        if(suspectCard == null){
            while(!validCard){
                System.out.println("Invalid input! Please select one of the following suspect (Type in room name):");
                System.out.println(this.getRoomCards());
                suspectCard = this.findSuspectCard(sc.nextLine());
                if(suspectCard!=null){
                    validCard = true;
                }
            }
        }
        return new CardChoice(playerPiece, roomCard, weaponCard, suspectCard);



    }
    public void verifySuggestion(CardChoice cardChoices) {
        RoomCard room = cardChoices.getRoom();
        WeaponCard weapon = cardChoices.getWeapon();
        SuspectCard suspect = cardChoices.getSuspect();
        PlayerPiece playerPiece = cardChoices.getPlayerPiece();
        boolean endSuggestion = false;
        int prevIndex = this.getPlayerPieces().indexOf(playerPiece) - 1;
        while (!endSuggestion) {
            List<Card> foundCards = new ArrayList<Card>();
            PlayerPiece piece = this.getPlayerPieces().get(prevIndex);
            if (!piece.equals(playerPiece)) {
                Player player = piece.getBelongsTo();
                List<Card> playerCards = player.getCards();
                for (Card card : playerCards) {
                    if (card instanceof RoomCard && card.equals(room)|| card instanceof WeaponCard && card.equals(weapon)||card instanceof SuspectCard && card.equals(suspect)) {
                        foundCards.add(card);
                    }
                }
                if (foundCards.size() == 0) {
                    endSuggestion = true;
                } else {
                    Card revealedCard = foundCards.get((int) (Math.random() * foundCards.size() + 1));
                    System.out.println("Card has been revealed from " + player + " " + revealedCard);
                    prevIndex--;
                    if (prevIndex < 0) {
                        prevIndex = 6;
                    }
                }
            }

        }
    }

    public boolean makeAccusation(CardChoice cardChoice){
        return (cardChoice.getRoom().equals(this.envelope.getRoom()) && cardChoice.getWeapon().equals(this.envelope.getWeapon())&& cardChoice.getSuspect().equals(this.envelope.getSuspect()));
    }

    public void useSecretPassage(Room room, PlayerPiece playerPiece) {
        String roomName = room.getName();
        switch (roomName) {
            case "Kitchen":
                Room study = this.board.getRoom("Study");
                Tile studyPassage = study.getSecretPassage();
                studyPassage.setOccupier(playerPiece);
                playerPiece.getLocation().removeOccupier();
                playerPiece.setLocation(studyPassage);
                break;
            case "Study":
                Room kitchen = this.board.getRoom("Kitchen");
                Tile kitchenPassage = kitchen.getSecretPassage();
                kitchenPassage.setOccupier(playerPiece);
                playerPiece.getLocation().removeOccupier();
                playerPiece.setLocation(kitchenPassage);
                break;
            case "Conservatory":
                Room lounge = this.board.getRoom("Lounge");
                Tile loungePassage = lounge.getSecretPassage();
                loungePassage.setOccupier(playerPiece);
                playerPiece.getLocation().removeOccupier();
                playerPiece.setLocation(loungePassage);
                break;
            case "Lounge":
                Room conservatory = this.board.getRoom("Conservatory");
                Tile conservatoryPassage = conservatory.getSecretPassage();
                conservatoryPassage.setOccupier(playerPiece);
                playerPiece.getLocation().removeOccupier();
                playerPiece.setLocation(conservatoryPassage);
                break;
        }
    }

    private String getUserDirection() {
        System.out.println("Which direction u going fammmmmm??\n>>>");
        String direction = sc.nextLine().toLowerCase();
        return direction;
    }

    private void playerMove(PlayerPiece playerPiece, String direction) {
        // Row will be -1 from player location with the same column value
        System.out.println(playerPiece + " trying to move " + direction + ".");
        Tile playerLocation = playerPiece.getLocation();
        System.out.println("Player in: " + playerLocation.getRow() + " " + playerLocation.getColumn());
        int newRow = playerLocation.getRow();
        int newColumn = playerLocation.getColumn();
        switch (direction) {
            case "u":
                newRow -= 1;
                break;
            case "d":
                newRow += 1;
                break;
            case "l":
                newColumn -= 1;
                break;
            case "r":
                newColumn += 1;
                break;
        }
        if (newRow >= 0 && newColumn >= 0 && newRow < this.board.getGrid().getRows() && newColumn < this.board.getGrid().getColumns()) {
            System.out.println(newRow + " " + newColumn);
            Tile newLocation = this.board.getGrid().getTile(newRow, newColumn);
            System.out.println("New Location:" + newLocation);
            System.out.println("Cords: " + newLocation.getRow() + " " + newLocation.getColumn());
            if (newLocation.available()) {
                playerLocation.removeOccupier();
                playerPiece.setLocation(newLocation);
                newLocation.setOccupier(playerPiece);
                System.out.println("Player moved to: " + playerLocation.getRow() + " " + playerLocation.getColumn());
                this.board.getGrid().print();
            }
        }
    }

    public void allocateWeapons() {
        Collections.shuffle(this.getWeaponPieces());
        List<Room> rooms = this.board.getRooms();
        Collections.shuffle(rooms);
        int roomIndex = 0;
        for (WeaponPiece weapon : this.getWeaponPieces()) {
            Room room = rooms.get(roomIndex);
            if (room.getWeaponPiece() == null) {
                room.setWeaponPiece(weapon);
                roomIndex += 1;
            }
        }
    }

    public List<PlayerPiece> getPlayerPieces() {
        return this.playerPieces;
    }

    public Board getBoard() {
        return this.board;
    }

    /**
     * Assign the player pieces to the players.
     * Takes in playerHash which is a hashmap that contains all the players and the number they got from rolling the dice.
     * Player with the highest numbers gets a playerPiece assigned to them first.
     *
     * @param playerHash
     */
    public void assignPlayerPieces(Map<Player, Integer> playerHash) {
        List<Integer> nums = new ArrayList<>(playerHash.values());// get all the numbers from playerHash
        List<Player> players = new ArrayList<>(playerHash.keySet()); // get all the players from the hash map
        Collections.sort(nums); // sort the list of numbers
        Collections.reverse(nums);// reverse the list so the highest numbers are first
        int pieceIndex = 0; // variable used to represent the index for the playerPiece
        List<Integer> usedNums = new ArrayList<Integer>();// Empty list that will be used to know what numbers have been used.
        for (Integer num : nums) { // go through each number in numbers
            if (!usedNums.contains(num)) {// if the usedNums list doesn't contain num
                for (Player player : players) { // for each player
                    if (playerHash.get(player).equals(num)) {// if the num is the number they got from their dice roll
                        player.setPiece(this.playerPieces.get(pieceIndex)); // give them the playerPiece at index pieceIndex
                        pieceIndex++;// increment pieceIndex
                        usedNums.add(num);//add the used number into usedNums list
                    }
                }
            }
        }
    }

    /**
     * Rolls the dice for all the players and adds it to a HashMap and runs the assignPlayerPieces function giving the hashmap as a parameter.
     */
    public void getPlayerTurnOrder() {
        Map<Player, Integer> turn = new HashMap<Player, Integer>();
        for (Player player : this.players) {
            turn.put(player, rollDice());
        }
        System.out.println(turn);
        this.assignPlayerPieces(turn);
    }

    /**
     * @return an integer that represents a roll of two die by the player
     */
    public static int rollDice() {
        return (int) (Math.random() * 6 + 1) + (int) (Math.random() * 6 + 1);
    }

    /**
     * @return true or false representing whether the class is being run from a test class.
     */
    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gives all the playerPieces a detective slip.
     */
    public void setUpDetectiveSlips() {
        for (PlayerPiece playerPiece : this.playerPieces) {
            playerPiece.setSlip(new DetectiveSlip(this.suspectCards, this.weaponCards, this.roomCards));
        }
    }

    /**
     * Sets the spawns for each playerPiece
     * A list of possible spawns are imported from board and then a random tile is selected to the spawn of a playerPiece/
     */
    public void setSpawns() {
        List<Tile> spawns = this.board.getSpawns();// get all the possible spawns from board
        List<Tile> usedSpawns = new ArrayList<Tile>();// empty list used to represent all the spawns that have been used so no playerPiece gets the same spawn
        for (PlayerPiece playerPiece : this.playerPieces) { // for each playerPiece
            boolean hasSpawn = false; // boolean to represent whether the playerPiece has a spawn and used to initiate and stop the following while loop.
            while (!hasSpawn) { // while hasSpawn is false.
                int random_int = (int) (Math.random() * spawns.size());// get random number with max number of the total possible tiles the pieces can spawn from
                Tile spawn = spawns.get(random_int); // get spawn from spawns list using the random int as an index
                if (!usedSpawns.contains(spawn)) {// if the spawn isn't in the usedSpawn list
                    playerPiece.setLocation(spawn);// set the location of the playerPiece to the spawn
                    usedSpawns.add(spawn);//add the spawn to usedSpawns
                    hasSpawn = true; // set the variable to true so the while loop stops.
                }
            }
        }
    }

    /**
     * Temporary function to setUp players
     */
    public void setUpPlayers() {
        this.players.add(new Player("Diogo"));
        this.players.add(new Player("Hayden"));
        this.players.add(new Player("Gogo"));
    }

    /**
     * @return the variable players (List of players)
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    private void fillDetectiveSlips() {
        for (Player player : this.players) {
            player.fillSlip();
        }
    }

    /**
     * Function to run all the necessary functions to setUp the cards in the game in a specific order.
     */
    public void setUpCards() {
        this.setSuspectCardsAndPlayerPieces();
        this.setWeaponCardsAndPieces();
        this.setRoomCards();
        this.setUpDetectiveSlips();
        this.shuffleCards();
        this.setEnvelope();
        this.shuffleCards();
        this.gatherCards();
        this.giveOutCards();
    }


    /**
     * Makes the Murder Envelope and removes the cards put into the envelope from their individual lists.
     */
    public void setEnvelope() {
       this.envelope = new Envelope(this.roomCards.get(0), this.weaponCards.get(0), this.suspectCards.get(0));
        this.roomCards.remove(0);
        this.weaponCards.remove(0);
        this.suspectCards.remove(0);
    }

    /**
     * @return cards
     */
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Shuffles all the cards lists.
     */
    public void shuffleCards() {
        Collections.shuffle(suspectCards);
        Collections.shuffle(weaponCards);
        Collections.shuffle(roomCards);

    }

    /**
     * @param players (sets players to the param)
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Required for testing
     * Takes in hashMap and assigns new players to the players list removing the old ones.
     *
     * @param playerHash representing players and its roll die numbers
     */
    public void setPlayerOrder(Map<Player, Integer> playerHash) {
        this.players.clear();
        this.players.addAll(playerHash.keySet());
    }

    /**
     * Put all the cards into cards.
     */
    public void gatherCards() {
        this.cards.clear();
        this.cards.addAll(this.roomCards);
        this.cards.addAll(this.weaponCards);
        this.cards.addAll(this.suspectCards);
    }


    /**
     * Gives out all the cards to each player within the list of players
     * Only stops when all the cards have been given out.
     */
    public void giveOutCards() {
        boolean shared = false;
        while (!shared) {
            for (Player player : this.players) {
                if (this.cards.size() == 0) {
                    shared = true;
                } else {
                    player.addCard(this.cards.get(0));
                    cards.remove(0);
                }
            }
        }
    }

    /**
     * @return suspectCards
     */
    public List<SuspectCard> getSuspectCards() {
        return this.suspectCards;
    }

    /**
     * @return weaponCards
     */
    public List<WeaponCard> getWeaponCards() {
        return this.weaponCards;
    }

    /**
     * @return roomCards
     */
    public List<RoomCard> getRoomCards() {
        return this.roomCards;
    }

    /**
     * It makes a list of all the suspect cards  and playerPieces from the file: data.json
     */
    public void setSuspectCardsAndPlayerPieces() {
        List<SuspectCard> suspectCards = new ArrayList<SuspectCard>(); // empty list for the suspect cards
        List<PlayerPiece> playerPieces = new ArrayList<PlayerPiece>(); // empty list for playerPieces
        Data data = new Data("data.json");// read data from file data.json
        JSONObject suspectData = (JSONObject) data.getJsonData().get("Suspects");// get the data within the class suspects
        for (int i = 0; i < 6; i++) {// for i from 0 to 5
            String suspectName = String.valueOf(suspectData.get(String.valueOf(i))); // Get name of suspect
            PlayerPiece playerPiece = new PlayerPiece(suspectName); // new playerPiece instance
            suspectCards.add(new SuspectCard(suspectName)); // add a new instance of suspectCard into suspectCards
            playerPieces.add(playerPiece);// add playerPiece instance into playerPieces

        }
        // assign the lists to class variables.
        this.suspectCards = suspectCards;
        this.playerPieces = playerPieces;
    }

    /**
     * Makes a list of weapon cards and pieces from the file data.json
     */
    public void setWeaponCardsAndPieces() {
        List<WeaponCard> weaponCards = new ArrayList<>(); // empty list for the new weapon cards
        List<WeaponPiece> weaponPieces = new ArrayList<>(); // empty list for the new weapon pieces

        Data data = new Data("data.json"); // read data from data.json
        JSONObject weaponData = (JSONObject) data.getJsonData().get("Weapons"); // get the weapon data from the data
        for (int i = 0; i < 6; i++) { // for loop 0 to 5
            String weaponName = String.valueOf(weaponData.get(String.valueOf(i))); // get the weapon name
            //Make and add weaponCard and WeaponPiece to their specified lists.
            weaponCards.add(new WeaponCard(weaponName));
            weaponPieces.add(new WeaponPiece(weaponName));
        }
        //Assign the lists into their class variables
        this.weaponCards = weaponCards;
        this.weaponPieces = weaponPieces;
    }

    /**
     * Make a list of all room cards from data.json
     */
    public void setRoomCards() {
        List<RoomCard> roomCards = new ArrayList<RoomCard>(); // empty list for the new roomCards
        Data data = new Data("data.json"); // get data from file
        JSONObject roomData = (JSONObject) ((JSONObject) data.getJsonData().get("OriginalBoard")).get("Rooms"); // get the room data from data
        for (String keyStr : roomData.keySet()) {
            String roomName = String.valueOf(((JSONObject) roomData.get(keyStr)).get("name")); // get room name from data
            if (!roomName.equals("X")) {// if room isn't named X
                roomCards.add(new RoomCard(roomName)); // add room to list
            }
        }
        // assign the list into its class variable
        this.roomCards = roomCards;
    }

//    public static void main(String[] args) {
//        Cluedo cluedo = new Cluedo();
//    }

    /**
     * @return weaponPieces
     */
    public List<WeaponPiece> getWeaponPieces() {
        return weaponPieces;
    }
}
