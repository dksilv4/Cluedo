package code;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents any entities which may be rendered in the game.
 */
public class Sprite {
    private final ImageView imView;

    public Sprite(Image img, double initHeight, double initWidth, double initX,
                  double initY) {
        imView = new ImageView();
        imView.setSmooth(true);
        imView.setCache(true);
        setImage(img);
        imView.setX(initX);
        imView.setY(initY);
        setDims(initWidth, initHeight);
    }

    /**
     * Sets the image of the Sprite.
     *
     * @param img Image to set to
     */
    public void setImage(Image img) {
        imView.setImage(img);
    }

    public void assignClickAction(Action action, Cluedo model, Tile t) {
        switch (action) {
            case Move:
                imView.setOnMouseClicked(event -> {
                    model.movePlayerPiece(t);
                });
                break;
            case UsePassage:
                imView.setOnMouseClicked(event -> {
                    Room currRoom = model.getCurrentPlayersTurn()
                            .getPiece().getLocation().getBelongsTo();
                    model.useSecretPassage(currRoom);
                    model.endTurn();
                });
                break;
            default:

        }
    }

    /**
     * Sets the dimensions of the Sprite.
     *
     * @param newWidth  Sprite width
     * @param newHeight Sprite height
     */
    public void setDims(double newWidth, double newHeight) {
        imView.setFitHeight(newHeight);
        imView.setFitWidth(newWidth);
    }

    /**
     * Renders the Sprite are the corresponding x and y-coordinates.
     *
     * @param x x-coordinate to render at
     * @param y y-coordinate to renter at
     */
    public void render(double x, double y) {
        imView.setX(x);
        imView.setY(y);
    }

    /**
     * Getter for the Sprite's ImageView object.
     *
     * @return The Sprite's ImageView object
     */
    public ImageView getImView() {
        return imView;
    }

    @Override
    public String toString() {
        return "Sprite:"
                + "\n  Pos {" + imView.getX() + ", " + imView.getY() + "}"
                + "\n  Dims {" + imView.getFitHeight() + ", "
                    + imView.getFitWidth() + "}";
    }
}
