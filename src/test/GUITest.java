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
    public void testInitialisingImages() throws FileNotFoundException {
        // Get the current directory and make sure it is the Cluedo project.
        String currentDir = Paths.get("").toAbsolutePath().toString();
        Assert.assertTrue(currentDir.endsWith("Cluedo"));

        // Get the path to images and make sure it is a valid path.
        Path imgsDirPath = Paths.get(currentDir,
                "\\src\\main\\resources\\images");
        String imagesDir = imgsDirPath.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(imgsDirPath));
        Assert.assertTrue(imagesDir.endsWith("images"));


        // Get the paths to all images and make sure they are valid paths.
        Path doorImgPathObj = Paths.get(imagesDir,  "door.png");
        String doorImgPath = doorImgPathObj.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(doorImgPathObj));
        Assert.assertTrue(doorImgPath.endsWith("door.png"));

        Path floorImgPathObj = Paths.get(imagesDir,  "floor.png");
        String floorImgPath = floorImgPathObj.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(floorImgPathObj));
        Assert.assertTrue(floorImgPath.endsWith("floor.png"));

        Path roomImgPathObj = Paths.get(imagesDir,  "room.png");
        String roomImgPath = roomImgPathObj.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(roomImgPathObj));
        Assert.assertTrue(roomImgPath.endsWith("room.png"));

        Path wallImgPathObj = Paths.get(imagesDir,  "wall.png");
        String wallImgPath = wallImgPathObj.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(wallImgPathObj));
        Assert.assertTrue(wallImgPath.endsWith("wall.png"));

        Path debugImgPathObj = Paths.get(imagesDir,  "debug.png");
        String debugImgPath = debugImgPathObj.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(debugImgPathObj));
        Assert.assertTrue(debugImgPath.endsWith("debug.png"));


        InputStream doorIS = new FileInputStream(doorImgPath);
        InputStream floorIS = new FileInputStream(floorImgPath);
        InputStream roomIS = new FileInputStream(roomImgPath);
        InputStream wallIS = new FileInputStream(wallImgPath);
        InputStream debugIS = new FileInputStream(debugImgPath);

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
