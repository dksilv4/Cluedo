package code;

import org.json.JSONObject;

import java.util.*;
import javafx.util.Pair;

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
    private List<Pair<Player, Integer>> playerOrder = new ArrayList<>();
    private Envelope envelope;
    private Player currentPlayersTurn;
    private int currentPlayersSteps;
    private int turnIndicator = 0; // This will iterate through playerOrder to indicate who's turn it is.
    private GameState state;
    int turnNo = 0;

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
            this.turn();
        }
    }
    public GameState state() {
        return this.state;
    }
    public Player getCurrentPlayersTurn() {
        return this.currentPlayersTurn;
    }
    public int getStepsLeft() {
        return currentPlayersSteps;
    }
    public List<Pair<Player, Integer>> getPlayOrder() {
        return this.playerOrder;
    }
    private void assignPlayerPiecesB(List<Pair<Player, Integer>> playerOrder) {
        playerOrder.sort(Comparator.comparing(p -> -p.getValue()));

        for (int i = 0; i < playerOrder.size(); i++) {
            playerOrder.get(i).getKey().setPiece(this.playerPieces.get(i));
        }
    }
    public void rollDiceB(GameState currentState, List<Pair<Player, Integer>> playerOrder) {
        switch (currentState) {
            case AssigningPlayerPieces:
                int roll = rollDice();
                System.out.println(currentPlayersTurn.getName() + " rolled " + roll);
                playerOrder.add(new Pair<>(currentPlayersTurn, roll));

                int nextPlayersIndex = (players.indexOf(currentPlayersTurn) + 1) % players.size();
                currentPlayersTurn = players.get(nextPlayersIndex);

                if (playerOrder.size() == players.size()) {
                    this.assignPlayerPiecesB(playerOrder);
                    this.fillDetectiveSlips();
                    this.allocateWeapons();
                    currentPlayersTurn = playerOrder.get(0).getKey();
                    currentPlayersSteps = rollDice();
                    state = GameState.InPlay;
                    System.out.println("All player pieces assigned.");
                }
                break;
            case InPlay:
                currentPlayersSteps = rollDice();
                break;
            default:

        }
    }

    // Moves the current player's player piece.
    public void movePlayerPiece(Tile newTile) {
        // Check if the target tile is a position that can be moved to.
        PlayerPiece pp = currentPlayersTurn.getPiece();
        int tileRow = pp.getLocation().getRow();
        int tileCol = pp.getLocation().getColumn();
        List<Pair<Integer, Integer>> neighbours = new ArrayList<>();

        // Generate immediately adjacent tiles.
        for (int row = tileRow - 1; row <= tileRow + 1; row++) {
            if (row == tileRow || row > board.getGrid().getRows() ||
                    row < 0) {
                continue;
            }
            neighbours.add(new Pair<>(row, tileCol));
        }
        for (int col = tileCol - 1; col <= tileCol + 1; col++) {
            if (col == tileCol || col > board.getGrid().getColumns() ||
                    col < 0) {
                continue;
            }
            neighbours.add(new Pair<>(tileRow, col));
        }

        boolean canMove = neighbours.contains(new Pair<>(newTile.getRow(),
                newTile.getColumn())) && (currentPlayersSteps > 0)
                && state == GameState.InPlay;

        if(canMove) {
            currentPlayersTurn.getPiece().getLocation().removeOccupier();
            currentPlayersTurn.getPiece().setLocation(newTile);
            currentPlayersSteps--;
        }

        if (currentPlayersSteps <= 0) {
            endTurn();
            this.getPlayerTurnOrder();
            this.fillDetectiveSlips();
            this.allocateWeapons();
            this.turn();
//            for(Room room: this.board.getRooms()){
//                System.out.println(room.getWeaponPiece());
//            }
        }
    }

    private void endTurn() {
        turnIndicator = (turnIndicator + 1) % playerOrder.size();
        currentPlayersTurn = playerOrder.get(turnIndicator).getKey();
    }

    /**
     * Represents a turn within the game.
     */
    public void turn() {
        boolean running = true;
        while (running) {
            this.turnNo++;
            sc.nextLine();
            for (PlayerPiece playerPiece : this.playerPieces) {
                if (!running) {
                    break;
                }
                if (this.turnNo > 10) {
                    break;
                }
                System.out.println("Current Turn: " + this.turnNo + "\nCurrent Player: " + playerPiece.getBelongsTo());
                System.out.println(envelope);
                if (playerPiece.ableToPlay()) {
                    playerPiece.setPlaying(true);
                    boolean endTurn = false;
                    boolean rolledDice = false;
                    boolean madeSuggestion = false;
                    boolean madeAccusation = false;
                    while (!endTurn) {
                        int playerChoice = this.askOptions(playerPiece, rolledDice, madeSuggestion, madeAccusation);
                        this.board.getGrid().print(); //Print out the grid.
                        if (playerChoice == 1) {
                            int steps = rollDice(); //Roll Dice
                            System.out.println(playerPiece + " rolled " + steps);
                            while (steps > 0) {
                                System.out.println("Steps left:" + steps);
                                String direction = this.getUserDirection(playerPiece);
                                System.out.println("Player chose to go: " + direction);
                                if (direction.equals("u") || direction.equals("d") || direction.equals("l") || direction.equals("r")) {
                                    Tile oldLocation = playerPiece.getLocation();
                                    Tile newLocation = this.playerMoveNewLocation(playerPiece, direction);
                                    if(newLocation !=null && newLocation.isAvailable()){
                                        this.playerMove(newLocation, playerPiece);
                                        if (oldLocation.equals(playerPiece.getLocation())) {
                                            System.out.println("Player could not move in that direction.");
                                        } else if (playerPiece.getLocation().getType().equals("room") && oldLocation.getType().equals("door")) {
                                            steps = 0;
                                        } else if (!(playerPiece.getLocation().getType().equals("room") && oldLocation.getType().equals("room"))) {
                                            steps--;
                                        } else if (playerPiece.getLocation().getType().equals("passage") && oldLocation.getType().equals("room")) {
                                            useSecretPassage(playerPiece.getLocation().getBelongsTo(), playerPiece);
                                            steps--;
                                        }
                                    }
                                    else{
                                        System.out.println("Invalid Direction!");
                                    }

                                } else if (direction.equals("s")) {
                                    steps = 0;
                                    playerPiece.setPlaying(false);
                                } else {
                                    System.out.println("Invalid Direction!");
                                }
                            }
                            rolledDice = true;
                        } else if (playerChoice == 2) {
                            //Make Accusation
                            if (!this.makeAccusation(this.getCardChoices(playerPiece))) {
                                System.out.println("You made an invalid accusation. You have been eliminated.");
                                playerPiece.setKicked(true);
                                playerPiece.setPlaying(false);
                            } else {
                                running = false;
                                System.out.println(playerPiece.getBelongsTo().getName() + " won the game!");
                            }
                            break;
                        } else if (playerChoice == 3) {
                            //Skip turn
                            System.out.println("You skipped your turn.");
                            endTurn = true;
                        } else if (playerChoice == 4) {
                            //Make Suggestion
                            this.verifySuggestion(this.getCardChoices(playerPiece));
                            madeSuggestion = true;
                        }
                    }
                }
            }
        }
    }
    private Tile playerMoveNewLocation(PlayerPiece playerPiece, String direction){
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
            return this.board.getGrid().getTile(newRow, newColumn);
        }
        return null;
    }
    private void playerMove(Tile newLocation, PlayerPiece playerPiece){
        Tile playerLocation = playerPiece.getLocation();
        if (newLocation.getType().equals("passage")) {
            this.useSecretPassage(newLocation.getBelongsTo(), playerPiece);
        } else {
            playerLocation.removeOccupier();
            playerPiece.setLocation(newLocation);
            newLocation.setOccupier(playerPiece);
            System.out.println("Player moved to: " + playerLocation.getRow() + " " + playerLocation.getColumn());
            this.board.getGrid().print();
        }
    }
    public int askOptions(PlayerPiece playerPiece, boolean rolledDice, boolean madeAccusation, boolean madeSuggestion) {
        //What do you want to do?
        //1: Roll Dice
        //2: Skip (can press s when choosing direction to skip the rest of the steps and end turn)
        //3: Make a accusation
        //4: Make a suggestion (This shows up only when the player's in a room)
        boolean madeChoice = false;
        boolean inRoom = playerPiece.getLocation().getType().equals("room") && !playerPiece.getLocation().getBelongsTo().getName().equals("X");
        int choice = 0;
        while (!madeChoice) {
            List<Integer> aiOptions = new ArrayList<Integer>();
            boolean suggestion = false;
            System.out.println("What do you want to do?");
            if (!rolledDice) {
                System.out.println("1. Roll the dice");
                aiOptions.add(1);
            }
            if (!madeAccusation) {
                System.out.println("2. Make an accusation");
                if (this.turnNo > 15) {
                    aiOptions.add(2);
                }

            }
            System.out.println("3. Skip");
            aiOptions.add(3);
            if (inRoom) {
                if (!madeSuggestion) {
                    System.out.println("4. Make a suggestion");
                    suggestion = true;
                    aiOptions.add(1);
                }
            }
            if (!playerPiece.getBelongsTo().isAI()) {//If not an AI
                System.out.println("Please enter the number.");
                String str = sc.nextLine();
                if (str.matches("-?\\d+(\\.\\d+)?")) {
                    choice = Integer.parseInt(str);
                    if ((choice == 1 && !rolledDice) || (choice == 2 && !madeAccusation) || choice == 3 || (choice == 4 && suggestion)) {
                        madeChoice = true;
                    } else {
                        System.out.println("Invalid option.");
                    }
                }
            } else {
                System.out.println("AI Choices: " + aiOptions);
                choice = aiOptions.get((int) (Math.random() * aiOptions.size()));
                System.out.println("AI chose: " + choice);
                madeChoice = true;
            }

        }
        return choice;
    }

    private RoomCard findRoomCard(String search) {
        for (RoomCard card : this.getRoomCards()) {
            if (card.getName().equalsIgnoreCase(search)) {
                return card;
            }
        }
        return null;
    }

    public WeaponCard findWeaponCard(String search) {
        for (WeaponCard card : this.getWeaponCards()) {
            if (card.getName().equalsIgnoreCase(search)) {
                return card;
            }
        }
        return null;
    }

    public SuspectCard findSuspectCard(String search) {
        for (SuspectCard card : this.getSuspectCards()) {
            if (card.getName().equalsIgnoreCase(search)) {
                return card;
            }
        }
        return null;
    }

    public CardChoice getCardChoices(PlayerPiece playerPiece) {
        System.out.println("Please select one of the following Rooms (Type in room name):");
        System.out.println(this.getRoomCards());
        boolean validCard = false;
        RoomCard roomCard = this.findRoomCard(sc.nextLine());
        if (roomCard == null) {
            while (!validCard) {
                System.out.println("Invalid input! Please select one of the following Rooms (Type in room name):");
                System.out.println(this.getRoomCards());
                roomCard = this.findRoomCard(sc.nextLine());
                if (roomCard != null) {
                    validCard = true;
                }
            }
        }
        validCard = false;
        System.out.println("Please select one of the following Weapon (Type in room name):");
        System.out.println(this.getWeaponCards());
        WeaponCard weaponCard = this.findWeaponCard(sc.nextLine());
        if (weaponCard == null) {
            while (!validCard) {
                System.out.println("Invalid input! Please select one of the following weapons (Type in room name):");
                System.out.println(this.getWeaponCards());
                weaponCard = this.findWeaponCard(sc.nextLine());
                if (weaponCard != null) {
                    validCard = true;
                }
            }
        }
        validCard = false;
        System.out.println("Please select one of the following Suspect (Type in room name):");
        System.out.println(this.getSuspectCards());
        SuspectCard suspectCard = this.findSuspectCard(sc.nextLine());
        if (suspectCard == null) {
            while (!validCard) {
                System.out.println("Invalid input! Please select one of the following suspect (Type in room name):");
                System.out.println(this.getSuspectCards());
                suspectCard = this.findSuspectCard(sc.nextLine());
                if (suspectCard != null) {
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
                    if (card instanceof RoomCard && card.equals(room) || card instanceof WeaponCard && card.equals(weapon) || card instanceof SuspectCard && card.equals(suspect)) {
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

    public boolean makeAccusation(CardChoice cardChoice) {
        return (cardChoice.getRoom().equals(this.envelope.getRoom()) && cardChoice.getWeapon().equals(this.envelope.getWeapon()) && cardChoice.getSuspect().equals(this.envelope.getSuspect()));
    }

    public void useSecretPassage(Room room, PlayerPiece playerPiece) {
        String roomName = room.getName();
        switch (roomName) {
            case "Kitchen":
                Room study = this.board.getRoom("Study");
                Tile studyPassage = study.getSecretPassage();
                playerPiece.setLocation(studyPassage);
                break;
            case "Study":
                Room kitchen = this.board.getRoom("Kitchen");
                Tile kitchenPassage = kitchen.getSecretPassage();
                playerPiece.setLocation(kitchenPassage);
                break;
            case "Conservatory":
                Room lounge = this.board.getRoom("Lounge");
                Tile loungePassage = lounge.getSecretPassage();
                playerPiece.setLocation(loungePassage);
                break;
            case "Lounge":
                Room conservatory = this.board.getRoom("Conservatory");
                Tile conservatoryPassage = conservatory.getSecretPassage();
                playerPiece.setLocation(conservatoryPassage);
                break;
        }
        this.board.getGrid().print();

    }

    private String getUserDirection(PlayerPiece playerPiece) {
        System.out.println("Which direction would you like to go?\n(u=up, d=down, l=left, r=right)");
        if (!playerPiece.getBelongsTo().isAI()) {
            return sc.nextLine().toLowerCase();
        }
        List<String> options = new ArrayList<>();
        if(Objects.requireNonNull(this.playerMoveNewLocation(playerPiece, "u")).isAvailable()){
            options.add("u");
        }
        if(Objects.requireNonNull(this.playerMoveNewLocation(playerPiece, "d")).isAvailable()){
            options.add("d");
        }if(Objects.requireNonNull(this.playerMoveNewLocation(playerPiece, "l")).isAvailable()){
            options.add("l");
        }if(Objects.requireNonNull(this.playerMoveNewLocation(playerPiece, "r")).isAvailable()){
            options.add("r");
        }
        return options.get((int) (Math.random() * options.size()));
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
        this.players.add(new AI("Diogo"));
        this.players.add(new AI("Hayden"));
        this.players.add(new AI("Gogo"));
//        this.players.add(new Human("Diogo"));
//        this.players.add(new Human("Hayden"));
//        this.players.add(new Human("Gogo"));
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
                    Card card = this.cards.get(0);
                    if (!envelope.has(card)) {
                        player.addCard(card);
                    }
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

    public static void main(String[] args) {
        Cluedo cluedo = new Cluedo();
    }

    /**
     * @return weaponPieces
     */
    public List<WeaponPiece> getWeaponPieces() {
        return weaponPieces;
    }
}
