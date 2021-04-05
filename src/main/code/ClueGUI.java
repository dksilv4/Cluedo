package code;

import com.sun.prism.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private BorderPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) throws FileNotFoundException {
        Cluedo gameModel = new Cluedo();
        Board gameBoard = gameModel.getBoard();
        Grid boardTiles = gameBoard.getGrid();

        // Set-up scene and generate sprites.
        Pane gameBoardCanvas = initialiseGUI(theStage);
        HashMap<Tile, Sprite> tileSprites = generateTileSprites(boardTiles,
                gameBoardCanvas);
        HashMap<PlayerPiece, Sprite> playerPieceSprites =
                generatePlayerPieceSprites(gameModel.getPlayerPieces(),
                        gameBoardCanvas);

        // Generate panels to display players cards and player piece's
        // detective slips.
        HashMap<Player, VBox> playerCardPanels =
                generatePlayersCardPanels(gameModel.getPlayers());
        HashMap<PlayerPiece, VBox> playerPieceDSlipPanels =
                generateCharactersDetectiveSlipPanels(gameModel.getPlayerPieces());

        Player p = gameModel.getPlayers().get(0);
        root.setLeft(playerCardPanels.get(p));
        root.setRight(playerPieceDSlipPanels.get(p.getPiece()));

        /* --- Main game loop. --- */
        new AnimationTimer() {
            public void handle(long currentTime) {
                // Set tile size to be relative to container size, magic number
                // scaling, not nice but works.
                double tileHeight = gameBoardCanvas.getHeight() * 0.03575;
                double tileWidth = gameBoardCanvas.getWidth() * 0.04175;

                // Render tiles.
                for (List<Tile> row : boardTiles.getGrid()) {
                    for (Tile t : row) {
                        tileSprites.get(t).setDims(tileWidth, tileHeight);
                        double x = t.getColumn() * tileWidth;
                        double y = t.getRow() * tileHeight;
                        tileSprites.get(t).render(x, y);
                    }
                }

                // Render player pieces.
                for (PlayerPiece pp : gameModel.getPlayerPieces()) {
                    playerPieceSprites.get(pp).setDims(tileWidth, tileHeight);
                    double x = pp.getLocation().getColumn() * tileWidth;
                    double y = pp.getLocation().getRow() * tileHeight;
                    playerPieceSprites.get(pp).render(x, y);
                }

                // Render current player's detective slip.

            }
        }.start();

        theStage.show();
    }

    /**
     * Sets-up the stage, and root containers which hold the contents of the GUI.
     *
     * @param theStage The stage which contains all nodes
     * @return The node that the game board renders to
     */
    private Pane initialiseGUI(Stage theStage) {
        // Main container for GUI (currently only holds board canvas but will
        // later hold turn relevant stuff, dice, detective cards etc.)
        root = new BorderPane();
        Scene theScene = new Scene(root);

        Pane gameBoardCanvas = new Pane(); // Canvas to render game board to.
        HBox turnIndicator = initTurnIndicator();

        root.setCenter(gameBoardCanvas);
        root.setTop(turnIndicator);
        BorderPane.setAlignment(gameBoardCanvas, Pos.CENTER);

        theStage.setHeight(512);
        theStage.setWidth(512);
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

        Text displayText = new Text("Current Player's Turn: ");
        displayText.setFont(new Font(20));

        Text currentPlayersTurn = new Text("PLAYER_NAME");
        currentPlayersTurn.setFont(new Font(20));
        currentPlayersTurn.setStyle("-fx-fill: blue");

        turnIndicator.getChildren()
                .addAll(displayText, currentPlayersTurn);
        turnIndicator.setAlignment(Pos.CENTER);
        turnIndicator.setPadding(new Insets(10, 10, 10, 10));

        return turnIndicator;
    }

    /**
     * Generates containers which display detective slip contents for all
     * player pieces.
     * @param playerPieces  Player pieces to generate containers for.
     * @return              Collection of detective slip containers.
     */
    private HashMap<PlayerPiece, VBox> generateCharactersDetectiveSlipPanels(
            List<PlayerPiece> playerPieces) {
        HashMap<PlayerPiece, VBox> dSlipPanels = new HashMap<>();
        for (PlayerPiece pp : playerPieces) {
            dSlipPanels.put(pp, generateDetectiveSlipPanel(pp));
        }
        return dSlipPanels;
    }

    /**
     * Creates and returns a container which displays the contents of the
     * given player piece's detective slip.
     * @param pp    Player piece to generate container for.
     * @return      Container representing detective slip.
     */
    private VBox generateDetectiveSlipPanel(PlayerPiece pp) {
        VBox dSlipPanel = new VBox();
        dSlipPanel.setPadding(new Insets(10, 10, 10, 10));

        // Display who's detective slip this is.
        Text dSlipOwner = new Text(pp.getName() + "'s Detective Slip");
        dSlipOwner.setFont(new Font(20));
        dSlipPanel.getChildren().add(dSlipOwner);

        // Add all cards and their marking to container.
        DetectiveSlip dSlip = pp.getSlip();
        Iterator it = dSlip.getCards().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry card = (Map.Entry) it.next();

            String cardName = ((Card) card.getKey()).getName();
            String marked = card.getValue().toString();

            Pane spacer = new Pane(); // Enables full justification of nodes.

            HBox cardContainer = new HBox();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            cardContainer.getChildren().addAll(new Text(cardName), spacer,
                    new Text(marked));

            dSlipPanel.getChildren().add(cardContainer);

            it.remove();
        }

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
                                                      Pane gameBoardCanvas)
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
                tileSprites.put(t, s);
            }
        }
        return tileSprites;
    }

    private HashMap<Player, VBox> generatePlayersCardPanels(List<Player> players) {
        HashMap<Player, VBox> cardPanels = new HashMap<>();
        for (Player p : players) {
            cardPanels.put(p, generateCardPanel(p));
        }
        return cardPanels;
    }

    private VBox generateCardPanel(Player player) {
        VBox cardPanel = new VBox();
        cardPanel.setPadding(new Insets(10, 10, 10, 10));

        // Display who's cards these are.
        Text cardsOwner = new Text(player.getName() + "'s Cards");
        cardsOwner.setFont(new Font(20));
        cardPanel.getChildren().add(cardsOwner);

        // Add all cards to container.
        for (Card c : player.getCards()) {
            Text cardName = new Text(c.getName());
            cardPanel.getChildren().add(cardName);
        }

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
                new ArrayList<>(Arrays.asList("door", "space", "room", "wall"));
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
}
