import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GUITest {

    @Test
    public void testImagesFilePathExists() throws FileNotFoundException {
        String currentDir = Paths.get("").toAbsolutePath().toString();
        Assert.assertTrue(currentDir.endsWith("Cluedo"));

        String imagesDir = Paths.get(currentDir,
                "\\src\\main\\code\\resources\\images")
                .toAbsolutePath().toString();

        String DOOR_IM_PATH = Paths.get(imagesDir,  "door.png")
                .toAbsolutePath().toString();
        String FLOOR_IM_PATH = Paths.get(imagesDir,  "floor.png")
                .toAbsolutePath().toString();
        String ROOM_IM_PATH = Paths.get(imagesDir,  "room.png")
                .toAbsolutePath().toString();
        String WALL_IM_PATH = Paths.get(imagesDir,  "wall.png")
                .toAbsolutePath().toString();
        String DEBUG_IM_PATH = Paths.get(imagesDir,  "debug.png")
                .toAbsolutePath().toString();

        Assert.assertTrue(imagesDir.endsWith("images"));
        Assert.assertTrue(DOOR_IM_PATH.endsWith("door.png"));
        Assert.assertTrue(FLOOR_IM_PATH.endsWith("floor.png"));
        Assert.assertTrue(ROOM_IM_PATH.endsWith("room.png"));
        Assert.assertTrue(WALL_IM_PATH.endsWith("wall.png"));
        Assert.assertTrue(DEBUG_IM_PATH.endsWith("debug.png"));

        InputStream doorIS = new FileInputStream(DOOR_IM_PATH);
        InputStream floorIS = new FileInputStream(FLOOR_IM_PATH);
        InputStream roomIS = new FileInputStream(ROOM_IM_PATH);
        InputStream wallIS = new FileInputStream(WALL_IM_PATH);
        InputStream debugIS = new FileInputStream(DEBUG_IM_PATH);

        Image doorImg = new Image(doorIS);
        Image floorImg = new Image(floorIS);
        Image roomImg = new Image(roomIS);
        Image wallImg = new Image(wallIS);
        Image debugImg = new Image(debugIS);

        Assert.assertNotNull(doorImg);
        Assert.assertNotNull(floorImg);
        Assert.assertNotNull(roomImg);
        Assert.assertNotNull(wallImg);
        Assert.assertNotNull(debugImg);
    }
}
