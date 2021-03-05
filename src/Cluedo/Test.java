package Cluedo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TempGrid board = new TempGrid();
        int canvasHeight = board.grid.rows * 25;
        int canvasWidth = board.grid.columns * 25;
        primaryStage.setTitle("Initial GUI");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);

        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Floor sprite.
        Image floorImg = new Image("Cluedo\\resources\\images\\floor.png");
        Sprite floorTile = new Sprite();
        floorTile.setImage(floorImg);

        // Room sprite.
        Image roomImg = new Image("Cluedo\\resources\\images\\room.png");
        Sprite roomTile = new Sprite();
        roomTile.setImage(roomImg);

        // Wall sprite.
        Image wallImg = new Image("Cluedo\\resources\\images\\wall.png");
        Sprite wallTile = new Sprite();
        wallTile.setImage(wallImg);

        // Door sprite.
        Image doorImg = new Image("Cluedo\\resources\\images\\door.png");
        Sprite doorTile = new Sprite();
        doorTile.setImage(doorImg);

        new AnimationTimer() {
            public void handle(long currentTime) {
                gc.clearRect(0,0,canvasWidth, canvasHeight);
                for (List<Tile> row : board.grid.grid) {
                    for (Tile t : row) {
                        String tileType = t.getType();

                        if (tileType.equals("door")) {
                            doorTile.setPosition(t.row * 25, t.column * 25);
                            doorTile.render(gc);
                        } else if (tileType.equals("room")) {
                            roomTile.setPosition(t.row * 25, t.column * 25);
                            roomTile.render(gc);
                        } else if (tileType.equals("wall")) {
                            wallTile.setPosition(t.row * 25, t.column * 25);
                            wallTile.render(gc);
                        } else {
                            floorTile.setPosition(t.row * 25, t.column * 25);
                            floorTile.render(gc);
                        }
                    }
                }
            }
        }.start();

        primaryStage.show();
    }
}
