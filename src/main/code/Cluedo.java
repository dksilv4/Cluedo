package code;

import javafx.util.Pair;
import org.json.JSONObject;

import java.util.*;

public class Cluedo {
    private List<SuspectCard> suspectCards; // list of suspects cards
    private List<WeaponCard> weaponCards; // list of weapon cards
    private List<RoomCard> roomCards; // list of room cards
    private List<PlayerPiece> playerPieces; // list of all the playing pieces
    private List<WeaponPiece> weaponPieces; // list of all the weapon pieces
    private final List<Card> cards = new ArrayList<Card>(); // list of all cards // used to give out cards
    private List<Player> players = new ArrayList<Player>(); // list of all the players
    private final Board board = new Board(); // board variable for a Board instance
    private Scanner sc = new Scanner(System.in); // Used for inputs
    private Envelope envelope; // murder envelope variable
    private Player currentPlayersTurn; // variable that indicates the current player playing
    private List<Pair<Player, Integer>> playerOrder; // list of all the players in order of their initial roll
    private int turnIndicator; // This will iterate through playerOrder to indicate who's turn it is.
    private int currentPlayersSteps; // steps the player can take after rolling the dice
    private GameState state; // game state
    private static Random rand = new Random(); // random number generator
    private CardChoice accusationChoices; // accusation choice
    private CardChoice suggestionChoices; // suggestion choice
    private Tile locAtTurnStart; // location at the start of a round of the player
    private PlayerPiece winner = null; // winner
    private List<String> messageLogs; // message logs
    private boolean suggestionMade = false; // if suggestion has been made (used to enable or disable the player from making a suggestion)

    /**
     * Initializes Cluedo and loads all the necessary data to run the game
     */
    public Cluedo() {
        if (!isJUnitTest()) { // if not running from test
            this.setUpPlayers();
            this.setUpCards();
            this.setSpawns();

            playerOrder = new ArrayList<>();
            messageLogs = new ArrayList<>();
            turnIndicator = 0;
            // Choose a random player to roll the dice first.
            currentPlayersTurn = players.get(rand.nextInt(players.size()));
            state = GameState.AssigningPlayerPieces;
            accusationChoices = new CardChoice();
            suggestionChoices = new CardChoice();
            logMessage("This is the message log box where \n" +
                    " events of the game will be displayed.");
        }
    }

    public Envelope getEnvelope() {
        return this.envelope;
    }

    /**
     * Make a selection of a card for an accusation.
     *
     * @param cardName String of the name of the card
     */
    public void addAccusationSelection(String cardName) {
        SuspectCard suspect = findSuspectCard(cardName);
        RoomCard room = findRoomCard(cardName);
        WeaponCard weapon = findWeaponCard(cardName);

        if (suspect != null) {
            this.accusationChoices.setSuspect(suspect);
            logMessage(currentPlayersTurn.getPiece().getName() + " selected "
                    + cardName + " as part of their accusation.");
            return;
        }
        if (room != null) {
            this.accusationChoices.setRoom(room);
            logMessage(currentPlayersTurn.getPiece().getName() + " selected "
                    + cardName + " as part of their accusation.");
            return;
        }
        if (weapon != null) {
            this.accusationChoices.setWeapon(weapon);
            logMessage(currentPlayersTurn.getPiece().getName() + " selected "
                    + cardName + " as part of their accusation.");
            return;
        }

    }

    /**
     * Takes in a name of a card and is used to fill in the suggestion choice to be able to make a suggestion
     *
     * @param cardName name of card
     */
    public void addSuggestionSelection(String cardName) {
        SuspectCard suspect = findSuspectCard(cardName);
        RoomCard room = findRoomCard(currentPlayersTurn.getPiece().getLocation().getBelongsTo().getName());
        WeaponCard weapon = findWeaponCard(cardName);

        this.suggestionChoices.setRoom(room);
        if (findRoomCard(cardName) != null) {
            logMessage("The room for a suggestion can only be the room you are currently in.");
            return;
        }
        if (suspect != null) {
            this.suggestionChoices.setSuspect(suspect);
            logMessage(currentPlayersTurn.getPiece().getName() + " selected "
                    + cardName + " as part of their suggestion.");
            return;
        }
        if (weapon != null) {
            this.suggestionChoices.setWeapon(weapon);
            logMessage(currentPlayersTurn.getPiece().getName() + " selected "
                    + cardName + " as part of their suggestion.");
            return;
        }
    }

