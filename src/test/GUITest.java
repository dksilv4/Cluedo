import code.Cluedo;
import code.PlayerPiece;
import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUITest {

    private List<String> tileTypeNames;
    private Cluedo gameModel;
    private List<PlayerPiece> playerPieceList;

    @Before
    public void before() {
        tileTypeNames =
                new ArrayList<>(Arrays.asList("door", "space", "room", "wall"));
        gameModel = new Cluedo();
        gameModel.setUpPlayers();
        gameModel.setUpCards();
        gameModel.setSpawns();
        gameModel.getPlayerTurnOrder();
        playerPieceList = gameModel.getPlayerPieces();
    }

    @Test
    public void testTileImagesAreAvailable() throws FileNotFoundException {
        // Get the current directory and make sure it is the Cluedo project.
        String currentDir = Paths.get("").toAbsolutePath().toString();
        Assert.assertTrue(currentDir.endsWith("Cluedo"));

        // Get the path to images file and make sure it is a valid path.
        Path imgsDirPath = Paths.get(currentDir,
                "\\src\\main\\resources\\images");
        String imagesDir = imgsDirPath.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(imgsDirPath));
        Assert.assertTrue(imagesDir.endsWith("images"));

        // Check all tile images are available.
        for (String tName : tileTypeNames) {
            Path tileImgPathObj = Paths.get(imagesDir,  tName + ".png");
            String imgPath = tileImgPathObj.toAbsolutePath().toString();

            Assert.assertTrue(Files.exists(tileImgPathObj));
            Assert.assertTrue(imgPath.endsWith(tName + ".png"));

            InputStream imgIS = new FileInputStream(imgPath);
            Image tileImg = new Image(imgIS);

            Assert.assertNotNull(tileImg);
        }
    }

    @Test
    public void testPlayerPieceImagesAreAvailable() throws FileNotFoundException {
        // Get the current directory and make sure it is the Cluedo project.
        String currentDir = Paths.get("").toAbsolutePath().toString();
        Assert.assertTrue(currentDir.endsWith("Cluedo"));

        // Get the path to images file and make sure it is a valid path.
        Path imgsDirPath = Paths.get(currentDir,
                "\\src\\main\\resources\\images");
        String imagesDir = imgsDirPath.toAbsolutePath().toString();
        Assert.assertTrue(Files.exists(imgsDirPath));
        Assert.assertTrue(imagesDir.endsWith("images"));

        Assert.assertNotNull(playerPieceList);

        // Check all player piece images are available.
        for (PlayerPiece pp : playerPieceList) {
            String imageName = "playerPiece_" +
                    pp.getName().replaceAll("\\s+","");
            System.out.println(imageName);
            Path ppImgPathObj = Paths.get(imagesDir,  imageName + ".png");
            String imgPath = ppImgPathObj.toAbsolutePath().toString();

            Assert.assertTrue(Files.exists(ppImgPathObj));
            Assert.assertTrue(imgPath.endsWith(imageName + ".png"));

            InputStream imgIS = new FileInputStream(imgPath);
            Image tileImg = new Image(imgIS);

            Assert.assertNotNull(tileImg);
        }
    }
}
