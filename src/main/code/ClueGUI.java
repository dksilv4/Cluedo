package code;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.*;

/**
 * This class acts as the View and Controller of the Clue! game's MVC structure,
 * currently it only renders the tiles and rooms from the Model.
 */
public class ClueGUI extends Application {

    private static Image FLOOR_IMG;
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
        // @ToDo: Swap this to use board and grid once its done.
        TempGrid boardWrapper = new TempGrid();
        Grid gameBoard = boardWrapper.grid;

        // Set-up scene and generate sprites from tiles in game board.
        Pane gameBoardCanvas = initialiseGUI(theStage);
        HashMap<Tile, Sprite> tileSprites = generateTileSprites(gameBoard, gameBoardCanvas);

        /* --- Main game loop. --- */
        new AnimationTimer() {
            public void handle(long currentTime) {

                // Render tiles.
                double tileHeight = gameBoardCanvas.getHeight() * 0.04;
                double tileWidth = gameBoardCanvas.getWidth() * 0.04;
                for (List<Tile> row : gameBoard.grid) {
                    for (Tile t : row) {
                        // Tiles' sizes are relative to their container's size.
                        tileSprites.get(t).setDims(tileWidth, tileHeight);

                        // Render tiles.
                        double x = t.column * tileWidth;
                        double y = t.row * tileHeight;
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

        theStage.sizeToScene();
        theStage.setTitle("Clue!");
        theStage.setScene(theScene);

        return gameBoardCanvas;
    }

    /**
     * Iterates over tiles in the game board and generates a corresponding
     * Sprite to represent each one in the GUI.
     *
     * @param gameBoard  The Model from which the GUI is rendered
     * @param mainCanvas The node to which the GUI renders the game board
     * @return A hashmap which maps tiles to Sprites
     */
    private HashMap<Tile, Sprite> generateTileSprites(Grid gameBoard, Pane mainCanvas) {
        HashMap<Tile, Sprite> tileSprites = new HashMap<>();

        // Iterate over all tiles and generate a Sprite for each one according
        // to the tile's type.
        for (List<Tile> row : gameBoard.grid) {
            for (Tile t : row) {
                // Tiles are square by default.
                double DEFAULT_TILE_SIZE = 30;
                Sprite s = new Sprite(DEFAULT_IMG, DEFAULT_TILE_SIZE,
                        DEFAULT_TILE_SIZE, t.row * DEFAULT_TILE_SIZE,
                        t.column * DEFAULT_TILE_SIZE);

                // Set Sprite images based om their types.
                switch (t.type) {
                    case "door":
                        s.setImage(DOOR_IMG);
                        break;
                    case "room":
                        s.setImage(ROOM_IMG);
                        break;
                    case "wall":
                        s.setImage(WALL_IMG);
                        break;
                    default:
                        s.setImage(FLOOR_IMG);
                        break;
                }
                // Add the Sprite's ImageView to the rendering node and the
                // Sprite to the Sprite collection.
                mainCanvas.getChildren().add(s.getImView());
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
     * @throws FileNotFoundException    If method fails to generate
     *                                  FileInputStreams for images to load.
     *
     */
    private static void initialiseImages() throws FileNotFoundException {
        String currentDir = Paths.get("").toAbsolutePath().toString();
        String imagesDir = Paths.get(currentDir,
                "\\src\\main\\resources\\images")
                .toAbsolutePath().toString();

        String DOOR_IM_PATH = Paths.get(imagesDir,  "door.png")
                .toAbsolutePath().toString();
        String FLOOR_IM_PATH = Paths.get(imagesDir,  "floor.png")
                .toAbsolutePath().toString();
        String ROOM_IM_PATH = Paths.get(imagesDir,  "room.png")
                .toAbsolutePath().toString();
        String WALL_IM_PATH = Paths.get(imagesDir,  "wall.png")
                .toAbsolutePath().toString();
        String DEFAULT_IM_PATH = Paths.get(imagesDir,  "debug.png")
                .toAbsolutePath().toString();

        FileInputStream doorIS = new FileInputStream(DOOR_IM_PATH);
        FileInputStream floorIS = new FileInputStream(FLOOR_IM_PATH);
        FileInputStream roomIS = new FileInputStream(ROOM_IM_PATH);
        FileInputStream wallIS = new FileInputStream(WALL_IM_PATH);
        FileInputStream defaultIS = new FileInputStream(DEFAULT_IM_PATH);

        FLOOR_IMG = new Image(floorIS);
        ROOM_IMG = new Image(roomIS);
        WALL_IMG = new Image(wallIS);
        DOOR_IMG = new Image(doorIS);
        DEFAULT_IMG = new Image(defaultIS);
    }
}
