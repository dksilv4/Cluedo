package code;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.*;
import java.util.List;

/**
 * This class acts as the View and Controller of the Clue! game's MVC structure,
 * currently it only renders the tiles and rooms from the Model.
 */
public class ClueGUI extends Application {

    private double DEFAULT_TILE_SIZE = 30;
    private static String CURR_DIR = Paths.get("").toAbsolutePath()
            .toString();
    private HashMap<String, String> playerPieceColours; // {PlayerPiece: Colour} mapping.
    private BorderPane root;
    private final String BCKGND_CLR = "#FEF9E7";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) throws FileNotFoundException {
        Cluedo gameModel = new Cluedo();
        Board gameBoard = gameModel.getBoard();
        Grid boardTiles = gameBoard.getGrid();

        // Set-up scene and generate sprites.
        Pane gameBoardCanvas = initGUI(theStage, gameModel);
        final HashMap<Tile, Sprite>[] tileSprites = new HashMap[]{generateTileSprites(boardTiles,
                gameBoardCanvas, gameModel)};
        final HashMap<PlayerPiece, Sprite>[] playerPieceSprites = new HashMap[]{new HashMap<>()};
        final HashMap<String, Sprite>[] weapPieceSprites = new HashMap[]{new HashMap<>()};
        gameBoardCanvas.setDisable(true);
        final HashMap<Room, Text>[] roomSprites = new HashMap[]{new HashMap<>()};

        final boolean[] playerGUIGenerated = {false};

        // Generate panels to display players cards and player piece's
        // detective slips.
        final HashMap<Player, VBox>[] playerCardPanels = new HashMap[]{new HashMap<>()};
        final HashMap<PlayerPiece, VBox>[] playerPieceDSlipPanels = new HashMap[]{new HashMap<>()};

        /* --- Main game loop. --- */
        new AnimationTimer() {
            public void handle(long currentTime) {

                Player currPlayer = gameModel.getCurrentPlayersTurn();
                if (gameModel.state() == GameState.InPlay) {

                    Pane board = (Pane) root.getCenter();
                    HBox actionContainer = (HBox) root.getBottom();
                    // Disable roll
                    if (gameModel.getStepsLeft() <= 0) {
                        for (Node n : board.getChildren()) {
                            n.setDisable(true);
                        }
                        actionContainer.getChildren().get(3).setDisable(false);
                    } else { // Disable board.
                        for (Node n : board.getChildren()) {
                            n.setDisable(false);
                        }
                        actionContainer.getChildren().get(3).setDisable(true);
                    }
                    // Enable accusations, skip and suggestions.
                    actionContainer.getChildren().get(0).setDisable(false);
                    if (gameModel.getCurrentPlayersTurn().getPiece().getLocation().getBelongsTo() == null ||
                            gameModel.getCurrentPlayersTurn().getPiece().getLocation().getBelongsTo().getName().equals("X")) {
                        actionContainer.getChildren().get(1).setDisable(true);
                    } else {
                        actionContainer.getChildren().get(1).setDisable(false);
                    }
                    actionContainer.getChildren().get(2).setDisable(false);
                    actionContainer.getChildren().get(4).setDisable(false);


                    if (!playerGUIGenerated[0]) {
                        try {
                            weapPieceSprites[0] = generateWeaponPieceSprites(
                                    gameBoard.getRooms(), gameBoardCanvas);
                            playerPieceSprites[0] = generatePlayerPieceSprites(
                                    gameModel.getPlayerPieces(), gameBoardCanvas);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        playerCardPanels[0] =
                                generatePlayersCardPanels(gameModel.getPlayers(),
                                        theStage);
                        playerPieceDSlipPanels[0] =
                                generateCharactersDetectiveSlipPanels(gameModel.getPlayerPieces(),
                                        theStage, gameModel);

                        roomSprites[0] = generateRoomSprites(gameModel, gameBoardCanvas);

                        playerGUIGenerated[0] = true;
                    }

                    gameBoardCanvas.setDisable(false);
                    // Render current player piece's detective slip.
                    root.setRight(playerPieceDSlipPanels[0].get(currPlayer.getPiece()));

                    // Render current player's cards.
                    root.setLeft(playerCardPanels[0].get(currPlayer));

                    double tileHeight = gameBoardCanvas.getHeight() / gameBoard.getGrid().getRows();
                    double tileWidth = gameBoardCanvas.getWidth() / gameBoard.getGrid().getColumns();

                    // Search the tiles of each room that has a weapon piece in it
                    // and choose the first available tile to render the weapon
                    // pieces at.
                    for (Room r : gameBoard.getRooms()) {
                        if (r.getWeaponPiece() != null) {
                            List<Tile> potentialWeapPlaces = new ArrayList<>();
                            for (Tile t : r.getTiles()) {
                                if (!t.isOccupied() && t != r.getSecretPassage() &&
                                        t.getType() != "wall") {
                                    potentialWeapPlaces.add(t);
                                }
                            }
                            Tile chosenTile = potentialWeapPlaces.get(0);
                            weapPieceSprites[0].get(r.getWeaponPiece().getName()).
                                    setDims(tileWidth, tileHeight);
                            double x = chosenTile.getColumn() * tileWidth;
                            double y = chosenTile.getRow() * tileHeight;
                            weapPieceSprites[0].get(r.getWeaponPiece().getName()).render(x, y);
                        }
                    }

                    // Render player pieces.
                    for (PlayerPiece pp : gameModel.getPlayerPieces()) {
                        playerPieceSprites[0].get(pp).setDims(tileWidth, tileHeight);
                        double x = pp.getLocation().getColumn() * tileWidth;
                        double y = pp.getLocation().getRow() * tileHeight;
                        playerPieceSprites[0].get(pp).render(x, y);
                    }

                    // Render room names.
                    for (Room r : gameModel.getBoard().getRooms()) {
                        if (r.getName().equals("X")) continue;
                        Text roomName = roomSprites[0].get(r);

                        // Get top right and bottom left tile to calculate size.
                        List<Tile> tiles = r.getTiles();

                        Tile topLeft = tiles.get(0);
                        Tile botRight = tiles.get(tiles.size() - 1);

                        double tlX = topLeft.getColumn() * tileWidth;
                        double tlY = (topLeft.getRow() + 1) * tileHeight;

                        double brX = botRight.getColumn() * tileWidth;
                        double brY = (botRight.getRow() + 1) * tileHeight;

                        roomName.setX((brX + tlX) / 2);
                        roomName.setY((brY + tlY) / 2);
                    }

                    // Check if all players have lost the game and the game is over.
                    if (playerGUIGenerated[0]
                            && gameModel.state() != GameState.AssigningPlayerPieces) {
                        List<Pair<Player, Integer>> playOrder = gameModel.getPlayOrder();
                        List<PlayerPiece> piecesStillPlaying = new ArrayList<>();
                        for (int i = 0; i < playOrder.size(); i++) {
                            if (playOrder.get(i).getKey().getPiece().isKicked()) {
                                continue;
                            } else {
                                piecesStillPlaying.add(playOrder.get(i).getKey().getPiece());
                            }
                        }
                        if (piecesStillPlaying.isEmpty()) {
                            // Game over all players have lost.
                            gameBoardCanvas.setDisable(true);
                            gameModel.setState(GameState.GameOver);
                            logMessage(gameModel, "Game over, all players have lost.");
                        }
                    }

                    // End players turn if they have been kicked.
                    PlayerPiece currPP = gameModel.getCurrentPlayersTurn().getPiece();
                    if (currPP.isKicked()) {
                        logMessage(gameModel, currPP.getName() +
                                " is not in the game anymore, skipping.");
                        gameModel.endTurn();
                    }


                    // If player has just entered a room, end their turn. Buggy, removed.
//                    if (currPP.getLocation().getType().equals("room")
//                            && !gameModel.getLocAtTurnStart().getType().equals("room")) {
//                        System.out.println("Entered " + currPP.getLocation().getType());
//                        gameModel.endTurn();
//                    }

                } else if (gameModel.state() == GameState.MakingAccusation) {
                    // Disable game board.
                    Pane board = (Pane) root.getCenter();
                    for (Node n : board.getChildren()) {
                        n.setDisable(false);
                    }
                    // Disable buttons while accusation is in progress.
                    HBox actionContainer = (HBox) root.getBottom();
                    for (Node n : actionContainer.getChildren()) {
                        n.setDisable(true);
                    }
                    actionContainer.getChildren().get(4).setDisable(false);

                    // Build up selections from player and pass to make accusation.
                    CardChoice choices = gameModel.getAccusationChoices();
                    if (choices.getRoom() != null && choices.getWeapon() != null &&
                            choices.getSuspect() != null) {
                        gameModel.makeAccusation(choices);
                    }
                } else if (gameModel.state() == GameState.MakingSuggestion) {
                    // Disable game board.
                    Pane board = (Pane) root.getCenter();
                    for (Node n : board.getChildren()) {
                        n.setDisable(false);
                    }

                    // Disable roll and suggestion.
                    HBox actionContainer = (HBox) root.getBottom();
                    actionContainer.getChildren().get(0).setDisable(false);
                    actionContainer.getChildren().get(1).setDisable(true);
                    actionContainer.getChildren().get(3).setDisable(true);
                    if (gameModel.isSuggestionMade()) {
                        actionContainer.getChildren().get(1).setDisable(true);
                    }

                    // Build up selections from player and pass to make suggestion.
                    CardChoice choices = gameModel.getSuggestionChoices();
                    if (choices.getRoom() != null && choices.getWeapon() != null &&
                            choices.getSuspect() != null) {
                        Card foundCard = gameModel.verifySuggestion(choices);
                        if (foundCard != null) {
                            logMessage(gameModel, currPlayer.getPiece().getName()
                                    + "'s suggestion was successful.");
                            Alert markYourCard = new Alert(Alert.AlertType.INFORMATION,
                                    "Your suggestion revealed " + foundCard.getName()
                                            + " make sure to mark your detective slip!");
                            markYourCard.show();
                            gameModel.playerHasUsedSuggestion();
                        } else {
                            logMessage(gameModel, currPlayer.getPiece().getName()
                                    + "'s suggestion was unsuccessful.");
                        }
                    }

                } else if (gameModel.state() == GameState.GameOver) {
                    // Disable game board.
                    Pane board = (Pane) root.getCenter();
                    for (Node n : board.getChildren()) {
                        n.setDisable(false);
                    }
                    // Disable buttons execept message logs.
                    HBox actionContainer = (HBox) root.getBottom();
                    for (Node n : actionContainer.getChildren()) {
                        n.setDisable(true);
                    }
                    actionContainer.getChildren().get(4).setDisable(false);

                    // Disable det slip.
                    VBox currDSlip = (VBox) root.getRight();
                    for (Node n : currDSlip.getChildren()) {
                        n.setDisable(true);
                    }

                    // Display that game has ended somehow.
                    this.stop();
                    String endGameMsg = (gameModel.getWinner() == null) ? "Game Over (No winner)"
                            : "Game Over (" + gameModel.getWinner().getName() + "  wins!)";

                    Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION, endGameMsg);
                    gameOverAlert.show();

                }
                // Display messages for players.
                HBox actionContainer = (HBox) root.getBottom();
                Text messages = new Text(getMsgLogs(gameModel));
                ((ScrollPane) actionContainer.getChildren().get(4)).setContent(messages);

                // Update turn.
                updateTurnIndicator(currPlayer, gameModel);

                // Set tile size to be relative to container size.
                double tileHeight = gameBoardCanvas.getHeight() / gameBoard.getGrid().getRows();
                double tileWidth = gameBoardCanvas.getWidth() / gameBoard.getGrid().getColumns();

                // Render tiles and weapon pieces.
                for (List<Tile> row : boardTiles.getGrid()) {
                    for (Tile t : row) {
                        tileSprites[0].get(t).setDims(tileWidth, tileHeight);
                        double x = t.getColumn() * tileWidth;
                        double y = t.getRow() * tileHeight;
                        tileSprites[0].get(t).render(x, y);
                    }
                }


            }
        }.start();

        theStage.show();
    }

    private String getMsgLogs(Cluedo model) {
        List<String> messages = model.getMessageLogs();
        String output = new String();

        for (String m : messages) {
            output += "> " + m + '\n';
        }

        return output;
    }

    private void logMessage(Cluedo model, String msg) {
        model.logMessage(msg);
    }

    /**
     * Sets-up the stage, and root containers which hold the contents of the GUI.
     *
     * @param theStage The stage which contains all nodes
     * @return The node that the game board renders to
     */
    private Pane initGUI(Stage theStage, Cluedo model) {
        // Main container for GUI (currently only holds board canvas but will
        // later hold turn relevant stuff, dice, detective cards etc.)
        root = new BorderPane();
        Scene theScene = new Scene(root);

        Pane gameBoardCanvas = new Pane(); // Canvas to render game board to.
        HBox turnIndicator = initTurnIndicator();
        HBox actionContainer = initActionsPane(model, theStage);

        gameBoardCanvas.setPrefHeight(700);
        gameBoardCanvas.prefWidthProperty().bind(theStage.widthProperty().multiply(0.70));

        root.setCenter(gameBoardCanvas);
        root.setTop(turnIndicator);
        root.setBottom(actionContainer);
        BorderPane.setAlignment(gameBoardCanvas, Pos.CENTER);

        theStage.setWidth(850);
        theStage.setHeight(700);
        theStage.setX(0);
        theStage.setY(0);
        theStage.sizeToScene();
        theStage.setTitle("Clue!");
        theStage.setScene(theScene);

        return gameBoardCanvas;
    }

    /**
     * Creates and returns the container that displays who's turn it current is.
     *
     * @return Container that displays who's turn it current is.
     */
    private HBox initTurnIndicator() {
        HBox turnIndicator = new HBox();
        playerPieceColours = new HashMap<>();

        playerPieceColours.put("Rev Green", "#0ed145");
        playerPieceColours.put("Prof Plum", "#b83dba");
        playerPieceColours.put("Mrs White", "#ffffff");
        playerPieceColours.put("Mrs Peacock", "#00a8f3");
        playerPieceColours.put("Miss Scarlett", "#ec1c24");
        playerPieceColours.put("Col Mustard", "#fff200");

        Text displayText = new Text("Currently Rolling for Player Piece Assignment: ");
        displayText.setFont(new Font(20));

        Text currentPlayersTurn = new Text("PLAYER_NAME");
        currentPlayersTurn.setFont(new Font(22));
        Text stepCount = new Text();

        turnIndicator.getChildren()
                .addAll(displayText, currentPlayersTurn, stepCount);
        turnIndicator.setAlignment(Pos.CENTER);
        turnIndicator.setPadding(new Insets(10, 10, 10, 10));
        turnIndicator.setStyle("-fx-background-color: " + BCKGND_CLR);

        return turnIndicator;
    }

    /**
     * Updates the turn indicator to display the given player as being the
     * current turn.
     *
     * @param currentPlayer
     */
    private void updateTurnIndicator(Player currentPlayer, Cluedo model) {
        HBox turnIndicator = (HBox) root.getTop();

        if (model.state() == GameState.AssigningPlayerPieces) {
            turnIndicator.getChildren().clear();
            Text newPrefix = new Text("Rolling for Assignment: ");
            newPrefix.setFont(new Font(20));
            Text newName = new Text(currentPlayer.getName());
            newName.setFont(new Font(22));
            turnIndicator.getChildren().addAll(newPrefix, newName);
            return;
        }
        String currentPlayerCol = playerPieceColours.get(currentPlayer.getPiece().getName());
        turnIndicator.getChildren().clear();

        Text newStepCount = new Text(" (Steps Left: " + model.getStepsLeft() + ")");
        newStepCount.setFont(new Font(20));

        Text newName = new Text(currentPlayer.getName());
        newName.setFont(new Font(20));
        newName.setStyle("-fx-stroke: " + currentPlayerCol);

        Text newPrefix = new Text("Current Player's Turn: ");
        newPrefix.setFont(new Font(20));

        turnIndicator.getChildren().addAll(newPrefix, newName, newStepCount);

    }

    /**
     * Generates containers which display detective slip contents for all
     * player pieces.
     *
     * @param playerPieces Player pieces to generate containers for.
     * @return Collection of detective slip containers.
     */
    private HashMap<PlayerPiece, VBox> generateCharactersDetectiveSlipPanels(
            List<PlayerPiece> playerPieces, Stage theStage, Cluedo model) {
        HashMap<PlayerPiece, VBox> dSlipPanels = new HashMap<>();
        for (PlayerPiece pp : playerPieces) {
            dSlipPanels.put(pp, generateDetectiveSlipPanel(pp, theStage, model));
        }
        return dSlipPanels;
    }

    /**
     * Creates and returns a container which displays the contents of the
     * given player piece's detective slip.
     *
     * @param pp Player piece to generate a detective slip for.
     * @return Container representing detective slip.
     */
    private VBox generateDetectiveSlipPanel(PlayerPiece pp, Stage theStage, Cluedo model) {
        VBox dSlipPanel = new VBox();
        dSlipPanel.setPadding(new Insets(10, 10, 10, 10));
        dSlipPanel.setStyle("-fx-background-color: " + BCKGND_CLR);
        Button revealSlip = new Button("Reveal Slip");

        revealSlip.setOnMousePressed(event -> {
            for (int i = 1; i < dSlipPanel.getChildren().size() - 1; i++) {
                Node child = dSlipPanel.getChildren().get(i);
                child.setVisible(!child.isVisible());
            }
        });

        // Display who's detective slip this is.
        Text dSlipOwner = new Text(pp.getName() + "'s Detective Slip");
        dSlipOwner.setFont(new Font(20));
        dSlipPanel.prefWidthProperty().bind(theStage.widthProperty().multiply(0.15));
        dSlipPanel.getChildren().add(dSlipOwner);


        // Add all cards and their marking to container.
        DetectiveSlip dSlip = pp.getSlip();
        Iterator it = dSlip.getCards().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry dSlipEntry = (Map.Entry) it.next();

            Card card = (Card) dSlipEntry.getKey();
            boolean marked = (Boolean) dSlipEntry.getValue();

            Pane spacer = new Pane(); // Enables full justification of nodes.

            HBox cardContainer = new HBox();
            Text cardName = new Text(card.getName());
            cardContainer.setSpacing(10);
            String initMarking = marked ? "Marked" : "Not Marked";
            Text markedDisplay = new Text(initMarking);
            HBox.setHgrow(spacer, Priority.ALWAYS);

            cardName.setOnMouseClicked(event -> {
                if (model.state() == GameState.MakingAccusation) {
                    model.addAccusationSelection(card.getName());
                } else if (model.state() == GameState.MakingSuggestion) {
                    model.addSuggestionSelection(card.getName());
                }
            });

            cardContainer.getChildren().addAll(cardName, spacer,
                    markedDisplay);

            // Don't add a checkbox for cards held by player.
            if (pp.getBelongsTo() != null
                    && !pp.getBelongsTo().getCards().contains(card)) {
                CheckBox cardMarker = new CheckBox();
                cardMarker.setOnMouseClicked(event -> {
                    dSlip.markSlip(card, true);
                    markedDisplay.setText("Marked");
                    System.out.println(pp.getName() + " has marked: " + card.getName());
                    cardMarker.setDisable(true);
                });

                cardContainer.getChildren().add(cardMarker);
            } else {
                CheckBox cardMarker = new CheckBox();
                cardMarker.setDisable(true);
                cardMarker.setSelected(true);
                cardContainer.getChildren().add(cardMarker);
            }

            cardContainer.setVisible(false);
            dSlipPanel.getChildren().add(cardContainer);
            it.remove();
        }

        dSlipPanel.getChildren().add(revealSlip);
        return dSlipPanel;
    }

    /**
     * Iterates over tiles in the game board and generates a corresponding
     * Sprite to represent each one in the GUI.
     *
     * @param boardTiles      The Model from which the GUI is rendered
     * @param gameBoardCanvas The node to which the GUI renders the game board
     * @return A hashmap which maps tiles to Sprites
     */
    private HashMap<Tile, Sprite> generateTileSprites(Grid boardTiles,
                                                      Pane gameBoardCanvas, Cluedo model)
            throws FileNotFoundException {
        HashMap<String, Image> tileImages = initTileImgs();
        HashMap<Tile, Sprite> tileSprites = new HashMap<>();

        // Iterate over all tiles and generate a Sprite for each one.
        for (List<Tile> row : boardTiles.getGrid()) {
            for (Tile t : row) {
                // Tiles are square by default.
                Sprite s = new Sprite(tileImages.get(t.getType()),
                        DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE,
                        t.getColumn() * DEFAULT_TILE_SIZE,
                        t.getRow() * DEFAULT_TILE_SIZE);

                gameBoardCanvas.getChildren().add(s.getImView());
                if (!t.getType().equals("wall") && !t.getType().equals("passage")) {
                    s.assignClickAction(Action.Move, model, t);
                }
                if (t.getType().equals("passage")) {
                    s.assignClickAction(Action.UsePassage, model, t);
                }
                tileSprites.put(t, s);
            }
        }

        return tileSprites;
    }

    private HashMap<Room, Text> generateRoomSprites(Cluedo model, Pane gameBoardCanvas) {
        HashMap<Room, Text> roomSprites = new HashMap<>();

        for (Room r : model.getBoard().getRooms()) {
            if (r.getName().equals("X")) continue;

            Text roomName = new Text(r.getName());

            // Get top right and bottom left tile to calculate size.
            List<Tile> tiles = r.getTiles();

            Tile topLeft = tiles.get(0);
            Tile botRight = tiles.get(tiles.size() - 1);

            double tlX = topLeft.getColumn() * DEFAULT_TILE_SIZE;
            double tlY = (topLeft.getRow() + 1) * DEFAULT_TILE_SIZE;

            double brX = botRight.getColumn() * DEFAULT_TILE_SIZE;
            double brY = (botRight.getRow() + 1) * DEFAULT_TILE_SIZE;

            roomName.setX((brX + tlX) / 2);
            roomName.setY((brY + tlY) / 2);

            roomSprites.put(r, roomName);
            gameBoardCanvas.getChildren().add(roomName);
        }

        return roomSprites;
    }

    private HBox initActionsPane(Cluedo model, Stage theStage) {
        HBox actionContainer = new HBox();
        Button rollDice = new Button("Roll Dice");
        Button makeAccusation = new Button("Make Accusation");
        Button makeSuggestion = new Button("Make Suggestion");
        Button skipTurn = new Button("Skip Turn");
        ScrollPane messageLogs = new ScrollPane();

        messageLogs.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        actionContainer.prefWidthProperty().bind(theStage.widthProperty().multiply(0.25));
        actionContainer.prefHeightProperty().bind(theStage.widthProperty().multiply(0.1));
        makeAccusation.setDisable(true);
        makeSuggestion.setDisable(true);
        skipTurn.setDisable(true);

        rollDice.setOnMouseClicked(event -> {
            model.rollDice(model.state(), model.getPlayOrder());
        });

        makeAccusation.setOnMouseClicked(event -> {
            logMessage(model, model.getCurrentPlayersTurn().getPiece().getName()
                    + " is making an accusation.");
            model.setState(GameState.MakingAccusation);
        });

        skipTurn.setOnMouseClicked(event -> {
            logMessage(model, model.getCurrentPlayersTurn().getPiece().getName() +
                    " has skipped their turn.");
            model.setState(GameState.InPlay);
            model.endTurn();
        });

        makeSuggestion.setOnMouseClicked(event -> {
            logMessage(model, model.getCurrentPlayersTurn().getPiece().getName()
                    + " is making a suggestion.");
            model.setState(GameState.MakingSuggestion);
        });

        actionContainer.setPadding(new Insets(10, 10, 10, 10));
        actionContainer.setAlignment(Pos.CENTER);
        actionContainer.setStyle("-fx-background-color: " + BCKGND_CLR);

        actionContainer.getChildren().addAll(makeAccusation, makeSuggestion, skipTurn,
                rollDice, messageLogs);

        return actionContainer;
    }

    private HashMap<Player, VBox> generatePlayersCardPanels(List<Player> players,
                                                            Stage theStage) {
        HashMap<Player, VBox> cardPanels = new HashMap<>();
        for (Player p : players) {
            cardPanels.put(p, generateCardPanel(p, theStage));
        }
        return cardPanels;
    }

    private VBox generateCardPanel(Player player, Stage theStage) {
        VBox cardPanel = new VBox();
        cardPanel.setPadding(new Insets(10, 10, 10, 10));
        cardPanel.setStyle("-fx-background-color: " + BCKGND_CLR);
        cardPanel.prefWidthProperty().bind(theStage.widthProperty().multiply(0.15));
        Button revealCards = new Button("Reveal Cards");

        revealCards.setOnMousePressed(event -> {
            for (int i = 1; i < cardPanel.getChildren().size() - 1; i++) {
                Node child = cardPanel.getChildren().get(i);
                child.setVisible(!child.isVisible());
            }
        });

        // Display who's cards these are.
        Text cardsOwner = new Text(player.getName() + "'s Cards");
        cardsOwner.setFont(new Font(20));
        cardPanel.getChildren().add(cardsOwner);

        // Add all cards to container.
        for (Card c : player.getCards()) {
            Text cardName = new Text(c.getName());
            cardName.setVisible(false);
            cardPanel.getChildren().add(cardName);
        }

        cardPanel.getChildren().add(revealCards);
        return cardPanel;
    }

    /**
     * Obtains the current directory and from there obtains the images directory.
     *
     * @throws FileNotFoundException If method fails to generate
     *                               FileInputStreams for images to load.
     */
    private HashMap<String, Image> initTileImgs() throws FileNotFoundException {
        List<String> tileTypeNames =
                new ArrayList<>(Arrays.asList("door", "space", "room",
                        "wall", "passage"));
        HashMap<String, Image> tileImages = new HashMap<>();

        // Find the images directory relative to the current directory.
        String imagesDir = Paths.get(CURR_DIR,
                "\\src\\main\\resources\\images")
                .toAbsolutePath().toString();

        // Retrieve all images and store them in hashmap, keyed by their type.
        for (String tType : tileTypeNames) {
            String tileImgPath = Paths.get(imagesDir, tType + ".png")
                    .toAbsolutePath().toString();
            FileInputStream imgIS = new FileInputStream(tileImgPath);
            Image tileImg = new Image(imgIS);
            tileImages.put(tType, tileImg);
        }

        return tileImages;
    }

    private HashMap<String, Image> initPlayerPieceImgs(
            List<PlayerPiece> playerPieceList)
            throws FileNotFoundException {
        HashMap<String, Image> playerPieceImages = new HashMap<>();

        // Find the images directory relative to the current directory.
        String imagesDir = Paths.get(CURR_DIR,
                "\\src\\main\\resources\\images")
                .toAbsolutePath().toString();

        // Retrieve images for each Player Piece taking part in the game.
        for (PlayerPiece p : playerPieceList) {
            String playerPieceName = p.getName().replaceAll("\\s+", "");
            String imFileName = "playerPiece_" + playerPieceName + ".png";

            // Get image for current Player Piece and store in hashmap.
            String playerPieceImgPath = Paths.get(imagesDir, imFileName)
                    .toAbsolutePath().toString();
            FileInputStream playerPieceIS = new FileInputStream(playerPieceImgPath);
            Image playerPieceImg = new Image(playerPieceIS);

            playerPieceImages.put(p.getName(), playerPieceImg);
        }

        return playerPieceImages;
    }

    private HashMap<PlayerPiece, Sprite> generatePlayerPieceSprites(
            List<PlayerPiece> playerPieces, Pane gameBoardCanvas)
            throws FileNotFoundException {
        HashMap<String, Image> playerPieceImages = initPlayerPieceImgs(playerPieces);
        HashMap<PlayerPiece, Sprite> playerPieceSprites = new HashMap<>();

        // Iterate over player pieces and generate sprite for each one.
        for (PlayerPiece pp : playerPieces) {
            Sprite s = new Sprite(playerPieceImages.get(pp.getName()),
                    DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE,
                    pp.getLocation().getColumn() * DEFAULT_TILE_SIZE,
                    pp.getLocation().getRow() * DEFAULT_TILE_SIZE);

            gameBoardCanvas.getChildren().add(s.getImView());
            playerPieceSprites.put(pp, s);
        }

        return playerPieceSprites;
    }

    private HashMap<String, Sprite> generateWeaponPieceSprites(
            List<Room> rooms, Pane gameBoardCanvas)
            throws FileNotFoundException {
        HashMap<String, Image> weapPieceImages = new HashMap<>();
        HashMap<String, Sprite> weapPieceSprites = new HashMap<>();

        // Find the images directory relative to the current directory.
        String imagesDir = Paths.get(CURR_DIR,
                "\\src\\main\\resources\\images")
                .toAbsolutePath().toString();

        // Retrieve images for each Weapon Piece.
        for (Room r : rooms) {
            if (r.getWeaponPiece() != null) {
                String weapName = r.getWeaponPiece().getName()
                        .replaceAll("\\s+", "");
                String imFileName = "weapon_" + weapName + ".png";

                // Get image for current Player Piece and store in hashmap.
                String playerPieceImgPath = Paths.get(imagesDir, imFileName)
                        .toAbsolutePath().toString();
                FileInputStream weapPieceIS = new FileInputStream(playerPieceImgPath);
                Image weapPieceImg = new Image(weapPieceIS);

                weapPieceImages.put(r.getWeaponPiece().getName(), weapPieceImg);

                Sprite s = new Sprite(weapPieceImages.get(r.getWeaponPiece().getName()),
                        DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE, 0, 0);

                gameBoardCanvas.getChildren().add(s.getImView());
                weapPieceSprites.put(r.getWeaponPiece().getName(), s);
            }
        }

        return weapPieceSprites;
    }

}
