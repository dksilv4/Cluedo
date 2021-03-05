package Cluedo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    private Image image;
    private double positionX;
    private double positionY;
    private double width;
    private double height;

    public Sprite() {
        positionX = 0;
        positionY = 0;
    }

    public void setImage(Image img) {
        image = img;
        width = img.getWidth();
        height = img.getHeight();
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public void update(double newX, double newY) {
        positionX = newX;
        positionY = newY;
    }
}