    /**
     * @return CardChoice, the accusation made by a player
     */
    public CardChoice getAccusationChoices() {
        return this.accusationChoices;
    }

    /**
     * @return CardChoice, the suggestion made by a player
     */
    public CardChoice getSuggestionChoices() {
        return suggestionChoices;
    }

    /**
     * @return GameState, the state of the game
     */
    public GameState state() {
        return this.state;
    }

    /**
     * Set the state of the game
     *
     * @param s game state
     */
    public void setState(GameState s) {
        this.state = s;
    }

    /**
     * @return PlayerPiece, the winner of the game
     */
    public PlayerPiece getWinner() {
        return winner;
    }

    /**
     * Add lines to messageLogs
     *
     * @param msg string variable to be added to the message logs
     */
    public void logMessage(String msg) {
        messageLogs.add(msg);
    }

    /**
     * @return A list of string, get messageLogs
     */
    public List<String> getMessageLogs() {
        return messageLogs;
    }

    /**
     * Move the current player's piece to a new tile.
     *
     * @param newTile new tile to be moved to
     */
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
                && state == GameState.InPlay && !newTile.isOccupied();

        if (canMove) {
            currentPlayersTurn.getPiece().getLocation().removeOccupier();
            currentPlayersTurn.getPiece().setLocation(newTile);
            currentPlayersSteps--;
        }

