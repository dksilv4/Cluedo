package code;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
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

    private static Image PATH_TILE_IMG;
    private static Image ROOM_IMG;
    private static Image WALL_IMG;
    private static Image DOOR_IMG;
    private static Image DEFAULT_IMG;

    public ClueGUI() throws FileNotFoundException {
        initialiseImages();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        Board gameBoard = new Board();
        Grid boardTiles = gameBoard.getGrid();

        // Set-up scene and generate sprites from tiles in game board.
        Pane gameBoardCanvas = initialiseGUI(theStage);
        HashMap<Tile, Sprite> tileSprites = generateTileSprites(boardTiles,
                gameBoardCanvas);

        /* --- Main game loop. --- */
        new AnimationTimer() {
            public void handle(long currentTime) {

                // Render tiles.
                double tileHeight = gameBoardCanvas.getHeight() * 0.03575;
                double tileWidth = gameBoardCanvas.getWidth() * 0.04175;
                for (List<Tile> row : boardTiles.getGrid()) {
                    for (Tile t : row) {
                        // Tiles' sizes are relative to their container's size.
                        tileSprites.get(t).setDims(tileWidth, tileHeight);

                        // Render tiles.
                        double x = t.getColumn() * tileWidth;
                        double y = t.getRow() * tileHeight;
                        renderSprite(tileSprites.get(t), x, y);
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
        Scene theScene = new Scene(root); // Outer container (below stage).
        Pane gameBoardCanvas = new Pane(); // Canvas to render game board to.

        root.setCenter(gameBoardCanvas);
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
     * @param boardTiles        The Model from which the GUI is rendered
     * @param gameBoardCanvas   The node to which the GUI renders the game board
     * @return A hashmap which maps tiles to Sprites
     */
    private HashMap<Tile, Sprite> generateTileSprites(Grid boardTiles,
                                                      Pane gameBoardCanvas) {
        HashMap<Tile, Sprite> tileSprites = new HashMap<>();

        // Iterate over all tiles and generate a Sprite for each one according
        // to the tile's type.
        for (List<Tile> row : boardTiles.getGrid()) {
            for (Tile t : row) {
                // Tiles are square by default.
                double defaultTileSize = 30;
                Sprite s = new Sprite(DEFAULT_IMG, defaultTileSize,
                        defaultTileSize, t.getColumn() * defaultTileSize,
                        t.getRow() * defaultTileSize);

                // Set Sprite images based om their types.
                switch (t.getType()) {
                    case "door":
                        s.setImage(DOOR_IMG);
                        break;
                    case "space":
                        s.setImage(PATH_TILE_IMG);
                        break;
                    case "room":
                        s.setImage(ROOM_IMG);
                        break;
                    case "wall":
                        s.setImage(WALL_IMG);
                        break;
                    default:
                        s.setImage(DEFAULT_IMG);
                        break;
                }

                // Add the Sprite's ImageView to the rendering node and the
                // Sprite to the Sprite collection.
                gameBoardCanvas.getChildren().add(s.getImView());
                tileSprites.put(t, s);
            }
        }
        return tileSprites;
    }

    /**
     * Renders a given Sprite on the GUI.
     *
     * @param s Sprite to render
     * @param x x-coordinate to render the Sprite at
     * @param y y-coordinate to render the Sprite at
     */
    private void renderSprite(Sprite s, double x, double y) {
        s.render(x, y);
    }

    /**
     * Obtains the current directory and from there obtains the images directory.
     *
     * @throws FileNotFoundException If method fails to generate
     *                               FileInputStreams for images to load.
     */
    private static void initialiseImages() throws FileNotFoundException {
        // Get the current directory and find the images directory relative to
        // the current directory.
        String currentDir = Paths.get("").toAbsolutePath().toString();
        String imagesDir = Paths.get(currentDir,
                "\\src\\main\\resources\\images")
                .toAbsolutePath().toString();

        // Set image paths for all images.
        String doorImgPath = Paths.get(imagesDir, "door.png")
                .toAbsolutePath().toString();
        String floorImgPath = Paths.get(imagesDir, "floor.png")
                .toAbsolutePath().toString();
        String roomImgPath = Paths.get(imagesDir, "room.png")
                .toAbsolutePath().toString();
        String wallImgPath = Paths.get(imagesDir, "wall.png")
                .toAbsolutePath().toString();
        String defaultImgPath = Paths.get(imagesDir, "debug.png")
                .toAbsolutePath().toString();

        // Initialise images.
        FileInputStream doorIS = new FileInputStream(doorImgPath);
        FileInputStream floorIS = new FileInputStream(floorImgPath);
        FileInputStream roomIS = new FileInputStream(roomImgPath);
        FileInputStream wallIS = new FileInputStream(wallImgPath);
        FileInputStream defaultIS = new FileInputStream(defaultImgPath);

        PATH_TILE_IMG = new Image(floorIS);
        ROOM_IMG = new Image(roomIS);
        WALL_IMG = new Image(wallIS);
        DOOR_IMG = new Image(doorIS);
        DEFAULT_IMG = new Image(defaultIS);
    }
}
