package Cluedo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class ClueGUI extends Application {

    private static final String IMAGES_FILE_PATH = "Cluedo\\resources\\images\\";
    private static Image FLOOR_IMG = new Image(IMAGES_FILE_PATH + "floor.png");
    private static Image ROOM_IMG = new Image(IMAGES_FILE_PATH + "room.png");
    private static Image WALL_IMG= new Image(IMAGES_FILE_PATH + "wall.png");
    private static Image DOOR_IMG  = new Image(IMAGES_FILE_PATH + "door.png");

    private final double DEFAULT_TILE_SIZE = 25; // Tiles are square.

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Clue!");
        TempGrid boardWrapper = new TempGrid();
        Grid gameBoard = boardWrapper.grid;

        Pane mainCanvas = initialiseScene(theStage, gameBoard);
        HashMap<Tile, Sprite> sprites = generateSprites(gameBoard, mainCanvas);

        // Main game loop.
        new AnimationTimer() {
            public void handle(long currentTime) {
                double tileHeight = mainCanvas.getHeight() * 0.04;
                double tileWidth = mainCanvas.getWidth() * 0.04;

                for (List<Tile> row : gameBoard.grid) {
                    for (Tile t : row) {
                        double x = t.row * tileWidth;
                        double y = t.column * tileHeight;
                        sprites.get(t).setDims(tileWidth, tileHeight);
                        sprites.get(t).render(x, y);
                    }
                }
            }
        }.start();

        theStage.show();
    }

    private Pane initialiseScene(Stage primaryStage, Grid gameBoard) {
        BorderPane root = new BorderPane();
        Scene theScene = new Scene(root);
        primaryStage.sizeToScene();
        primaryStage.setScene(theScene);

        Pane canvas = new Pane();

        root.setCenter(canvas);
        BorderPane.setAlignment(canvas, Pos.CENTER);

        return canvas;
    }

    // Map is {(Row, Col): Sprite}
    private HashMap<Tile, Sprite> generateSprites(Grid gameBoard, Pane mainCanvas) {
        HashMap<Tile, Sprite> sprites = new HashMap<>();

        for (List<Tile> row : gameBoard.grid) {
            for (Tile t : row) {
                Sprite s = new Sprite(FLOOR_IMG, DEFAULT_TILE_SIZE,
                        DEFAULT_TILE_SIZE, t.row * DEFAULT_TILE_SIZE,
                        t.column * DEFAULT_TILE_SIZE);
                switch (t.type){
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
                        break;
                }
                mainCanvas.getChildren().add(s.getImView());
                sprites.put(t, s);
            }
        }
        return sprites;
    }

}