        if (currentPlayersSteps <= 0) {
            endTurn();
        }
    }

    /**
     * @return int, return how many steps are left
     */
    public int getStepsLeft() {
        return currentPlayersSteps;
    }

    /**
     * @return Tile, get the starting tile location of the turn
     */
    public Tile getLocAtTurnStart() {
        return locAtTurnStart;
    }

    /**
     * Set that a player has made a suggestion
     */
    public void playerHasUsedSuggestion() {
        suggestionMade = true;
    }

    /**
     * @return Boolean value representing if a suggestion has been made
     */
    public boolean isSuggestionMade() {
        return suggestionMade;
    }

    /**
     * End the turn
     * resets and changes variables to be ready for the new player for the next turn
     */
    public void endTurn() {
        suggestionMade = false;
        locAtTurnStart = currentPlayersTurn.getPiece().getLocation();
        accusationChoices = new CardChoice();
        suggestionChoices = new CardChoice();
        turnIndicator = (turnIndicator + 1) % playerOrder.size();
        currentPlayersTurn = playerOrder.get(turnIndicator).getKey();
        currentPlayersSteps = 0;
    }

    /**
     * Roll the dice to add steps to a player
     *
     * @param currentState GameState
     * @param playerOrder
     */
    public void rollDice(GameState currentState, List<Pair<Player, Integer>> playerOrder) {
        switch (currentState) {
            case AssigningPlayerPieces:
                int roll = rollDice();
                logMessage(currentPlayersTurn.getName() + " rolled " + roll);
                playerOrder.add(new Pair<>(currentPlayersTurn, roll));

                int nextPlayersIndex = (players.indexOf(currentPlayersTurn) + 1) % players.size();
                currentPlayersTurn = players.get(nextPlayersIndex);

                if (playerOrder.size() == players.size()) {
                    this.assignPlayerPieces(playerOrder);
                    this.fillDetectiveSlips();
                    this.allocateWeapons();
                    currentPlayersTurn = playerOrder.get(0).getKey();
                    currentPlayersSteps = rollDice();
                    state = GameState.InPlay;
                    logMessage("All player pieces assigned.");
                }
                break;
            case InPlay:
                currentPlayersSteps = rollDice();
                logMessage(currentPlayersTurn.getName() + " rolled a " + currentPlayersSteps);
                locAtTurnStart = currentPlayersTurn.getPiece().getLocation();
                break;
            default:

        }
    }

    /**
     * Return the order of the players for the game.
     *
     * @return List<Pair < Player, Integer>>
     */
    public List<Pair<Player, Integer>> getPlayOrder() {
        return this.playerOrder;
    }

    /**
     * Returns the RoomCard if the given string matches it.
     *
     * @param search
     * @return
     */
    private RoomCard findRoomCard(String search) {
        for (RoomCard card : this.getRoomCards()) {
            if (card.getName().toLowerCase().equals(search.toLowerCase())) {
                return card;
            }
        }
        return null;
    }

    /**
     * Returns the WeaponCard if the given string matches it.
     *
     * @param search
     * @return
     */
    public WeaponCard findWeaponCard(String search) {
        for (WeaponCard card : this.getWeaponCards()) {
            if (card.getName().toLowerCase().equals(search.toLowerCase())) {
                return card;
            }
        }
        return null;
    }

    /**
     * Returns the SuspectCard if the given string matches it.
     *
     * @param search
     * @return SuspectCard
     */
    public SuspectCard findSuspectCard(String search) {
        for (SuspectCard card : this.getSuspectCards()) {
            if (card.getName().toLowerCase().equals(search.toLowerCase())) {
                return card;
            }
        }
        return null;
    }

    /**
     * Returns the CardChoice that the PlayerPiece given has made
     *
     * @param playerPiece
     * @return CardChoice
     */
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
        WeaponCard weaponCard = this.findWeaponCard(sc.nextLine());
        if (weaponCard == null) {
            while (!validCard) {
                System.out.println("Invalid input! Please select one of the following weapons (Type in room name):");
                System.out.println(this.getRoomCards());
                weaponCard = this.findWeaponCard(sc.nextLine());
                if (weaponCard != null) {
                    validCard = true;
                }
            }
        }
        validCard = false;
        SuspectCard suspectCard = this.findSuspectCard(sc.nextLine());
        if (suspectCard == null) {
            while (!validCard) {
                System.out.println("Invalid input! Please select one of the following suspect (Type in room name):");
                System.out.println(this.getRoomCards());
                suspectCard = this.findSuspectCard(sc.nextLine());
                if (suspectCard != null) {
                    validCard = true;
                }
            }
        }
        return new CardChoice(playerPiece, roomCard, weaponCard, suspectCard);

    }

    /**
     * Main method of Cluedo
     *
     * @param args
     */
    public static void main(String[] args) {
        Cluedo cluedo = new Cluedo();
        cluedo.setUpPlayers();
        CardChoice cc = new CardChoice(cluedo.getPlayerPieces().get(0), cluedo.findRoomCard("Billiard"), cluedo.findWeaponCard("Candlestick"), cluedo.findSuspectCard("Prof Plum"));
        cluedo.verifySuggestion(cc);

    }

    /**
     * Reveals the card that matches the CardChoice
     *
     * @param cardChoices
     * @return Card
     */
    public Card verifySuggestion(CardChoice cardChoices) {
        RoomCard room = cardChoices.getRoom();
        WeaponCard weapon = cardChoices.getWeapon();
        SuspectCard suspect = cardChoices.getSuspect();
        Card revealedCard = null;
        PlayerPiece playerPiece = currentPlayersTurn.getPiece();
        System.out.println(playerPiece.getBelongsTo() + " made a suggestion that " + suspect + " was the murderer! They used " + weapon + " in " + room + ".");
        List<Card> foundCards = new ArrayList<Card>();
        // go through all the players and check if any of the suggestions apply to any of the players
        for (Player player : this.players) {
            if (player != playerPiece.getBelongsTo()) {
                for (Card card : player.getCards()) {
                    if (card instanceof RoomCard && card.equals(room) || card instanceof WeaponCard && card.equals(weapon) || card instanceof SuspectCard && card.equals(suspect)) {
                        foundCards.add(card);
                    }
                }
            }
        }
        // if any cards were found, reveal one card
        if (foundCards.size() > 0) {
            revealedCard = foundCards.get((int) (Math.random() * foundCards.size()));
            System.out.println("Found Card!");
            System.out.println("Card has been revealed from " + playerPiece.getBelongsTo() + " " + revealedCard);
            playerPiece.getSlip().markSlip(revealedCard, true);
        } else {
            System.out.println("No cards found, ended suggestion!");
        }
        state = GameState.InPlay;
        return revealedCard;
    }

    /**
     * Make a whole accusation with the CardChoice given. Makes the Player win if it is valid or kicks them out.
     *
     * @param cardChoice
     * @return Boolean, True if the accusation was valid.
     */
    public boolean makeAccusation(CardChoice cardChoice) {
        boolean successful = (cardChoice.getRoom().equals(this.envelope.getRoom())
                && cardChoice.getWeapon().equals(this.envelope.getWeapon())
                && cardChoice.getSuspect().equals(this.envelope.getSuspect()));
        state = (successful) ? GameState.GameOver : GameState.InPlay;
        logMessage(currentPlayersTurn.getPiece().getName() + " accused " +
                cardChoice.getSuspect().getName() + " of murder in the " + cardChoice.getRoom().getName() + " \n   with the "
                + cardChoice.getWeapon().getName() + " accusation success = " + successful + ".");
        if (!successful) {
            currentPlayersTurn.getPiece().setKicked(true);
            logMessage(currentPlayersTurn.getPiece().getName() + " has been removed from the game.");
        } else {
            // Player wins.
            winner = currentPlayersTurn.getPiece();
            logMessage(winner + " has won the game.");
            setState(GameState.GameOver);
        }
        endTurn();
        return successful;
    }

    /**
     * Uses a secret passage in the Room given
     *
     * @param room
     */
    public void useSecretPassage(Room room) {
        String roomName = room.getName();
        switch (roomName) {
            case "Kitchen":
                Room study = this.board.getRoom("Study");
                Tile studyPassage = study.getSecretPassage();
                this.currentPlayersTurn.getPiece().setLocation(studyPassage);
                break;
            case "Study":
                Room kitchen = this.board.getRoom("Kitchen");
                Tile kitchenPassage = kitchen.getSecretPassage();
                this.currentPlayersTurn.getPiece().setLocation(kitchenPassage);
                break;
            case "Conservatory":
                Room lounge = this.board.getRoom("Lounge");
                Tile loungePassage = lounge.getSecretPassage();
                this.currentPlayersTurn.getPiece().setLocation(loungePassage);
                break;
            case "Lounge":
                Room conservatory = this.board.getRoom("Conservatory");
                Tile conservatoryPassage = conservatory.getSecretPassage();
                this.currentPlayersTurn.getPiece().setLocation(conservatoryPassage);
                break;
        }

    }

    /**
     * Gets the input of which direction the Player wants to go
     *
     * @return String
     */
    private String getUserDirection() {
        System.out.println("Which direction do you want to go?\n>>>");
        String direction = sc.nextLine().toLowerCase();
        return direction;
    }

    /**
     * Moves the PlayerPiece to the given direction
     *
     * @param playerPiece PlayerPiece
     * @param direction   String
     */
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
            if (newLocation.isAvailable()) {
                playerLocation.removeOccupier();
                playerPiece.setLocation(newLocation);
                newLocation.setOccupier(playerPiece);
                System.out.println("Player moved to: " + playerLocation.getRow() + " " + playerLocation.getColumn());
                this.board.getGrid().print();
            }
        }
    }

    /**
     * Allocates WeaponPieces to the Rooms
     */
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

    /**
     * @return A list of PlayerPieces in the game
     */
    public List<PlayerPiece> getPlayerPieces() {
        return this.playerPieces;
    }

    /**
     * @return Board of the game
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Assigns PlayerPieces to the Players in order
     *
     * @param playerOrder
     */
    private void assignPlayerPieces(List<Pair<Player, Integer>> playerOrder) {
        Collections.sort(playerOrder, Comparator.comparing(p -> -p.getValue()));

        for (int i = 0; i < playerOrder.size(); i++) {
            playerOrder.get(i).getKey().setPiece(this.playerPieces.get(i));
        }
        locAtTurnStart = currentPlayersTurn.getPiece().getLocation();
    }

    /**
     * Assign the player pieces to the players.
     * Takes in playerHash which is a hashmap that contains all the players and the number they got from rolling the dice.
     * Player with the highest numbers gets a playerPiece assigned to them first.
     * Used for terminal version of the game that was used for testing before gui was available
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

    public Player getCurrentPlayersTurn() {
        return this.currentPlayersTurn;
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
                    this.cards.remove(0);
                    if (!envelope.has(card)) {
                        player.addCard(card);
                    }
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


    /**
     * @return weaponPieces
     */
    public List<WeaponPiece> getWeaponPieces() {
        return weaponPieces;
    }
}
