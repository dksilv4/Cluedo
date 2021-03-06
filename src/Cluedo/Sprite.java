package Cluedo;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite {
    private ImageView imView;

    public Sprite(Image img, double initHeight, double initWidth,
                  double initX, double initY) {
        imView = new ImageView();
        setImage(img);
        setPosition(initX, initY);
        setDims(initWidth, initHeight);
    }

    public void setImage(Image img) {
        imView.setImage(img);
    }

    public void setDims(double newWidth, double newHeight) {
        imView.setFitHeight(newHeight);
        imView.setFitWidth(newWidth);
    }

    public void setPosition(double x, double y) {
        imView.setX(x);
        imView.setY(y);
    }

    public void render(double x, double y) {
        setPosition(x, y);
    }

    public void update(double newX, double newY) {

    }

    public ImageView getImView() {
        return imView;
    }
}
