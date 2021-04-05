package code;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

                        // Render Player Pieces.
                        if (t.isOccupied()) {
                            playerPieceSprites.get(t.getOccupier())
                                    .setDims(tileWidth, tileHeight);
                            tileSprites.get(t).render(x, y);
                        }
                    }
                }
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
        BorderPane root = new BorderPane();
        Scene theScene = new Scene(root);

        Pane gameBoardCanvas = new Pane(); // Canvas to render game board to.
        HBox turnIndicatorContainer = new HBox();

        // Test
        Button test1 = new Button("test left");
        Button test2 = new Button("test right");
        turnIndicatorContainer.getChildren().addAll(test1, test2);

        root.setCenter(gameBoardCanvas);
        root.setTop(turnIndicatorContainer);
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
